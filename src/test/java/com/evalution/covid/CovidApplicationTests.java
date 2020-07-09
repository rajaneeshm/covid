package com.evalution.covid;


import com.evalution.covid.constant.CaseStatus;
import com.evalution.covid.dto.*;
import com.evalution.covid.entity.CovidCase;
import com.evalution.covid.service.defitition.ICovidCaseService;
import com.evalution.covid.service.defitition.IDailyTotalCaseSummary;
import com.evalution.covid.service.defitition.IStateCaseSummary;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CovidApplicationTests {

    static List<String> states = new ArrayList<String>(Arrays.asList("Delhi", "Telangana", "Andhra Pradesh", "Jammu and Kashmir", "Tamil Nadu", "Punjab", "Rajasthan", "Uttar Pradesh", "Haryana", "Karnataka", "Ladakh", "Maharashtra", "Kerala", "Gujarat", "Chandigarh", "Puducherry", "Chhattisgarh", "Uttarakhand", "Odisha", "West Bengal", "Madhya Pradesh", "Himachal Pradesh", "Bihar", "Manipur", "Mizoram", "Goa", "Andaman and Nicobar Islands", "Jharkhand", "Assam", "Arunachal Pradesh", "Tripura", "Meghalaya"));

    @Autowired
    private ICovidCaseService covidCaseService;
    @Autowired
    private IDailyTotalCaseSummary dailyTotalCaseSummary;
    @Autowired
    private IStateCaseSummary stateCaseSummary;

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void verifyTotals() {
        for (int i = 0; i < 10; i++) {
            int month = getRandomNumber(1, 7);
            int day = getRandomNumber(1, 21);
            int increment = getRandomNumber(1, 6);
            CovidCaseDTO covidCaseDTO = new CovidCaseDTO();
            covidCaseDTO.setDateannounced("2020-" + month + "-" + day);
            covidCaseDTO.setDetectedstate(states.get(getRandomNumber(1, 20)));
            covidCaseDTO.setCurrentstatus(CaseStatus.Hospitalized.name());
            final CovidCase covidCase = covidCaseService.saveNewCase(covidCaseDTO);

            if(i % 6 < getRandomNumber(1, 8)) {
                if(i % 3 == 0)
                    covidCaseDTO.setCurrentstatus(CaseStatus.Recovered.name());
                else
                    covidCaseDTO.setCurrentstatus(CaseStatus.Deceased.name());
            }
            covidCaseDTO.setStatuschangedate("2020-" + month + "-" + day + increment);
            covidCaseDTO.setPatientnumber(covidCase.getPatientnumber());
            covidCaseService.update(covidCaseDTO);
        }

        final TotalCasesDTO totals = dailyTotalCaseSummary.getTotals();
        assertEquals(totals.getTotalCases(), totals.getActiveCases() + totals.getDeceasedCases() + totals.getRecoveredCases());

        final List<TotalStateCasesDTO> summary = stateCaseSummary.getSummary();
        for (TotalStateCasesDTO totalStateCasesDTO : summary) {
            assertEquals(totalStateCasesDTO.getTotalCases(), totalStateCasesDTO.getActiveCases() + totalStateCasesDTO.getDeceasedCases() + totalStateCasesDTO.getRecoveredCases());
        }
    }

    @Test
    public void verifyDailyTotals() {

        Calendar calendar = Calendar.getInstance();
        String state = "Delhi";
        long totalCasesOnGivenDate = 0;
        long stateTotalCasesOnGivenDate = 0;
        long totalCasesOnGivenDateAfterTest = 0;
        long stateTotalCasesOnGivenDateAfterTest = 0;
        calendar.set(2020, calendar.MARCH, 01);
        final List<DailyTotalCasesDTO> summary = dailyTotalCaseSummary.getSummary();
        final List<DailyTotalStateCasesDTO> stateDailySummary = stateCaseSummary.getDailySummary(state);
        for (DailyTotalCasesDTO dailyTotalCasesDTO : summary) {
            if(dailyTotalCasesDTO.getRecordedOn().getDate() == calendar.getTime().getDate() && dailyTotalCasesDTO.getRecordedOn().getMonth() == calendar.getTime().getMonth()) {
                totalCasesOnGivenDate = totalCasesOnGivenDate + dailyTotalCasesDTO.getTotalCases();
            }
        }
        for (DailyTotalStateCasesDTO dailyTotalStateCasesDTO : stateDailySummary) {
            if(dailyTotalStateCasesDTO.getRecordedOn().getDate() == calendar.getTime().getDate() && dailyTotalStateCasesDTO.getRecordedOn().getMonth() == calendar.getTime().getMonth()) {
                stateTotalCasesOnGivenDate = stateTotalCasesOnGivenDate + dailyTotalStateCasesDTO.getTotalCases();
            }
        }
        CovidCaseDTO covidCaseDTO = new CovidCaseDTO();
        covidCaseDTO.setDateannounced("2020-03-01");
        covidCaseDTO.setDetectedstate(state);
        covidCaseDTO.setCurrentstatus(CaseStatus.Hospitalized.name());
        final CovidCase covidCase = covidCaseService.saveNewCase(covidCaseDTO);
        covidCaseDTO.setCurrentstatus(CaseStatus.Recovered.name());
        covidCaseDTO.setStatuschangedate("2020-03-04");
        covidCaseDTO.setPatientnumber(covidCase.getPatientnumber());
        covidCaseService.update(covidCaseDTO);
        final List<DailyTotalCasesDTO> summaryAfterTest = dailyTotalCaseSummary.getSummary();
        final List<DailyTotalStateCasesDTO> stateCaseSummaryDailySummaryAfterTest = stateCaseSummary.getDailySummary(state);
        for (DailyTotalCasesDTO dailyTotalCasesDTO : summaryAfterTest) {
            if(dailyTotalCasesDTO.getRecordedOn().getDate() == calendar.getTime().getDate() && dailyTotalCasesDTO.getRecordedOn().getMonth() == calendar.getTime().getMonth()) {
                totalCasesOnGivenDateAfterTest = totalCasesOnGivenDateAfterTest + dailyTotalCasesDTO.getTotalCases();
            }
        }
        for (DailyTotalStateCasesDTO dailyTotalStateCasesDTO : stateCaseSummaryDailySummaryAfterTest) {
            if(dailyTotalStateCasesDTO.getRecordedOn().getDate() == calendar.getTime().getDate() && dailyTotalStateCasesDTO.getRecordedOn().getMonth() == calendar.getTime().getMonth()) {
                stateTotalCasesOnGivenDateAfterTest = stateTotalCasesOnGivenDateAfterTest + dailyTotalStateCasesDTO.getTotalCases();
            }
        }
        assertEquals(totalCasesOnGivenDate + 1, totalCasesOnGivenDateAfterTest);
        assertEquals(stateTotalCasesOnGivenDate + 1, stateTotalCasesOnGivenDateAfterTest);
    }


}
