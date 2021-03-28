package com.depanker.ticker.parsers;

import com.depanker.ticker.beans.Ticker;
import com.depanker.ticker.beans.TickerCase;
import com.depanker.ticker.exceptions.InvalidTickExceptions;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.text.ParseException;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * This class will parse
 * Ticker string to pojo
 */
@RequiredArgsConstructor
public class TickerParser {
    public static final Pattern PATTERN = Pattern.compile("(\\w+|_)(\\=)(\\.*)");
    private final CsvMapper csvMapper;
    private final CsvSchema csvSchema;

    @SneakyThrows
    public TickerCase parse(String message, Locale locale) throws ParseException {
        if (message == null || message.isBlank()) {
            throw new InvalidTickExceptions("Not a valid tick " + message);
        }
        String cleanString = PATTERN.matcher(message).replaceAll("$3");
        MappingIterator<Ticker> it = csvMapper.readerFor(Ticker.class)
                .with(csvSchema.withColumnSeparator('|'))
                .readValues(cleanString);
        Iterable<Ticker> iterable = () -> it;
        return new TickerCase(StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList()), System.currentTimeMillis());
    }

    public String print(TickerCase tickerList, Locale locale) {
        return tickerList.toString();
    }
}
