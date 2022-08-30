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
@TableName("dept_info")
public class DeptInfo extends BaseTreeDomain {

    private static final long serialVersionUID = 1L;

    private String deptDesc;

    private String deptName;

    private String deptState;

    private String deptType;

    private String deptUrl;
}
