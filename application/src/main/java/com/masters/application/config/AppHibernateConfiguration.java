package com.masters.application.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan({"com.masters.authorization.config"})
@PropertySource(value = {"classpath:application.properties"})
public class AppHibernateConfiguration {
	
	@Autowired
	private Environment environment;
	
	@Bean(name="authSessionFactory")
	public LocalSessionFactoryBean authSessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(authDataSource());
        sessionFactory.setPackagesToScan(new String[] { "com.masters.authorization.model" });
        sessionFactory.setHibernateProperties(hibernateProperties());
		return sessionFactory;
	}
	
	@Bean(name="authDataSource")
    public DataSource authDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("auth.hibernate.connection.driver.class"));
        dataSource.setUrl(environment.getRequiredProperty("auth.hibernate.connection.url"));
        dataSource.setUsername(environment.getRequiredProperty("auth.hibernate.connection.username"));
        dataSource.setPassword(environment.getRequiredProperty("auth.hibernate.connection.password"));
        return dataSource;
    }
	
	@Bean(name="appSessionFactory")
	public LocalSessionFactoryBean appSessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(appDataSource());
        sessionFactory.setPackagesToScan(new String[] { "com.masters.application.model" });
        sessionFactory.setHibernateProperties(hibernateProperties());
		return sessionFactory;
	}
	
	@Bean(name="appDataSource")
    public DataSource appDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("app.hibernate.connection.driver.class"));
        dataSource.setUrl(environment.getRequiredProperty("app.hibernate.connection.url"));
        dataSource.setUsername(environment.getRequiredProperty("app.hibernate.connection.username"));
        dataSource.setPassword(environment.getRequiredProperty("app.hibernate.connection.password"));
        return dataSource;
    }
	
    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("auth.hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show.sql"));
        properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format.sql"));
        return properties;        
    }
     
    @Autowired
    @Qualifier("authSessionFactory")
    private SessionFactory authSession;
    
    @Bean(name="authTransactionManager")
    public HibernateTransactionManager authTransactionManager() {
       HibernateTransactionManager txManager = new HibernateTransactionManager();
       txManager.setSessionFactory(authSession);
       return txManager;
    }
    
    
    @Autowired
    @Qualifier("appSessionFactory")
    private SessionFactory appSession;
    
    @Bean(name="appTransactionManager")
    public HibernateTransactionManager appTransactionManager() {
       HibernateTransactionManager txManager = new HibernateTransactionManager();
       txManager.setSessionFactory(appSession);
       return txManager;
    }
}