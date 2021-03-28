package com.depanker.ticker.security.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TickerUser {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
