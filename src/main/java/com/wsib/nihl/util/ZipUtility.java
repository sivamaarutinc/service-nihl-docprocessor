package com.wsib.nihl.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ZipUtility {
	private Logger logger = LoggerFactory.getLogger(ZipUtility.class);

     public void zip(final String sourcNoteseDirPath, final String zipFilePath) throws IOException {
        Path zipFile = Files.createFile(Paths.get(zipFilePath));

        Path sourceDirPath = Paths.get(sourcNoteseDirPath);
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(zipFile));
             Stream<Path> paths = Files.walk(sourceDirPath)) {
            paths
                    .filter(path -> !Files.isDirectory(path))
                    .forEach(path -> {
                        ZipEntry zipEntry = new ZipEntry(sourceDirPath.relativize(path).toString());
                        try {
                            zipOutputStream.putNextEntry(zipEntry);
                            Files.copy(path, zipOutputStream);
                            zipOutputStream.closeEntry();
                        } catch (IOException e) {
                           logger.error(e.getMessage());
                        }
                    });
        }
        logger.info("Zip file generated:"+zipFilePath);

    }
}