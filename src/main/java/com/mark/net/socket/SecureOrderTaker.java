package com.mark.net.socket;

import java.io.Console;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.Scanner;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;

/**
 * Author: Mark
 * Date  : 2017/4/30
 */
public class SecureOrderTaker {

    private final static int PORT = 7000;
    private final static String algorithm = "SSL";

    public static void main(String[] args) {
        try {
            SSLContext context = SSLContext.getInstance(algorithm);

            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");

            KeyStore ks = KeyStore.getInstance("JKS");

            Scanner scanner = new Scanner(System.in);
            char[] password = scanner.nextLine().toCharArray();
            ks.load(new FileInputStream("jnp4e.keys"), password);
            kmf.init(ks, password);
            context.init(kmf.getKeyManagers(), null, null);

            Arrays.fill(password, '0');

            SSLServerSocketFactory factory = context.getServerSocketFactory();

            SSLServerSocket server = (SSLServerSocket) factory.createServerSocket(PORT);

            String[] supported = server.getSupportedCipherSuites();
            String[] anonCipherSuitesSupported = new String[supported.length];
            int numAnonCipherSuiteSupported = 0;
            for (String aSupported : supported) {
                if (aSupported.indexOf("_anon_") > 0) {
                    anonCipherSuitesSupported[numAnonCipherSuiteSupported++] = aSupported;
                }
            }

            String[] oldEnabled = server.getEnabledCipherSuites();
            String[] newEnabled = new String[oldEnabled.length + numAnonCipherSuiteSupported];
            System.arraycopy(oldEnabled, 0, newEnabled, 0, oldEnabled.length);
            System.arraycopy(anonCipherSuitesSupported, 0, newEnabled, oldEnabled.length, numAnonCipherSuiteSupported);

            server.setEnabledCipherSuites(newEnabled);

            while (true) {
                try (Socket theConnection = server.accept()) {
                    InputStream in = theConnection.getInputStream();
                    int c;
                    while ((c = in.read()) != -1) {
                        System.out.write(c);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (NoSuchAlgorithmException | KeyStoreException | CertificateException | IOException | KeyManagementException | UnrecoverableKeyException e) {
            e.printStackTrace();
        }
    }

}
