package com.wsib.nihl.modeltest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import com.wsib.nihl.model.ImportSession;



public class ImportSessionTest {

	MockMultipartFile mockMultipartFile = new MockMultipartFile("file1", "content".getBytes());

	@Test
	void testgettersSetters() {
		ImportSession importSession = new ImportSession();
		ImportSession.Batches batches = new ImportSession.Batches();
		ImportSession.Batches.Batch batch = new ImportSession.Batches.Batch();
		ImportSession.Batches.Batch.BatchFields batchFields = new ImportSession.Batches.Batch.BatchFields();
		ImportSession.Batches.Batch.Documents documents = new ImportSession.Batches.Batch.Documents();
		ImportSession.Batches.Batch.Documents.Document document = new ImportSession.Batches.Batch.Documents.Document();
		List<ImportSession.Batches.Batch.Documents.Document> documentList = new ArrayList<>();
		ImportSession.Batches.Batch.Documents.Document.IndexFields indexFields = new ImportSession.Batches.Batch.Documents.Document.IndexFields();
		ImportSession.Batches.Batch.Documents.Document.Pages pages = new ImportSession.Batches.Batch.Documents.Document.Pages();
		ImportSession.Batches.Batch.Documents.Document.IndexFields.IndexField indexField = new ImportSession.Batches.Batch.Documents.Document.IndexFields.IndexField();
		ImportSession.Batches.Batch.Documents.Document.Pages.Page page = new ImportSession.Batches.Batch.Documents.Document.Pages.Page();
		ImportSession.Batches.Batch.BatchFields.BatchField batchField=new ImportSession.Batches.Batch.BatchFields.BatchField();

		importSession.setBatches(batches);
		batches.setBatch(batch);
		batch.setBatchFields(batchFields);
		batch.setDocuments(documents);
		document.setIndexFields(indexFields);
		document.setPages(pages);
		document.setFormTypeName("FormTypeName");
		indexField.setValue("value");
		indexField.setName("Name");
		indexField.setValueAttribute("Attribute");
		page.setImportFileName("FileName");
		page.setValue("value");
		pages.setPage(page);
		documentList.add(document);
		batchField.setName("FileName");
		batchField.setValue("value");
		batchField.setValueAttribute("Attribute");
		indexFields.getIndexField();
		documents.getDocument();
		batchFields.getBatchField();
		batch.getDocuments();
		

		assertEquals(batches, importSession.getBatches());
		assertEquals(batch, batches.getBatch());
		assertEquals(batchFields, batch.getBatchFields());
		assertEquals(indexFields, document.getIndexFields());
		assertEquals(pages, document.getPages());
		assertEquals("FormTypeName", document.getFormTypeName());
		assertEquals("Name", indexField.getName());
		assertEquals("value", indexField.getValue());
		assertEquals("Attribute", indexField.getValueAttribute());
		assertEquals("value", page.getValue());
		assertEquals("FileName", page.getImportFileName());
		assertEquals(page, pages.getPage());
		assertEquals("Attribute", batchField.getValueAttribute());
		assertEquals("value", batchField.getValue());
		assertEquals("FileName", batchField.getName());
		 

	}

}
