package com.evalution.covid.service.implementation;

import com.evalution.covid.cache.DailyStateTotalCasesCache;
import com.evalution.covid.cache.TotalStateCasesCache;
import com.evalution.covid.dto.DailyTotalStateCasesDTO;
import com.evalution.covid.dto.TotalStateCasesDTO;
import com.evalution.covid.entity.DailyTotalStateCases;
import com.evalution.covid.entity.TotalStateCases;
import com.evalution.covid.repository.DailyTotalStateCasesRepository;
import com.evalution.covid.repository.TotalStateCasesRepository;
import com.evalution.covid.service.defitition.IStateCaseSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("stateCaseSummary")
public class StateCaseSummaryImpl implements IStateCaseSummary {


    @Autowired
    private DailyTotalStateCasesRepository dailyTotalStateCasesRepository;
    @Autowired
    private TotalStateCasesRepository totalStateCasesRepository;


    @Autowired
    private TotalStateCasesCache totalStateCasesCache;

    @Autowired
    private DailyStateTotalCasesCache dailyStateTotalCasesCache;


    @Override
    public List<TotalStateCasesDTO> getSummary() {
        List<TotalStateCasesDTO> totalStateCasesDTOS = new ArrayList<>();
        final List<String> distinctByState = totalStateCasesRepository.findDistinctByState();
        for (String state : distinctByState) {
            totalStateCasesDTOS.add(getStateTotal(state));
        }
        return totalStateCasesDTOS;
    }

    @Override
    public List<DailyTotalStateCasesDTO> getDailySummary(String state) {
        List<DailyTotalStateCasesDTO> dtos = new ArrayList<>();
        final Optional<DailyTotalStateCases> optional = dailyTotalStateCasesRepository.findTopByOrderById();
        if(optional.isPresent()) {
            final DailyTotalStateCases dailyTotalStateCases = optional.get();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dailyTotalStateCases.getRecordedOn());
            Date beginDate = calendar.getTime();
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            Date endDate = calendar.getTime();
            Date current = beginDate;
            int i = 0;
            while (current.before(endDate)) {
                DailyTotalStateCasesDTO dailyTotalStateCasesDTO = getStateTotalByDate(state, current);
                if(dailyTotalStateCasesDTO == null) {
                    dailyTotalStateCasesDTO = new DailyTotalStateCasesDTO();
                    dailyTotalStateCasesDTO.setRecordedOn(current);
                }

                if(dtos.size() > 0) {
                    final DailyTotalStateCasesDTO beforeDay = dtos.get(i);
                    dailyTotalStateCasesDTO.setActiveCasesCumulative(dailyTotalStateCasesDTO.getActiveCases() + beforeDay.getActiveCasesCumulative());
                    dailyTotalStateCasesDTO.setTotalCasesCumulative(dailyTotalStateCasesDTO.getTotalCases() + beforeDay.getTotalCasesCumulative());
                    dailyTotalStateCasesDTO.setRecoveredCasesCumulative(dailyTotalStateCasesDTO.getRecoveredCases() + beforeDay.getRecoveredCasesCumulative());
                    dailyTotalStateCasesDTO.setDeceasedCasesCumulative(dailyTotalStateCasesDTO.getDeceasedCases() + beforeDay.getDeceasedCasesCumulative());
                    i++;
                } else {
                    dailyTotalStateCasesDTO.setActiveCasesCumulative(dailyTotalStateCasesDTO.getActiveCases());
                    dailyTotalStateCasesDTO.setTotalCasesCumulative(dailyTotalStateCasesDTO.getTotalCases());
                    dailyTotalStateCasesDTO.setRecoveredCasesCumulative(dailyTotalStateCasesDTO.getRecoveredCases());
                    dailyTotalStateCasesDTO.setDeceasedCasesCumulative(dailyTotalStateCasesDTO.getDeceasedCases());

                }
                if(dailyTotalStateCasesDTO.getActiveCases() < 0)
                    dailyTotalStateCasesDTO.setActiveCases(0);

                dtos.add(dailyTotalStateCasesDTO);
                calendar = Calendar.getInstance();
                calendar.setTime(current);
                calendar.add(Calendar.DATE, 1);
                current = calendar.getTime();
            }
        }

        return dtos;
    }

    private TotalStateCasesDTO getStateTotal(String state) {
        TotalStateCasesDTO totalStateCasesDTO = totalStateCasesCache.get(state);
        if(totalStateCasesDTO == null) {
            final Optional<TotalStateCases> optional = totalStateCasesRepository.findByState(state);
            if(optional.isPresent()) {
                totalStateCasesDTO = convert(optional.get());
            }
        }
        return totalStateCasesDTO;
    }

    private DailyTotalStateCasesDTO getStateTotalByDate(String state, Date date) {
        DailyTotalStateCasesDTO dailyTotalStateCasesDTO = dailyStateTotalCasesCache.get(date, state);
        if(dailyTotalStateCasesDTO == null) {
            final Optional<DailyTotalStateCases> optional = dailyTotalStateCasesRepository.findByStateAndRecordedOn(state, date);
            if(optional.isPresent()) {
                dailyTotalStateCasesDTO = convert(optional.get());
            }
        }
        return dailyTotalStateCasesDTO;
    }

    private DailyTotalStateCasesDTO convert(DailyTotalStateCases value) {

        if(value == null) {
            return null;
        }
        DailyTotalStateCasesDTO dailyTotalStateCasesDTO = new DailyTotalStateCasesDTO();
        dailyTotalStateCasesDTO.setId(value.getId());
        dailyTotalStateCasesDTO.setState(value.getState());
        dailyTotalStateCasesDTO.setTotalCases(value.getTotalCases());
        dailyTotalStateCasesDTO.setActiveCases(value.getActiveCases());
        dailyTotalStateCasesDTO.setRecoveredCases(value.getRecoveredCases());
        dailyTotalStateCasesDTO.setDeceasedCases(value.getDeceasedCases());
        dailyTotalStateCasesDTO.setTotalCasesCumulative(value.getTotalCasesCumulative());
        dailyTotalStateCasesDTO.setActiveCasesCumulative(value.getActiveCasesCumulative());
        dailyTotalStateCasesDTO.setRecoveredCasesCumulative(value.getRecoveredCasesCumulative());
        dailyTotalStateCasesDTO.setDeceasedCasesCumulative(value.getDeceasedCasesCumulative());
        dailyTotalStateCasesDTO.setRecordedOn(value.getRecordedOn());
        return dailyTotalStateCasesDTO;
    }

    private TotalStateCasesDTO convert(TotalStateCases totalStateCases) {


        if(totalStateCases == null) {
            return null;
        }
        TotalStateCasesDTO totalStateCasesDTO = new TotalStateCasesDTO();
        totalStateCasesDTO.setId(totalStateCases.getId());
        totalStateCasesDTO.setState(totalStateCases.getState());
        totalStateCasesDTO.setTotalCases(totalStateCases.getTotalCases());
        totalStateCasesDTO.setActiveCases(totalStateCases.getActiveCases());
        totalStateCasesDTO.setRecoveredCases(totalStateCases.getRecoveredCases());
        totalStateCasesDTO.setDeceasedCases(totalStateCases.getDeceasedCases());
        return totalStateCasesDTO;
    }
}
