package com.wsib.nihl.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int claimId;

    @Pattern(
    		regexp ="^[a-zA-Z0-9.!#$%&amp;'^_`{}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$",
    		message = "No special characters are allowed on email other than @ and .")
    private String email;
    private String referenceNumber;
    private String status;
    private LocalDateTime creationDate;
    private LocalDateTime submitionDate;
    @Pattern(
    		regexp ="^[a-zA-Z0-9.!#$%&amp;'^_`{}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$",
    		message = "No special characters are allowed on email with consent")
    private String emailWithConsent;

    private String consentGivenBy;	
    private String communicationLanguage;

    @OneToOne(mappedBy = "claim", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private PersonalInformation personalInformation;

    @OneToOne(mappedBy = "claim", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private HealthCareProviderInformation healthCareProviderInformation;

    @OneToOne(mappedBy = "claim", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private EmploymentInformation employmentInformation;

    @OneToMany(mappedBy = "claim", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ClaimDocuments> claimDocumentsList;

    public int getClaimId() {
        return claimId;
    }

    public void setClaimId(int claimId) {
        this.claimId = claimId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getSubmitionDate() {
        return submitionDate;
    }

    public void setSubmitionDate(LocalDateTime submitionDate) {
        this.submitionDate = submitionDate;
    }

    public PersonalInformation getPersonalInformation() {
        return personalInformation;
    }

    public void setPersonalInformation(PersonalInformation personalInformation) {
        this.personalInformation = personalInformation;
    }

    public HealthCareProviderInformation getHealthCareProviderInformation() {
        return healthCareProviderInformation;
    }

    public void setHealthCareProviderInformation(HealthCareProviderInformation healthCareProviderInformation) {
        this.healthCareProviderInformation = healthCareProviderInformation;
    }

    public EmploymentInformation getEmploymentInformation() {
        return employmentInformation;
    }

    public void setEmploymentInformation(EmploymentInformation employmentInformation) {
        this.employmentInformation = employmentInformation;
    }

    public String getEmailWithConsent() {
        return emailWithConsent;
    }

    public void setEmailWithConsent(String emailWithConsent) {
        this.emailWithConsent = emailWithConsent;
    }

   
    public List<ClaimDocuments> getClaimDocumentsList() {
        return claimDocumentsList;
    }

    public void setClaimDocumentsList(List<ClaimDocuments> claimDocumentsList) {
        this.claimDocumentsList = claimDocumentsList;
    }

    public String getCommunicationLanguage() {
        return communicationLanguage;
    }

    public void setCommunicationLanguage(String communicationLanguage) {
        this.communicationLanguage = communicationLanguage;
    }

    @Override
    public String toString() {
        return "Claim{" +
                "claimId=" + claimId +
                ", email='" + email + '\'' +
                ", referenceNumber='" + referenceNumber + '\'' +
                ", status='" + status + '\'' +
                ", creationDate=" + creationDate +
                ", submitionDate=" + submitionDate +
                ", emailWithConsent='" + emailWithConsent + '\'' +
                ", hasConsentForEmail=" + consentGivenBy +
                ", personalInformation=" + personalInformation +
                ", healthCareProviderInformation=" + healthCareProviderInformation +
                ", employmentInformation=" + employmentInformation +
                '}';
    }

	public String getConsentGivenBy() {
		return consentGivenBy;
	}

	public void setConsentGivenBy(String consentBy) {
		this.consentGivenBy = consentBy;
	}
}
