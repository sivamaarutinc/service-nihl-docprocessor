package com.wsib.nihl.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.MaskFormatter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.itextpdf.text.DocumentException;
import com.wsib.nihl.config.ApplicationProperties;
import com.wsib.nihl.constants.CLAIM_STATUS;
import com.wsib.nihl.entity.Claim;
import com.wsib.nihl.entity.ClaimDocuments;
import com.wsib.nihl.entity.PastEmploymentInformation;
import com.wsib.nihl.entity.ToolsUsed;
import com.wsib.nihl.model.DocumentRequest;
import com.wsib.nihl.model.ImportSession;
import com.wsib.nihl.model.ImportSession.Batches;
import com.wsib.nihl.model.ImportSession.Batches.Batch;
import com.wsib.nihl.model.ImportSession.Batches.Batch.BatchFields;
import com.wsib.nihl.model.ImportSession.Batches.Batch.BatchFields.BatchField;
import com.wsib.nihl.model.ImportSession.Batches.Batch.Documents;
import com.wsib.nihl.model.ImportSession.Batches.Batch.Documents.Document;
import com.wsib.nihl.model.ImportSession.Batches.Batch.Documents.Document.IndexFields;
import com.wsib.nihl.model.ImportSession.Batches.Batch.Documents.Document.IndexFields.IndexField;
import com.wsib.nihl.model.ImportSession.Batches.Batch.Documents.Document.Pages;
import com.wsib.nihl.model.ImportSession.Batches.Batch.Documents.Document.Pages.Page;
import com.wsib.nihl.model.ObjectFactory;
import com.wsib.nihl.model.PastEmploymentInformationTable;
import com.wsib.nihl.util.PDFAConverter;
import com.wsib.nihl.util.ZipUtility;

import ch.qos.logback.classic.Logger;

@Service
public class DocGenerationService {
	private Logger logger = (Logger) LoggerFactory.getLogger(DocGenerationService.class);

	@Autowired
	ClaimService claimService;

	@Autowired
	ITemplateEngine templateEngine;

	@Autowired
	ZipUtility zipUtil;
	@Autowired
	PDFAConverter converter;

	@Autowired
	FileUploadService ftpUploader;

	@Autowired
	ApplicationProperties appConfig;

	private static Integer docStartingSequence = 00;
	// private Integer docInt = 00;
	private static String docExtn = "00.zip";
	private static String fileSeperatorInXML = "\\";
	private static String documentSrcId = "EC05";

	/**
	 * This method calls other methods to create the required documents and finally
	 * zip it and send it through MFT. savePDF method generates PDF document for the
	 * given claim under a given path saveXML generates an xml with supporting
	 * document information .
	 * 
	 * @param docReq. the POST method request
	 * @return string value
	 * @throws Exception
	 */
	@Async("asyncExecutor")
	public String sendDocuments(DocumentRequest docReq) {
		Claim claim = null;
		String docName = "";
		try {
			if (null != docReq.getClaimId()) {
				claim = claimService.findByClaimId(docReq.getClaimId());
			} else if (null != docReq.getDateOfBirth() && null != docReq.getReferenceNumber()
					&& null != docReq.getEmail() && null != docReq.getStatus()) {
				claim = claimService.findClaimByRefAndEmail(docReq.getReferenceNumber(), docReq.getEmail(),
						docReq.getStatus(), docReq.getDateOfBirth());
			} else {
				return null;
			}

			DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			
			/*
			 * ZoneId etZoneId = ZoneId.of("America/New_York"); ZonedDateTime zone =
			 * ZonedDateTime.of(now, ZoneId.of("UTC")); LocalDateTime ldate =
			 * zone.withZoneSameInstant(etZoneId).toLocalDateTime();
			 */
			
			String dateTime = date.format(now).replace("/", "").replace(":", "").replace(" ", "");

			docName = documentSrcId + dateTime;
			String docPath = appConfig.getDocPath() + appConfig.getPathFormat() + docName;
			String pathRoot = appConfig.getDocPath();
			File file = new File(pathRoot);
			boolean fileDir = false;
			if (!file.exists()) {
				if (file.mkdir()) {
					fileDir = true;
				} else {
					fileDir = true;
				}
			} else {
				FileUtils.deleteDirectory(file);
				file.mkdir();
				fileDir = true;
			}
			File docFile = new File(docPath);
			if (!docFile.exists()) {
				if (docFile.mkdir()) {
					fileDir = true;
				} else {
					fileDir = true;
				}
			} else {
				FileUtils.deleteDirectory(docFile);
				docFile.mkdir();
				fileDir = true;
			}
			logger.info("Documents path created: " + docPath);
			logger.info("New claim name : " + docName);

			if (fileDir) {

				String path = docPath + appConfig.getPathFormat() + docName + "00";
				File newFile = new File(path);
				newFile.mkdir();
				String pathPDF = path + appConfig.getPdfPath();
				File filePdf = new File(pathPDF);
				if (!filePdf.exists()) {
					filePdf.mkdir();
					logger.info("PDF path created: " + pathPDF);
				}
				generatePDF(claim, path, docName);
				generateXML(claim, path, docName);
				FileUtils.forceDelete(filePdf);

			}

			zipUtil.zip(docPath, appConfig.getDocRoot() + docName + docExtn);
			InputStream inputStream = new FileInputStream(new File(appConfig.getDocRoot() + docName + docExtn));
			
			ftpUploader.uploadFile(docName + docExtn, inputStream);
			
			FileUtils.deleteDirectory(docFile);
			inputStream.close();
			FileUtils.forceDelete(new File(appConfig.getDocRoot() + docName + docExtn));
			logger.info("Document process completed");
			claimService.updateClaimStatus(docReq.getClaimId(), CLAIM_STATUS.TRANSFER_SUCCESS.name());
			return "sent";
		} catch (IOException | DocumentException | ParseException e) {
			logger.error(e.getLocalizedMessage());
			claimService.updateClaimStatus(docReq.getClaimId(), CLAIM_STATUS.TRANSFER_FAILED.name());
		} catch (JAXBException e) {
			logger.error(e.getLocalizedMessage());
			claimService.updateClaimStatus(docReq.getClaimId(), CLAIM_STATUS.TRANSFER_FAILED.name());

		} catch (Exception ex) {
			logger.error(ex.getLocalizedMessage());
			// ex.printStackTrace();
			claimService.updateClaimStatus(docReq.getClaimId(), CLAIM_STATUS.TRANSFER_FAILED.name());
		}
		return null;
	}

	/**
	 * This method takes the byte array and path and saves the file in the path.
	 * 
	 * @param file
	 * @param pathDir
	 * @param documentName
	 * @return
	 * @throws IOException
	 */
	private String saveFile(ClaimDocuments doc, String pathDir, String documentName, Integer individualDocumentSeq)
			throws IOException {
		byte[] file = doc.getDocument();
		DecimalFormat formatter = new DecimalFormat("00");
		String docStr = formatter.format(individualDocumentSeq);
		String extension = FilenameUtils.getExtension(doc.getDocumentName());
		String docName = documentName + docStr + "." + extension;
		Path path = Paths.get(pathDir + appConfig.getPathFormat() + docName);
		individualDocumentSeq++;
		Files.write(path, file);
		logger.info("Claim documents copied: " + docName);
		return documentName + docStr;

	}

	/**
	 * This method generates an NIHL claim request PDF for a given claim.
	 * 
	 * @param claim
	 * @param fileDir
	 * @throws IOException
	 * @throws DocumentException
	 * @throws ParseException
	 */

	public void generatePDF(Claim claim, String fileDir, String docName)
			throws IOException, DocumentException, ParseException {
		Context context = new Context();

		String phoneMask = "###-###-####";
		DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		DateTimeFormatter subPattern = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss");
		MaskFormatter maskFormatter = new MaskFormatter(phoneMask);
		maskFormatter.setValueContainsLiteralCharacters(false);

		LocalDateTime localDate = LocalDateTime.now();
		/*
		 * ZoneId etZoneId = ZoneId.of("America/New_York"); ZonedDateTime zone =
		 * ZonedDateTime.of(now, ZoneId.of("UTC")); LocalDateTime localDate =
		 * zone.withZoneSameInstant(etZoneId).toLocalDateTime();
		 */
		if (claim.getSubmitionDate() != null) {
			localDate = claim.getSubmitionDate();
		}

		context.setVariable("submissionDate", localDate != null ? localDate.format(subPattern) : "");
		// Persoanl Information
		if (null != claim.getPersonalInformation()) {
			context.setVariable("firstName",
					claim.getPersonalInformation().getFirstName() != null
							? claim.getPersonalInformation().getFirstName()
							: "N/A");
			context.setVariable("lastName",
					claim.getPersonalInformation().getLastName() != null ? claim.getPersonalInformation().getLastName()
							: "N/A");
			context.setVariable("dob",
					claim.getPersonalInformation().getDateOfBirth() != null
							? claim.getPersonalInformation().getDateOfBirth().format(pattern)
							: "N/A");
			context.setVariable("sin",
					(claim.getPersonalInformation().getUnmaskedSocialInsuranceNumber() != null
							&& !claim.getPersonalInformation().getUnmaskedSocialInsuranceNumber().isEmpty())
									? claim.getPersonalInformation().getUnmaskedSocialInsuranceNumber()
									: "N/A");
			context.setVariable("address",
					claim.getPersonalInformation().getStreetAddress() != null
							? claim.getPersonalInformation().getStreetAddress()
							: "N/A");
			context.setVariable("apt",
					claim.getPersonalInformation().getApartment() != null
							? claim.getPersonalInformation().getApartment()
							: "N/A");
			context.setVariable("city",
					claim.getPersonalInformation().getCity() != null ? claim.getPersonalInformation().getCity()
							: "N/A");
			context.setVariable("state",
					claim.getPersonalInformation().getProvinceOrState() != null
							? claim.getPersonalInformation().getProvinceOrState()
							: "N/A");
			context.setVariable("postalCode",
					claim.getPersonalInformation().getPostalCode() != null
							? claim.getPersonalInformation().getPostalCode()
							: "N/A");
			context.setVariable("country",
					claim.getPersonalInformation().getCountry() != null ? claim.getPersonalInformation().getCountry()
							: "N/A");
			context.setVariable("primaryPhNum",
					claim.getPersonalInformation().getPrimaryTelephoneNumber() != null
							? claim.getPersonalInformation().getPrimaryTelephoneNumber()
							: "0000000000");
			context.setVariable("secondaryPhNum",
					claim.getPersonalInformation().getSecondaryTelephoneNumber() != null
							? claim.getPersonalInformation().getSecondaryTelephoneNumber()
							: "0000000000");
			context.setVariable("language",
					claim.getPersonalInformation().getLanguagePreference() != null
							? claim.getPersonalInformation().getLanguagePreference()
							: "N/A");
			context.setVariable("otherLanguage", claim.getPersonalInformation().getOtherLanguagePreference());
		}
		// Healthcare information
		if (null != claim.getHealthCareProviderInformation()) {
			String hearingLossDate = claim.getHealthCareProviderInformation().getHearingLossNoticedYear();
			if (null != hearingLossDate)
				hearingLossDate = hearingLossDate.substring(0, 4);
			else
				hearingLossDate = "N/A";
			context.setVariable("hearingLossDate", hearingLossDate);
			context.setVariable("audiogramDate",
					claim.getHealthCareProviderInformation().getDateOfFirstAudiogram() != null
							? claim.getHealthCareProviderInformation().getDateOfFirstAudiogram().format(pattern)
							: "N/A");
			if (null != claim.getClaimDocumentsList() && claim.getClaimDocumentsList().size() > 0) {
				for (ClaimDocuments doc : claim.getClaimDocumentsList()) {
					if (("Audiogram".equalsIgnoreCase(doc.getDocumentType()))) {
						context.setVariable("audiogramDocName",
								doc.getDocumentName() != null && doc.getDocumentName() != "" ? doc.getDocumentName()
										: "N/A");
					} else {
						context.setVariable("audiogramDocName", "N/A");
					}
				}
			} else {
				context.setVariable("audiogramDocName", "N/A");
			}
			context.setVariable("audiogramName",
					claim.getHealthCareProviderInformation().getAudiogramClinicName() != null
							? claim.getHealthCareProviderInformation().getAudiogramClinicName()
							: "N/A");
			context.setVariable("audiogramPhNum",
					claim.getHealthCareProviderInformation().getAudiogramClinicPhoneNumber() != null
							? claim.getHealthCareProviderInformation().getAudiogramClinicPhoneNumber()
							: "0000000000");
			context.setVariable("ringingInEar", claim.getHealthCareProviderInformation().getHasRingingInEar());
			context.setVariable("hasHearingAid", claim.getHealthCareProviderInformation().getHasHearingAid());
			context.setVariable("hearingAidDate",
					claim.getHealthCareProviderInformation().getHearingAidUsageDate() != null
							? claim.getHealthCareProviderInformation().getHearingAidUsageDate().format(pattern)
							: "N/A");
			context.setVariable("hasConstantRinging",
					claim.getHealthCareProviderInformation().getHasConstantRingingInEar());
			context.setVariable("ringingDuration",
					claim.getHealthCareProviderInformation().getRingingEarDuration() != null
							? claim.getHealthCareProviderInformation().getRingingEarDuration()
							: "N/A");
			context.setVariable("hasSevereRinging",
					claim.getHealthCareProviderInformation().getHasSevereRingingInEar());
			context.setVariable("audiogramAddress",
					claim.getHealthCareProviderInformation().getAudiogramClinicAddress() != null
							? claim.getHealthCareProviderInformation().getAudiogramClinicAddress()
							: "N/A");
			context.setVariable("visitedENT", claim.getHealthCareProviderInformation().getHasVisitedEntSpecialist());
			context.setVariable("entAppoinmentDate",
					claim.getHealthCareProviderInformation().getEntAppointmentDate() != null
							? claim.getHealthCareProviderInformation().getEntAppointmentDate().format(pattern)
							: "N/A");
			context.setVariable("entAddress",
					claim.getHealthCareProviderInformation().getEntSpecialistAddress() != null
							? claim.getHealthCareProviderInformation().getEntSpecialistAddress()
							: "N/A");
			context.setVariable("entName",
					claim.getHealthCareProviderInformation().getEntSpecialistName() != null
							? claim.getHealthCareProviderInformation().getEntSpecialistName()
							: "N/A");
			context.setVariable("entPhNum",
					claim.getHealthCareProviderInformation().getEntSpecialistPhoneNumber() != null
							? claim.getHealthCareProviderInformation().getEntSpecialistPhoneNumber()
							: "0000000000");
		}

		List<PastEmploymentInformationTable> pastInfoList = new ArrayList<PastEmploymentInformationTable>();
		// Employment Information
		if (null != claim.getEmploymentInformation()) {
			context.setVariable("currentEmployerAddress", claim.getEmploymentInformation().getCurrentEmployerAddress());
			context.setVariable("currentEmployerHazardous",
					claim.getEmploymentInformation().getCurrentEmployerIsHazardous());
			context.setVariable("currentEmployerName", claim.getEmploymentInformation().getCurrentEmployerName());
			context.setVariable("currentEmployerPhNum",
					claim.getEmploymentInformation().getCurrentEmployerPhoneNumber() != null
							? claim.getEmploymentInformation().getCurrentEmployerPhoneNumber()
							: "0000000000");
			context.setVariable("currentlyEmployed", claim.getEmploymentInformation().getCurrentlyEmployed());
			context.setVariable("currentSituation", claim.getEmploymentInformation().getCurrentSituation());
			context.setVariable("hasRetired", claim.getEmploymentInformation().getHasRetired());
			context.setVariable("retireDate",
					claim.getEmploymentInformation().getRetirementDate() != null
							? claim.getEmploymentInformation().getRetirementDate().format(pattern)
							: "N/A");
			context.setVariable("selfEmployed", claim.getEmploymentInformation().getHasEverBeenSelfEmployed());
			context.setVariable("noisyEqpmt", claim.getEmploymentInformation().getHasUsedNoisyEquipmentOutOfWork());
			context.setVariable("noisyEqmtDetails",
					claim.getEmploymentInformation().getNoisyEquipmentDetails() != null
							? claim.getEmploymentInformation().getNoisyEquipmentDetails()
							: "N/A");
			context.setVariable("selfEmpAddress", claim.getEmploymentInformation().getSelfEmpBusinessAddress());
			context.setVariable("selfEmpName",
					claim.getEmploymentInformation().getSelfEmpBusinessName() != null
							? claim.getEmploymentInformation().getSelfEmpBusinessName()
							: "N/A");
			context.setVariable("selfEmpEndDate",
					claim.getEmploymentInformation().getSelfEmpEndDate() != null
							? claim.getEmploymentInformation().getSelfEmpEndDate().format(pattern)
							: "N/A");
			context.setVariable("selfEmpHasInsurance", claim.getEmploymentInformation().getSelfEmpHasInsurance());
			context.setVariable("selfEmpStartDt",
					claim.getEmploymentInformation().getSelfEmpStartDate() != null
							? claim.getEmploymentInformation().getSelfEmpStartDate().format(pattern)
							: "N/A");
			if (null != claim.getEmploymentInformation().getPastEmploymentInformationList()
					&& claim.getEmploymentInformation().getPastEmploymentInformationList().size() > 0) {
				for (PastEmploymentInformation pastInfo : claim.getEmploymentInformation()
						.getPastEmploymentInformationList()) {
					PastEmploymentInformationTable pastInfoTable = new PastEmploymentInformationTable();

					/*
					 * String a = pastInfo.getEmployerName() != null ? pastInfo.getEmployerName() :
					 * " " + "," + pastInfo.getEmployerAddress() != null
					 * ?pastInfo.getEmployerAddress(): " " +","+ pastInfo.getProvinceOrState() !=
					 * null ? pastInfo.getProvinceOrState(): " "+","+pastInfo.getCountry() != null ?
					 * pastInfo.getCountry() : " ";
					 */

					String address = pastInfo.getEmployerName();
					String employerAddress = pastInfo.getEmployerAddress();
					String province = pastInfo.getProvinceOrState();
					String country = pastInfo.getCountry();
					String phNumber = pastInfo.getEmployerPhoneNumber();
					
					if (employerAddress != null) {
						address = address + "," + employerAddress;
					}
					if (province != null) {
						address = address + "," + province;
					}
					if (country != null) {
						address = address + "," + country;
					}
					if (phNumber !=null) {
						address = address + "," + phNumber;
					}
					pastInfoTable.setAddress(address);
					String employerStillInBusiness = "No";
					String employerStillInBusinessFr = "Non";
					if (pastInfo.getEmployerInBusiness()) {
						employerStillInBusiness = "Yes";
						employerStillInBusinessFr = "Oui";
					}
					pastInfoTable.setIsStillInBusiness(employerStillInBusiness);
					pastInfoTable.setIsStillInBusinessFr(employerStillInBusinessFr);

					pastInfoTable.setOccupation(pastInfo.getJobTitle() != null ? pastInfo.getJobTitle() : " ");
					String date = "From: " + pastInfo.getEmploymentStartDate().format(pattern) + " To: "
							+ pastInfo.getEmploymentEndDate().format(pattern);
					String dateFr = "Du: " + pastInfo.getEmploymentStartDate().format(pattern) + " Au: "
							+ pastInfo.getEmploymentEndDate().format(pattern);
					pastInfoTable.setDate(date);
					pastInfoTable.setDateFr(dateFr);
					String toolsStr = "";
					if (null != pastInfo.getToolsUsedList() && pastInfo.getToolsUsedList().size() > 0) {
						for (ToolsUsed tools : pastInfo.getToolsUsedList()) {
							toolsStr = toolsStr + tools.getToolName() + "  " + tools.getHoursUsed() + " hours per day";
						}
					}
					pastInfoTable.setToolsUsed(toolsStr);
					pastInfoList.add(pastInfoTable);
				}
			}
		}
		context.setVariable("pastInfo", pastInfoList);
		// Supporting Documents
		List<String> empDocs = new ArrayList<String>();
		List<PastEmploymentInformationTable> pastTableInfo = new ArrayList<PastEmploymentInformationTable>();

		if (null != claim.getClaimDocumentsList() && claim.getClaimDocumentsList().size() > 0) {
			List<String> audiograms = new ArrayList<String>();
			for (ClaimDocuments claims : claim.getClaimDocumentsList()) {
				if (null != claim.getHealthCareProviderInformation() && "3275".equalsIgnoreCase(claims.getFormId())
						&& claims.getReferenceId() == claim.getHealthCareProviderInformation().getHealthCareInfoId()) {
					audiograms.add(claims.getDocumentName() != null && claims.getDocumentName() != ""
							? claims.getDocumentName()
							: "N/A");

				} else {
					for (PastEmploymentInformation pastInfo : claim.getEmploymentInformation()
							.getPastEmploymentInformationList()) {
						if (pastInfo.getPastEmploymentInfoId() == claims.getReferenceId()) {
							PastEmploymentInformationTable pastTable = new PastEmploymentInformationTable();
							pastTable.setEmpName(pastInfo.getEmployerName());
							pastTable.setEmpPhNum(
									pastInfo.getEmployerPhoneNumber() != null ? pastInfo.getEmployerPhoneNumber()
											: "N/A");
							pastTable.setAddress(pastInfo.getEmployerAddress());
							pastTable.setDocumentName(claims.getDocumentName());
							pastTableInfo.add(pastTable);
							empDocs.add(claims.getDocumentName());
							context.setVariable("audiogram", "N/A");
						}
					}
				}
				context.setVariable("audiogram", audiograms);
				context.setVariable("documents", pastTableInfo);
			}
		} else {
			empDocs.add("N/A");
			context.setVariable("audiogram", "N/A");
			context.setVariable("documents", pastTableInfo);
		}
		String htmlContentToRender = "";
		if ("FR".equalsIgnoreCase(claim.getCommunicationLanguage())) {
			htmlContentToRender = templateEngine.process("pdf-template_fr", context);
		} else {
			htmlContentToRender = templateEngine.process("pdf-template_en", context);
		}
		String xHtml = xhtmlConvert(htmlContentToRender);

		ITextRenderer renderer = new ITextRenderer();

		String baseUrl = FileSystems.getDefault().getPath("src", "main", "resources", "templates").toUri().toURL()
				.toString();
		renderer.setDocumentFromString(xHtml, baseUrl);
		renderer.layout();
		DecimalFormat formatter = new DecimalFormat("00");
		String docStr = formatter.format(docStartingSequence);
		/*
		 * String strTmp = System.getProperty("java.io.tmpdir");
		 * System.out.println("Temp path:"+ strTmp);
		 */
		OutputStream outputStream = new FileOutputStream(
				fileDir + appConfig.getPdfPath() + appConfig.getPathFormat() + docName + docStr + "sample.PDF");
		logger.info(
				"PDF" + fileDir + appConfig.getPdfPath() + appConfig.getPathFormat() + docName + docStr + "sample.PDF");
		// docInt++;
		renderer.createPDF(outputStream);
		outputStream.close();
		converter.convertPDFToPDFA(
				fileDir + appConfig.getPdfPath() + appConfig.getPathFormat() + docName + docStr + "sample.PDF",
				fileDir + appConfig.getPathFormat() + docName + docStr + ".PDF", docName);
		// FileUtils.deleteDirectory(file);
		logger.info("PDFA document generated ");

	}

	/**
	 * Method to convert html to xhtml for conversion to pdf.
	 * 
	 * @param html
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String xhtmlConvert(String html) throws UnsupportedEncodingException {
		Tidy tidy = new Tidy();
		tidy.setInputEncoding("UTF-8");
		tidy.setOutputEncoding("UTF-8");
		tidy.setXHTML(true);
		ByteArrayInputStream inputStream = new ByteArrayInputStream(html.getBytes("UTF-8"));
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		tidy.parseDOM(inputStream, outputStream);
		return outputStream.toString("UTF-8");
	}

	/**
	 * This method gereates an xml with the supporting documents information
	 * 
	 * @param claim
	 * @param fileDir
	 * @throws IOException
	 * @throws JAXBException
	 * @throws ParseException
	 */
	public void generateXML(Claim claim, String fileDir, String docName)
			throws IOException, JAXBException, ParseException {
		logger.info("DocGeneration: generateXML() - Started ");

		try {
			ObjectFactory factory = new ObjectFactory();
			ImportSession xmlObj = factory.createImportSession();
			Batches batches = factory.createImportSessionBatches();
			Batch batch = factory.createImportSessionBatchesBatch();
			BatchFields batchFList = factory.createImportSessionBatchesBatchBatchFields();
			List<BatchField> batchFs = new ArrayList<BatchField>();
			Integer docInt = 00;
			DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			if (null != claim) {
				logger.info("DocGeneration: generateXML() - Claim Reference#:" + claim.getReferenceNumber());
				Documents documentList = factory.createImportSessionBatchesBatchDocuments();
				List<Document> documents = new ArrayList<Document>();
				BatchField batchF1 = factory.createImportSessionBatchesBatchBatchFieldsBatchField();

				LocalDateTime date = LocalDateTime.now();
				/*
				 * ZoneId etZoneId = ZoneId.of("America/New_York"); ZonedDateTime zone =
				 * ZonedDateTime.of(now, ZoneId.of("UTC")); LocalDateTime date =
				 * zone.withZoneSameInstant(etZoneId).toLocalDateTime().withNano(0);
				 */
				if (claim.getSubmitionDate() != null) {
					date = claim.getSubmitionDate().withNano(0);
				}

				String[] dateStr = date.toString().split("T");
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDate dateFormat = LocalDate.parse(dateStr[0], formatter);
				batchF1.setName("ReceivedDate");
				batchF1.setValueAttribute(dateFormat.format(pattern));
				batchF1.setValue("");
				batchFs.add(batchF1);

				BatchField batchF2 = factory.createImportSessionBatchesBatchBatchFieldsBatchField();
				batchF2.setName("ReceivedTime");
				batchF2.setValue("");
				batchF2.setValueAttribute(dateStr[1]);
				batchFs.add(batchF2);

				BatchField batchF3 = factory.createImportSessionBatchesBatchBatchFieldsBatchField();
				batchF3.setName("LanguageIndicator");
				batchF3.setValue("");
				batchF3.setValueAttribute(claim.getCommunicationLanguage());
				batchFs.add(batchF3);

				batchFList.getBatchField().addAll(batchFs);
				batch.setBatchFields(batchFList);

				if (!claim.getClaimDocumentsList().isEmpty()) {
					logger.info("DocGeneration: generateXML() - Claim documents not empty ");
					// PDF
					Document doc = factory.createImportSessionBatchesBatchDocumentsDocument();
					IndexFields indexF = factory.createImportSessionBatchesBatchDocumentsDocumentIndexFields();
					Pages pages = factory.createImportSessionBatchesBatchDocumentsDocumentPages();
					Page page = factory.createImportSessionBatchesBatchDocumentsDocumentPagesPage();
					doc.setFormTypeName("FT_WSIB Base Class");
					List<IndexField> indexes = new ArrayList<IndexField>();
					indexes.addAll(addInitialGenericIndexField(claim));
					IndexField idx1 = factory.createImportSessionBatchesBatchDocumentsDocumentIndexFieldsIndexField();
					idx1.setName("EmployerTradeandLegalName");
					idx1.setValue("");
					if (null != claim.getEmploymentInformation() && !claim.getEmploymentInformation().getHasRetired()) {
						idx1.setValueAttribute(claim.getEmploymentInformation().getCurrentEmployerName() != null
								? claim.getEmploymentInformation().getCurrentEmployerName()
								: "N/A");
					} else {
						idx1.setValueAttribute("");
					}
					indexes.add(idx1);
					indexes.addAll(addGenericIndexField(claim, null, "32", date));
					IndexField idx2 = factory.createImportSessionBatchesBatchDocumentsDocumentIndexFieldsIndexField();
					idx2.setName("UDID");
					DecimalFormat formatterDec = new DecimalFormat("00");
					String docStr = formatterDec.format(docInt);
					idx2.setValueAttribute(docName + docStr);
					idx2.setValue("");
					indexes.add(idx2);
					IndexField idx3 = factory.createImportSessionBatchesBatchDocumentsDocumentIndexFieldsIndexField();
					idx3.setName("eDocID");
					idx3.setValueAttribute(docName);
					idx3.setValue("");
					indexes.add(idx3);
					indexF.getIndexField().addAll(indexes);
					String docString = formatterDec.format(docInt);
					int fileIndex = fileDir.lastIndexOf(appConfig.getPathFormat());
					page.setImportFileName(
							fileDir.substring(fileIndex + 1) + fileSeperatorInXML + docName + docString + ".PDF");
					page.setValue("");

					pages.setPage(page);
					doc.setIndexFields(indexF);
					doc.setPages(pages);

					documents.add(doc);
					logger.info("DocGeneration: generateXML() - Document path:" + page.getImportFileName());
					logger.info("DocGeneration: generateXML() - PDFA added");
					// For claim docs

					for (ClaimDocuments docs : claim.getClaimDocumentsList()) {
						if ("3275".equalsIgnoreCase(docs.getFormId()) && docs.getReferenceId() == claim
								.getHealthCareProviderInformation().getHealthCareInfoId()) {
							logger.info("DocGeneration: generateXML() - adding healthCare document details ");
							// Save the file
							String extension = FilenameUtils.getExtension(docs.getDocumentName());
							docInt++;
							String fileName = saveFile(docs, fileDir, docName, docInt);

							// PDF
							Document doc1 = factory.createImportSessionBatchesBatchDocumentsDocument();
							IndexFields indexF1 = factory.createImportSessionBatchesBatchDocumentsDocumentIndexFields();
							Pages pages1 = factory.createImportSessionBatchesBatchDocumentsDocumentPages();
							Page page1 = factory.createImportSessionBatchesBatchDocumentsDocumentPagesPage();
							doc1.setFormTypeName("FT_WSIB Base Class");
							List<IndexField> indexes1 = new ArrayList<IndexField>();
							indexes1.addAll(addInitialGenericIndexField(claim));
							IndexField idx01 = factory
									.createImportSessionBatchesBatchDocumentsDocumentIndexFieldsIndexField();
							idx01.setName("EmployerTradeandLegalName");
							idx01.setValue("");
							idx01.setValueAttribute(claim.getEmploymentInformation().getCurrentEmployerName() != null
									? claim.getEmploymentInformation().getCurrentEmployerName()
									: "N/A");

							indexes1.add(idx01);
							indexes1.addAll(addGenericIndexField(claim, null, "3275", date));
							IndexField idx11 = factory
									.createImportSessionBatchesBatchDocumentsDocumentIndexFieldsIndexField();
							idx11.setName("UDID");
							idx11.setValue("");
							idx11.setValueAttribute(fileName);
							indexes1.add(idx11);
							IndexField idx12 = factory
									.createImportSessionBatchesBatchDocumentsDocumentIndexFieldsIndexField();
							idx12.setName("eDocID");
							idx12.setValue("");
							idx12.setValueAttribute(docName);
							indexes1.add(idx12);

							indexF1.getIndexField().addAll(indexes1);
							page1.setImportFileName(
									fileDir.substring(fileIndex + 1) + fileSeperatorInXML + fileName + "." + extension);
							page1.setValue("");
							pages1.setPage(page1);
							doc1.setIndexFields(indexF1);
							doc1.setPages(pages1);
							documents.add(doc1);
							logger.info("DocGeneration: generateXML() - Document path inside if:"
									+ page1.getImportFileName());
							logger.info("DocGeneration: generateXML() - Health Care info added");

						} else {
							if (claim.getEmploymentInformation().getPastEmploymentInformationList().size() > 0) {

								for (PastEmploymentInformation pastInfo : claim.getEmploymentInformation()
										.getPastEmploymentInformationList()) {
									if (docs.getReferenceId() == pastInfo.getPastEmploymentInfoId()) {
										logger.info("DocGeneration: generateXML() - adding past employment details ");
										// Save the file
										String extension = FilenameUtils.getExtension(docs.getDocumentName());
										docInt++;
										String fileName = saveFile(docs, fileDir, docName, docInt);
										Document doc1 = factory.createImportSessionBatchesBatchDocumentsDocument();
										IndexFields indexF1 = factory
												.createImportSessionBatchesBatchDocumentsDocumentIndexFields();
										Pages pages1 = factory.createImportSessionBatchesBatchDocumentsDocumentPages();
										Page page1 = factory
												.createImportSessionBatchesBatchDocumentsDocumentPagesPage();
										doc1.setFormTypeName("FT_WSIB Base Class");
										List<IndexField> indexes1 = new ArrayList<IndexField>();
										indexes1.addAll(addInitialGenericIndexField(claim));
										IndexField idx01 = factory
												.createImportSessionBatchesBatchDocumentsDocumentIndexFieldsIndexField();
										idx01.setName("EmployerTradeandLegalName");
										idx01.setValue("");
										idx01.setValueAttribute(pastInfo.getEmployerName());
										indexes1.add(idx01);
										indexes1.addAll(addGenericIndexField(claim, docs, "5", date));
										IndexField idx11 = factory
												.createImportSessionBatchesBatchDocumentsDocumentIndexFieldsIndexField();
										idx11.setName("UDID");
										idx11.setValue("");
										idx11.setValueAttribute(fileName);
										indexes1.add(idx11);
										IndexField idx12 = factory
												.createImportSessionBatchesBatchDocumentsDocumentIndexFieldsIndexField();
										idx12.setName("eDocID");
										idx12.setValue("");
										idx12.setValueAttribute(docName);
										indexes1.add(idx12);
										indexF1.getIndexField().addAll(indexes1);

										page1.setImportFileName(fileDir.substring(fileIndex + 1) + fileSeperatorInXML
												+ fileName + "." + extension);
										page1.setValue("");
										pages1.setPage(page1);
										doc1.setIndexFields(indexF1);
										doc1.setPages(pages1);
										documents.add(doc1);
										logger.info("DocGeneration: generateXML() - Document path inside else:"
												+ page1.getImportFileName());
										logger.info("DocGeneration: generateXML() - Employer info added");
									}
								}

							}
						}

					}
				} else {
					logger.info("DocGeneration: generateXML() in else- Claim documents are empty ");
					// PDF
					Document doc = factory.createImportSessionBatchesBatchDocumentsDocument();
					IndexFields indexF = factory.createImportSessionBatchesBatchDocumentsDocumentIndexFields();
					Pages pages = factory.createImportSessionBatchesBatchDocumentsDocumentPages();
					Page page = factory.createImportSessionBatchesBatchDocumentsDocumentPagesPage();
					doc.setFormTypeName("FT_WSIB Base Class");
					List<IndexField> indexes = new ArrayList<IndexField>();
					indexes.addAll(addInitialGenericIndexField(claim));
					IndexField idx1 = factory.createImportSessionBatchesBatchDocumentsDocumentIndexFieldsIndexField();
					idx1.setName("EmployerTradeandLegalName");
					idx1.setValue("");
					if (null != claim.getEmploymentInformation() && !claim.getEmploymentInformation().getHasRetired()) {
						idx1.setValueAttribute(claim.getEmploymentInformation().getCurrentEmployerName() != null
								? claim.getEmploymentInformation().getCurrentEmployerName()
								: "N/A");
					} else {
						idx1.setValueAttribute("");
					}
					indexes.add(idx1);
					indexes.addAll(addGenericIndexField(claim, null, "32", date));
					IndexField idx2 = factory.createImportSessionBatchesBatchDocumentsDocumentIndexFieldsIndexField();
					idx2.setName("UDID");
					DecimalFormat formatterDec = new DecimalFormat("00");
					String docStr = formatterDec.format(docInt);
					idx2.setValueAttribute(docName + docStr);
					idx2.setValue("");
					indexes.add(idx2);
					IndexField idx3 = factory.createImportSessionBatchesBatchDocumentsDocumentIndexFieldsIndexField();
					idx3.setName("eDocID");
					idx3.setValueAttribute(docName);
					idx3.setValue("");
					indexes.add(idx3);
					indexF.getIndexField().addAll(indexes);
					String docString = formatterDec.format(docInt);
					int fileIndex = fileDir.lastIndexOf(appConfig.getPathFormat());
					page.setImportFileName(
							fileDir.substring(fileIndex + 1) + fileSeperatorInXML + docName + docString + ".PDF");
					page.setValue("");

					pages.setPage(page);
					doc.setIndexFields(indexF);
					doc.setPages(pages);

					documents.add(doc);
					logger.info("DocGeneration: generateXML() - Document path:" + page.getImportFileName());
					logger.info("DocGeneration: generateXML() - PDFA added");
					// For claim docs
				}
				documentList.getDocument().addAll(documents);
				logger.info("DocGeneration: generateXML() - adding all the details to documentList node ");
				batch.setDocuments(documentList);
				batches.setBatch(batch);
				xmlObj.setBatches(batches);
				logger.info("DocGeneration: generateXML() - preparing batches ");
			}
			JAXBContext context = JAXBContext.newInstance(ImportSession.class);

			Marshaller mar = context.createMarshaller();
			mar.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			mar.marshal(xmlObj, new File(appConfig.getDocPath() + appConfig.getPathFormat() + docName
					+ appConfig.getPathFormat() + docName + "00.xml"));
			logger.info("XML generated under the path :" + fileDir + appConfig.getPathFormat() + docName + "00.xml");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

	}

	/**
	 * This method is a supporting method for saveXML. This generates the required
	 * node elements for the xml.
	 * 
	 * @param claim
	 * @param claimDoc
	 * @return
	 */
	public List<IndexField> addGenericIndexField(Claim claim, ClaimDocuments claimDoc, String formId,
			LocalDateTime submittedDate) {
		ObjectFactory factory = new ObjectFactory();
		List<IndexField> indexes = new ArrayList<IndexField>();
		DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		IndexField idx4 = factory.createImportSessionBatchesBatchDocumentsDocumentIndexFieldsIndexField();
		idx4.setName("EmployerPostalCode");
		idx4.setValue("");
		indexes.add(idx4);
		IndexField idx5 = factory.createImportSessionBatchesBatchDocumentsDocumentIndexFieldsIndexField();
		idx5.setName("WorkerDateOfBirth");
		idx5.setValue("");
		idx5.setValueAttribute(claim.getPersonalInformation().getDateOfBirth().format(pattern).toString());
		indexes.add(idx5);
		IndexField idx6 = factory.createImportSessionBatchesBatchDocumentsDocumentIndexFieldsIndexField();
		idx6.setName("WorkerFirstName");
		idx6.setValue("");
		idx6.setValueAttribute(claim.getPersonalInformation().getFirstName());
		indexes.add(idx6);
		IndexField idx7 = factory.createImportSessionBatchesBatchDocumentsDocumentIndexFieldsIndexField();
		idx7.setName("WorkerSIN");
		idx7.setValue("");
		if (claim.getPersonalInformation().getUnmaskedSocialInsuranceNumber() != "") {
			idx7.setValueAttribute(claim.getPersonalInformation().getUnmaskedSocialInsuranceNumber());
		}
		indexes.add(idx7);
		IndexField idx8 = factory.createImportSessionBatchesBatchDocumentsDocumentIndexFieldsIndexField();
		idx8.setName("WorkerGender");

		idx8.setValue("");
		indexes.add(idx8);
		IndexField idx9 = factory.createImportSessionBatchesBatchDocumentsDocumentIndexFieldsIndexField();
		idx9.setName("WorkerLastName");
		idx9.setValue("");
		idx9.setValueAttribute(claim.getPersonalInformation().getLastName());
		indexes.add(idx9);
		IndexField idx0 = factory.createImportSessionBatchesBatchDocumentsDocumentIndexFieldsIndexField();
		idx0.setName("WorkerPreferredLanguage");
		idx0.setValue("");
		idx0.setValueAttribute(claim.getPersonalInformation().getLanguagePreference());
		indexes.add(idx0);
		IndexField idx11 = factory.createImportSessionBatchesBatchDocumentsDocumentIndexFieldsIndexField();
		idx11.setName("WorkerPostalCode");
		idx11.setValue("");
		idx11.setValueAttribute(claim.getPersonalInformation().getPostalCode());
		indexes.add(idx11);
		String PhNo = claim.getPersonalInformation().getPrimaryTelephoneNumber();
		IndexField idx12 = factory.createImportSessionBatchesBatchDocumentsDocumentIndexFieldsIndexField();
		idx12.setName("WorkerTelephoneAreaCode");
		idx12.setValue("");

		if (PhNo != null && PhNo.length() > 10) {
			idx12.setValueAttribute(
					PhNo.substring(0, PhNo.length() - 10).replace("(", "").replace(")", "").replace("+", ""));
		}
		indexes.add(idx12);
		IndexField idx13 = factory.createImportSessionBatchesBatchDocumentsDocumentIndexFieldsIndexField();
		idx13.setName("WorkerTelephoneNumber");
		idx13.setValue("");
		idx13.setValueAttribute(StringUtils.right(PhNo, 10));
		indexes.add(idx13);
		IndexField idx14 = factory.createImportSessionBatchesBatchDocumentsDocumentIndexFieldsIndexField();
		idx14.setName("DocumentDate");
		idx14.setValueAttribute(submittedDate.format(pattern).toString());
		idx14.setValue("");
		indexes.add(idx14);
		IndexField idx15 = factory.createImportSessionBatchesBatchDocumentsDocumentIndexFieldsIndexField();
		idx15.setName("FormID");
		idx15.setValue("");
		idx15.setValueAttribute(formId);
		indexes.add(idx15);
		IndexField idx16 = factory.createImportSessionBatchesBatchDocumentsDocumentIndexFieldsIndexField();
		idx16.setName("HealthcarePaymentIndicator");
		idx16.setValue("");
		indexes.add(idx16);
		IndexField idx17 = factory.createImportSessionBatchesBatchDocumentsDocumentIndexFieldsIndexField();
		idx17.setName("WorkStationSourceID");
		idx17.setValueAttribute("NIHL");
		idx17.setValue("");
		indexes.add(idx17);
		IndexField idx18 = factory.createImportSessionBatchesBatchDocumentsDocumentIndexFieldsIndexField();
		idx18.setName("DocumentSource");
		idx18.setValueAttribute("SC");
		idx18.setValue("");
		indexes.add(idx18);
		IndexField idx19 = factory.createImportSessionBatchesBatchDocumentsDocumentIndexFieldsIndexField();
		idx19.setName("eServUserID");
		idx19.setValue("");
		indexes.add(idx19);
		IndexField idx20 = factory.createImportSessionBatchesBatchDocumentsDocumentIndexFieldsIndexField();
		idx20.setName("eServSchemaVersion");
		idx20.setValue("");
		idx20.setValueAttribute("1.00");
		indexes.add(idx20);
		IndexField idx21 = factory.createImportSessionBatchesBatchDocumentsDocumentIndexFieldsIndexField();
		idx21.setName("eServConfirmationNumber");
		idx21.setValue("");
		idx21.setValueAttribute(claim.getReferenceNumber());
		indexes.add(idx21);
		IndexField idx22 = factory.createImportSessionBatchesBatchDocumentsDocumentIndexFieldsIndexField();
		idx22.setName("eServICMarker");
		idx22.setValue("");
		idx22.setValueAttribute("0");
		indexes.add(idx22);
		logger.info("DocGeneration: generateXML() - added generic fields ");
		return indexes;
	}

	/**
	 * This method is a supporting method for saveXML. This generates the required
	 * node elements for the xml.
	 * 
	 * @param claim
	 * @param claimDoc
	 * @return
	 */
	public List<IndexField> addInitialGenericIndexField(Claim claim) {
		ObjectFactory factory = new ObjectFactory();
		List<IndexField> indexes = new ArrayList<IndexField>();
		DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		IndexField idx = factory.createImportSessionBatchesBatchDocumentsDocumentIndexFieldsIndexField();
		idx.setName("eServChannelName");
		idx.setValueAttribute(documentSrcId);
		idx.setValue("");
		indexes.add(idx);
		IndexField idx1 = factory.createImportSessionBatchesBatchDocumentsDocumentIndexFieldsIndexField();
		idx1.setName("ClaimNumber");
		idx1.setValue("");
		indexes.add(idx1);
		IndexField idx2 = factory.createImportSessionBatchesBatchDocumentsDocumentIndexFieldsIndexField();
		idx2.setName("IncidentDate");
		idx2.setValue("");
		idx2.setValueAttribute(
				claim.getCreationDate() != null ? claim.getCreationDate().format(pattern).toString() : "N");
		indexes.add(idx2);

		logger.info("DocGeneration: generateXML() - adding initial fields in xml ");
		return indexes;
	}

}
