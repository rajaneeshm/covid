package com.evalution.covid.cache;

import com.evalution.covid.configuration.RedisUtil;
import com.evalution.covid.dto.TotalCasesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TotalCasesCache {

    @Autowired
    private RedisUtil<TotalCasesDTO> redisUtil;

    public  void set(TotalCasesDTO totalCasesDTO){
        redisUtil.putValue("totalCases",totalCasesDTO);
    }

    public TotalCasesDTO get(){
        return redisUtil.getValue("totalCases");
    }
}
