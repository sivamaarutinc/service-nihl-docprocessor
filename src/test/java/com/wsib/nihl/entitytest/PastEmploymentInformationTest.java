package com.wsib.nihl.entitytest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.wsib.nihl.entity.EmploymentInformation;
import com.wsib.nihl.entity.PastEmploymentInformation;
import com.wsib.nihl.entity.ToolsUsed;


public class PastEmploymentInformationTest {

	PastEmploymentInformation pastEmploymentInformation=new PastEmploymentInformation();
	private final LocalDate date = LocalDate.now();
	List<ToolsUsed> list = Arrays.asList(new ToolsUsed());
	EmploymentInformation employmentInformation=new EmploymentInformation();
	
	@BeforeEach
	void beforeEach() {

		pastEmploymentInformation.setCountry("country");
		pastEmploymentInformation.setEmployerAddress("employerAddress");
		pastEmploymentInformation.setEmployerInBusiness(true);
		pastEmploymentInformation.setEmployerName("employerName");
		pastEmploymentInformation.setEmployerPhoneNumber("123456");
		pastEmploymentInformation.setEmploymentEndDate(date);
		pastEmploymentInformation.setEmploymentStartDate(date);
		pastEmploymentInformation.setPastEmploymentInfoId(1);
		pastEmploymentInformation.setJobTitle("jobTitle");
		pastEmploymentInformation.setProvinceOrState("province");
		pastEmploymentInformation.setToolsUsedList(list);
		pastEmploymentInformation.setEmploymentInformation(employmentInformation);
	}

	@Test
	void testGetters() {
		assertAll(() -> assertEquals("country", pastEmploymentInformation.getCountry()),
				() -> assertEquals("employerAddress", pastEmploymentInformation.getEmployerAddress()),
				() -> assertEquals(true, pastEmploymentInformation.getEmployerInBusiness()),
				() -> assertEquals("employerName", pastEmploymentInformation.getEmployerName()),
				() -> assertEquals("123456", pastEmploymentInformation.getEmployerPhoneNumber()),
				() -> assertEquals(date, pastEmploymentInformation.getEmploymentEndDate()),
				() -> assertEquals(date, pastEmploymentInformation.getEmploymentStartDate()),
				() -> assertEquals(1, pastEmploymentInformation.getPastEmploymentInfoId()),
				() -> assertEquals("jobTitle", pastEmploymentInformation.getJobTitle()),
				() -> assertEquals("province", pastEmploymentInformation.getProvinceOrState()),
				() -> assertEquals(list, pastEmploymentInformation.getToolsUsedList()),
				() -> assertEquals(employmentInformation, pastEmploymentInformation.getEmploymentInformation()),
				() -> assertEquals(pastEmploymentInformation.toString(), pastEmploymentInformation.toString())

		);
	}
}
