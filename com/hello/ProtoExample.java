package com.hello;
import com.hello.Hello.hellorequest;

public class ProtoExample {
    public static void main(String[] args) {
        hellorequest.Builder request= hellorequest.newBuilder();

        // Print the messa
        request.setName("hari");
        System.out.println(request.getName());
    }
}
