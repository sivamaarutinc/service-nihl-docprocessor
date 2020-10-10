package com.wsib.nihl.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class EmploymentInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int employmentInfoId;

    private Boolean hasRetired;
    private LocalDate retirementDate;
    private Boolean hasEverBeenSelfEmployed;
    private Boolean isCurrentlyEmployed;

    @Size(max = 250, message="Only 250 characters are allowed")
    private String currentSituation;

    @Size(max = 250, message="Only 250 characters are allowed")
    private String currentEmployerName;
    
    @Size(max = 250, message="Only 250 characters are allowed")
    private String currentEmployerAddress;

    private String currentEmployerPhoneNumber;
    private Boolean currentEmployerIsHazardous;
    private Boolean selfEmpHasInsurance;

    @Size(max = 250, message="Only 250 characters are allowed")
    private String selfEmpBusinessName;
 
    @Size(max = 250, message="Only 250 characters are allowed")
    private String selfEmpBusinessAddress;
    private LocalDate selfEmpStartDate;
    private LocalDate selfEmpEndDate;
    private Boolean hasUsedNoisyEquipmentOutOfWork;

    @Size(max = 250, message="Only 250 characters are allowed")
    private String noisyEquipmentDetails;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "claim_id", referencedColumnName = "claimId")
    @JsonIgnore
    private Claim claim;

    @OneToMany(mappedBy = "employmentInformation", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PastEmploymentInformation> pastEmploymentInformationList;

    public int getEmploymentInfoId() {
        return employmentInfoId;
    }

    public void setEmploymentInfoId(int employmentInfoId) {
        this.employmentInfoId = employmentInfoId;
    }

    public Boolean getHasRetired() {
        return hasRetired;
    }

    public void setHasRetired(Boolean hasRetired) {
        this.hasRetired = hasRetired;
    }

    public LocalDate getRetirementDate() {
        return retirementDate;
    }

    public void setRetirementDate(LocalDate retirementDate) {
        this.retirementDate = retirementDate;
    }

    public Boolean getHasEverBeenSelfEmployed() {
        return hasEverBeenSelfEmployed;
    }

    public void setHasEverBeenSelfEmployed(Boolean hasEverBeenSelfEmployed) {
        this.hasEverBeenSelfEmployed = hasEverBeenSelfEmployed;
    }

    public Boolean getCurrentlyEmployed() {
        return isCurrentlyEmployed;
    }

    public void setCurrentlyEmployed(Boolean currentlyEmployed) {
        isCurrentlyEmployed = currentlyEmployed;
    }

    public String getCurrentSituation() {
        return currentSituation;
    }

    public void setCurrentSituation(String currentSituation) {
        this.currentSituation = currentSituation;
    }

    public String getCurrentEmployerName() {
        return currentEmployerName;
    }

    public void setCurrentEmployerName(String currentEmployerName) {
        this.currentEmployerName = currentEmployerName;
    }

    public String getCurrentEmployerAddress() {
        return currentEmployerAddress;
    }

    public void setCurrentEmployerAddress(String currentEmployerAddress) {
        this.currentEmployerAddress = currentEmployerAddress;
    }

    public String getCurrentEmployerPhoneNumber() {
        return currentEmployerPhoneNumber;
    }

    public void setCurrentEmployerPhoneNumber(String currentEmployerPhoneNumber) {
        this.currentEmployerPhoneNumber = currentEmployerPhoneNumber;
    }

    public Boolean getCurrentEmployerIsHazardous() {
        return currentEmployerIsHazardous;
    }

    public void setCurrentEmployerIsHazardous(Boolean currentEmployerIsHazardous) {
        this.currentEmployerIsHazardous = currentEmployerIsHazardous;
    }

    public Boolean getSelfEmpHasInsurance() {
        return selfEmpHasInsurance;
    }

    public void setSelfEmpHasInsurance(Boolean selfEmpHasInsurance) {
        this.selfEmpHasInsurance = selfEmpHasInsurance;
    }

    public String getSelfEmpBusinessName() {
        return selfEmpBusinessName;
    }

    public void setSelfEmpBusinessName(String selfEmpBusinessName) {
        this.selfEmpBusinessName = selfEmpBusinessName;
    }

    public String getSelfEmpBusinessAddress() {
        return selfEmpBusinessAddress;
    }

    public void setSelfEmpBusinessAddress(String selfEmpBusinessAddress) {
        this.selfEmpBusinessAddress = selfEmpBusinessAddress;
    }

    public LocalDate getSelfEmpStartDate() {
        return selfEmpStartDate;
    }

    public void setSelfEmpStartDate(LocalDate selfEmpStartDate) {
        this.selfEmpStartDate = selfEmpStartDate;
    }

    public LocalDate getSelfEmpEndDate() {
        return selfEmpEndDate;
    }

    public void setSelfEmpEndDate(LocalDate selfEmpEndDate) {
        this.selfEmpEndDate = selfEmpEndDate;
    }

    public Boolean getHasUsedNoisyEquipmentOutOfWork() {
        return hasUsedNoisyEquipmentOutOfWork;
    }

    public void setHasUsedNoisyEquipmentOutOfWork(Boolean hasUsedNoisyEquipmentOutOfWork) {
        this.hasUsedNoisyEquipmentOutOfWork = hasUsedNoisyEquipmentOutOfWork;
    }

    public String getNoisyEquipmentDetails() {
        return noisyEquipmentDetails;
    }

    public void setNoisyEquipmentDetails(String noisyEquipmentDetails) {
        this.noisyEquipmentDetails = noisyEquipmentDetails;
    }

    public Claim getClaim() {
        return claim;
    }

    public void setClaim(Claim claim) {
        this.claim = claim;
    }

    public List<PastEmploymentInformation> getPastEmploymentInformationList() {
        return pastEmploymentInformationList;
    }

    public void setPastEmploymentInformationList(List<PastEmploymentInformation> pastEmploymentInformationList) {
        this.pastEmploymentInformationList = pastEmploymentInformationList;
    }

    @Override
    public String toString() {
        return "EmploymentInformation{" +
                "employmentInfoId=" + employmentInfoId +
                ", hasRetired=" + hasRetired +
                ", retirementDate=" + retirementDate +
                ", hasEverBeenSelfEmployed=" + hasEverBeenSelfEmployed +
                ", isCurrentlyEmployed=" + isCurrentlyEmployed +
                ", currentSituation='" + currentSituation + '\'' +
                ", currentEmployerName='" + currentEmployerName + '\'' +
                ", currentEmployerAddress='" + currentEmployerAddress + '\'' +
                ", currentEmployerPhoneNumber='" + currentEmployerPhoneNumber + '\'' +
                ", currentEmployerIsHazardous=" + currentEmployerIsHazardous +
                ", hasUsedNoisyEquipmentOutOfWork=" + hasUsedNoisyEquipmentOutOfWork +
                ", noisyEquipmentDetails='" + noisyEquipmentDetails + '\'' +
                ", claim=" + claim +
                '}';
    }
}
