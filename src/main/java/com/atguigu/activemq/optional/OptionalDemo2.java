package com.atguigu.activemq.optional;

import java.net.UnknownServiceException;
import java.util.Optional;

public class OptionalDemo2 {

    public static void main(String[] args) {

        User user = User.builder()
                .id(1)
                .age(new Short("2"))
                .name("tony")
                .build();
        System.out.println(user);
        System.out.println("分割线-------------------------------------------------------------------");
        System.out.println();

        User user1 = null;

        // Optional.of 方法传入的对象不可以为null，否则会抛出空指针异常
        // Optional<User> op1 = Optional.of(user1);  // 空指针异常

        // Optional.ofNullable 方法传入的对象可以为null，如果是null则返回一个没有装在对象的Optional容器
        Optional<User> op2 = Optional.ofNullable(user1);

        Optional<User> op3 = Optional.ofNullable(user);
        System.out.println(op3.get());
        System.out.println(op3.isPresent());
        System.out.println(op3.orElse(user1));
        System.out.println(op2.orElse(user));

        // user1为空返回orElse中的新对象
        System.out.println(Optional.ofNullable(user1).orElse(User.builder().id(23).build()));
        System.out.println(Optional.ofNullable(user1).orElseGet(() -> User.builder().id(21).build()));

        System.out.println("分割线-------------------------------------------------------------------");
        System.out.println();

        System.out.println(user);
        // 如果对象存在，并且符合过滤条件，则返回装在对象的Optional容器，否则会返回一个空的Optional容器
        System.out.println(Optional.ofNullable(user).filter((s -> "Tony".equals(s.getName()))));
        System.out.println(Optional.ofNullable(user).filter((s -> "Tony1".equals(s.getName()))));

        // 如果容器中的user对象不为空，则对其执行调用mapping函数得到返回值，然后创建包含mapping函数返回值的Optional，否则返回空的Optional
        System.out.println(Optional.ofNullable(user).map(s -> s.getId()).orElse(99));

        //  Optional使用
        System.out.println(Optional.ofNullable(user).filter(s -> s.getId() == 1).map(s -> s.getName()).map(s -> s.toUpperCase()).orElse("wawa"));
        System.out.println(Optional.ofNullable(user1).filter(s -> s.getId() == 1).map(s -> s.getName()).map(s -> s.toUpperCase()).orElse("wawa"));
        System.out.println(Optional.ofNullable(user));
    }
}
