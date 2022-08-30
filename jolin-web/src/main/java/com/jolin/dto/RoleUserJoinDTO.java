package com.jolin.dto;

import com.jolin.common.dto.BaseJoinDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleUserJoinDTO extends BaseJoinDTO {
    private static final long serialVersionUID = -3861976282974227713L;

    private String roleId;

    private String userId;
}
