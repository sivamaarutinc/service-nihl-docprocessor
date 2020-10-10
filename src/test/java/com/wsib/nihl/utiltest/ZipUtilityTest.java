package com.wsib.nihl.utiltest;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.itextpdf.text.DocumentException;
import com.wsib.nihl.util.ZipUtility;

@ExtendWith(MockitoExtension.class)
class ZipUtilityTest {

	@InjectMocks
	ZipUtility zipUtility;

	@Test
	void testZip() throws DocumentException, IOException {
		String tempFolderPath = System.getProperty("user.dir") + File.separator + "tmp";
		File tempFolder = new File(tempFolderPath);
		tempFolder.mkdir();
		String documentpath = tempFolder + File.separator + "Documents";
		File documentFolder = new File(documentpath);
		documentFolder.exists();
		documentFolder.mkdir();
		File file2 = new File(documentpath + File.separator + "test.txt");
		file2.createNewFile();
		zipUtility.zip(documentpath, tempFolderPath + File.separator + "00.zip");
		FileUtils.forceDelete(new File(tempFolderPath));
	}

}
