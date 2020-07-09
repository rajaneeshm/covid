package com.evalution.covid.cache;

import com.evalution.covid.configuration.RedisUtil;
import com.evalution.covid.dto.DailyTotalStateCasesDTO;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DailyStateTotalCasesCache {


    private RedisUtil<DailyTotalStateCasesDTO> redisUtil;
    private SimpleDateFormat dateFormat;


    public DailyStateTotalCasesCache(RedisUtil<DailyTotalStateCasesDTO> redisUtil) {
        this.redisUtil = redisUtil;
        this.dateFormat = new SimpleDateFormat("ddMMyyyy");
    }

    public void set(DailyTotalStateCasesDTO dailyTotalStateCasesDTO) {
        String state = dailyTotalStateCasesDTO.getState();
       // String state = dailyTotalStateCasesDTO.getState().replaceAll("\\s", "").toLowerCase();
        redisUtil.putValue("DailyTotalStateCasesDTO-" + dateFormat.format(dailyTotalStateCasesDTO.getRecordedOn()) + "-" + state, dailyTotalStateCasesDTO);
    }

    public DailyTotalStateCasesDTO get(Date date, String state) {
         //state = state.replaceAll("\\s", "").toLowerCase();
        return redisUtil.getValue("DailyTotalStateCasesDTO-" + dateFormat.format(date) + "-" + state);
    }
}
