package com.atguigu.activemq.optional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OptionalDemo {

    public static void main(String[] args) {
        // lambda表达式创建线程
        Thread thread = new Thread(() -> System.out.println("lambda demo......"));
        thread.start();
        System.out.println("分割线-------------------------------------------------------------------");
        System.out.println();


        // lambda表达式遍历Map集合
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("a1", "b1");
        hashMap.put("a2", "b2");
        hashMap.forEach((s1, s2) -> System.out.println(s1 + " : " + s2));
        System.out.println("分割线-------------------------------------------------------------------");
        System.out.println();


        // lambda表达式遍历List
        List<String> list = new ArrayList<>();
        list.add("Java");
        list.add("3y");
        list.add("光头");
        list.add("帅哥");
        list.removeIf(s -> s.equals("3y"));
        list.forEach(s -> System.out.println(s));






    }
}
