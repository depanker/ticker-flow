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
class TickRequesterTest extends BaseTest {

    @Test
    void getData() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/lookup")
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkZXBhbmtlciJ9")
                .queryParam("query", "select * from AAPL.OQ where close_price !=null" +
                        " order by timestamp desc"))
                .andExpect(status().isOk());
    }

    @Test
    void getDataWhenRicFileDoesNotExist() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/lookup")
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkZXBhbmtlciJ9")
                .queryParam("query", "select * from ABCD where close_price !=null" +
                        " order by timestamp desc"))
                .andExpect(status().isNoContent());
    }
    @Test
    void getDataWithBlankQuery() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/lookup")
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkZXBhbmtlciJ9")
                .queryParam("query", ""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getDataWithMalformedQuery() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/lookup")
                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkZXBhbmtlciJ9")
                .queryParam("query", "asdfasdfsad"))
                .andExpect(status().isBadRequest());
    }
}