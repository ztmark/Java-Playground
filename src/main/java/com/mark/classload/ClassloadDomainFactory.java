package com.mark.classload;

/**
 * Author: Mark
 * Date  : 2017/2/12
 */
public class ClassloadDomainFactory {


    public static Object instance() {
        return new ClassloadDomain();
    }

}
