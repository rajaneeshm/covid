package com.evalution.covid.controller;

import com.evalution.covid.dto.DailyTotalCasesDTO;
import com.evalution.covid.dto.DailyTotalStateCasesDTO;
import com.evalution.covid.service.defitition.IDailyTotalCaseSummary;
import com.evalution.covid.service.defitition.IStateCaseSummary;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class GetTotalGraphData {

    @Autowired
    private IDailyTotalCaseSummary dailyTotalCaseSummary;
    @Autowired
    private IStateCaseSummary stateCaseSummary;

    @GetMapping(value = "/getTotalCasesByDay")
    @ResponseBody
    public String getTotalCasesByDay() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        final List<DailyTotalCasesDTO> dailyTotalCasesDTOS = dailyTotalCaseSummary.getSummary();
        return objectMapper.writeValueAsString(dailyTotalCasesDTOS);
    }

    @GetMapping(value = "/getTotalStateCasesByDay/{state}")
    @ResponseBody
    public String getTotalStateCasesByDay(@PathVariable("state") String state) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        final List<DailyTotalStateCasesDTO> dailySummary = stateCaseSummary.getDailySummary(state);
        return objectMapper.writeValueAsString(dailySummary);
    }


}
