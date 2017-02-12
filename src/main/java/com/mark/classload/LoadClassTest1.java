package com.mark.classload;

/**
 * Author: Mark
 * Date  : 2017/2/12
 */
public class LoadClassTest1 {

    public static void main(String[] args) {
        // 如果在一个 Web 环境中，这里可能会出现 CastException
        final ClassloadDomain instance = (ClassloadDomain) ClassloadDomainFactory.instance();
        instance.sayHi();
    }


}
