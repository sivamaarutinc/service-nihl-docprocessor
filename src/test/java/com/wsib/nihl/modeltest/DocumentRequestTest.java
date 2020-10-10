package com.wsib.nihl.modeltest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.wsib.nihl.model.DocumentRequest;


public class DocumentRequestTest {

	DocumentRequest documentRequest = new DocumentRequest(1);

	private final String referenceNumber = "123456";

	@BeforeEach
	void beforEach() {
		DocumentRequest documentRequest2=new DocumentRequest();
		documentRequest.setClaimId(1);
		documentRequest.setReferenceNumber(referenceNumber);
	}

	@Test
	void testGettersSetters() {
		assertAll(() -> assertEquals(1, documentRequest.getClaimId()),
				() -> assertEquals(referenceNumber, documentRequest.getReferenceNumber()),
				() -> assertEquals(documentRequest.toString(), documentRequest.toString()));
	}
}
