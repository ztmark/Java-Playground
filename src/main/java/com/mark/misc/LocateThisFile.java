package com.mark.misc;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.security.ProtectionDomain;

/**
 * Author: Mark
 * Date  : 2017/6/7
 */
public class LocateThisFile {

    public static void main(String[] args) throws URISyntaxException {
        final ProtectionDomain protectionDomain = LocateThisFile.class.getProtectionDomain();
        final CodeSource codeSource = protectionDomain.getCodeSource();
        final URI uri = codeSource.getLocation().toURI();
        final String path = uri.getSchemeSpecificPart();
        System.out.println(path);
        final File file = new File(path);
        if (file.isDirectory()) {
            System.out.println(file);
        }
    }


}
