package com.depanker.ticker.services.impl;

import com.depanker.ticker.beans.Ticker;
import com.depanker.ticker.beans.TickerCase;
import com.depanker.ticker.repos.TickerRepository;
import com.depanker.ticker.services.TickRecorder;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class TickRecorderImplTest {

    @MockBean
    TickerRepository tickerRepository;

    @Autowired
    TickRecorder tickRecorderImpl;
    @Captor
    ArgumentCaptor<String> fileNameCaptor;
    @Captor
    ArgumentCaptor<List<Ticker>> tickerCaptor;

    @Test
    void save() throws IOException {
        tickRecorderImpl.save(new TickerCase(Arrays.asList(new Ticker(1l,1d,1d,
                "A",
                "b")), 1l));
        verify(tickerRepository, times(1)).save(fileNameCaptor.capture(), tickerCaptor.capture());
        assertEquals("b", fileNameCaptor.getValue(), "Matching file name");
        assertEquals(1, tickerCaptor.getValue().size(), "Matching ticker list");

    }

    @Test
    void noSaveCalledWhenThereIsNoClosePrice() throws IOException {
        tickRecorderImpl.save(new TickerCase(Arrays.asList(new Ticker(1l,1d,null,
                "A",
                "b")), 1l));
        verify(tickerRepository, times(0)).save(any(), any());

    }
}