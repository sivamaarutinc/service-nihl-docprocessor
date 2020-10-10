package com.wsib.nihl.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDMetadata;
import org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure.PDMarkInfo;
import org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure.PDStructureTreeRoot;
import org.apache.pdfbox.pdmodel.graphics.color.PDOutputIntent;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.viewerpreferences.PDViewerPreferences;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.xmpbox.XMPMetadata;
import org.apache.xmpbox.schema.DublinCoreSchema;
import org.apache.xmpbox.schema.PDFAIdentificationSchema;
import org.apache.xmpbox.xml.XmpSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wsib.nihl.config.ApplicationProperties;

@Component
public class PDFAConverter{
	private Logger logger = LoggerFactory.getLogger(PDFAConverter.class);
	
	@Autowired
	ApplicationProperties appConfig;
	
	public String convertPDFToPDFA(String sourceFile, String destFile, String docName) throws FileNotFoundException, IOException {
		FileInputStream in = new FileInputStream(sourceFile);

		PDDocument doc = new PDDocument();
		try 
		{
			PDPage page = new PDPage();
			doc.addPage(page); 
			doc.setVersion(1.7f);
			String resultFile = destFile;  
			String pathRoot = appConfig.getPdfaPath();
			
			File file = new File(pathRoot);
			boolean fileDir = false;
			if (!file.exists()) {
				if (file.mkdir()) {
					fileDir = true;
				} else {
					fileDir = true;
				}
			}else {
				//FileUtils.deleteDirectory(file);
				file.mkdir();
				fileDir = true;
			}
			logger.info("Directory created for PDF :"+pathRoot);
			PDDocument docSource = PDDocument.load(in);
			PDFRenderer pdfRenderer = new PDFRenderer(docSource);  
			PDFMergerUtility pdfMerger = new PDFMergerUtility();
			pdfMerger.setDestinationFileName(resultFile);

			for (int numPage = 0; numPage < docSource.getNumberOfPages(); numPage++) {
				PDPageContentStream contents = new PDPageContentStream(doc, page,PDPageContentStream.AppendMode.OVERWRITE, true);

				BufferedImage imagePage = pdfRenderer.renderImageWithDPI(numPage, 200); 
				PDImageXObject pdfXOImage = LosslessFactory.createFromImage(doc, imagePage);
				contents.drawImage(pdfXOImage, 0,0, page.getMediaBox().getWidth(),
						page.getMediaBox().getHeight()); 
				contents.close();

				// add XMP metadata
				XMPMetadata xmp = XMPMetadata.createXMPMetadata();
				PDDocumentCatalog catalogue = doc.getDocumentCatalog();
				Calendar cal =  Calendar.getInstance();          

				DublinCoreSchema dc = xmp.createAndAddDublinCoreSchema();
				// dc.setTitle(file);
				dc.addCreator("My APPLICATION Creator");
				dc.addDate(cal);
				PDFAIdentificationSchema id = xmp.createAndAddPFAIdentificationSchema();
				id.setPart(3);  //value => 2|3
				id.setConformance("A"); // value => A|B|U

				XmpSerializer serializer = new XmpSerializer();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				serializer.serialize(xmp, baos, true);

				PDMetadata metadata = new PDMetadata(doc);
				metadata.importXMPMetadata(baos.toByteArray());                
				catalogue.setMetadata(metadata);
				// sRGB output intent
				InputStream colorProfile = PDFAConverter.class.getResourceAsStream("/org/apache/pdfbox/resources/icc/ISOcoated_v2_300_bas.icc");

				PDOutputIntent intent = new PDOutputIntent(doc, colorProfile);
				intent.setInfo("sRGB IEC61966-2.1");
				intent.setOutputCondition("sRGB IEC61966-2.1");
				intent.setOutputConditionIdentifier("sRGB IEC61966-2.1");
				intent.setRegistryName("http://www.color.org");

				catalogue.addOutputIntent(intent);  
				catalogue.setLanguage("en-US");

				PDViewerPreferences pdViewer =new PDViewerPreferences(page.getCOSObject());
				pdViewer.setDisplayDocTitle(true);; 
				catalogue.setViewerPreferences(pdViewer);

				PDMarkInfo  mark = new PDMarkInfo(); // new PDMarkInfo(page.getCOSObject()); 
				PDStructureTreeRoot treeRoot = new PDStructureTreeRoot(); 
				catalogue.setMarkInfo(mark);
				catalogue.setStructureTreeRoot(treeRoot);           
				catalogue.getMarkInfo().setMarked(true);

				PDDocumentInformation info = doc.getDocumentInformation();               
				info.setCreationDate(cal);
				info.setModificationDate(cal);            
				info.setAuthor("My APPLICATION Author");
				info.setProducer("My APPLICATION Producer");;
				info.setCreator("My APPLICATION Creator");
				info.setTitle("PDF title");
				info.setSubject("PDF to PDF/A{2,3}-{A,U,B}");           
				doc.save(pathRoot+appConfig.getPathFormat()+"PDFA-"+docName+numPage+".PDF");
				pdfMerger.addSource( new File(pathRoot+appConfig.getPathFormat()+"PDFA-"+docName+numPage+".PDF"));
			}
			pdfMerger.mergeDocuments(null);
			in.close();
			FileUtils.deleteDirectory(file);
			logger.info("PDFA file generated under the path :"+destFile);
		}catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw new IllegalArgumentException(e);
		}
		return null;
	}

}
