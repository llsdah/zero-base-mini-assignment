package com.example.zerobase.zerobaseminiassignment.common;

import com.example.zerobase.zerobaseminiassignment.model.ModificationDateModel;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class MyDateUtil {

    /**
     * 시간 등록을 위한 메소드
     * @return DateModel
     */
    public static ModificationDateModel getData(){
        ModificationDateModel modificationDateModel = new ModificationDateModel();
        // 한국 시간대로 설정
        ZoneId koreaZone = ZoneId.of("Asia/Seoul");
        ZonedDateTime koreaTime = ZonedDateTime.now(koreaZone);
        LocalDateTime currentTime = koreaTime.toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String formattedTime = currentTime.format(formatter);
        modificationDateModel.setRegistrationDate(formattedTime);
        modificationDateModel.updateModificationDate(formattedTime);

        return modificationDateModel;
    }
}
