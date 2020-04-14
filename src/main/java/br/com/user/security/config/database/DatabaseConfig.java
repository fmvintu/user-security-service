package br.com.user.security.config.database;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import static java.util.Optional.ofNullable;

@Slf4j
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "br.com.**.repository")
public class DatabaseConfig {

    private static final String JPA_TRSCTION_TIMEOUT_PROP_NAME = "jpa.custom.transaction.timeout";

    @Autowired
    private Environment environment;

    @PostConstruct
    private void init() {
        log.info("Loading Database Config");
    }

    @Bean("dataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource databaseDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean("entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, DataSource dataSource) {
        return builder.dataSource(dataSource)
                .persistenceUnit("default")
                .packages("br.com.**.domain.entity")
                .build();
    }

    @Bean("transactionManager")
    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager(emf);

        ofNullable(environment.getProperty(JPA_TRSCTION_TIMEOUT_PROP_NAME, Integer.class))
                .ifPresent(jpaTransactionManager::setDefaultTimeout);

        return new JpaTransactionManager(emf);
    }
}
