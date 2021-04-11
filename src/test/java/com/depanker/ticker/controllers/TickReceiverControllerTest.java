package com.depanker.ticker.controllers;

import com.depanker.ticker.BaseTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class TickReceiverControllerTest extends BaseTest {

    @Test
    void submitTicks() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/submit-tick")
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkZXBhbmtlciJ9")
                .content(getMockTick()))
                .andExpect(status().isAccepted());

    }

    @Test
    void submitTicksWithInvalidInput() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/submit-tick")
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkZXBhbmtlciJ9")
                .content("DUMMY-DATA"))
                .andExpect(status().isBadRequest());

    }

    @Test
    void submitTicksWithInvalidFieldValues() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/submit-tick")
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkZXBhbmtlciJ9.L3A9pzvMXgpOgktubpFO6RzHOw-CTDb-PDna019UFG9sFvwoBn5Pm9zNUza6vIgiCZWHsIZcyspr_jKT0rPnHw")
                .content(getMockTickWithMissingRequiredFields()))
                .andExpect(status().isBadRequest());

    }

    private String getMockTick() {
        return "TIMESTAMP=123123|PRICE=5.24|CLOSE_PRICE=|CURRENCY=EUR|RIC=AAPL.OQ\n" +
                "TIMESTAMP=4564564|PRICE=5.24|CLOSE_PRICE=|CURRENCY=EUR|RIC=IBM.N \n" +
                "TIMESTAMP=6786754|PRICE=|CLOSE_PRICE=7.5|CURRENCY=EUR|RIC=AAPL.OQ";
    }

    private String getMockTickWithMissingRequiredFields() {
        return "TIMESTAMP=|PRICE=5.24|CLOSE_PRICE=|CURRENCY=|RIC=\n" +
                "TIMESTAMP=4564564|PRICE=5.24|CLOSE_PRICE=|CURRENCY=EUR|RIC=IBM.N \n" +
                "TIMESTAMP=6786754|PRICE=|CLOSE_PRICE=7.5|CURRENCY=EUR|RIC=AAPL.OQ";
    }



}