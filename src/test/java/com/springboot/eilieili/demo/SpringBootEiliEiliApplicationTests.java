package com.springboot.eilieili.demo;

import com.springboot.eilieili.demo.util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class SpringBootEiliEiliApplicationTests {

    @Test
    void contextLoads() {

        try {

            String encode = PasswordUtil.Encrypt("1234");
            log.error(encode);
            log.error(PasswordUtil.Decrypt(encode));


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
