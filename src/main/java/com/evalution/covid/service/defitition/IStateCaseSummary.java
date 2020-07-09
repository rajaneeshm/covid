package com.evalution.covid.service.defitition;

import com.evalution.covid.dto.DailyTotalCasesDTO;
import com.evalution.covid.dto.DailyTotalStateCasesDTO;
import com.evalution.covid.dto.TotalStateCasesDTO;
import com.evalution.covid.entity.TotalStateCases;

import java.util.List;

public interface IStateCaseSummary {
    List<TotalStateCasesDTO> getSummary();

    List<DailyTotalStateCasesDTO> getDailySummary(String state);
}
