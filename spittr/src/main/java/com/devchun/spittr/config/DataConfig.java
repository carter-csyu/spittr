package com.devchun.spittr.config;

import java.io.IOException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import net.sf.log4jdbc.Log4jdbcProxyDataSource;
import net.sf.log4jdbc.tools.Log4JdbcCustomFormatter;
import net.sf.log4jdbc.tools.LoggingType;


@Configuration
@PropertySource("classpath:db.properties")
public class DataConfig {
	private static final Logger logger = LoggerFactory.getLogger(DataConfig.class);
	
	@Autowired
	private Environment env;
	
	@Bean
	public DriverManagerDataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		logger.info(env.getProperty("jdbc.driver"));
		logger.info(env.getProperty("jdbc.url"));
		logger.info(env.getProperty("jdbc.username"));
		logger.info(env.getProperty("jdbc.password"));
		
		dataSource.setDriverClassName(env.getProperty("jdbc.driver"));
		dataSource.setUrl(env.getProperty("jdbc.url"));
		dataSource.setUsername(env.getProperty("jdbc.username"));
		dataSource.setPassword(env.getProperty("jdbc.password"));
		
		return dataSource;
	}
	
	public DataSource dataSourceSpied() {
	  Log4jdbcProxyDataSource dataSourceSpied = new Log4jdbcProxyDataSource(dataSource());
	  Log4JdbcCustomFormatter formatter = new Log4JdbcCustomFormatter();
	  formatter.setLoggingType(LoggingType.MULTI_LINE);
	  formatter.setSqlPrefix("");
	  
	  dataSourceSpied.setLogFormatter(formatter);
	  
	  return dataSourceSpied;
	}
	
	@Bean
	public SqlSessionFactoryBean sqlSessionFactoryBean() throws IOException{
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSourceSpied());
		sessionFactory.setMapperLocations(
			new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/**/*Mapper.xml"));
		sessionFactory.setConfigLocation(
			new ClassPathResource("mybatis-config.xml"));
		
		return sessionFactory;
	}
	
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sessionFactory) {
		SqlSessionTemplate sessionTemplate = new SqlSessionTemplate(sessionFactory);
		return sessionTemplate;
	}
	
	@Bean
	public DataSourceTransactionManager dataSourceTransactionManager() {
		DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
		dataSourceTransactionManager.setDataSource(dataSourceSpied());
		
		return dataSourceTransactionManager;
	}
}
