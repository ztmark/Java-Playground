package com.mark.pkgscan;

import java.util.List;

/**
 * @author Mark
 * @date 2017/10/26
 */
public interface PackageScanner {

    /**
     * Scanning specified package then return a class list of the after the scan.
     */
    List<Class<?>> scan(String packageName);

    /**
     * Scanning specified package then invoke callback and
     * return a class list of the after the scan.
     */
    List<Class<?>> scan(String packageName, ScannedClassHandler handler);

}
