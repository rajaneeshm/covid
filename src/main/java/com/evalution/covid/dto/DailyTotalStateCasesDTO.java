package com.evalution.covid.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
public class DailyTotalStateCasesDTO implements Serializable {
    private long id;
    private String state;
    private long totalCases;
    private long activeCases;
    private long recoveredCases;
    private long deceasedCases;
    private long totalCasesCumulative;
    private long activeCasesCumulative;
    private long recoveredCasesCumulative;
    private long deceasedCasesCumulative;
    private Date recordedOn;
}
