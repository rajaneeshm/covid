package com.evalution.covid.cache;

import com.evalution.covid.configuration.RedisUtil;
import com.evalution.covid.dto.TotalStateCasesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TotalStateCasesCache {

    @Autowired
    private RedisUtil<TotalStateCasesDTO> redisUtil;

    public void set(TotalStateCasesDTO totalStateCasesDTO) {
        redisUtil.putValue("TotalStateCasesDTO-" + totalStateCasesDTO.getState(), totalStateCasesDTO);
    }

    public TotalStateCasesDTO get(String state) {
        return redisUtil.getValue("TotalStateCasesDTO-" + state);
    }
}
