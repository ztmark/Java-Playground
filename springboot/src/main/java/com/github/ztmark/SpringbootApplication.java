package com.github.ztmark;

import java.util.HashMap;
import java.util.Map;

import com.github.ztmark.domain.Person;
import com.github.ztmark.service.AnotherPropertyService;
import com.github.ztmark.service.DataGeneratorService;
import com.github.ztmark.service.DemoService;
import com.github.ztmark.service.PropertyService;
import io.swagger.annotations.ApiOperation;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
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

@EnableKafka
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

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapservers;

    @Bean
    public Map<String, Object> consumerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapservers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "spring-boot");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        return props;
    }


    @Bean
    ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }


    //=================== multiple group id ==================

    @Bean
    public ConsumerFactory<String, String> consumerFactoryG1() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigG1());
    }

    @Bean
    public Map<String, Object> consumerConfigG1() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapservers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "spring");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        return props;
    }


    @Bean
    ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactoryG1() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryG1());
        return factory;
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

