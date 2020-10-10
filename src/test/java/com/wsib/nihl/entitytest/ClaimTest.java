package com.wsib.nihl.entitytest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.wsib.nihl.constants.CLAIM_STATUS;
import com.wsib.nihl.entity.Claim;
import com.wsib.nihl.entity.ClaimDocuments;
import com.wsib.nihl.entity.EmploymentInformation;
import com.wsib.nihl.entity.HealthCareProviderInformation;
import com.wsib.nihl.entity.PersonalInformation;


public class ClaimTest {

	Claim claim = new Claim();
	private final int claimId = 1;
	private final String emailId = "user1@test.com";
	private final String userName = "user1";
	private final String emailWithConsent = "emailWithConsent";
	private final String referenceNumber = "123456";
	private final LocalDateTime localDateTime = LocalDateTime.now();
	List<ClaimDocuments> claimDocumentsList=Arrays.asList(new ClaimDocuments());

	@BeforeEach
	void beforeEach() {

		claim.setClaimId(claimId);
		claim.setConsentGivenBy(userName);
		claim.setCreationDate(localDateTime);
		claim.setEmail(emailId);
		claim.setEmailWithConsent(emailWithConsent);
		claim.setReferenceNumber(referenceNumber);
		claim.setSubmitionDate(localDateTime);
		claim.setEmail(emailId);
		claim.setStatus("PENDING");
		claim.setPersonalInformation(new PersonalInformation());
		claim.setHealthCareProviderInformation(new HealthCareProviderInformation());
		claim.setEmploymentInformation(new EmploymentInformation());
		claim.setClaimDocumentsList(claimDocumentsList);
	}

	@Test
	void testGettersSetters() {
		assertAll(() -> assertEquals(claimId, claim.getClaimId()),
				() -> assertEquals(userName, claim.getConsentGivenBy()),
				() -> assertEquals(localDateTime, claim.getCreationDate()),
				() -> assertEquals(referenceNumber, claim.getReferenceNumber()),
				() -> assertEquals(CLAIM_STATUS.PENDING.toString(), claim.getStatus()),
				() -> assertEquals(localDateTime, claim.getSubmitionDate()),
				() -> assertEquals(emailId, claim.getEmail()),
				() -> assertThat(claim.getPersonalInformation() instanceof PersonalInformation),
				() -> assertThat(claim.getHealthCareProviderInformation() instanceof HealthCareProviderInformation),
				() -> assertThat(claim.getEmploymentInformation() instanceof EmploymentInformation),
				() -> assertEquals(emailWithConsent, claim.getEmailWithConsent()),
				() -> assertEquals(claimDocumentsList, claim.getClaimDocumentsList()),
				() -> assertEquals(claim.toString(), claim.toString()));
	};
}
