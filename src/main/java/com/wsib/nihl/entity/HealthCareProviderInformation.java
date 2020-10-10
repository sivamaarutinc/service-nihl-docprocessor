package com.wsib.nihl.entity;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class HealthCareProviderInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int healthCareInfoId;
    @Pattern(
    		regexp ="\\d+",
    		message = "No special characters are allowed")
    @Size(min = 4, message = "Minimum 4characters required")
    private String hearingLossNoticedYear;
    
    private LocalDate dateOfFirstAudiogram;

    @Pattern(
    		regexp ="^[a-zA-ZÀ-ÿ0-9#&%@\\/\\-\\'\\. ]*$",
    		message = "No special characters are allowed")
    @Size(max = 250, message="Only 250 characters are allowed for audiogram clinic name")
    private String audiogramClinicName;
    
    
    @Size(max = 250, message="Only 250 characters are allowed for audiogram address")
    private String audiogramClinicAddress;


    @Size(max = 30, min = 10, message="Only 15 characters are allowed")
    private String audiogramClinicPhoneNumber;
    private Boolean hasVisitedEntSpecialist;
    private LocalDate entAppointmentDate;
    
    @Pattern(
    		regexp ="^[a-zA-ZÀ-ÿ0-9#&%@\\/\\-\\'\\. ]*$",
    	    message = "No special characters are allowed"
    	    )
    @Size(min = 1, max = 35, message = "ent name must be 1-35 characters long.")
    private String entSpecialistName;
    
    private String entSpecialistAddress;

    @Size(min = 10, max = 30, message = "Ph number must be 10-15 characters long.")
    private String entSpecialistPhoneNumber;
    private Boolean hasRingingInEar;
    private String ringingEarDuration;
    private Boolean hasSevereRingingInEar;
    private Boolean hasConstantRingingInEar;
    private Boolean hasHearingAid;
    private LocalDate hearingAidUsageDate;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "claim_id", referencedColumnName = "claimId")
    @JsonIgnore
    private Claim claim;

    public int getHealthCareInfoId() {
        return healthCareInfoId;
    }

    public void setHealthCareInfoId(int healthCareInfoId) {
        this.healthCareInfoId = healthCareInfoId;
    }

    public String getHearingLossNoticedYear() {
        return hearingLossNoticedYear;
    }

    public void setHearingLossNoticedYear(String hearingLossNoticedYear) {
        this.hearingLossNoticedYear = hearingLossNoticedYear;
    }

    public LocalDate getDateOfFirstAudiogram() {
        return dateOfFirstAudiogram;
    }

    public void setDateOfFirstAudiogram(LocalDate dateOfFirstAudiogram) {
        this.dateOfFirstAudiogram = dateOfFirstAudiogram;
    }

    public String getAudiogramClinicName() {
        return audiogramClinicName;
    }

    public void setAudiogramClinicName(String audiogramClinicName) {
        this.audiogramClinicName = audiogramClinicName;
    }

    public String getAudiogramClinicAddress() {
        return audiogramClinicAddress;
    }

    public void setAudiogramClinicAddress(String audiogramClinicAddress) {
        this.audiogramClinicAddress = audiogramClinicAddress;
    }

    public String getAudiogramClinicPhoneNumber() {
        return audiogramClinicPhoneNumber;
    }

    public void setAudiogramClinicPhoneNumber(String audiogramClinicPhoneNumber) {
        this.audiogramClinicPhoneNumber = audiogramClinicPhoneNumber;
    }

    public Boolean getHasVisitedEntSpecialist() {
        return hasVisitedEntSpecialist;
    }

    public void setHasVisitedEntSpecialist(Boolean hasVisitedEntSpecialist) {
        this.hasVisitedEntSpecialist = hasVisitedEntSpecialist;
    }

    public LocalDate getEntAppointmentDate() {
        return entAppointmentDate;
    }

    public void setEntAppointmentDate(LocalDate entAppointmentDate) {
        this.entAppointmentDate = entAppointmentDate;
    }

    public String getEntSpecialistName() {
        return entSpecialistName;
    }

    public void setEntSpecialistName(String entSpecialistName) {
        this.entSpecialistName = entSpecialistName;
    }

    public String getEntSpecialistAddress() {
        return entSpecialistAddress;
    }

    public void setEntSpecialistAddress(String entSpecialistAddress) {
        this.entSpecialistAddress = entSpecialistAddress;
    }

    public String getEntSpecialistPhoneNumber() {
        return entSpecialistPhoneNumber;
    }

    public void setEntSpecialistPhoneNumber(String entSpecialistPhoneNumber) {
        this.entSpecialistPhoneNumber = entSpecialistPhoneNumber;
    }

    public Boolean getHasRingingInEar() {
        return hasRingingInEar;
    }

    public void setHasRingingInEar(Boolean hasRingingInEar) {
        this.hasRingingInEar = hasRingingInEar;
    }

    public String getRingingEarDuration() {
        return ringingEarDuration;
    }

    public void setRingingEarDuration(String ringingEarDuration) {
        this.ringingEarDuration = ringingEarDuration;
    }

    public Boolean getHasSevereRingingInEar() {
        return hasSevereRingingInEar;
    }

    public void setHasSevereRingingInEar(Boolean hasSevereRingingInEar) {
        this.hasSevereRingingInEar = hasSevereRingingInEar;
    }

    public Boolean getHasHearingAid() {
        return hasHearingAid;
    }

    public void setHasHearingAid(Boolean hasHearingAid) {
        this.hasHearingAid = hasHearingAid;
    }

    public LocalDate getHearingAidUsageDate() {
        return hearingAidUsageDate;
    }

    public void setHearingAidUsageDate(LocalDate hearingAidUsageDate) {
        this.hearingAidUsageDate = hearingAidUsageDate;
    }

    public Claim getClaim() {
        return claim;
    }

    public void setClaim(Claim claim) {
        this.claim = claim;
    }

    @Override
    public String toString() {
        return "HealthCareProviderInformation{" +
                "healthCareInfoId=" + healthCareInfoId +
                ", hearingLossNoticedYear=" + hearingLossNoticedYear +
                ", dateOfFirstAudiogram=" + dateOfFirstAudiogram +
                ", audiogramClinicName='" + audiogramClinicName + '\'' +
                ", audiogramClinicAddress='" + audiogramClinicAddress + '\'' +
                ", audiogramClinicPhoneNumber='" + audiogramClinicPhoneNumber + '\'' +
                ", hasVisitedEntSpecialist=" + hasVisitedEntSpecialist +
                ", entAppointmentDate=" + entAppointmentDate +
                ", entSpecialistName='" + entSpecialistName + '\'' +
                ", entSpecialistAddress='" + entSpecialistAddress + '\'' +
                ", entSpecialistPhoneNumber='" + entSpecialistPhoneNumber + '\'' +
                ", hasRingingInEar=" + hasRingingInEar +
                ", ringingEarDuration='" + ringingEarDuration + '\'' +
                ", hasSevereRingingInEar=" + hasSevereRingingInEar +
                ", hasHearingAid=" + hasHearingAid +
                ", hearingAidUsageDate=" + hearingAidUsageDate +
                ", claim=" + claim +
                '}';
    }

	public Boolean getHasConstantRingingInEar() {
		return hasConstantRingingInEar;
	}

	public void setHasConstantRingingInEar(Boolean hasConstantRingingInEar) {
		this.hasConstantRingingInEar = hasConstantRingingInEar;
	}
}
