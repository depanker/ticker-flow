package com.depanker.ticker.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.Valid;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TickerCase implements Comparable<Long> {
    @Valid
    List<Ticker> tickers;
    @JsonIgnore
    Long version;

    @Override
    public int compareTo(Long o) {
        return version.compareTo(o);
    }
}
