package com.wsib.nihl.entitytest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.wsib.nihl.entity.Claim;
import com.wsib.nihl.entity.HealthCareProviderInformation;

public class HealthCareProviderInformationTest {
	HealthCareProviderInformation healthCareProviderInformation = new HealthCareProviderInformation();
	private final LocalDate date = LocalDate.now();
	Claim claim = new Claim();

	@BeforeEach
	void beforeEach() {
		healthCareProviderInformation.setHealthCareInfoId(1);
		healthCareProviderInformation.setAudiogramClinicAddress("audiogramClinicAddress");
		healthCareProviderInformation.setAudiogramClinicName("audiogramClinicName");
		healthCareProviderInformation.setAudiogramClinicPhoneNumber("13465");
		healthCareProviderInformation.setDateOfFirstAudiogram(date);
		healthCareProviderInformation.setEntAppointmentDate(date);
		healthCareProviderInformation.setEntSpecialistAddress("entSpecialistAddress");
		healthCareProviderInformation.setEntSpecialistName("entSpecialistName");
		healthCareProviderInformation.setEntSpecialistPhoneNumber("123456");
		healthCareProviderInformation.setHasConstantRingingInEar(true);
		healthCareProviderInformation.setHasRingingInEar(true);
		healthCareProviderInformation.setHasSevereRingingInEar(false);
		healthCareProviderInformation.setHasVisitedEntSpecialist(false);
		healthCareProviderInformation.setHearingAidUsageDate(date);
		healthCareProviderInformation.setHearingLossNoticedYear("2015");
		healthCareProviderInformation.setRingingEarDuration("9");
		healthCareProviderInformation.setHasHearingAid(true);
		healthCareProviderInformation.setClaim(claim);
	}

	@Test
	void testGettersSetters() {
		assertAll(
				() -> assertEquals("audiogramClinicAddress", healthCareProviderInformation.getAudiogramClinicAddress()),
				() -> assertEquals("audiogramClinicName", healthCareProviderInformation.getAudiogramClinicName()),
				() -> assertEquals("13465", healthCareProviderInformation.getAudiogramClinicPhoneNumber()),
				() -> assertEquals(date, healthCareProviderInformation.getDateOfFirstAudiogram()),
				() -> assertEquals(date, healthCareProviderInformation.getEntAppointmentDate()),
				() -> assertEquals("entSpecialistAddress", healthCareProviderInformation.getEntSpecialistAddress()),
				() -> assertEquals("entSpecialistName", healthCareProviderInformation.getEntSpecialistName()),
				() -> assertEquals("123456", healthCareProviderInformation.getEntSpecialistPhoneNumber()),
				() -> assertEquals(true, healthCareProviderInformation.getHasConstantRingingInEar()),
				() -> assertEquals(true, healthCareProviderInformation.getHasRingingInEar()),
				() -> assertEquals(false, healthCareProviderInformation.getHasSevereRingingInEar()),
				() -> assertEquals(false, healthCareProviderInformation.getHasVisitedEntSpecialist()),
				() -> assertEquals(date, healthCareProviderInformation.getHearingAidUsageDate()),
				() -> assertEquals("2015", healthCareProviderInformation.getHearingLossNoticedYear()),
				() -> assertEquals("9", healthCareProviderInformation.getRingingEarDuration()),
				() -> assertEquals(true, healthCareProviderInformation.getHasHearingAid()),
				() -> assertEquals(healthCareProviderInformation.toString(), healthCareProviderInformation.toString()),
				() -> assertEquals(1, healthCareProviderInformation.getHealthCareInfoId()),
				() -> assertEquals(claim, healthCareProviderInformation.getClaim()));
	}
}
