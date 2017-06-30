package com.mark.controller;

import com.mark.test.TestFactory;
import com.mark.test.TestUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: Mark
 * Date  : 16/3/1.
 */

@Controller
@Api(value = "Hello Controller")
public class HelloController {

    @Value("${test}")
    public String test;

    @ApiOperation(value = "greeting 接口", notes = "just a greeting")
    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public String greeting() {
        final TestUtil o = (TestUtil) TestFactory.newObject();
        o.sayHi();
        System.out.println(test);
        return "greeting";
    }

}
