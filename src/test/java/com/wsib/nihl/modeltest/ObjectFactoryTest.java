package com.wsib.nihl.modeltest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.wsib.nihl.model.ImportSession;
import com.wsib.nihl.model.ObjectFactory;



public class ObjectFactoryTest {

	@Test
	void testGettersSetters() {
		ObjectFactory objectFactory = new ObjectFactory();
		assertThat(objectFactory.createImportSession() instanceof ImportSession);
		assertThat(objectFactory.createImportSessionBatches() instanceof ImportSession.Batches);
		assertThat(objectFactory.createImportSessionBatchesBatch() instanceof ImportSession.Batches.Batch);
		assertThat(objectFactory
				.createImportSessionBatchesBatchBatchFields() instanceof ImportSession.Batches.Batch.BatchFields);
		assertThat(objectFactory
				.createImportSessionBatchesBatchBatchFieldsBatchField() instanceof ImportSession.Batches.Batch.BatchFields.BatchField);
		assertThat(objectFactory
				.createImportSessionBatchesBatchDocuments() instanceof ImportSession.Batches.Batch.Documents);
		assertThat(objectFactory
				.createImportSessionBatchesBatchDocumentsDocument() instanceof ImportSession.Batches.Batch.Documents.Document);
		assertThat(objectFactory
				.createImportSessionBatchesBatchDocumentsDocumentIndexFields() instanceof ImportSession.Batches.Batch.Documents.Document.IndexFields);
		assertThat(objectFactory
				.createImportSessionBatchesBatchDocumentsDocumentPages() instanceof ImportSession.Batches.Batch.Documents.Document.Pages);
		assertThat(objectFactory
				.createImportSessionBatchesBatchDocumentsDocumentPagesPage() instanceof ImportSession.Batches.Batch.Documents.Document.Pages.Page);
		assertThat(objectFactory
				.createImportSessionBatchesBatchDocumentsDocumentIndexFieldsIndexField() instanceof ImportSession.Batches.Batch.Documents.Document.IndexFields.IndexField);

	}
}
