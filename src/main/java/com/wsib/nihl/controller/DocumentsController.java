package com.wsib.nihl.controller;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wsib.nihl.constants.ErrorCodes;
import com.wsib.nihl.model.DocumentRequest;
import com.wsib.nihl.model.ErrorResponse;
import com.wsib.nihl.service.DocGenerationService;

import ch.qos.logback.classic.Logger;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags="Documents")
@RestController
public class DocumentsController {

	private Logger logger =(Logger) LoggerFactory.getLogger(DocumentsController.class);
	
	@Autowired
	DocGenerationService docService;
	
	@ApiOperation(value="Process the supporting documents")
	@RequestMapping(value="/document" ,method = RequestMethod.POST)
	public ResponseEntity<?> sendDocs(@RequestBody DocumentRequest docRequest) {
		if(logger.isDebugEnabled()) {
			logger.debug("Process the documents");
		}
		try {
			docService.sendDocuments(docRequest);
			return new ResponseEntity("Sent",HttpStatus.CREATED);
		}catch(Exception e) {
			ErrorResponse errorResp = new ErrorResponse();
			errorResp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.name());
			errorResp.setErrorCd(ErrorCodes.SYSTEM_ERROR_CD);
			errorResp.setMessage(ErrorCodes.SYSTEM_ERROR_MSG);
			return new ResponseEntity<ErrorResponse>(errorResp, HttpStatus.BAD_REQUEST);
		}
	}
}
