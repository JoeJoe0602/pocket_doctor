package com.jolin.dto;

import com.jolin.common.dto.BaseDTO;
import lombok.Data;

@Data
public class HealthRecordDTO extends BaseDTO {
    private String  userId;

    private  String realName;

    private  Integer sex;

    private  String birth;

    private  Integer height;

    private  Integer weight;

    private  Integer smokingHistory;

    private  Integer drinkingHistory;

    private  Integer liverFunction;

    private  Integer kidneyFunction;

    private  Integer isMarried;

    private  Integer isFertility;

    private  Integer diseaseHistory;

    private  Integer familyMedicalHistory;

    private  Integer foodAllergyHistory;

    private  Integer personalHabits;

}
