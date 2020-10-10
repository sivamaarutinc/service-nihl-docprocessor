package com.wsib.nihl.entitytest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.wsib.nihl.entity.Claim;
import com.wsib.nihl.entity.EmploymentInformation;
import com.wsib.nihl.entity.PastEmploymentInformation;


public class EmploymentInformationTest {

	EmploymentInformation employmentInformation=new EmploymentInformation();
	Claim claim=new Claim();
	PastEmploymentInformation pastEmploymentInformation=new PastEmploymentInformation();
	List<PastEmploymentInformation> employmentInformations=new ArrayList<>();
	
	private final LocalDate date = LocalDate.now();
	@BeforeEach
	void beforEach()
	{
		employmentInformations.add(pastEmploymentInformation);
		employmentInformation.setEmploymentInfoId(1);
		employmentInformation.setCurrentEmployerAddress("currentEmployerAddress");
		employmentInformation.setCurrentEmployerIsHazardous(true);
		employmentInformation.setCurrentEmployerName("currentEmployerName");
		employmentInformation.setCurrentEmployerPhoneNumber("123456");
		employmentInformation.setCurrentlyEmployed(false);
		employmentInformation.setCurrentSituation("currentSituation");
		employmentInformation.setHasEverBeenSelfEmployed(false);
		employmentInformation.setHasRetired(false);
		employmentInformation.setHasUsedNoisyEquipmentOutOfWork(false);
		employmentInformation.setNoisyEquipmentDetails("noisyEquipmentDetails");
		employmentInformation.setRetirementDate(date);
		employmentInformation.setSelfEmpBusinessAddress("selfEmpBusinessAddress");
		employmentInformation.setSelfEmpBusinessName("selfEmpBusinessName");
		employmentInformation.setSelfEmpEndDate(date);
		employmentInformation.setSelfEmpHasInsurance(false);
		employmentInformation.setSelfEmpStartDate(date);
		employmentInformation.setHasEverBeenSelfEmployed(true);
		employmentInformation.setCurrentSituation("currentSituation");
		employmentInformation.setClaim(claim);
		employmentInformation.setPastEmploymentInformationList(employmentInformations);
	}
	
	@Test
	void testGettersSetters() {
		assertAll(() -> assertEquals("currentEmployerAddress", employmentInformation.getCurrentEmployerAddress()),
				() -> assertEquals(true, employmentInformation.getCurrentEmployerIsHazardous()),
				() -> assertEquals("currentEmployerName", employmentInformation.getCurrentEmployerName()),
				() -> assertEquals("123456", employmentInformation.getCurrentEmployerPhoneNumber()),
				() -> assertEquals(false, employmentInformation.getCurrentlyEmployed()),
				() -> assertEquals(false, employmentInformation.getHasRetired()),
				() -> assertEquals(false, employmentInformation.getHasUsedNoisyEquipmentOutOfWork()),
				() -> assertEquals("noisyEquipmentDetails", employmentInformation.getNoisyEquipmentDetails()),
				() -> assertEquals("noisyEquipmentDetails", employmentInformation.getNoisyEquipmentDetails()),
				() -> assertEquals(date, employmentInformation.getRetirementDate()),
				() -> assertEquals("selfEmpBusinessAddress", employmentInformation.getSelfEmpBusinessAddress()),
				() -> assertEquals(date, employmentInformation.getSelfEmpEndDate()),
				() -> assertEquals(false, employmentInformation.getSelfEmpHasInsurance()),
				() -> assertEquals(date, employmentInformation.getSelfEmpStartDate()),
				() -> assertEquals(true, employmentInformation.getHasEverBeenSelfEmployed()),
				() -> assertEquals("currentSituation", employmentInformation.getCurrentSituation()),
				() -> assertEquals(employmentInformation.toString(), employmentInformation.toString()),
				() -> assertEquals("selfEmpBusinessName", employmentInformation.getSelfEmpBusinessName()),
				() -> assertEquals(1, employmentInformation.getEmploymentInfoId()),
				() -> assertEquals(claim, employmentInformation.getClaim()),
				() -> assertEquals(employmentInformations, employmentInformation.getPastEmploymentInformationList())

		);
	}
}
