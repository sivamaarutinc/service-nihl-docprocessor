package com.wsib.nihl.controllertest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wsib.nihl.constants.ErrorCodes;
import com.wsib.nihl.controller.DocumentsController;
import com.wsib.nihl.model.DocumentRequest;
import com.wsib.nihl.model.ErrorResponse;
import com.wsib.nihl.service.ClaimService;
import com.wsib.nihl.service.DocGenerationService;

@ExtendWith(MockitoExtension.class)
class DocumentsControllerTest {

	@InjectMocks
	DocumentsController documentsController;

	@Mock
	ClaimService claimService;

	@Mock
	DocGenerationService docService;

	private final int claimId = 1;
	private final String referenceNumber = "123456";

	@Test
	void testSendDocs() {
		DocumentRequest documentRequest = getDocumentRequest();
		assertEquals("Sent", documentsController.sendDocs(documentRequest).getBody());
	}

	@Test
	void testSendDocsThrowsException() {
		DocumentRequest documentRequest = getDocumentRequest();
		doThrow(new NullPointerException()).when(docService).sendDocuments(documentRequest);
		ErrorResponse errorResponse = (ErrorResponse) documentsController.sendDocs(documentRequest).getBody();
		assertEquals(ErrorCodes.SYSTEM_ERROR_CD, errorResponse.getErrorCd());
	}

	public DocumentRequest getDocumentRequest() {
		DocumentRequest documentRequest = new DocumentRequest();
		documentRequest.setClaimId(claimId);
		documentRequest.setReferenceNumber(referenceNumber);
		return documentRequest;
	}
}
