/*
public class RetroApplication {
    public static void main(String[] args) throws Exception {
        User user = new User();
        user.setName("Manish Seller");
        user.setEmail("manishrathoreo273@gmail.com");
        user.setPhone("8827304100");
        user.setPassword("manish");
        user.setUserType("seller");

        UserDAO dao = new UserDAO();
        dao.addUser(user);

        System.out.println("User inserted successfully!");
    }
*/
package com.thinking.machines.retro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RetroApplication {
    public static void main(String[] args) {
        SpringApplication.run(RetroApplication.class, args);
    }


}
