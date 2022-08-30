package com.jolin.domain;

import com.jolin.common.base.BaseTreeDomain;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@TableName("data_item")
public class DataItem extends BaseTreeDomain {

    private static final long serialVersionUID = 1L;

    private String code;

    private String name;

    private String state;

    private String dataItemTypeId;

}
