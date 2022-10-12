package com.jolin.common.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseDomain extends BaseCommonDomain {


    @TableField(value = "sort", fill = FieldFill.INSERT)
    private Integer sort;

    /**
     * Delete
     */
    @TableField(value = "is_delete", fill = FieldFill.INSERT)
    @TableLogic
    private Integer isDelete;

    /**
     * Create time
     */
    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * Update time
     */
    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

}
