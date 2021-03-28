package com.depanker.ticker.controllers;

import com.depanker.ticker.beans.Ticker;
import com.depanker.ticker.services.DataLookUp;
import lombok.RequiredArgsConstructor;
import net.sf.jsqlparser.JSQLParserException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TickRequester {
    private final DataLookUp dataLookUp;

    @GetMapping("/lookup")
    public List<Ticker> getData(@NotBlank @RequestParam("query") String query) throws JSQLParserException {
        return dataLookUp.getData(query).getTickers();
    }

}
