package com.wsib.nihl.modeltest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.wsib.nihl.model.PastEmploymentInformationTable;


public class PastEmploymentInformationTableTest {

	PastEmploymentInformationTable pastEmploymentInformationTable = new PastEmploymentInformationTable();

	@BeforeEach
	void beforeEach() {
		pastEmploymentInformationTable.setAddress("address");
		pastEmploymentInformationTable.setDate("09/09/2020");
		pastEmploymentInformationTable.setDocumentName("documentName");
		pastEmploymentInformationTable.setEmpName("user1");
		pastEmploymentInformationTable.setEmpPhNum("465446");
		pastEmploymentInformationTable.setIsStillInBusiness("No");
		pastEmploymentInformationTable.setOccupation("occupation");
		pastEmploymentInformationTable.setToolsUsed("toolsUsed");
		pastEmploymentInformationTable.setIsStillInBusinessFr("isStillInBusinessFr");
		pastEmploymentInformationTable.setDateFr("dateFr");
	}

	@Test
	void testGetters() {
		assertAll(() -> assertEquals("address", pastEmploymentInformationTable.getAddress()),
				() -> assertEquals("09/09/2020", pastEmploymentInformationTable.getDate()),
				() -> assertEquals("documentName", pastEmploymentInformationTable.getDocumentName()),
				() -> assertEquals("user1", pastEmploymentInformationTable.getEmpName()),
				() -> assertEquals("465446", pastEmploymentInformationTable.getEmpPhNum()),
				() -> assertEquals("No", pastEmploymentInformationTable.getIsStillInBusiness()),
				() -> assertEquals("occupation", pastEmploymentInformationTable.getOccupation()),
				() -> assertEquals("toolsUsed", pastEmploymentInformationTable.getToolsUsed()),
				() -> assertEquals("isStillInBusinessFr", pastEmploymentInformationTable.getIsStillInBusinessFr()),
				() -> assertEquals("dateFr", pastEmploymentInformationTable.getDateFr())

		);
	}
}
