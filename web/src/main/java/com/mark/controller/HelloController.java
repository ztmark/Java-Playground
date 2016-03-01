package com.mark.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: Mark
 * Date  : 16/3/1.
 */

@RestController
public class HelloController {

    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public String greeting() {
        return "Hi there";
    }

}
