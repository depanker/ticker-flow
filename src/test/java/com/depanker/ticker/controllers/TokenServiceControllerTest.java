package com.depanker.ticker.controllers;

import com.depanker.ticker.BaseTest;
import com.depanker.ticker.security.bean.TickerUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class TokenServiceControllerTest extends BaseTest {
    @Test
    public void fethcToken() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/token")
                .content(getString(new TickerUser("depanker", "depanker"))))
                .andExpect(status().isAccepted());

    }
}
