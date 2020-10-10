package com.wsib.nihl.entitytest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.wsib.nihl.entity.Claim;
import com.wsib.nihl.entity.PersonalInformation;


public class PersonalInformationTest {

	PersonalInformation personalInformation=new PersonalInformation();
	
	private final LocalDate date = LocalDate.now();
	Claim claim=new Claim();

	@BeforeEach
	void beforeEach() {
		personalInformation.setPersonalInfoId(1);
		personalInformation.setFirstName("user1");
		personalInformation.setLastName("lastName");
		personalInformation.setDateOfBirth(date);
		personalInformation.setSocialInsuranceNumber("123546");
		personalInformation.setStreetAddress("address");
		personalInformation.setApartment("apartment");
		personalInformation.setCity("city");
		personalInformation.setProvinceOrState("provinceOrState");
		personalInformation.setPostalCode("postalCode");
		personalInformation.setCountry("country");
		personalInformation.setPrimaryTelephoneNumber("123456");
		personalInformation.setSecondaryTelephoneNumber("56789");
		personalInformation.setOtherLanguagePreference("FR");
		personalInformation.setClaim(claim);
	}

	@Test
	void testGetters() {
		assertAll(() -> assertEquals("user1", personalInformation.getFirstName()),
				() -> assertEquals("lastName", personalInformation.getLastName()),
				() -> assertEquals(date, personalInformation.getDateOfBirth()),
				() -> assertEquals("123546", personalInformation.getSocialInsuranceNumber()),
				() -> assertEquals("address", personalInformation.getStreetAddress()),
				() -> assertEquals("apartment", personalInformation.getApartment()),
				() -> assertEquals("city", personalInformation.getCity()),
				() -> assertEquals("provinceOrState", personalInformation.getProvinceOrState()),
				() -> assertEquals("postalCode", personalInformation.getPostalCode()),
				() -> assertEquals("country", personalInformation.getCountry()),
				() -> assertEquals("123456", personalInformation.getPrimaryTelephoneNumber()),
				() -> assertEquals("56789", personalInformation.getSecondaryTelephoneNumber()),
				() -> assertEquals("FR", personalInformation.getOtherLanguagePreference()),
				() -> assertEquals(1, personalInformation.getPersonalInfoId()),
				() -> assertEquals(personalInformation.toString(), personalInformation.toString()),
				() -> assertEquals(claim, personalInformation.getClaim())

		);
	}
}
