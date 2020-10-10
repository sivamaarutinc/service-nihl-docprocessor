package com.wsib.nihl.utiltest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.wsib.nihl.util.CommonUtils;


public class CommonUtilsTest {

	private static String password = "user@123";

	@BeforeAll
	public static void init() {
		CommonUtils commonUtils = new CommonUtils();
	}

	@Test
	void testApplyMaskingWithLessThanEightCharacters() {
		assertThat(CommonUtils.applyMasking("test")).isInstanceOf(String.class);
	}

	@Test
	void testApplyMasking() {
		assertThat(CommonUtils.applyMasking("testApplyMasking")).isInstanceOf(String.class);
	}

	@Test
	void testApplyMaskingWithEmptyString() {
		assertThat(CommonUtils.applyMasking("")).isInstanceOf(String.class);
	}

	@Test
	void testApplyMaskingWithEmptyNullString() {
		Assertions.assertNull(CommonUtils.applyMasking(null));
	}

	@Test
	void testApplyMaskingWithSpecialCharacters() {
		assertThat(CommonUtils.applyMasking("-+()testApplyMasking")).isInstanceOf(String.class);
	}

	@Test
	void testEncodePassword() {
		assertThat(CommonUtils.encodePassword(password)).isInstanceOf(String.class);
	}

	@Test
	void testDecodePassword() throws IOException {
		String encodPassword = CommonUtils.encodePassword(password);
		assertEquals(password, CommonUtils.decodePassword(encodPassword));
	}
}
