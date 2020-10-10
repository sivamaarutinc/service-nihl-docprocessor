package com.wsib.nihl.entitytest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.wsib.nihl.entity.PastEmploymentInformation;
import com.wsib.nihl.entity.ToolsUsed;


public class ToolsUsedTest {

	ToolsUsed toolsUsed = new ToolsUsed();
	PastEmploymentInformation pastEmploymentInformation = new PastEmploymentInformation();

	@BeforeEach
	void beforeEach() {
		toolsUsed.setHoursUsed("5");
		toolsUsed.setToolName("toolName");
		toolsUsed.setToolsUsedId(1);
		toolsUsed.setPastEmploymentInformation(pastEmploymentInformation);
	}

	@Test
	void testGetters() {
		assertEquals("5", toolsUsed.getHoursUsed());
		assertEquals(1, toolsUsed.getToolsUsedId());
		assertEquals("toolName", toolsUsed.getToolName());
		assertEquals(pastEmploymentInformation, toolsUsed.getPastEmploymentInformation());
		assertEquals(pastEmploymentInformation, toolsUsed.getPastEmploymentInformation());
		assertEquals(toolsUsed.toString(), toolsUsed.toString());
	}
}
