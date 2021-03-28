package com.depanker.ticker.controllers;

import com.depanker.ticker.BaseTest;
import com.depanker.ticker.security.bean.TickerUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class RegisterUserControllerTest extends BaseTest {

    @Test
    void registerUser() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/register-user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getString(new TickerUser("depanker", "depanker"))))
                .andExpect(status().isOk());

    }

    @Test
    void registerInvalidUser() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/register-user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getString(new TickerUser("", ""))))
                .andExpect(status().isBadRequest());
    }

}