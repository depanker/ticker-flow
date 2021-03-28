package com.depanker.ticker.controllers;

import com.depanker.ticker.beans.TickerCase;
import com.depanker.ticker.events.producer.TickerEventProducer;
import com.depanker.ticker.parsers.TickRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Validated
@RequiredArgsConstructor
public class TickReceiverController {

    private final TickerEventProducer tickerEventProducerImpl;

    @PostMapping(value = "/")
    public  TickerCase index(@Valid @TickRequestBody TickerCase tickers) {
        tickerEventProducerImpl.publishEvent(tickers);
        return tickers;
    }
}
