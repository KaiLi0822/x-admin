package com.example;

import com.example.common.utils.JwtUtil;
import com.example.sys.entity.User;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtUtilTest {
    @Autowired
    private JwtUtil jwtUtil;

    @Test
    public void testCreateJwt(){
        User user = new User();
        user.setUsername("张三");
        user.setPhone("12344444444");
        String token = jwtUtil.createToken(user);
        System.out.println(token);

    }

    @Test
    public void testParseJwt(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJlMzA1Y2Q2Ni04Mjk0LTRmMjItOGU4Yy1iYTRhYjhiYjE2NjgiLCJzdWIiOiJ7XCJwaG9uZVwiOlwiMTIzNDQ0NDQ0NDRcIixcInVzZXJuYW1lXCI6XCLlvKDkuIlcIn0iLCJpc3MiOiJzeXN0ZW0iLCJpYXQiOjE2OTM1Nzg5NjYsImV4cCI6MTY5MzU4MDc2Nn0.quqgUt6T5kX6jXhncbZagrW4abwGi7LdGhIYc8LyzkA";
        Claims claims = jwtUtil.parseToken(token);
        System.out.println(claims);
    }

    @Test
    public void testParseJwt2(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJlMzA1Y2Q2Ni04Mjk0LTRmMjItOGU4Yy1iYTRhYjhiYjE2NjgiLCJzdWIiOiJ7XCJwaG9uZVwiOlwiMTIzNDQ0NDQ0NDRcIixcInVzZXJuYW1lXCI6XCLlvKDkuIlcIn0iLCJpc3MiOiJzeXN0ZW0iLCJpYXQiOjE2OTM1Nzg5NjYsImV4cCI6MTY5MzU4MDc2Nn0.quqgUt6T5kX6jXhncbZagrW4abwGi7LdGhIYc8LyzkA";
        User user = jwtUtil.parseToken(token, User.class);
        System.out.println(user);
    }
}
