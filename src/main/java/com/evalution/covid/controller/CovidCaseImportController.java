package com.evalution.covid.controller;

import com.evalution.covid.dto.CovidCaseDTO;
import com.evalution.covid.entity.CovidCase;
import com.evalution.covid.service.defitition.ICovidCaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Slf4j
@Controller
public class CovidCaseImportController {
    @Autowired
    private Job covidCaseImportJob;
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private ICovidCaseService covidCaseService;

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/runbatch")
    public String importCases() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        jobParametersBuilder.addParameter("file", new JobParameter("casesRawData/raw_data1.json"));
        jobParametersBuilder.addLong("date", new Date().getTime());
        final JobParameters jobParameters = jobParametersBuilder.toJobParameters();
        final JobExecution run = jobLauncher.run(covidCaseImportJob, jobParameters);
        log.debug(String.format("*********** Exit status: %s", run.getExitStatus().getExitCode()));
        return "redirect:/dashboard";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/addNewCase")
    String addCase(Model model) {
        CovidCaseDTO covidCaseDTO = new CovidCaseDTO();
        model.addAttribute("covidCase", covidCaseDTO);
        return "addNewCase";
    }

    @PostMapping("/addNewCase")
    @PreAuthorize("hasAuthority('ADMIN')")
    String addCasePost(@ModelAttribute("covidCase") CovidCaseDTO covidCaseDTO,Model model) {
        CovidCase covidCase = covidCaseService.saveNewCase(covidCaseDTO);
        model.addAttribute("patientNumber",covidCase.getPatientnumber());
        return "successCaseCreation";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/editCase")
    String editCase( Model model) {
        model.addAttribute("case", new CovidCaseDTO());
        return "editCase";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/getCase")
    String getCase(@ModelAttribute("case") CovidCaseDTO covidCaseDTO, Model model) {
        final CovidCaseDTO caseDTO = covidCaseService.getCaseById(covidCaseDTO.getPatientnumber());
        model.addAttribute("caseDTO", caseDTO);
        return "updateCase";
    }

    @PostMapping("/updateCase")
    @PreAuthorize("hasAuthority('ADMIN')")
    String updateCasePost(@ModelAttribute("covidCase") CovidCaseDTO covidCaseDTO) {
        CovidCase covidCase = covidCaseService.update(covidCaseDTO);
        return "redirect:/dashboard";
    }

}
