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
@TableName("dictionary_type")
public class DictionaryType extends BaseDomain {

    @TableField(value = "`name`")
    private  String name;

    @TableField(value = "`key`")
    private  Integer key;




}
