package com.evalution.covid.service.defitition;

import com.evalution.covid.dto.DailyTotalCasesDTO;
import com.evalution.covid.dto.TotalCasesDTO;
import com.evalution.covid.entity.CovidCase;

import java.util.List;

public interface IDailyTotalCaseSummary {
    TotalCasesDTO getTotals();

    List<DailyTotalCasesDTO> getSummary();
}
