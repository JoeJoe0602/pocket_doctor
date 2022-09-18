package com.jolin.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jolin.common.base.BaseDomain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("health_record")
public class HealthRecord extends BaseDomain {
    @TableField(value = "user_id")
    private String userId;

    private String realName;

    private Integer sex;

    private String birth;

    private Integer height;

    private Integer weight;

    private Integer smokingHistory;

    private Integer drinkingHistory;

    private Integer liverFunction;

    private Integer kidneyFunction;

    private Integer isMarried;

    private Integer isFertility;

    private Integer diseaseHistory;

    private Integer familyMedicalHistory;

    private Integer foodAllergyHistory;

    private Integer personalHabits;

}
