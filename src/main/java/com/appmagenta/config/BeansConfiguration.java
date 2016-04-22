package com.appmagenta.config;



import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:jdbc.properties")
@ComponentScan("com.appmagenta.repositories")
public class BeansConfiguration {

    @Autowired
    private Environment environment;

    @Bean
    public DataSource dataSource() {
        String url = environment.getProperty("database.url");
        String username = environment.getProperty("database.username");
        String password = environment.getProperty("database.password");
        String driver = environment.getProperty("database.driver");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driver);
        return dataSource;
    }

    @Bean
    public SessionFactory sessionFactory(){
        LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource());
        builder.scanPackages("com.appmagenta.entities").addProperties(hibernateProperties());
        return builder.buildSessionFactory();
    }

    @Bean
    public Properties hibernateProperties(){
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("hibernate.hbm2ddl.auto", "create");
        properties.put("hibernate.format_sql", "true");
        properties.put("hibernate.show_sql", "true");
        return properties;
    }

    @Bean
    public HibernateTemplate hibernateTemplate(){
        HibernateTemplate template = new HibernateTemplate(sessionFactory());
        return template;
    }

    @Bean
    public HibernateTransactionManager transactionManager(){
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory());
        return transactionManager;
    }
}