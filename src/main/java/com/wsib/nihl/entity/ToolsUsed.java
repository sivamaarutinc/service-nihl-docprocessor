package com.wsib.nihl.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class ToolsUsed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int toolsUsedId;


    @Size(max = 250, message="Only 250 characters are allowed")
    private String toolName;

    @Size(max = 5, message="Only 4 chars is allowed")
    private String HoursUsed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "past_employment_info_id", referencedColumnName = "pastEmploymentInfoId")
    @JsonIgnore
    private PastEmploymentInformation pastEmploymentInformation;

    public int getToolsUsedId() {
        return toolsUsedId;
    }

    public void setToolsUsedId(int toolsUsedId) {
        this.toolsUsedId = toolsUsedId;
    }

    public String getToolName() {
        return toolName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public String getHoursUsed() {
        return HoursUsed;
    }

    public void setHoursUsed(String hoursUsed) {
        HoursUsed = hoursUsed;
    }

    public PastEmploymentInformation getPastEmploymentInformation() {
        return pastEmploymentInformation;
    }

    public void setPastEmploymentInformation(PastEmploymentInformation pastEmploymentInformation) {
        this.pastEmploymentInformation = pastEmploymentInformation;
    }

    @Override
    public String toString() {
        return "ToolsUsed{" +
                "toolsUsedId=" + toolsUsedId +
                ", toolName='" + toolName + '\'' +
                ", HoursUsed=" + HoursUsed +
                '}';
    }
}
