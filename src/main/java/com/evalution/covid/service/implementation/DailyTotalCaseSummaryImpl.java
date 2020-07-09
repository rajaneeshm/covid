package com.evalution.covid.service.implementation;

import com.evalution.covid.cache.DailyTotalCasesCache;
import com.evalution.covid.cache.TotalCasesCache;
import com.evalution.covid.dto.DailyTotalCasesDTO;
import com.evalution.covid.dto.TotalCasesDTO;
import com.evalution.covid.entity.DailyTotalCases;
import com.evalution.covid.entity.TotalCases;
import com.evalution.covid.repository.DailyTotalCasesRepository;
import com.evalution.covid.repository.TotalCasesRepository;
import com.evalution.covid.service.defitition.IDailyTotalCaseSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("dailyTotalCaseSummary")
public class DailyTotalCaseSummaryImpl implements IDailyTotalCaseSummary {

    @Autowired
    private DailyTotalCasesCache dailyTotalCasesCache;
    @Autowired
    private TotalCasesCache totalCasesCache;

    @Autowired
    private TotalCasesRepository totalCasesRepository;
    @Autowired
    private DailyTotalCasesRepository dailyTotalCasesRepository;

    @Override
    public TotalCasesDTO getTotals() {
        TotalCasesDTO totalCasesDTO = totalCasesCache.get();
        if(totalCasesDTO == null) {
            final Optional<TotalCases> optional = totalCasesRepository.findById(1L);
            if(optional.isPresent()) {
                totalCasesDTO = convert(optional.get());
                totalCasesCache.set(totalCasesDTO);
            }
        }
        return totalCasesDTO;
    }

    @Override
    public List<DailyTotalCasesDTO> getSummary() {
        List<DailyTotalCasesDTO> dailyTotalCasesDTOS = new ArrayList<>();
        final Optional<DailyTotalCases> optional = dailyTotalCasesRepository.findTopByOrderById();
        if(optional.isPresent()) {
            final DailyTotalCases dailyTotalCases = optional.get();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dailyTotalCases.getRecordedOn());
            Date beginDate = calendar.getTime();
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            Date endDate = calendar.getTime();
            Date current = beginDate;

            int i = 0;
            while (current.before(endDate)) {
                DailyTotalCasesDTO dailyTotalCasesDTO = dailyTotalCasesDTORecordedOn(current);
                if(dailyTotalCasesDTO == null) {
                    dailyTotalCasesDTO = new DailyTotalCasesDTO();
                    dailyTotalCasesDTO.setRecordedOn(current);
                }
                if(dailyTotalCasesDTOS.size() > 0) {
                    final DailyTotalCasesDTO beforeDay = dailyTotalCasesDTOS.get(i);
                    dailyTotalCasesDTO.setActiveCasesCumulative(dailyTotalCasesDTO.getActiveCases() + beforeDay.getActiveCasesCumulative());
                    dailyTotalCasesDTO.setTotalCasesCumulative(dailyTotalCasesDTO.getTotalCases() + beforeDay.getTotalCasesCumulative());
                    dailyTotalCasesDTO.setRecoveredCasesCumulative(dailyTotalCasesDTO.getRecoveredCases() + beforeDay.getRecoveredCasesCumulative());
                    dailyTotalCasesDTO.setDeceasedCasesCumulative(dailyTotalCasesDTO.getDeceasedCases() + beforeDay.getDeceasedCasesCumulative());
                    i++;
                } else {
                    dailyTotalCasesDTO.setActiveCasesCumulative(dailyTotalCasesDTO.getActiveCases());
                    dailyTotalCasesDTO.setTotalCasesCumulative(dailyTotalCasesDTO.getTotalCases());
                    dailyTotalCasesDTO.setRecoveredCasesCumulative(dailyTotalCasesDTO.getRecoveredCases());
                    dailyTotalCasesDTO.setDeceasedCasesCumulative(dailyTotalCasesDTO.getDeceasedCases());
                }
                if(dailyTotalCasesDTO.getActiveCases() < 0)
                    dailyTotalCasesDTO.setActiveCases(0);
                dailyTotalCasesDTOS.add(dailyTotalCasesDTO);


                calendar = Calendar.getInstance();
                calendar.setTime(current);
                calendar.add(Calendar.DATE, 1);
                current = calendar.getTime();
            }
        }


        return dailyTotalCasesDTOS;
    }

    private DailyTotalCasesDTO dailyTotalCasesDTORecordedOn(Date date) {
        DailyTotalCasesDTO dailyTotalCasesDTO = dailyTotalCasesCache.get(date);
        if(dailyTotalCasesDTO == null) {
            final Optional<DailyTotalCases> optional = dailyTotalCasesRepository.findByRecordedOn(date);
            if(optional.isPresent()) {
                dailyTotalCasesDTO = convert(optional.get());
                dailyTotalCasesCache.set(dailyTotalCasesDTO);
            }
        }
        return dailyTotalCasesDTO;
    }

    private TotalCasesDTO convert(TotalCases totalCases) {

        if(totalCases == null) {
            return null;
        }
        TotalCasesDTO totalCasesDTO = new TotalCasesDTO();
        totalCasesDTO.setId(totalCases.getId());
        totalCasesDTO.setTotalCases(totalCases.getTotalCases());
        totalCasesDTO.setActiveCases(totalCases.getActiveCases());
        totalCasesDTO.setRecoveredCases(totalCases.getRecoveredCases());
        totalCasesDTO.setDeceasedCases(totalCases.getDeceasedCases());
        return totalCasesDTO;
    }

    private DailyTotalCasesDTO convert(DailyTotalCases dailyTotalCases) {

        if(dailyTotalCases == null) {
            return null;
        }
        DailyTotalCasesDTO dailyTotalCasesDTO = new DailyTotalCasesDTO();
        dailyTotalCasesDTO.setId(dailyTotalCases.getId());
        dailyTotalCasesDTO.setTotalCases(dailyTotalCases.getTotalCases());
        dailyTotalCasesDTO.setActiveCases(dailyTotalCases.getActiveCases());
        dailyTotalCasesDTO.setRecoveredCases(dailyTotalCases.getRecoveredCases());
        dailyTotalCasesDTO.setDeceasedCases(dailyTotalCases.getDeceasedCases());

        dailyTotalCasesDTO.setTotalCasesCumulative(dailyTotalCases.getTotalCasesCumulative());
        dailyTotalCasesDTO.setActiveCasesCumulative(dailyTotalCases.getActiveCasesCumulative());
        dailyTotalCasesDTO.setRecoveredCasesCumulative(dailyTotalCases.getRecoveredCasesCumulative());
        dailyTotalCasesDTO.setDeceasedCasesCumulative(dailyTotalCases.getDeceasedCasesCumulative());
        dailyTotalCasesDTO.setRecordedOn(dailyTotalCases.getRecordedOn());
        return dailyTotalCasesDTO;
    }
}
