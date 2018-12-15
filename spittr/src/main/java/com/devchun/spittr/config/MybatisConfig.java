package com.devchun.spittr.config;

import java.io.IOException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import net.sf.log4jdbc.Log4jdbcProxyDataSource;
import net.sf.log4jdbc.tools.Log4JdbcCustomFormatter;
import net.sf.log4jdbc.tools.LoggingType;

@Configuration
@EnableTransactionManagement(proxyTargetClass=true)
public class MybatisConfig {
  private static final Logger logger = LoggerFactory.getLogger(MybatisConfig.class);

  @Autowired
  private DataSource dataSource;
  
  public DataSource dataSourceSpied() {
    Log4jdbcProxyDataSource dataSourceSpied = new Log4jdbcProxyDataSource(dataSource);
    Log4JdbcCustomFormatter formatter = new Log4JdbcCustomFormatter();
    formatter.setLoggingType(LoggingType.MULTI_LINE);
    formatter.setSqlPrefix("");

    dataSourceSpied.setLogFormatter(formatter);

    return dataSourceSpied;
  }

  @Bean
  public SqlSessionFactoryBean sqlSessionFactoryBean() throws IOException {
    SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
    sessionFactory.setDataSource(dataSourceSpied());
    sessionFactory
        .setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/**/*Mapper.xml"));
    sessionFactory.setConfigLocation(new ClassPathResource("mybatis-config.xml"));

    return sessionFactory;
  }

  @Bean
  public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sessionFactory) {
    SqlSessionTemplate sessionTemplate = new SqlSessionTemplate(sessionFactory);
    return sessionTemplate;
  }

  @Bean
  @Qualifier("mybatisTxManager")
  public DataSourceTransactionManager transactionManager() {
    DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
    dataSourceTransactionManager.setDataSource(dataSourceSpied());

    return dataSourceTransactionManager;
  }
}
