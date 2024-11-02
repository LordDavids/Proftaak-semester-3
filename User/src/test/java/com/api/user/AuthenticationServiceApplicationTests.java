package com.api.user;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;


@TestPropertySource(locations = "classpath:applicationTest.properties")
@SpringBootTest ()
class AuthenticationServiceApplicationTests {

    @Test
    void contextLoads() {
    }

}

