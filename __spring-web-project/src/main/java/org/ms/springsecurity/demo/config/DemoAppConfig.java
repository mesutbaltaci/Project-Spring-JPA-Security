package org.ms.springsecurity.demo.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages ="org.ms.springsecurity.demo")
public class DemoAppConfig implements WebMvcConfigurer{

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
