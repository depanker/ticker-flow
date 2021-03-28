package com.depanker.ticker.config;

import com.depanker.ticker.beans.Ticker;
import com.depanker.ticker.parsers.TickRequestBodyArgumentResolver;
import com.depanker.ticker.parsers.TickerParser;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {


    public TickRequestBodyArgumentResolver customBodyArgumentResolver() {
        return new TickRequestBodyArgumentResolver(tickerParser());
    }

    public TickerParser tickerParser() {
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = csvMapper.typedSchemaFor(Ticker.class).withoutHeader();
        return new TickerParser(csvMapper, csvSchema);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(customBodyArgumentResolver());
    }

}
