package com.evalution.covid.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
public class DailyTotalCasesDTO  implements Serializable {
    private long serialVersionUID=1l;
    private long id;

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
