package com.evalution.covid.controller;

import com.evalution.covid.cache.TotalCasesCache;
import com.evalution.covid.dto.DailyTotalCasesDTO;
import com.evalution.covid.dto.TotalCasesDTO;
import com.evalution.covid.dto.TotalStateCasesDTO;
import com.evalution.covid.service.defitition.IDailyTotalCaseSummary;
import com.evalution.covid.service.defitition.IStateCaseSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    private TotalCasesCache totalCasesCache;
    @Autowired
    private IDailyTotalCaseSummary dailyTotalCaseSummary;
    @Autowired
    private IStateCaseSummary stateCaseSummary;

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('END_USER')")
    public String get(Model model) {

        final TotalCasesDTO totalCasesDTO = dailyTotalCaseSummary.getTotals();
        model.addAttribute("totalCasesDTO", totalCasesDTO);

        final List<DailyTotalCasesDTO> dailyTotalCasesDTOS = dailyTotalCaseSummary.getSummary();
        model.addAttribute("dailyTotalCasesDTOS", dailyTotalCasesDTOS);

        final List<TotalStateCasesDTO> totalStateCasesDTOS = stateCaseSummary.getSummary();
        model.addAttribute("totalStateCasesDTOS", totalStateCasesDTOS);
        return "dashboard";

    }
}
