package com.wsib.nihl.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wsib.nihl.util.CommonUtils;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Data
public class PersonalInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int personalInfoId;
    
    @Pattern(
    		regexp ="^[a-zA-ZÀ-ÿ0-9#&%@\\/\\-\\'\\. ]*$",
    	    message = "No special characters are allowed"
    	    ) 
    private String firstName;
    @Pattern(
    		regexp ="^[a-zA-ZÀ-ÿ0-9#&%@\\/\\-\\'\\. ]*$", 
    	    message = "No special characters are allowed"
    	    )
    @Size(min = 1, max = 35, message = "Lastname must be 1-35 characters long.")
    private String lastName;
    
    @Past(message = "Date input is invalid for a birth date.") 
    private LocalDate dateOfBirth;
    
    private String socialInsuranceNumber;
    
    @Size(max = 250, message="Only 250 characters are allowed for street address")
    private String StreetAddress;
   
    @Size(max = 20, message="More than 20 characters")
    private String apartment;
   
    @Size(max = 60, message="More than 60 characters")
    private String city;
    
    @Size(max = 60, message="More than 60 characters")
    private String provinceOrState;
    
    @Size(max = 25, message="More than 25 characters")
    private String postalCode;
    private String country;
    private String primaryTelephoneNumber;
    private String secondaryTelephoneNumber;
    private String languagePreference;
    private String otherLanguagePreference;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "claim_id", referencedColumnName = "claimId")
    @JsonIgnore
    private Claim claim;

    public int getPersonalInfoId() {
        return personalInfoId;
    }

    public void setPersonalInfoId(int personalInfoId) {
        this.personalInfoId = personalInfoId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public String getUnmaskedSocialInsuranceNumber() {
    	//return CommonUtils.applyMasking(socialInsuranceNumber);
        return socialInsuranceNumber;
    }

    public String getSocialInsuranceNumber() {
    	return CommonUtils.applyMasking(socialInsuranceNumber);
        //return socialInsuranceNumber;
    }

    public void setSocialInsuranceNumber(String socialInsuranceNumber) {
        this.socialInsuranceNumber = socialInsuranceNumber;
    }

    public String getStreetAddress() {
        return StreetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        StreetAddress = streetAddress;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvinceOrState() {
        return provinceOrState;
    }

    public void setProvinceOrState(String provinceOrState) {
        this.provinceOrState = provinceOrState;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPrimaryTelephoneNumber() {
        return primaryTelephoneNumber;
    }

    public void setPrimaryTelephoneNumber(String primaryTelephoneNumber) {
        this.primaryTelephoneNumber = primaryTelephoneNumber;
    }

    public String getSecondaryTelephoneNumber() {
        return secondaryTelephoneNumber;
    }

    public void setSecondaryTelephoneNumber(String secondaryTelephoneNumber) {
        this.secondaryTelephoneNumber = secondaryTelephoneNumber;
    }

    public String getLanguagePreference() {
        return languagePreference;
    }

    public void setLanguagePreference(String languagePreference) {
        this.languagePreference = languagePreference;
    }

    public Claim getClaim() {
        return claim;
    }

    public void setClaim(Claim claim) {
        this.claim = claim;
    }

    @Override
    public String toString() {
        return "PersonalInformation{" +
                "personalInfoId=" + personalInfoId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", socialInsuranceNumber=" + socialInsuranceNumber +
                ", StreetAddress='" + StreetAddress + '\'' +
                ", apartment=" + apartment +
                ", city='" + city + '\'' +
                ", provinceOrState='" + provinceOrState + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", country='" + country + '\'' +
                ", primaryTelephoneNumber='" + primaryTelephoneNumber + '\'' +
                ", secondaryTelephoneNumber='" + secondaryTelephoneNumber + '\'' +
                ", languagePreference='" + languagePreference + '\'' +
                '}';
    }

	public String getOtherLanguagePreference() {
		return otherLanguagePreference;
	}

	public void setOtherLanguagePreference(String otherLanguagePreference) {
		this.otherLanguagePreference = otherLanguagePreference;
	}

}
