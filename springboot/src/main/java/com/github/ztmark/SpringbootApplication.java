package com.github.ztmark;

import com.github.ztmark.domain.Person;
import com.github.ztmark.service.DemoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.aop.PointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class SpringbootApplication implements CommandLineRunner {

    @Autowired
    public SpringbootApplication(DemoService demoService) {
        this.demoService = demoService;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }

    private final DemoService demoService;


    @Override
    public void run(String... args) throws Exception {
        System.out.println(demoService.greeting("Mark"));
        Person person = new Person();
        person.setName("Jim");
        person.setAge(23);
        System.out.println(demoService.greeting(person));
    }

    @ApiOperation(value = "return a person", response = Person.class)
    @RequestMapping("/person")
    public Person person(@RequestParam("name") String name) {
        Person person = new Person();
        person.setName(name);
        person.setAge(23);
        System.out.println(demoService.greeting(person));
        return person;
    }

    @RequestMapping("/some/bad")
    public String bad() {
        return "Oops";
    }
}

