package br.com.erudio.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
@Component
@ConfigurationProperties("greeting-service")
public @Data class GreetingConfiguration {
	
	private String greeting;
	private String defaultValue;
	
}
