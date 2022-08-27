package com.jolin.common.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseDomain extends BaseCommonDomain {


    /**
     * 删除标记
     */
    @TableField(value = "sort", fill = FieldFill.INSERT)
    private Integer sort;

    /**
     * 删除标记
     */
    @TableField(value = "is_delete", fill = FieldFill.INSERT)
    private Integer isDelete;

    /**
     * 创建时间
     */
    @TableField(value = "createdAt", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * 修改时间
     */
    @TableField(value = "updatedAt", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

}
