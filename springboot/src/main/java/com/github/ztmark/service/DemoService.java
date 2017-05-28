package com.github.ztmark.service;

import com.github.ztmark.Mine;
import com.github.ztmark.annotation.LogParam;
import com.github.ztmark.domain.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * Author: Mark
 * Date  : 2017/5/27
 */
@Service
public class DemoService {

    private static final Logger logger = LoggerFactory.getLogger(DemoService.class);

//    @Mine
    public String greeting(String name) {
//        logger.info("greeting method {}", name);
        return "Hello " + name;
    }

    @LogParam
    public String greeting(Person person) {
//        logger.info("greeting method {}", person.toString());
        return "Hello " + person.getName();
    }

}
