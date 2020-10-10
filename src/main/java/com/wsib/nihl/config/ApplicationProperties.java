package com.wsib.nihl.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Primary
@Configuration
public class ApplicationProperties {

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

	public String getDocPath() {
		return docPath;
	}

	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}

	public String getPdfPath() {
		return pdfPath;
	}

	public String getPathFormat() {
		return pathFormat;
	}

	public void setPathFormat(String pathFormat) {
		this.pathFormat = pathFormat;
	}

	public String getPdfaPath() {
		return pdfaPath;
	}

	public void setPdfaPath(String pdfaPath) {
		this.pdfaPath = pdfaPath;
	}

	public String getDocRoot() {
		return docRoot;
	}

	public void setDocRoot(String docRoot) {
		this.docRoot = docRoot;
	}

	public void setPdfPath(String pdfPath) {
		this.pdfPath = pdfPath;
	}
	
	

}