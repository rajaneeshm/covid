package com.evalution.covid.cache;

import com.evalution.covid.configuration.RedisUtil;
import com.evalution.covid.dto.DailyTotalCasesDTO;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DailyTotalCasesCache {


    private RedisUtil<DailyTotalCasesDTO> redisUtil;
    private SimpleDateFormat dateFormat;


    public DailyTotalCasesCache(RedisUtil<DailyTotalCasesDTO> redisUtil) {
        this.redisUtil = redisUtil;
        this.dateFormat = new SimpleDateFormat("ddMMyyyy");
    }

    public void set(DailyTotalCasesDTO dailyTotalCasesDTO) {
        redisUtil.putValue("dailyTotalCasesDTO-" + dateFormat.format(dailyTotalCasesDTO.getRecordedOn()), dailyTotalCasesDTO);
    }

    public DailyTotalCasesDTO get(Date date) {
        return redisUtil.getValue("dailyTotalCasesDTO-" + dateFormat.format(date));
    }
}
