package com.github.ztmark.service;

import com.github.ztmark.annotation.LogParam;
import org.springframework.stereotype.Service;

/**
 * Author: Mark
 * Date  : 2017/5/28
 */
@Service
@LogParam
public class TestServiceImpl implements TestService {
    @Override
    public String testMethodOne(String arg) {
        return "Test method " + arg;
    }
}
