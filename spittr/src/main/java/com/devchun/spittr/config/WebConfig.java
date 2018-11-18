package com.devchun.spittr.config;

import java.io.IOException;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan("com.devchun.spittr.web")
public class WebConfig extends WebMvcConfigurerAdapter{
	
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		resolver.setExposeContextBeansAsAttributes(true);
		resolver.setOrder(Integer.MAX_VALUE);
		
		return resolver;
	}
	
	@Bean
	public ViewResolver tilesViewResolver() {
		TilesViewResolver resolver = new TilesViewResolver();
		resolver.setOrder(2);
		
		return resolver;
	}
	
	@Bean
  public TilesConfigurer tilesConfigurer() {
    TilesConfigurer tiles = new TilesConfigurer();
    String[] definitions = {
      "/WEB-INF/layout/tiles.xml",
      "/WEB-INF/**/tiles.xml" 
    };
    
    tiles.setDefinitions(definitions);
    tiles.setCheckRefresh(true);
    
    return tiles;
  }
	
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = 
				new ReloadableResourceBundleMessageSource();
		
		messageSource.setBasename("file:///C:/Users/chuns/git/spittr/spittr/src/main/webapp/messages");
		messageSource.setCacheSeconds(10);
		
		return messageSource;
	}
	
//	@Bean
//	public ViewResolver thymeleafViewResolver() {
//	  ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
//	  viewResolver.setTemplateEngine(templateEngine());
//	  viewResolver.setOrder(1);
//	  
//	  return viewResolver;
//	}
//	
//	@Bean
//	public SpringResourceTemplateResolver templateResolver() {
//	  SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
//	  templateResolver.setPrefix("/WEB-INF/templates/");
//	  templateResolver.setSuffix(".html");
//	  templateResolver.setTemplateMode(TemplateMode.HTML);
//	  templateResolver.setCacheable(true);
//	  
//	  return templateResolver;
//	}
//	
//	@Bean
//	public SpringTemplateEngine templateEngine() {
//	  SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//	  templateEngine.setTemplateResolver(templateResolver());
//	  
//	  return templateEngine;
//	}
	
	@Bean
	public MultipartResolver multipartResolver() throws IOException {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		
		multipartResolver.setUploadTempDir(new FileSystemResource("/tmp/spittr/uploads"));
		multipartResolver.setMaxUploadSize(2097152);
		multipartResolver.setMaxInMemorySize(0);
		
		return multipartResolver;
	}
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
	  registry.addViewController("/login").setViewName("login");
	}
}
