package br.com.user.security.domain.entity;

import br.com.user.security.domain.entity.support.Auditable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "USER_INFO",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "USER_INFO_UID"),
                @UniqueConstraint(columnNames = "EMAIL")})
public class UserInfo extends Auditable {
    private static final long serialVersionUID = -1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "USER_INFO_UID", unique = true, nullable = false)
    private Long id;

    @NotEmpty(message = "*Please provide your name")
    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @NotEmpty(message = "*Please provide your last name")
    @Column(name = "LAST_NAME", nullable = false, length = 100)
    private String lastName;

    @Column(name = "EMAIL", nullable = false, length = 50)
    private String email;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_UID", referencedColumnName = "USER_UID")
    private User user;
}
