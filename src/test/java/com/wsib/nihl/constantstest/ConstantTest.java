package com.wsib.nihl.constantstest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.wsib.nihl.constants.CLAIM_STATUS;
import com.wsib.nihl.constants.ErrorCodes;

public class ConstantTest {

	@Test
	void testClaimStatus()
	{
		ErrorCodes errorCodes=new ErrorCodes();
		assertEquals(CLAIM_STATUS.SUBMITTED.name(), CLAIM_STATUS.SUBMITTED.name());
		assertEquals(CLAIM_STATUS.TRANSFER_FAILED.name(), CLAIM_STATUS.TRANSFER_FAILED.name());
		assertEquals(CLAIM_STATUS.TRANSFER_SUCCESS.name(), CLAIM_STATUS.TRANSFER_SUCCESS.name());
	}
}
