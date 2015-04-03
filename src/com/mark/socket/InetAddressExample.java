package com.mark.socket;

import java.net.*;
import java.util.Enumeration;

/**
 * Author: Mark
 * Date  : 2015/3/23
 * Time  : 18:11
 */
public class InetAddressExample {

    public static void main(String[] args) {
        // Get network interface and associated addresses for this host
        try {
            Enumeration<NetworkInterface> interfaceList = NetworkInterface.getNetworkInterfaces();
            if (interfaceList == null) {
                System.out.println("--No interfaces found--");
            } else {
                while (interfaceList.hasMoreElements()) {
                    NetworkInterface iface = interfaceList.nextElement();
                    System.out.println("Interface " + iface.getName() + ":");
                    Enumeration<InetAddress> addrLiest = iface.getInetAddresses();
                    if (!addrLiest.hasMoreElements()) {
                        System.out.println("\t(No addresses for this interface)");
                    }
                    while (addrLiest.hasMoreElements()) {
                        InetAddress address = addrLiest.nextElement();
                        System.out.println("\tAddress "
                                + ((address instanceof Inet4Address ? "(v4)" : (address instanceof Inet6Address ? "(v6)" : "(?)"))));
                        System.out.println(": " + address.getHostAddress());
                    }
                }
            }
        } catch (SocketException e) {
            System.out.println("Error getting network interfaces:" + e.getMessage());
        }

        // Get names(s)/address(es) of hosts given on command line
        for (String host : args) {
            try {
                System.out.println(host + ":");
                InetAddress[] addressesList = InetAddress.getAllByName(host);
                for (InetAddress address : addressesList) {
                    System.out.println("\t" + address.getHostName() + "/" + address.getHostAddress());
                }
            } catch (UnknownHostException e) {
                System.out.println("\tUnable to find address for " + host);
            }
        }
    }

}
