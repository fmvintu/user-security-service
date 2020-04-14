package br.com.user.security.config.database;

import br.com.user.security.domain.entity.support.Auditable;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Component;

@Component
public class AuditableListener implements ApplicationContextAware {
    private static ApplicationContext context;

    public AuditableListener() {
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        context = applicationContext;
    }

    @PrePersist
    public void setCreatedOn(Auditable auditable) {
        auditable.setCreatedOn(LocalDateTime.now());
        auditable.setCreatedBy(this.getRepositoryName(auditable));
        this.setUpdatedOn(auditable);
    }

    @PreUpdate
    public void setUpdatedOn(Auditable auditable) {
        auditable.setUpdateOn(LocalDateTime.now());
        auditable.setUpdatedBy(this.getRepositoryName(auditable));
    }

    private String getRepositoryName(Auditable auditable) {
        Objects.requireNonNull(context);
        Repositories repositories = new Repositories(context);
        RepositoryInformation repository = repositories.getRepositoryInformationFor(auditable.getClass()).get();
        return repository.getRepositoryInterface().getSimpleName();
    }
}
