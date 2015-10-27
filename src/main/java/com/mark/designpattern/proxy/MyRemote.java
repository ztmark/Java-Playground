package com.mark.designpattern.proxy;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Author: Mark
 * Date  : 2015/3/30
 * Time  : 15:47
 */
public interface MyRemote extends Remote {

    public String sayHello() throws RemoteException;

}
