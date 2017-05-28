package com.github.ztmark.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Author: Mark
 * Date  : 2017/5/28
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestServiceImplTest {

    @Autowired
    private TestService testService;

    @Test
    public void testMethodOne() throws Exception {
        System.out.println(testService.testMethodOne("hello there"));
    }

}