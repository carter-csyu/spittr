package com.devchun.spittr.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement(proxyTargetClass=true)
@PropertySource("classpath:db.properties")
public class HibernateConfig {
  private static final Logger logger = LoggerFactory.getLogger(HibernateConfig.class);

  @Autowired
  private DataSource dataSource;
  
  @Autowired
  private Environment env;

  @Bean
  @Qualifier("hibernateSessionFactory")
  public LocalSessionFactoryBean hibernateSessionFactory() {
    LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
    sessionFactory.setDataSource(dataSource);
    sessionFactory.setPackagesToScan(new String[] { "com.devchun.spittr.domain" });

    Properties properties = new Properties();
    properties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
    properties.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
    properties.setProperty("hibernate.format_sql", env.getProperty("hibernate.format_sql"));

    sessionFactory.setHibernateProperties(properties);

    return sessionFactory;
  }

  @Bean
  @Qualifier("hibernateTxManager")
  public HibernateTransactionManager hibernateTxManager(@Qualifier("hibernateSessionFactory") SessionFactory sessionFactory) {
    HibernateTransactionManager txManager = new HibernateTransactionManager();
    txManager.setSessionFactory(sessionFactory);

    return txManager;
  }

  @Bean
  public BeanPostProcessor exceptionTranslation() {
    return new PersistenceExceptionTranslationPostProcessor();
  }
}
