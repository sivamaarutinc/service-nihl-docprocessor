package com.wsib.nihl.modeltest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.wsib.nihl.model.ErrorResponse;



public class ErrorResponseTest {

	ErrorResponse errorResponse = new ErrorResponse();

	@BeforeEach
	void beforeEach() {
		errorResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.name());
		errorResponse.setErrorCd("500");
		errorResponse.setMessage("Failed to create a claim");
	}

	@Test
	void testGettersSetters() {
		assertAll(
				() -> assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.name(), errorResponse.getStatus()),
				() -> assertEquals("500", errorResponse.getErrorCd()),
				() -> assertEquals("Failed to create a claim", errorResponse.getMessage()),
				() -> assertEquals(errorResponse.toString(), errorResponse.toString()));
	}
}
