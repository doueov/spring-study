package com.example.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {

    @Bean
    public MyBean helloMyBean() {
        return new MyBean();
    }

    @Bean
    public String myGreatString() {
        return "Great";
    }

    @Bean
    public Person myPerson() {
        Person p = new Person("John", 20);
        p.setSomething("important!");
        return p;
    }
}
