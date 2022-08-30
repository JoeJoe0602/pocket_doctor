package com.jolin.common.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.jolin.common.domain.CommonDomain;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
class BaseCommonDomain extends CommonDomain {

    @TableId(type = IdType.AUTO)
    private String id;
}
