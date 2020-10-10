package com.wsib.nihl.service;

import java.time.LocalDate;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.wsib.nihl.constants.CLAIM_STATUS;
import com.wsib.nihl.entity.Claim;
import com.wsib.nihl.repository.ClaimRepository;

import ch.qos.logback.classic.Logger;

@Service
public class ClaimService {

	private Logger logger = (Logger) LoggerFactory.getLogger(ClaimService.class);

	private static String TRANFER_FAILED = "TRANSFER_FAILED";

	@Autowired
	ClaimRepository claimRepository;

	/**
	 * Fetch a claim from DB.
	 * 
	 * @param referenceNumber
	 * @param email
	 * @param status
	 * @param dateOfBirth
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, noRollbackFor = Exception.class)
	public Claim findClaimByRefAndEmail(String referenceNumber, String email, String status, LocalDate dateOfBirth)
			throws Exception {
		try {
			Claim claim = claimRepository.findByReferenceNumberAndEmailAndStatus(referenceNumber, email, status);

			if (null != claim && null != claim.getPersonalInformation() && null != dateOfBirth
					&& dateOfBirth.equals(claim.getPersonalInformation().getDateOfBirth())) {
				return claim;
			}
		} catch (Exception e) {
			throw e;
		}
		return null;
	}

	/**
	 * Fetch a claim using ID from database.
	 * 
	 * @param claimId
	 * @return
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true, noRollbackFor = Exception.class)
	public Claim findByClaimId(Integer claimId) throws Exception {
		try {
			Optional<Claim> claimOptional = claimRepository.findById(claimId);
			if (claimOptional.isPresent()) {
				return claimOptional.get();
			}
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw e;
		}
		return null;
	}

	/**
	 * Method to update the claim status.
	 * 
	 * @param claimId
	 * @param status
	 * @return
	 */
	public Boolean updateClaimStatus(Integer claimId, String status) {
		try {
			Optional<Claim> claimOptional = claimRepository.findById(claimId);
			if (claimOptional.isPresent()) {

				Claim claimPersist = claimOptional.get();

				if (CLAIM_STATUS.TRANSFER_FAILED.name().equals(status)) {
					claimPersist.setStatus(CLAIM_STATUS.TRANSFER_FAILED.name());

				} else if (CLAIM_STATUS.TRANSFER_SUCCESS.name().equals(status)) {
					claimPersist.setStatus(status);
				}
				claimPersist = claimRepository.save(claimPersist);
				return true;
			}

		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
		return null;
	}

	/**
	 * Method to update the claim status.
	 * 
	 * @param claimId
	 * @param status
	 * @return
	 */

	@Transactional(propagation = Propagation.REQUIRED)
	public Boolean updateClaimStatusForFailed(Integer claimId, String status) {
		try {
			Optional<Claim> claimOptional = claimRepository.findById(claimId);
			if (claimOptional.isPresent()) {
				logger.info("Updating the status to TRANSFER_IN_PROGRESS for :" + claimId);
				int count = claimRepository.updateStatusForFailed(claimId, status, TRANFER_FAILED);
				if (count >= 1) {
					return true;
				} else
					return false;
			}

		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			return false;
		}
		return false;
	}

}