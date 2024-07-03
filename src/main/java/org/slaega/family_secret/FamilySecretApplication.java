package org.slaega.family_secret;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class FamilySecretApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(FamilySecretApplication.class, args);
	}

	@SuppressWarnings("null")
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.addPathPrefix("api", 
            c -> AnnotatedElementUtils.hasAnnotation(c, RestController.class));

			
    }

}
