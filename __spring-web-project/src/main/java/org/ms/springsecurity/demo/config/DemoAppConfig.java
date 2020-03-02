package org.ms.springsecurity.demo.config;

import java.beans.PropertyVetoException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages ="org.ms.springsecurity.demo")
@PropertySource("classpath:persistence-mysql.properties") //for jdbc
public class DemoAppConfig implements WebMvcConfigurer{

	// set up variable to hold the properties
	
			@Autowired
			private Environment env;
			
			// set up a logger for diagnostics
			
			private Logger logger = Logger.getLogger(getClass().getName());
			
			
					
			// define a bean for our security datasource
			
			@Bean
			public DataSource securityDataSource() {
				
				// create connection pool
				ComboPooledDataSource securityDataSource
											= new ComboPooledDataSource();
						
				// set the jdbc driver class
				
				try {
					securityDataSource.setDriverClass(env.getProperty("jdbc.driver"));
				} catch (PropertyVetoException exc) {
					throw new RuntimeException(exc);
				}
				
				// log the connection props
				// for sanity's sake, log this info
				// just to make sure we are REALLY reading data from properties file
				
				logger.info(">>> jdbc.url=" + env.getProperty("jdbc.url"));
				logger.info(">>> jdbc.user=" + env.getProperty("jdbc.user"));
				
				
				// set database connection props
				
				securityDataSource.setJdbcUrl(env.getProperty("jdbc.url"));
				securityDataSource.setUser(env.getProperty("jdbc.user"));
				securityDataSource.setPassword(env.getProperty("jdbc.password"));
				
				// set connection pool props
				
				securityDataSource.setInitialPoolSize(
						getIntProperty("connection.pool.initialPoolSize"));

				securityDataSource.setMinPoolSize(
						getIntProperty("connection.pool.minPoolSize"));

				securityDataSource.setMaxPoolSize(
						getIntProperty("connection.pool.maxPoolSize"));

				securityDataSource.setMaxIdleTime(
						getIntProperty("connection.pool.maxIdleTime"));
				
				return securityDataSource;
			}
			
			// need a helper method 
			// read environment property and convert to int
			
			private int getIntProperty(String propName) {
				
				String propVal = env.getProperty(propName);
				
				// now convert to int
				int intPropVal = Integer.parseInt(propVal);
				
				return intPropVal;
			}
	
	
	
	
	
	
	@Override
	   public void configureViewResolvers(ViewResolverRegistry registry) {
	      registry.jsp("/WEB-INF/view/", ".jsp");
	   }

	   @Override
	   public void addResourceHandlers(ResourceHandlerRegistry registry) {

	      // Register resource handler for CSS and JS
	      registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/statics/");
	            

	      // Register resource handler for images
	      registry.addResourceHandler("/images/**").addResourceLocations("/WEB-INF/images/");
	      registry.addResourceHandler("/js/**").addResourceLocations("/WEB-INF/js/");
	      registry.addResourceHandler("/plugins/**").addResourceLocations("/WEB-INF/plugins/");
	      registry.addResourceHandler("/css/**").addResourceLocations("/WEB-INF/css/");
	            
	   }
	
	
  
}
