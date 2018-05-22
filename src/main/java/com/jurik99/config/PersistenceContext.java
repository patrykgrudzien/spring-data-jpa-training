package com.jurik99.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import java.util.Properties;

@Configuration
/*
 * We can enable annotation-driven management by annotation the "PersistenceContext" class with the:
 * @EnableTransactionManagement annotation.
 */
@EnableTransactionManagement
/*
 * We can configure Spring Data JPA by the following steps:
 * 1) Enable Spring Data JPA by annotating the "PersistenceContext" class with the:
 * @EnableJpaRepositories annotation.
 * 2) Configure the base packages that are scanned when Spring Data JPA creates implementations for our repository interfaces.
 */
@EnableJpaRepositories(basePackages = {
		"com.jurik99.jpa"
})
public class PersistenceContext {

	/**
	 * --------- CONFIGURING THE DATASOURCE BEAN ---------
	 * 1) Ensure that the "close()" method of the created "DataSource" object is invoked when the application context is closed.
	 * 2) Configure the database connection. We need to set the name of the JDBC driver class, the JDBC url, the username of
	 * database user, and the password of the database user.
	 * 3) Create a new "HikariDataSource" object and return the created object.
	 */
	@Bean(destroyMethod = "close")
	public DataSource dataSource(final Environment environment) {

		final HikariConfig dataSourceConfig = new HikariConfig();
		dataSourceConfig.setDriverClassName(environment.getRequiredProperty("db.driver"));
		dataSourceConfig.setJdbcUrl(environment.getRequiredProperty("db.url"));
		dataSourceConfig.setUsername(environment.getRequiredProperty("db.username"));
		dataSourceConfig.setPassword(environment.getRequiredProperty("db.password"));

		return new HikariDataSource(dataSourceConfig);
	}

	/**
	 * --------- CONFIGURING THE ENTITY MANAGER FACTORY BEAN ---------
	 * 1) Create a new "LocalContainerEntityManagerFactoryBean" object. We need to create this object because it creates the
	 * JPA "EntityManagerFactory".
	 * 2) Configure the used datasource.
	 * 3) Configure the Hibernate specific implementation of the "JpaVendorAdapter" interface. It will initialize our
	 * configuration with the default settings that are compatible with Hibernate.
	 * 4) Configure the packages that are scanned for entity classes.
	 * 5) Configure the JPA properties that are used to provide additional configuration to the used JPA provider.
	 */
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(final DataSource dataSource,
	                                                                   final Environment environment) {

		final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource);
		entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactoryBean.setPackagesToScan("com.jurik99.jpa");

		final Properties jpaProperties = new Properties();

		//Configures the used database dialect. This allows Hibernate to create SQL
		//that is optimized for the used database.
		jpaProperties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));

		//Specifies the action that is invoked to the database when the Hibernate
		//SessionFactory is created or closed.
		jpaProperties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));

		//Configures the naming strategy that is used when Hibernate creates
		//new database objects and schema elements
		jpaProperties.put("hibernate.ejb.naming_strategy", environment.getRequiredProperty("hibernate.ejb.naming_strategy"));

		//If the value of this property is true, Hibernate writes all SQL
		//statements to the console.
		jpaProperties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));

		//If the value of this property is true, Hibernate will format the SQL
		//that is written to the console.
		jpaProperties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));

		entityManagerFactoryBean.setJpaProperties(jpaProperties);

		return entityManagerFactoryBean;
	}

	/**
	 * --------- CONFIGURING THE TRANSACTION MANAGER BEAN ---------
	 * Because we are using JPA, we have to create a transaction manager bean that integrates JPA provider with the Spring
	 * transaction mechanism. We can do this by using the "JpaTransactionManager" class as the transaction manager of our
	 * application.
	 *
	 * 1) Create a new "JpaTransactionManager" object.
	 * 2) Configure the entity manager factory whose transactions are managed by the created "JpaTransactionManager" object.
	 */
	@Bean
	public JpaTransactionManager transactionManager(final EntityManagerFactory entityManagerFactory) {

		final JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);

		return transactionManager;
	}
}