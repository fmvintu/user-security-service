package br.com.user.security.domain.dto;

import br.com.user.security.domain.entity.enu.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    private long id;
    private RoleEnum roleName;
}
