package Impl;

import API.Hello;

import java.util.HashMap;

//hello + 1
public class HelloImpl_1 implements Hello {
    public String sayHello(String name){
        return "Hello ! "+ name;
    }
}
