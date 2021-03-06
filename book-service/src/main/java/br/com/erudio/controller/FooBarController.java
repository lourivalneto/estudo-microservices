package br.com.erudio.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

@RestController
@RequestMapping("/book-service")
public class FooBarController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@GetMapping("/foo-bar")
	// @Retry(name = "foo-bar", fallbackMethod = "fallbackMethod")
	// @CircuitBreaker(name = "default", fallbackMethod = "fallbackMethod")
	@RateLimiter(name = "default")
	@Bulkhead(name = "default")
	public String fooBar() {
		logger.info("Request to foobar is received.");
		// var response = new
		// RestTemplate().getForEntity("http://localhost:8080/foo-bar", String.class);
		return "Foo-Bar!!!";
		// return response.getBody();
	}

	public String fallbackMethod(Exception e) {
		return "Fallback Method Foo Bar!!!";
	}

}
