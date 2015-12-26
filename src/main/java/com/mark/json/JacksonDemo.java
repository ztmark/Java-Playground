package com.mark.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Author: Mark
 * Date  : 15/12/26.
 */
public class JacksonDemo {

    public static void main(String[] args) throws IOException {
//        demo1();
        List<Person> personList = Arrays.asList(new Person("mark", 23), new Person("jim", 22), new Person("john", 30));
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(personList));
        String ss = "[{\"name\":\"mark\",\"age\":23},{\"name\":\"jim\",\"age\":22},{\"name\":\"john\",\"age\":30}]";
        List<Person> persons = mapper.readValue(ss, new TypeReference<List<Person>>() {});
        System.out.println(persons);
    }

    private static void demo1() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Person person = new Person("mark", 23);
        System.out.println(mapper.writeValueAsString(person));
        String json = "{\"name\":\"mark\", \"age\":23}";
        Person p = mapper.readValue(json, Person.class);
        System.out.println(p);
    }

}
