package com.example.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class DiApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(DiApplication.class, args);

		String[] beanNames = context.getBeanDefinitionNames();
		for (String beanName : beanNames) {
			// 단, 기본적으로 스프링에서 제공하는 Bean들은 가급적 필터링해서 보여주기 (전부 다 걸러지진 않음)
			if(!beanName.startsWith("org.") && !beanName.startsWith("spring.")) {
				System.out.println(beanName);
			}
		}

		// Person p = (Person) context.getBean("myPerson");
		// p.sayName();

		MyCalculatorService myCalculatorService = (MyCalculatorService) context.getBean("myCalculatorService");
		myCalculatorService.calcAdd(3, 4);

		MyDatabaseService myDatabaseService = (MyDatabaseService) context.getBean("myDatabaseService");
		System.out.println(myDatabaseService.simpleRepository instanceof SimpleCrudRepositoryImpl);
		System.out.println(myDatabaseService.complexRepository instanceof ComplexCrudRepositoryImpl);

//		MyCalculatorService myCalculatorService = (MyCalculatorService) context.getBean("myCalculatorService");
//		System.out.println(myCalculatorService.calcAdd(3, 4));
	}
}

