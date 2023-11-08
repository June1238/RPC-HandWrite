package Impl;

import API.Hello;

//hello + 2
public class HelloImpl_2 implements Hello {
    public String sayHello(String name){
        return "Hello "+name+" !";
    }
}
