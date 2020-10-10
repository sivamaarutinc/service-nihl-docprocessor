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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class PastEmploymentInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pastEmploymentInfoId;

    private Boolean isEmployerInBusiness;
    private LocalDate employmentStartDate;
    private LocalDate employmentEndDate;

    @Size(max = 250, message="Only 250 characters are allowed")
    private String employerName;

    @Size(max = 30, min= 10, message="Only 10-15 chars are allowed")
    private String employerPhoneNumber;

    @Size(max = 250, message="Only 250 characters are allowed")
    private String employerAddress;

    @Size(max = 50, message="Only 50 characters are allowed")
    private String jobTitle;
    
    private String country;

    private String provinceOrState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employment_info_id", referencedColumnName = "employmentInfoId")
    @JsonIgnore
    private EmploymentInformation employmentInformation;
    
    @OneToMany(mappedBy = "pastEmploymentInformation",orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ToolsUsed> toolsUsedList;

    public int getPastEmploymentInfoId() {
        return pastEmploymentInfoId;
    }

    public void setPastEmploymentInfoId(int pastEmploymentInfoId) {
        this.pastEmploymentInfoId = pastEmploymentInfoId;
    }

    public Boolean getEmployerInBusiness() {
        return isEmployerInBusiness;
    }

    public void setEmployerInBusiness(Boolean employerInBusiness) {
        isEmployerInBusiness = employerInBusiness;
    }

    public LocalDate getEmploymentStartDate() {
        return employmentStartDate;
    }

    public void setEmploymentStartDate(LocalDate employmentStartDate) {
        this.employmentStartDate = employmentStartDate;
    }

    public LocalDate getEmploymentEndDate() {
        return employmentEndDate;
    }

    public void setEmploymentEndDate(LocalDate employmentEndDate) {
        this.employmentEndDate = employmentEndDate;
    }

    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    public String getEmployerAddress() {
        return employerAddress;
    }

    public void setEmployerAddress(String employerAddress) {
        this.employerAddress = employerAddress;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public EmploymentInformation getEmploymentInformation() {
        return employmentInformation;
    }

    public void setEmploymentInformation(EmploymentInformation employmentInformation) {
        this.employmentInformation = employmentInformation;
    }

    public List<ToolsUsed> getToolsUsedList() {
        return toolsUsedList;
    }

    public void setToolsUsedList(List<ToolsUsed> toolsUsedList) {
        this.toolsUsedList = toolsUsedList;
    }

    @Override
    public String toString() {
        return "PastEmploymentInformation{" +
                "pastEmploymentInfoId=" + pastEmploymentInfoId +
                ", isEmployerInBusiness=" + isEmployerInBusiness +
                ", employmentStartDate=" + employmentStartDate +
                ", employmentEndDate=" + employmentEndDate +
                ", employerName='" + employerName + '\'' +
                ", employerAddress='" + employerAddress + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", employmentInformation=" + employmentInformation +
                '}';
    }

	public String getEmployerPhoneNumber() {
		return employerPhoneNumber;
	}

	public void setEmployerPhoneNumber(String employerPhoneNumber) {
		this.employerPhoneNumber = employerPhoneNumber;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvinceOrState() {
		return provinceOrState;
	}

	public void setProvinceOrState(String provinceOrState) {
		this.provinceOrState = provinceOrState;
	}
}
