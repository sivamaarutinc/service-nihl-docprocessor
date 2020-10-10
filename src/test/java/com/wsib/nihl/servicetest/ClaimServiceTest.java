package com.wsib.nihl.servicetest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wsib.nihl.constants.CLAIM_STATUS;
import com.wsib.nihl.entity.Claim;
import com.wsib.nihl.entity.ClaimDocuments;
import com.wsib.nihl.entity.EmploymentInformation;
import com.wsib.nihl.entity.HealthCareProviderInformation;
import com.wsib.nihl.entity.PastEmploymentInformation;
import com.wsib.nihl.entity.PersonalInformation;
import com.wsib.nihl.repository.ClaimRepository;
import com.wsib.nihl.service.ClaimService;

@ExtendWith(MockitoExtension.class)
public class ClaimServiceTest {

	@InjectMocks
	ClaimService claimService;

	@Mock
	ClaimRepository claimRepository;

	@Mock
	Validator validator;

	@Mock
	EntityManager entityManager;

	private final int claimId = 1;
	private final String emailId = "user1@test.com";
	private final String userName = "user1";
	private final String emailWithConsent = "abc@gmail.com";
	private final String referenceNumber = "123456";
	private final int healthCareInfoId = 1;
	private final int pastEmploymentIfoId = 1;

	@Test
	void testFindClaimByRefAndEmail() throws Exception {
		Claim claim = getClaim();
		when(claimRepository.findByReferenceNumberAndEmailAndStatus(Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString())).thenReturn(claim);
		assertEquals(claim, claimService.findClaimByRefAndEmail("123", "email@gmail.com", "PENDING", LocalDate.now()));
	}

	@Test
	void testFindClaimByRefAndEmailValidateCondition() throws Exception {
		Claim claim = getClaim();
		claim.getPersonalInformation().setDateOfBirth(LocalDate.now().minusYears(1));
		when(claimRepository.findByReferenceNumberAndEmailAndStatus(Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString())).thenReturn(claim);
		assertNull(claimService.findClaimByRefAndEmail("123", "email@gmail.com", "PENDING", LocalDate.now()));
	}

	@Test
	void testFindClaimByRefAndEmailNullPersonalInformation() throws Exception {
		Claim claim = getClaim();
		claim.setPersonalInformation(null);
		when(claimRepository.findByReferenceNumberAndEmailAndStatus(Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString())).thenReturn(claim);
		assertNull(claimService.findClaimByRefAndEmail("123", "email@gmail.com", "PENDING", LocalDate.now()));
	}

	@Test
	void testFindClaimByRefAndEmailNullDOB() throws Exception {
		Claim claim = getClaim();
		when(claimRepository.findByReferenceNumberAndEmailAndStatus(Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString())).thenReturn(claim);
		assertNull(claimService.findClaimByRefAndEmail("123", "email@gmail.com", "PENDING", null));
	}

	@Test
	void testFindClaimByRefAndEmailReturnsNull() throws Exception {
		when(claimRepository.findByReferenceNumberAndEmailAndStatus(Mockito.anyString(), Mockito.anyString(),
				Mockito.anyString())).thenReturn(null);
		assertNull(claimService.findClaimByRefAndEmail("123", "email@gmail.com", "PENDING", LocalDate.now()));
	}

	@Test
	void testFindClaimByRefAndEmailThrowsException() throws Exception {
		doThrow(new NullPointerException()).when(claimRepository)
				.findByReferenceNumberAndEmailAndStatus(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
		assertThrows(NullPointerException.class,
				() -> claimService.findClaimByRefAndEmail("123", "email@gmail.com", "PENDING", LocalDate.now()));
	}

	@Test
	void testFindByClaimId() throws Exception {
		Claim claim = getClaim();
		when(claimRepository.findById(claimId)).thenReturn(Optional.of(claim));
		assertEquals(claim, claimService.findByClaimId(claimId));
	}

	@Test
	void testFindByClaimIdNotPresent() throws Exception {
		when(claimRepository.findById(claimId)).thenReturn(Optional.empty());
		assertNull(claimService.findByClaimId(claimId));
	}

	@Test
	void testFindByClaimIdThrowsException() throws Exception {
		doThrow(new NullPointerException()).when(claimRepository).findById(claimId);
		assertThrows(NullPointerException.class, () -> claimService.findByClaimId(claimId));
	}

	@Test
	void testUpdateClaimStatusForFailedReturnTrue() {
		Claim claim = getClaim();
		when(claimRepository.findById(claimId)).thenReturn(Optional.of(claim));
		when(claimRepository.updateStatusForFailed(Mockito.anyInt(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(3);
		assertEquals(true, claimService.updateClaimStatusForFailed(1, "PENDING"));
	}

	@Test
	void testUpdateClaimStatusForFailedReturnFalse() {
		Claim claim = getClaim();
		when(claimRepository.findById(claimId)).thenReturn(Optional.of(claim));
		when(claimRepository.updateStatusForFailed(Mockito.anyInt(), Mockito.anyString(), Mockito.anyString()))
				.thenReturn(0);
		assertEquals(false, claimService.updateClaimStatusForFailed(1, "PENDING"));
	}

	@Test
	void testUpdateClaimStatusEmptyClaim() {
		when(claimRepository.findById(claimId)).thenReturn(Optional.empty());
		assertEquals(false, claimService.updateClaimStatusForFailed(1, "PENDING"));
	}

	@Test
	void testUpdateClaimStatusThrowsException() {
		when(claimRepository.findById(claimId)).thenThrow(IllegalArgumentException.class);
		assertEquals(false, claimService.updateClaimStatusForFailed(1, "PENDING"));
	}

	@Test
	void testUpdateClaimStatusTransferFail() {
		Claim claim = getClaim();
		when(claimRepository.findById(claimId)).thenReturn(Optional.of(claim));
		assertTrue(claimService.updateClaimStatus(claimId, CLAIM_STATUS.TRANSFER_FAILED.name()));
	}

	@Test
	void testUpdateClaimStatusTransferSuccess() {
		Claim claim = getClaim();
		when(claimRepository.findById(claimId)).thenReturn(Optional.of(claim));
		assertTrue(claimService.updateClaimStatus(claimId, CLAIM_STATUS.TRANSFER_SUCCESS.name()));
	}

	@Test
	void testUpdateClaimStatusPending() {
		Claim claim = getClaim();
		when(claimRepository.findById(claimId)).thenReturn(Optional.of(claim));
		assertTrue(claimService.updateClaimStatus(claimId, CLAIM_STATUS.PENDING.name()));
	}

	@Test
	void testUpdateClaimStatusWithNullClaim() {
		when(claimRepository.findById(claimId)).thenReturn(Optional.empty());
		assertNull(claimService.updateClaimStatus(claimId, CLAIM_STATUS.TRANSFER_SUCCESS.name()));
	}

	@Test
	void testUpdateClaimStatusThrowError() {
		when(claimRepository.findById(claimId)).thenThrow(NullPointerException.class);
		assertNull(claimService.updateClaimStatus(claimId, CLAIM_STATUS.TRANSFER_SUCCESS.name()));
	}

	public Claim getClaim() {
		Claim claim = new Claim();
		claim.setClaimId(1);
		claim.setConsentGivenBy(userName);
		claim.setCreationDate(LocalDateTime.now());
		claim.setEmail(emailId);
		claim.setEmailWithConsent(emailWithConsent);
		claim.setReferenceNumber(referenceNumber);
		claim.setStatus(CLAIM_STATUS.PENDING.toString());
		claim.setSubmitionDate(LocalDateTime.now());
		claim.setPersonalInformation(getPersonalInformation());
		claim.setHealthCareProviderInformation(getHealthCareProviderInformation());
		claim.setEmploymentInformation(getEmploymentInformation());
		claim.setClaimDocumentsList(getClaimDocumentsList());
		return claim;

	}

	public PersonalInformation getPersonalInformation() {
		PersonalInformation personalInformation = new PersonalInformation();
		personalInformation.setFirstName("user1");
		personalInformation.setLastName("lastName");
		personalInformation.setDateOfBirth(LocalDate.now());
		personalInformation.setSocialInsuranceNumber("123546");
		personalInformation.setStreetAddress("address");
		personalInformation.setApartment("apartment");
		personalInformation.setCity("city");
		personalInformation.setProvinceOrState("provinceOrState");
		personalInformation.setPostalCode("postalCode");
		personalInformation.setCity("country");
		personalInformation.setPrimaryTelephoneNumber("123456");
		personalInformation.setSecondaryTelephoneNumber("56789");
		personalInformation.setOtherLanguagePreference("FR");
		return personalInformation;
	}

	public HealthCareProviderInformation getHealthCareProviderInformation() {

		HealthCareProviderInformation healthCareProviderInformation = new HealthCareProviderInformation();
		healthCareProviderInformation.setAudiogramClinicAddress("audiogramClinicAddress");
		healthCareProviderInformation.setAudiogramClinicName("audiogramClinicName");
		healthCareProviderInformation.setAudiogramClinicPhoneNumber("12345678910");
		healthCareProviderInformation.setDateOfFirstAudiogram(LocalDate.now());
		healthCareProviderInformation.setEntAppointmentDate(LocalDate.now());
		healthCareProviderInformation.setEntSpecialistAddress("entSpecialistAddress");
		healthCareProviderInformation.setEntSpecialistName("entSpecialistName");
		healthCareProviderInformation.setEntSpecialistPhoneNumber("12345678910");
		healthCareProviderInformation.setHasConstantRingingInEar(true);
		healthCareProviderInformation.setHasRingingInEar(true);
		healthCareProviderInformation.setHasSevereRingingInEar(false);
		healthCareProviderInformation.setHasVisitedEntSpecialist(false);
		healthCareProviderInformation.setHearingAidUsageDate(LocalDate.now());
		healthCareProviderInformation.setHearingLossNoticedYear("1990");
		healthCareProviderInformation.setRingingEarDuration("5");
		healthCareProviderInformation.setHealthCareInfoId(healthCareInfoId);
		return healthCareProviderInformation;
	}

	public EmploymentInformation getEmploymentInformation() {
		EmploymentInformation employmentInformation = new EmploymentInformation();
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
		employmentInformation.setRetirementDate(LocalDate.now());
		employmentInformation.setSelfEmpBusinessAddress("selfEmpBusinessAddress");
		employmentInformation.setSelfEmpBusinessName("selfEmpBusinessName");
		employmentInformation.setSelfEmpEndDate(LocalDate.now());
		employmentInformation.setSelfEmpHasInsurance(false);
		employmentInformation.setSelfEmpStartDate(LocalDate.now());
		employmentInformation.setPastEmploymentInformationList(getPastEmployeeInformationList());
		return employmentInformation;
	}

	public List<PastEmploymentInformation> getPastEmployeeInformationList() {
		List<PastEmploymentInformation> list = new ArrayList<PastEmploymentInformation>();
		list.add(getPastEmploymentInformation());
		return list;
	}

	public ClaimDocuments getClaimDocuments() {
		ClaimDocuments claimDocuments = new ClaimDocuments();
		claimDocuments.setDocumentName("file1");
		claimDocuments.setDocumentSize("documentSize");
		claimDocuments.setDocumentType("png");
		claimDocuments.setFormId("1");
		claimDocuments.setReferenceId(1);
		claimDocuments.setDocument("content".getBytes());
		Claim claim = new Claim();
		claimDocuments.setClaim(claim);
		claimDocuments.setClaimDocumentId(1);
		return claimDocuments;
	}

	public List<ClaimDocuments> getClaimDocumentsList() {
		List<ClaimDocuments> claimDocuments = new ArrayList<>();
		claimDocuments.add(getClaimDocuments());
		return claimDocuments;
	}

	public PastEmploymentInformation getPastEmploymentInformation() {
		PastEmploymentInformation pastEmploymentInformation = new PastEmploymentInformation();
		pastEmploymentInformation.setCountry("country");
		pastEmploymentInformation.setEmployerAddress("employerAddress");
		pastEmploymentInformation.setEmployerInBusiness(true);
		pastEmploymentInformation.setEmployerName("employerName");
		pastEmploymentInformation.setEmployerPhoneNumber("123456");
		pastEmploymentInformation.setEmploymentEndDate(LocalDate.now());
		pastEmploymentInformation.setEmploymentStartDate(LocalDate.now());
		pastEmploymentInformation.setJobTitle("jobTitle");
		pastEmploymentInformation.setProvinceOrState("province");
		pastEmploymentInformation.setPastEmploymentInfoId(pastEmploymentIfoId);
		pastEmploymentInformation.setPastEmploymentInfoId(1);
		return pastEmploymentInformation;
	}

}
