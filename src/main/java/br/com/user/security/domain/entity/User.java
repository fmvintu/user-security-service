package br.com.user.security.domain.entity;

import br.com.user.security.domain.entity.converter.YesNoConverter;
import br.com.user.security.domain.entity.support.Auditable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USER", uniqueConstraints = {@UniqueConstraint(columnNames = "USERNAME")})
public class User extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "USER_UID", unique = true, nullable = false)
    private long id;

    @Length(min = 5, message = "*Your user name must have at least 50 characters")
    @NotEmpty(message = "*Please provide a user name")
    @Column(name = "USERNAME", unique = true, nullable = false, length = 50)
    private String userName;

    @Column(name = "PASSWORD", unique = true, nullable = false)
    @Length(min = 5, message = "*Your password must have at least 5 characters")
    @NotEmpty(message = "*Please provide your password")
    private String password;

    @Convert(converter = YesNoConverter.class)
    @Column(name = "ACTIVE", unique = true, nullable = false)
    private boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_UID", referencedColumnName = "ROLE_UID", nullable = false)
    private Role role;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private UserInfo userInfo;
}
