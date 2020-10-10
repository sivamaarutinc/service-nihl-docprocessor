package com.wsib.nihl.service.schedulertest;

import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wsib.nihl.constants.CLAIM_STATUS;
import com.wsib.nihl.entity.Claim;
import com.wsib.nihl.model.DocumentRequest;
import com.wsib.nihl.repository.ClaimRepository;
import com.wsib.nihl.service.ClaimService;
import com.wsib.nihl.service.DocGenerationService;
import com.wsib.nihl.service.scheduler.ReprocessClaimsScheduler;

@ExtendWith(MockitoExtension.class)
public class ReprocessClaimsSchedulerTest {

	@InjectMocks
	ReprocessClaimsScheduler reprocessClaimsScheduler;

	@Mock
	ClaimRepository claimRepository;

	@Mock
	DocGenerationService docService;
	
	@Mock
	ClaimService claimService;
	
	private final int claimId = 1;
	private final String emailId = "user1@test.com";
	private final String userName = "user1";
	private final String emailWithConsent = "abc@gmail.com";
	private final String referenceNumber = "123456";
	private final int healthCareInfoId = 1;
	private final int pastEmploymentIfoId = 1;
	
	@Test
	void testreprocessClaims() throws Exception
	{
		Claim claim=getClaim();
		List<Claim> claims=new ArrayList<Claim>();
		claims.add(claim);
		when(claimRepository.findByStatusEquals(Mockito.anyString())).thenReturn(claims);
		when(docService.sendDocuments(Mockito.any(DocumentRequest.class))).thenReturn("sent");
		when(claimService.updateClaimStatusForFailed(Mockito.anyInt(),Mockito.anyString())).thenReturn(true);
		reprocessClaimsScheduler.reprocessClaims();
	}
	
	@Test
	void testreprocessClaimsWrongStatus() throws Exception
	{
		Claim claim=getClaim();
		List<Claim> claims=new ArrayList<Claim>();
		claims.add(claim);
		when(claimRepository.findByStatusEquals(Mockito.anyString())).thenReturn(claims);
		when(docService.sendDocuments(Mockito.any(DocumentRequest.class))).thenReturn("text");
		when(claimService.updateClaimStatusForFailed(Mockito.anyInt(),Mockito.anyString())).thenReturn(true);
		reprocessClaimsScheduler.reprocessClaims();
	}
	
	@Test
	void testreprocessClaimsNullStatus() throws Exception
	{
		Claim claim=getClaim();
		List<Claim> claims=new ArrayList<Claim>();
		claims.add(claim);
		when(claimRepository.findByStatusEquals(Mockito.anyString())).thenReturn(claims);
		when(docService.sendDocuments(Mockito.any(DocumentRequest.class))).thenReturn(null);
		when(claimService.updateClaimStatusForFailed(Mockito.anyInt(),Mockito.anyString())).thenReturn(true);
		reprocessClaimsScheduler.reprocessClaims();
	}
	
	@Test
	void testreprocessClaimsEmptyClaim() throws Exception
	{
		List<Claim> claims=new ArrayList<Claim>();
		when(claimRepository.findByStatusEquals(Mockito.anyString())).thenReturn(claims);
		reprocessClaimsScheduler.reprocessClaims();
	}
	
	@Test
	void testreprocessClaimsNullClaim() throws Exception
	{
		when(claimRepository.findByStatusEquals(Mockito.anyString())).thenReturn(null);
		reprocessClaimsScheduler.reprocessClaims();
	}
	
	@Test
	void testreprocessClaimsEmptyStatus() throws Exception
	{
		Claim claim=getClaim();
		List<Claim> claims=new ArrayList<Claim>();
		claims.add(claim);
		when(claimRepository.findByStatusEquals(Mockito.anyString())).thenReturn(claims);
		reprocessClaimsScheduler.reprocessClaims();
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
		return claim;

	}
}
