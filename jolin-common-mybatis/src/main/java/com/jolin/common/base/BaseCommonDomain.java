package com.jolin.common.base;

import com.baomidou.mybatisplus.annotation.TableId;
import com.jolin.common.domain.CommonDomain;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
class BaseCommonDomain extends CommonDomain {

    @TableId(value = "id")
    private String id;
}
