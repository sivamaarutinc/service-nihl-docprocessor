package com.wsib.nihl.configtest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.wsib.nihl.config.ApplicationProperties;

@SpringBootTest(classes = ApplicationProperties.class)
public class ApplicationPropertiesTest {

	@Autowired
	ApplicationProperties applicationProperties;

	@Value("${document.path}")
	private String docPath;

	@Value("${document.root}")
	private String docRoot;

	@Value("${document.format}")
	private String pathFormat;

	@Value("${document.pdf.path}")
	private String pdfPath;

	@Value("${document.pdfa.path}")
	private String pdfaPath;

	@BeforeEach
	void setPropertyValues()
	{
		applicationProperties.setDocPath(docPath);
		applicationProperties.setDocRoot(docRoot);
		applicationProperties.setPathFormat(pathFormat);
		applicationProperties.setPdfaPath(pdfaPath);
		applicationProperties.setPdfPath(pdfPath);
	}
	
	@Test
	void testHost() {
		assertEquals(docPath, applicationProperties.getDocPath());
		assertEquals(docRoot, applicationProperties.getDocRoot());
		assertEquals(pathFormat, applicationProperties.getPathFormat());
		assertEquals(pdfPath, applicationProperties.getPdfPath());
		assertEquals(pdfaPath, applicationProperties.getPdfaPath());

	}

}
