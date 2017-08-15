package com.github.ztmark;

import com.github.ztmark.domain.Person;
import com.github.ztmark.service.AnotherPropertyService;
import com.github.ztmark.service.DataGeneratorService;
import com.github.ztmark.service.DemoService;
import com.github.ztmark.service.PropertyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@RestController
@SpringBootApplication
@ImportResource("classpath*:/*.xml")
public class SpringbootApplication implements CommandLineRunner {

    @Autowired
    public SpringbootApplication(DemoService demoService) {
        this.demoService = demoService;
    }

    public static void main(String[] args) {
        final SpringApplication springApplication = new SpringApplication(SpringbootApplication.class);
//        springApplication.setSources(Sets.newHashSet("classpath*:/spring.xml"));
        springApplication.run(args);
//        SpringApplication.run(SpringbootApplication.class, args);
    }

    private final DemoService demoService;

    private PropertyService propertyService;

    private AnotherPropertyService anotherPropertyService;

    private DataGeneratorService generatorService;

    @Autowired
    public void setAnotherPropertyService(AnotherPropertyService anotherPropertyService) {
        this.anotherPropertyService = anotherPropertyService;
    }

    @Autowired
    public void setPropertyService(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @Autowired
    public void setGeneratorService(DataGeneratorService generatorService) {
        this.generatorService = generatorService;
    }

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.any())
                .build();

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Mark's spring boot api")
                .contact(new Contact("Mark", "https://github.com/ztmark", "shuyumark@email.com"))
                .description("just a test")
                .version("1.0.0")
                .build();
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(demoService.greeting("Mark"));
        Person person = new Person();
        person.setName("Jim");
        person.setAge(23);
        System.out.println(demoService.greeting(person));
        System.out.println(propertyService.getValue());
        System.out.println(anotherPropertyService.getRemote());
    }

    @ApiOperation(value = "return a person", response = Person.class)
    @RequestMapping("/person")
    public Person person(@RequestParam("name") String name) {
        Person person = new Person();
        person.setName(name);
        person.setAge(23);
        System.out.println(demoService.greeting(person));
        generatorService.doSomething(person.toString());
        return person;
    }

    @RequestMapping("/some/bad")
    public String bad() {
        return "Oops";
    }
}

