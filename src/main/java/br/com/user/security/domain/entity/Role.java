package br.com.user.security.domain.entity;

import br.com.user.security.domain.entity.converter.RoleConverter;
import br.com.user.security.domain.entity.enu.RoleEnum;
import br.com.user.security.domain.entity.support.Auditable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ROLE")
public class Role extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "ROLE_UID")
    private long id;

    @Convert(converter = RoleConverter.class)
    @Column(name = "ROLE_NAME", nullable = false, unique = true, length = 30)
    private RoleEnum roleName;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private List<User> user;
}
