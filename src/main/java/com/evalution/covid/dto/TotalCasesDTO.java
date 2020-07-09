package com.evalution.covid.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
public class TotalCasesDTO  implements Serializable {
    private long id;
    private long totalCases;
    private long activeCases;
    private long recoveredCases;
    private long deceasedCases;
}
