package com.wsib.nihl.entitytest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.wsib.nihl.entity.Claim;
import com.wsib.nihl.entity.ClaimDocuments;



public class ClaimDocumentsTest {

	ClaimDocuments claimDocuments = new ClaimDocuments();
	Claim claim = new Claim();
	byte[] doc="content".getBytes();
	@BeforeEach
	void beforeEach() {
		claimDocuments.setDocumentName("file1");
		claimDocuments.setDocumentSize("documentSize");
		claimDocuments.setDocumentType("png");
		claimDocuments.setFormId("1");
		claimDocuments.setReferenceId(1);
		claimDocuments.setDocument(doc);
		claimDocuments.setClaim(claim);
		claimDocuments.setClaimDocumentId(1);
	}

	@Test
	void testGetters() {
		assertAll(() -> assertEquals("file1", claimDocuments.getDocumentName()),
				() -> assertEquals("documentSize", claimDocuments.getDocumentSize()),
				() -> assertEquals("png", claimDocuments.getDocumentType()),
				() -> assertEquals("1", claimDocuments.getFormId()),
				() -> assertEquals(1, claimDocuments.getReferenceId()),
				() -> assertEquals(doc, claimDocuments.getDocument()),
				() -> assertEquals(claim, claimDocuments.getClaim()),
				() -> assertEquals(1, claimDocuments.getClaimDocumentId()),
				() -> assertEquals(claimDocuments.toString(), claimDocuments.toString())

		);

	}

}
