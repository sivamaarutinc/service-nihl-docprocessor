package com.wsib.nihl.servicetest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

import com.wsib.nihl.config.ApplicationProperties;
import com.wsib.nihl.constants.CLAIM_STATUS;
import com.wsib.nihl.entity.Claim;
import com.wsib.nihl.entity.ClaimDocuments;
import com.wsib.nihl.entity.EmploymentInformation;
import com.wsib.nihl.entity.HealthCareProviderInformation;
import com.wsib.nihl.entity.PastEmploymentInformation;
import com.wsib.nihl.entity.PersonalInformation;
import com.wsib.nihl.entity.ToolsUsed;
import com.wsib.nihl.model.DocumentRequest;
import com.wsib.nihl.service.ClaimService;
import com.wsib.nihl.service.DocGenerationService;
import com.wsib.nihl.service.FileUploadService;
import com.wsib.nihl.util.PDFAConverter;
import com.wsib.nihl.util.ZipUtility;

@SpringBootTest(classes = ApplicationProperties.class)
@ExtendWith(MockitoExtension.class)
class DocGenerationServiceTest {

	@Autowired
	ApplicationProperties applicationProperties;

	@InjectMocks
	DocGenerationService docGenerationService;

	@Mock
	ClaimService claimService;

	@Mock
	ITemplateEngine templateEngine;

	@Mock
	ApplicationProperties applicationPropertiesMock;

	@Spy
	ZipUtility zipUtil;

	@Mock
	FileUtils fileUtils;

	@Mock
	PDFAConverter converter;

	@Mock
	FileUploadService ftpUploader;

	MockMultipartFile mockMultipartFile = new MockMultipartFile("file1.txt", "hello".getBytes());

	DocGenerationService docGenerationService2 = new DocGenerationService();

	private final int claimId = 1;
	private final String emailId = "user1@test.com";
	private final String userName = "user1";
	private final String emailWithConsent = "emailWithConsent";
	private final String referenceNumber = "123456";
	private final int healthCareInfoId = 1;
	private final int pastEmploymentIfoId = 1;

	@Test
	void testSendDocuments() throws Exception {
		Path pathRoot = Paths.get(applicationProperties.getDocRoot());
		File file = pathRoot.toFile();
		file.mkdirs();
		DocumentRequest documentRequest = getDocumentRequest();
		documentRequest.setClaimId(claimId);
		String htmlContentToRender = getTemplateFR();
		Claim claim = getClaim();
		List<PastEmploymentInformation> pastEmploymentInformationList = Arrays.asList(getPastEmploymentInformation());
		claim.getEmploymentInformation().setPastEmploymentInformationList(pastEmploymentInformationList);
		claim.setCommunicationLanguage("FR");
		claim.setPersonalInformation(getPersonalInformation());
		when(claimService.findByClaimId(Mockito.anyInt())).thenReturn(claim);
		when(applicationPropertiesMock.getDocPath()).thenReturn(applicationProperties.getDocPath());
		when(applicationPropertiesMock.getPdfPath()).thenReturn(applicationProperties.getPdfPath());
		when(templateEngine.process(Mockito.anyString(), Mockito.any(Context.class))).thenReturn(htmlContentToRender);
		when(applicationPropertiesMock.getDocRoot()).thenReturn(applicationProperties.getDocRoot());
		when(applicationPropertiesMock.getPathFormat()).thenReturn(applicationProperties.getPathFormat());
		assertEquals("sent", docGenerationService.sendDocuments(documentRequest));
	}

	@Test
	void testSendDocumentsNullClaimId() throws Exception {
		Path pathRoot = Paths.get(applicationProperties.getDocRoot());
		File file = pathRoot.toFile();
		file.mkdirs();
		DocumentRequest documentRequest = getDocumentRequest();
		documentRequest.setClaimId(claimId);
		String htmlContentToRender = getTemplateFR();
		Claim claim = getClaim();
		documentRequest.setClaimId(null);
		List<PastEmploymentInformation> pastEmploymentInformationList = Arrays.asList(getPastEmploymentInformation());
		claim.getEmploymentInformation().setPastEmploymentInformationList(pastEmploymentInformationList);
		claim.setCommunicationLanguage("FR");
		claim.setPersonalInformation(getPersonalInformation());
		when(claimService.findClaimByRefAndEmail(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(),
				Mockito.any(LocalDate.class))).thenReturn(claim);
		when(applicationPropertiesMock.getDocPath()).thenReturn(applicationProperties.getDocPath());
		when(applicationPropertiesMock.getPdfPath()).thenReturn(applicationProperties.getPdfPath());
		when(templateEngine.process(Mockito.anyString(), Mockito.any(Context.class))).thenReturn(htmlContentToRender);
		when(applicationPropertiesMock.getDocRoot()).thenReturn(applicationProperties.getDocRoot());
		when(applicationPropertiesMock.getPathFormat()).thenReturn(applicationProperties.getPathFormat());
		assertEquals("sent", docGenerationService.sendDocuments(documentRequest));
	}

	/*
	 * @Test void testSendDocumentsDocumentListEmpty() throws Exception {
	 * 
	 * DocumentRequest documentRequest = getDocumentRequest();
	 * documentRequest.setClaimId(claimId); String htmlContentToRender =
	 * getTemplateFR(); Claim claim = getClaim(); List<PastEmploymentInformation>
	 * pastEmploymentInformationList =
	 * Arrays.asList(getPastEmploymentInformation());
	 * claim.getEmploymentInformation().setPastEmploymentInformationList(
	 * pastEmploymentInformationList); claim.setCommunicationLanguage("FR");
	 * claim.setPersonalInformation(getPersonalInformation());
	 * claim.setClaimDocumentsList(new ArrayList<>());
	 * when(claimService.findByClaimId(Mockito.anyInt())).thenReturn(claim);
	 * when(applicationPropertiesMock.getDocPath()).thenReturn(applicationProperties
	 * .getDocPath());
	 * when(applicationPropertiesMock.getPdfPath()).thenReturn(applicationProperties
	 * .getPdfPath()); when(templateEngine.process(Mockito.anyString(),
	 * Mockito.any(Context.class))).thenReturn(htmlContentToRender);
	 * when(applicationPropertiesMock.getDocRoot()).thenReturn(applicationProperties
	 * .getDocRoot()); when(applicationPropertiesMock.getPathFormat()).thenReturn(
	 * applicationProperties.getPathFormat()); assertEquals("sent",
	 * docGenerationService.sendDocuments(documentRequest)); }
	 * 
	 * @Test void testSendDocumentsPersonalAndHelthInformationWithNullValues()
	 * throws Exception {
	 * 
	 * DocumentRequest documentRequest = getDocumentRequest();
	 * documentRequest.setClaimId(claimId); String htmlContentToRender =
	 * getTemplateFR(); Claim claim = getClaim(); List<PastEmploymentInformation>
	 * pastEmploymentInformationList =
	 * Arrays.asList(getPastEmploymentInformation());
	 * claim.getEmploymentInformation().setPastEmploymentInformationList(
	 * pastEmploymentInformationList); claim.setCommunicationLanguage("FR");
	 * claim.setPersonalInformation(new PersonalInformation());
	 * claim.setHealthCareProviderInformation(new HealthCareProviderInformation());
	 * // claim.setEmploymentInformation(new EmploymentInformation());
	 * when(claimService.findByClaimId(Mockito.anyInt())).thenReturn(claim);
	 * when(applicationPropertiesMock.getDocPath()).thenReturn(applicationProperties
	 * .getDocPath());
	 * when(applicationPropertiesMock.getPdfPath()).thenReturn(applicationProperties
	 * .getPdfPath()); when(templateEngine.process(Mockito.anyString(),
	 * Mockito.any(Context.class))).thenReturn(htmlContentToRender);
	 * when(applicationPropertiesMock.getDocRoot()).thenReturn(applicationProperties
	 * .getDocRoot()); when(applicationPropertiesMock.getPathFormat()).thenReturn(
	 * applicationProperties.getPathFormat()); assertEquals("sent",
	 * docGenerationService.sendDocuments(documentRequest)); }
	 * 
	 * @Test void testSendDocumentsPersonalInformationWithEmtyValue() throws
	 * Exception {
	 * 
	 * DocumentRequest documentRequest = getDocumentRequest();
	 * documentRequest.setClaimId(claimId); PersonalInformation personalInformation
	 * = getPersonalInformation(); personalInformation.setSocialInsuranceNumber("");
	 * String htmlContentToRender = getTemplateFR(); Claim claim = getClaim();
	 * List<PastEmploymentInformation> pastEmploymentInformationList =
	 * Arrays.asList(getPastEmploymentInformation());
	 * claim.getEmploymentInformation().setPastEmploymentInformationList(
	 * pastEmploymentInformationList); claim.setCommunicationLanguage("FR");
	 * claim.setPersonalInformation(personalInformation);
	 * claim.getClaimDocumentsList().get(0).setDocumentType("Audiogram");
	 * claim.getClaimDocumentsList().get(0).setDocumentName(null);
	 * claim.setHealthCareProviderInformation(new HealthCareProviderInformation());
	 * claim.getEmploymentInformation().getPastEmploymentInformationList().get(0).
	 * setToolsUsedList(getToolsUseds()); claim.setClaimDocumentsList(null);
	 * when(claimService.findByClaimId(Mockito.anyInt())).thenReturn(claim);
	 * when(applicationPropertiesMock.getDocPath()).thenReturn(applicationProperties
	 * .getDocPath());
	 * when(applicationPropertiesMock.getPdfPath()).thenReturn(applicationProperties
	 * .getPdfPath()); when(templateEngine.process(Mockito.anyString(),
	 * Mockito.any(Context.class))).thenReturn(htmlContentToRender);
	 * when(applicationPropertiesMock.getDocRoot()).thenReturn(applicationProperties
	 * .getDocRoot()); when(applicationPropertiesMock.getPathFormat()).thenReturn(
	 * applicationProperties.getPathFormat()); assertEquals("sent",
	 * docGenerationService.sendDocuments(documentRequest)); }
	 */

	/*
	 * @Test void testSendDocumentsRefIdAndHelCareIdNotSame() throws Exception {
	 * DocumentRequest documentRequest = getDocumentRequest(); String
	 * htmlContentToRender = getTemplateFR(); Claim claim = getClaim();
	 * claim.getClaimDocumentsList().get(0).setReferenceId(5);
	 * List<PastEmploymentInformation> pastEmploymentInformationList =
	 * Arrays.asList(getPastEmploymentInformation());
	 * claim.getEmploymentInformation().setPastEmploymentInformationList(
	 * pastEmploymentInformationList); claim.setCommunicationLanguage("FR");
	 * claim.setPersonalInformation(getPersonalInformation());
	 * claim.getEmploymentInformation().getPastEmploymentInformationList().get(0).
	 * setPastEmploymentInfoId(5);
	 * when(applicationPropertiesMock.getDocPath()).thenReturn(applicationProperties
	 * .getDocPath());
	 * when(applicationPropertiesMock.getPdfPath()).thenReturn(applicationProperties
	 * .getPdfPath());
	 * when(claimService.findByClaimId(documentRequest.getClaimId())).thenReturn(
	 * claim); when(templateEngine.process(Mockito.anyString(),
	 * Mockito.any(Context.class))).thenReturn(htmlContentToRender);
	 * when(applicationPropertiesMock.getDocRoot()).thenReturn(applicationProperties
	 * .getDocRoot()); when(applicationPropertiesMock.getPathFormat()).thenReturn(
	 * applicationProperties.getPathFormat()); assertEquals("sent",
	 * docGenerationService.sendDocuments(documentRequest)); }
	 */

	@Test
	void testSendDocumentsWithNullBirthday() throws Exception {
		DocumentRequest documentRequest = getDocumentRequest();
		documentRequest.setClaimId(null);
		assertNull(docGenerationService.sendDocuments(documentRequest));
	}

	@Test
	void testSendDocumentsWithNullReferenceNumber() throws Exception {
		DocumentRequest documentRequest = getDocumentRequest();
		documentRequest.setReferenceNumber(null);
		documentRequest.setClaimId(null);
		assertNull(docGenerationService.sendDocuments(documentRequest));
	}

	@Test
	void testSendDocumentsWithNullEmailId() throws Exception {
		DocumentRequest documentRequest = getDocumentRequest();
		documentRequest.setClaimId(null);
		assertNull(docGenerationService.sendDocuments(documentRequest));
	}

	@Test
	void testSendDocumentsWithNullStatus() throws Exception {
		DocumentRequest documentRequest = getDocumentRequest();
		documentRequest.setClaimId(null);
		assertNull(docGenerationService.sendDocuments(documentRequest));
	}

	@Test
	void testSendDocumentsWithNullClaimId() throws Exception {
		DocumentRequest documentRequest = getDocumentRequest();
		String htmlContentToRender = getTemplateFR();
		Claim claim = getClaim();
		documentRequest.setClaimId(null);
		List<PastEmploymentInformation> pastEmploymentInformationList = Arrays.asList(getPastEmploymentInformation());
		claim.getEmploymentInformation().setPastEmploymentInformationList(pastEmploymentInformationList);
		claim.setCommunicationLanguage("EN");
		claim.setPersonalInformation(getPersonalInformation());
		assertNull(docGenerationService.sendDocuments(documentRequest));
	}

	@Test
	void testSendDocumentsThrowsIOException() throws Exception {
		DocumentRequest documentRequest = getDocumentRequest();
		documentRequest.setClaimId(claimId);
		Claim claim = getClaim();
		File file = new File(applicationProperties.getDocPath());
		file.mkdir();
		when(claimService.findByClaimId(documentRequest.getClaimId())).thenReturn(claim);
		assertNull(docGenerationService.sendDocuments(documentRequest));

	}

	@Test
	void testGenerateXML() throws IOException, JAXBException, ParseException {

		Claim claim = getClaim();
		String docName = "EC05" + getDateTime();
		String path = applicationProperties.getDocPath() + applicationProperties.getPathFormat() + docName + "00";
		docGenerationService.generateXML(claim, path, docName);
	}

//	Path issue
//	@Test
//	void testGenerateXMLEqualReferenceAndHelthcareInfoId() throws IOException, JAXBException, ParseException {
//		Claim claim = getClaim();
//		claim.setClaimDocumentsList(null);
//		claim.getHealthCareProviderInformation().setHealthCareInfoId(1);
//		ClaimDocuments claimDocuments = getClaimDocuments();
//		claimDocuments.setFormId("3275");
//		claimDocuments.setReferenceId(1);
//		claimDocuments.setDocument(mockMultipartFile.getBytes());
//		claimDocuments.setDocumentName("file1.PDF");
//		claimDocuments.setDocumentSize("documentSize");
//		claimDocuments.setDocumentType("PDF");
//		claimDocuments.setFormId("1");
//		claimDocuments.setReferenceId(1);
//		List<ClaimDocuments> claimDocumentsList = new ArrayList<>();
//		claimDocumentsList.add(claimDocuments);
//		claim.setClaimDocumentsList(claimDocumentsList);
//		String docName = "EC05" + getDateTime();
//		when(applicationPropertiesMock.getPathFormat()).thenReturn(applicationProperties.getPathFormat());
//		String path = applicationProperties.getDocPath() + applicationProperties.getPathFormat() + docName + "00";
//		docGenerationService.generateXML(claim, path, docName);
//	}

	@Test
	void testAddGenericIndexField() {
		Claim claim = getClaim();
		claim.getPersonalInformation().setPrimaryTelephoneNumber("123456789056");
		ClaimDocuments claimDocuments = getClaimDocuments();
		assertThat(docGenerationService.addGenericIndexField(claim, claimDocuments, "123", LocalDateTime.now()))
				.isInstanceOf(List.class);
	}

	@Test
	void testAddGenericIndexFieldSocialInsuranceNumber() {
		Claim claim = getClaim();
		claim.getPersonalInformation().setSocialInsuranceNumber("");
		ClaimDocuments claimDocuments = getClaimDocuments();
		assertThat(docGenerationService.addGenericIndexField(claim, claimDocuments, "123", LocalDateTime.now()))
				.isInstanceOf(List.class);
	}

	@Test
	void testAddGenericIndexFieldLessThan10DigitPhoneNumber() {
		Claim claim = getClaim();
		ClaimDocuments claimDocuments = getClaimDocuments();
		assertThat(docGenerationService.addGenericIndexField(claim, claimDocuments, "123", LocalDateTime.now()))
				.isInstanceOf(List.class);
	}

	@Test
	void testAddInitialGenericIndexField() {
		Claim claim = getClaim();
		assertThat(docGenerationService.addInitialGenericIndexField(claim)).isInstanceOf(List.class);
	}

	@Test
	void testAddInitialGenericIndexFieldClaimCreationDateNull() {
		Claim claim = getClaim();
		claim.setCreationDate(null);
		assertThat(docGenerationService.addInitialGenericIndexField(claim)).isInstanceOf(List.class);
	}

	public String getDateTime() {
		DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		return date.format(now).replace("/", "").replace(":", "").replace(" ", "");
	}

	public String getTemplateFR() {
		String xHtml = " <!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\"\n"
				+ "\"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">\n"
				+ "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" + "<head>\n"
				+ "  <title>Title of document</title>\n" + "</head>\n" + "<body>\n" + "\n" + "  some content here...\n"
				+ "\n" + "</body>\n" + "</html> ";
		return xHtml;
	}

	public DocumentRequest getDocumentRequest() {
		DocumentRequest documentRequest = new DocumentRequest();
		documentRequest.setClaimId(claimId);
		documentRequest.setReferenceNumber(referenceNumber);
		documentRequest.setDateOfBirth(LocalDate.now());
		documentRequest.setEmail("test@email.com");
		documentRequest.setReferenceNumber("REFNUM");
		documentRequest.setStatus("PENDING");
		return documentRequest;
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
		personalInformation.setCountry("country");
		personalInformation.setPrimaryTelephoneNumber("123456");
		personalInformation.setSecondaryTelephoneNumber("56789");
		personalInformation.setOtherLanguagePreference("FR");
		personalInformation.setLanguagePreference("FR");
		return personalInformation;
	}

	public List<ToolsUsed> getToolsUseds() {
		List<ToolsUsed> toolsUsedList = new ArrayList<ToolsUsed>();
		toolsUsedList.add(getToolUsed());
		return toolsUsedList;
	}

	public ToolsUsed getToolUsed()

	{
		ToolsUsed toolsUsed = new ToolsUsed();
		toolsUsed.setHoursUsed("1");
		toolsUsed.setPastEmploymentInformation(getPastEmploymentInformation());
		toolsUsed.setToolName("toolname");
		toolsUsed.setToolsUsedId(1);
		return toolsUsed;
	}

	public HealthCareProviderInformation getHealthCareProviderInformation() {

		HealthCareProviderInformation healthCareProviderInformation = new HealthCareProviderInformation();
		healthCareProviderInformation.setAudiogramClinicAddress("audiogramClinicAddress");
		healthCareProviderInformation.setAudiogramClinicName("audiogramClinicName");
		healthCareProviderInformation.setAudiogramClinicPhoneNumber("1234566");
		healthCareProviderInformation.setDateOfFirstAudiogram(LocalDate.now());
		healthCareProviderInformation.setEntAppointmentDate(LocalDate.now());
		healthCareProviderInformation.setEntSpecialistAddress("entSpecialistAddress");
		healthCareProviderInformation.setEntSpecialistName("entSpecialistName");
		healthCareProviderInformation.setEntSpecialistPhoneNumber("123456");
		healthCareProviderInformation.setHasConstantRingingInEar(true);
		healthCareProviderInformation.setHasRingingInEar(true);
		healthCareProviderInformation.setHasSevereRingingInEar(false);
		healthCareProviderInformation.setHasVisitedEntSpecialist(false);
		healthCareProviderInformation.setHearingAidUsageDate(LocalDate.now());
		healthCareProviderInformation.setHearingLossNoticedYear("hearingLossNoticedYear");
		healthCareProviderInformation.setRingingEarDuration("ringingEarDuration");
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
		employmentInformation.setPastEmploymentInformationList(Arrays.asList(getPastEmploymentInformation()));
		return employmentInformation;
	}

	public ClaimDocuments getClaimDocuments() {
		ClaimDocuments claimDocuments = new ClaimDocuments();
		claimDocuments.setDocumentName("file1.txt");
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
		return pastEmploymentInformation;
	}
}
