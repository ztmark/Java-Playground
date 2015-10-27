package com.mark.fakemockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Author: Mark
 * Date  : 15/10/27.
 */
public class MethodParams {

    private List<Object> params = new ArrayList<>();
    private int hash = 0;

    public MethodParams(List<Object> params) {
        this.params.addAll(params);
    }

    public MethodParams(Object... params) {
        this(Arrays.asList(params));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MethodParams methodParams1 = (MethodParams) o;

        return params != null ? params.equals(methodParams1.params) : methodParams1.params == null;

    }

    @Override
    public int hashCode() {
        return params != null ? params.hashCode() : 0;
    }
}
