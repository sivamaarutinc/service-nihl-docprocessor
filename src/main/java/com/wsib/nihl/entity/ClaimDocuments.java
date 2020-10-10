package com.wsib.nihl.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class ClaimDocuments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int claimDocumentId;

    @NotBlank(message = "DocumentType is mandatory")
 
    @Size(max = 100, message="Only 100 characters are allowed for")
    private String documentType;
    private int referenceId;
    @NotBlank(message = "Document name is mandatory")	
 
    @Size(max = 100, message="Only 100 characters are allowed ")
    private String documentName;
    @NotBlank(message = "FormId  is mandatory")
    private String formId;
    @NotBlank(message = "Document size is mandatory")
    private String documentSize;
    @JsonIgnore
    private byte[] document;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "claim_id", referencedColumnName = "claimId")
    @JsonIgnore
    private Claim claim;

    public int getClaimDocumentId() {
        return claimDocumentId;
    }

    public void setClaimDocumentId(int claimDocumentId) {
        this.claimDocumentId = claimDocumentId;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public int getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(int referenceId) {
        this.referenceId = referenceId;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public byte[] getDocument() {
        return document;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }

    public Claim getClaim() {
        return claim;
    }

    public void setClaim(Claim claim) {
        this.claim = claim;
    }

    @Override
    public String toString() {
        return "ClaimDocuments{" +
                "claimDocumentId=" + claimDocumentId +
                ", documentType='" + documentType + '\'' +
                ", referenceId=" + referenceId +
                ", documentName='" + documentName + '\'' +
                ", formId='" + formId + '\'' +
                ", document='" + document + '\'' +
                ", claim=" + claim +
                '}';
    }

	public String getDocumentSize() {
		return documentSize;
	}

	public void setDocumentSize(String documentSize) {
		this.documentSize = documentSize;
	}
}
