package com.evalution.covid.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
public class TotalStateCasesDTO  implements Serializable {
    private long id;
    private String state;
    private long totalCases;
    private long activeCases;
    private long recoveredCases;
    private long deceasedCases;
}
