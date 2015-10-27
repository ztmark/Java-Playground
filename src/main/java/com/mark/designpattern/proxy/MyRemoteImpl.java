package com.mark.designpattern.proxy;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Author: Mark
 * Date  : 2015/3/30
 * Time  : 15:51
 */
public class MyRemoteImpl extends UnicastRemoteObject implements MyRemote {

    // UnicastRemoteObject的构造器会抛出RemoteException
    public MyRemoteImpl() throws RemoteException {
    }

    @Override
    public String sayHello() {
        return "Server says, 'Hey'";
    }

    public static void main(String[] args) {
        try {
            MyRemote service = new MyRemoteImpl();
            Naming.rebind("RemoteHello", service);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
