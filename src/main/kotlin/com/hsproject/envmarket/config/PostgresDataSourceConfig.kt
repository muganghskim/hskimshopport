package com.hsproject.envmarket.config

import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import java.util.*
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = ["com.hsproject.envmarket.repository"], entityManagerFactoryRef = "postgresEntityManagerFactory", transactionManagerRef = "postgresTransactionManager")
class PostgresDataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource")
    fun postgresDataSourceProperties(): DataSourceProperties? {
        return DataSourceProperties()
    }

    @Bean
    @ConfigurationProperties("spring.datasource.configuration")
    fun postgresDataSource(
            @Qualifier("postgresDataSourceProperties") dataSourceProperties: DataSourceProperties): DataSource? {
        return dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource::class.java).build()
    }

    @Bean
    fun postgresEntityManagerFactory(builder: EntityManagerFactoryBuilder,
                                    @Qualifier("postgresDataSource") dataSource: DataSource?): LocalContainerEntityManagerFactoryBean? {
        return builder.dataSource(dataSource).packages("com.hsproject.envmarket.oauth","com.hsproject.envmarket.products")
                .persistenceUnit("postgresEntityManager").build()
    }

    @Bean
    fun postgresTransactionManager(
            @Qualifier("postgresEntityManagerFactory") entityManagerFactory: EntityManagerFactory?): PlatformTransactionManager? {
        return JpaTransactionManager(entityManagerFactory!!)
    }

}