package com.github.ztmark.service;

import com.github.ztmark.domain.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Author: Mark
 * Date  : 2017/5/28
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoServiceTest {

    @Autowired
    private DemoService demoService;

    @Test
    public void greeting() throws Exception {
        Person p = new Person();
        p.setAge(23);
        p.setName("DDDDDD");
        System.out.println(demoService.greeting(p));
    }

    @Test
    public void greeting1() throws Exception {
        System.out.println(demoService.greeting("mark"));
    }

}