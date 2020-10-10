package com.wsib.nihl.utiltest;

import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.itextpdf.text.DocumentException;
import com.wsib.nihl.config.ApplicationProperties;
import com.wsib.nihl.util.PDFAConverter;

@ExtendWith(MockitoExtension.class)
public class PDFAConverterTest {

	@InjectMocks
	PDFAConverter pdfaConverter;

	@Mock
	ApplicationProperties applicationPropertiesMock;

	@TempDir
	Path path;
	
	@TempDir
	Path pdfpath;

	@Test
	void testConvertPDFToPDFA() throws FileNotFoundException, IOException, DocumentException {
		String tempFolder = System.getProperty("user.dir") + File.separator + "tmp";
		String pdfFolder = System.getProperty("user.dir") + File.separator + "tmp" + File.separator + "PDFS";
		File file = new File(pdfFolder);
		file.mkdirs();
		String xHtml = " <!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\"\n"
				+ "\"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">\n"
				+ "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" + "<head>\n"
				+ "  <title>Title of document</title>\n" + "</head>\n" + "<body>\n" + "\n" + "  some content here...\n"
				+ "\n" + "</body>\n" + "</html> ";
		ITextRenderer renderer = new ITextRenderer();

		String baseUrl = FileSystems.getDefault().getPath("src", "main", "resources", "templates").toUri().toURL()
				.toString();
		renderer.setDocumentFromString(xHtml, baseUrl);
		renderer.layout();
		OutputStream outputStream = new FileOutputStream(pdfFolder + File.separator + "sample.PDF");
		renderer.createPDF(outputStream);
		outputStream.close();
		when(applicationPropertiesMock.getPdfaPath()).thenReturn(pdfFolder);
		when(applicationPropertiesMock.getPathFormat()).thenReturn(File.separator);
		pdfaConverter.convertPDFToPDFA(pdfFolder + File.separator + "sample.PDF",
				pdfFolder + File.separator + "sample.PDF", "1");
		FileUtils.forceDelete(new File(tempFolder));
	}
	
	
}
