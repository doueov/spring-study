package com.example.di;

import org.springframework.stereotype.Component;

@Component
public class Person {
    private String name;
    private int age;

    // 기본 생성자(no-args constructor)를 정의해야 함을 주의!
    // 생략하면 에러남! 왜냐하면 Bean을 생성할 때 기본 선택지는 no-args 생성자를 호출하기 때문!
    // (단, 생성자 주입을 쓸 경우에는 예외!)
    public Person() {
        System.out.println("Person() called!");
    }

    // 이건 호출 안 함, 할 수가 없음!
    // 이유는? Spring 입장에서 이 객체에 어떤 정보를 넣어줘야 할 지 모르기 때문! (어떤 name 값?, age 값?)
    public Person(String name, int age) {
        System.out.println("Person(String name, int age) called!");
        this.name = name;
        this.age = age;
    }

    public void sayName() {
        System.out.println(this.name);
    }

    public void setSomething(String important) {
        // do some jobs...
    }
}