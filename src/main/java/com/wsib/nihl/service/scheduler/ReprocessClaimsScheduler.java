package com.wsib.nihl.service.scheduler;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.wsib.nihl.constants.CLAIM_STATUS;
import com.wsib.nihl.constants.ErrorCodes;
import com.wsib.nihl.entity.Claim;
import com.wsib.nihl.model.DocumentRequest;
import com.wsib.nihl.repository.ClaimRepository;
import com.wsib.nihl.service.ClaimService;
import com.wsib.nihl.service.DocGenerationService;

/**
 * 
 * This class serves to reprocess failed claims. It does below things
 * 
 * 1. Get all failed claims in last 31 days (TRANSFER_FAILED)
 * 2. Process all these claims by calling /document
 * 
 * The job runs everyday at 6AM and 5PM
 *
 */
@Service
@EnableScheduling
public class ReprocessClaimsScheduler {

	private Logger logger = LoggerFactory.getLogger(ReprocessClaimsScheduler.class);

	@Autowired
	ClaimRepository claimRepository;

	@Autowired
	DocGenerationService docService;

	@Autowired
	ClaimService claimService;

	/**
	 * Scheduler runs everyday at 6AM and 5PM
	 * @throws Exception 
	 */

	@Scheduled(cron = "${cronjob.reprocess-claims}")
	public void reprocessClaims() throws Exception {

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		logger.info("Reprocess claims scheduler started at: " + timestamp.getTime());

		List<Claim> claimsList = claimRepository.findByStatusEquals(ErrorCodes.CLAIM_STATUS_FAILED);

		if(null != claimsList && !claimsList.isEmpty()) {
			logger.info("No. of claims to reprocess: " + claimsList.size());
			DocumentRequest docRequest = null;
			for(Claim reprocessClaim : claimsList) {
				boolean claimStatusInd = claimService.updateClaimStatusForFailed(reprocessClaim.getClaimId(), CLAIM_STATUS.TRANSFER_IN_PROGRESS.name());
				if( claimStatusInd ) {
					docRequest = new DocumentRequest();
					docRequest.setClaimId(reprocessClaim.getClaimId());
					docRequest.setEmail(reprocessClaim.getEmail());
					docRequest.setReferenceNumber(reprocessClaim.getReferenceNumber());
					docRequest.setStatus(reprocessClaim.getStatus());

					String status = docService.sendDocuments(docRequest);

					if(null != status && status.equals("sent")) {
						logger.info("Claim with reference number: " + reprocessClaim.getReferenceNumber() + " processed at :" + timestamp.getTime());
					} else {
						logger.info("Reprocessing failed for claimId: " + reprocessClaim.getClaimId() + "with reference numnber: " + reprocessClaim.getReferenceNumber());
					}				
				}
			}

		} else
			logger.info("No claims to reprocess");


		logger.info("Reprocess claims scheduler ended at: " + timestamp.getTime());
	}

}
