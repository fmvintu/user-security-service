package br.com.user.security.domain.entity.support;

import br.com.user.security.config.database.AuditableListener;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;

@Getter
@Setter
@EntityListeners({AuditableListener.class})
@MappedSuperclass
public abstract class Auditable implements Serializable {
    @NotNull
    @Column(name = "CREATE_DT", nullable = false)
    @CreatedDate
    private LocalDateTime createdOn;

    @NotNull
    @Column(name = "CREATE_FUNCTION_NAME", nullable = false, length = 300)
    @CreatedBy
    protected String createdBy;

    @NotNull
    @Column(name = "UPDATE_DT", nullable = false)
    private LocalDateTime updateOn;

    @NotNull
    @Column(name = "UPDATE_FUNCTION_NAME", nullable = false, length = 300)
    @LastModifiedBy
    private String updatedBy;
}
