package com.wsib.nihl.model;

import java.time.LocalDate;

public class DocumentRequest {

	private Integer claimId;
	private String email;
	private String referenceNumber;
	private LocalDate dateOfBirth;
	private String status;

	public DocumentRequest(Integer claimId) {
		super();
		this.claimId = claimId;
	}
	public DocumentRequest() {

	}
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getReferenceNumber() {
		return referenceNumber;
	}
	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}
	public Integer getClaimId() {
		return claimId;
	}
	public void setClaimId(Integer claimId) {
		this.claimId = claimId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
