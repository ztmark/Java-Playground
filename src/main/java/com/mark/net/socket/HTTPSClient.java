package com.mark.net.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.security.cert.X509Certificate;

/**
 * Author: Mark
 * Date  : 2017/4/29
 */
public class HTTPSClient {

    public static void main(String[] args) {
        int port = 443;
        String host = "www.usps.com";
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        SSLSocket socket = null;
        try {
            socket = (SSLSocket) factory.createSocket(host, port);
            final String[] supportedCipherSuites = socket.getSupportedCipherSuites();
            socket.setEnabledCipherSuites(supportedCipherSuites);
            for (String supportedCipherSuite : supportedCipherSuites) {
                System.out.println(supportedCipherSuite);
            }
            System.out.println();

            socket.addHandshakeCompletedListener(handshakeCompletedEvent -> {
                final String cipherSuite = handshakeCompletedEvent.getCipherSuite();
                System.out.println("-----");
                System.out.println(cipherSuite);
                try {
                    final X509Certificate[] peerCertificateChain = handshakeCompletedEvent.getPeerCertificateChain();
                    for (X509Certificate x509Certificate : peerCertificateChain) {
                        System.out.println(x509Certificate);
                    }
                } catch (SSLPeerUnverifiedException e) {
                    e.printStackTrace();
                }

                System.out.println("-----");
            });

            Writer out = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);

            out.write("GET / HTTP/1.1\r\n");
            out.write("Host: " + host + "\r\n");
            out.write("\r\n");
            out.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String s;
            while (!(s = in.readLine()).equals("")) {
                System.out.println(s);
            }
            System.out.println();

            String contentLength = in.readLine();
            int length = Integer.MAX_VALUE;
            try {
                length = Integer.parseInt(contentLength.trim(), 16);
            } catch (NumberFormatException ignored) {

            }
            System.out.println(contentLength);
            System.out.println(length);

            int c;
            int i = 0;
            while ((c = in.read()) != -1 && i++ < length) {
                System.out.write(c);
            }

            System.out.println();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
