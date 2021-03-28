package com.depanker.ticker.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonPropertyOrder(value = { "timestamp", "price",  "closePrice", "currency", "ric"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Ticker {
    @NotNull(message = "Timestamp cannot be null")
    @JsonProperty("TIMESTAMP")
    private Long timestamp;
    @JsonProperty("PRICE")
    private Double price;
    @JsonProperty("CLOSE_PRICE")
    private Double closePrice;
    @NotBlank
    @JsonProperty("CURRENCY")
    private String currency;
    @NotBlank
    @JsonProperty("RIC")
    private String ric;
}
