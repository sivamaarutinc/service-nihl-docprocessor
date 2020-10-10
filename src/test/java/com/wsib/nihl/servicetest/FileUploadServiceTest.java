package com.wsib.nihl.servicetest;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.wsib.nihl.config.ApplicationProperties;
import com.wsib.nihl.config.SftpProperties;
import com.wsib.nihl.service.FileUploadService;

@SpringBootTest(classes = { SftpProperties.class, ApplicationProperties.class })
@ExtendWith(MockitoExtension.class)
public class FileUploadServiceTest {

	@Autowired
	SftpProperties sftpProperties;

	@Autowired
	ApplicationProperties applicationProperties;

	@InjectMocks
	FileUploadService fileUploadService;

	@Mock
	SftpProperties config;

	@Mock
	ChannelSftp channelSftp;

	@Mock
	Session session;

	@Spy
	JSch jsch;
	

//	@Test
//	void testCreateSftp() throws Exception {
//
//		String junitPath = applicationProperties.getDocRoot() + File.separator + "junit";
//
//		when(config.getHost()).thenReturn(sftpProperties.getHost());
//		when(config.getUsername()).thenReturn(sftpProperties.getUsername());
//		when(config.getPassword()).thenReturn(sftpProperties.getPassword());
//		when(config.getSessionStrictHostKeyChecking()).thenReturn(sftpProperties.getSessionStrictHostKeyChecking());
//		when(config.getPort()).thenReturn(sftpProperties.getPort());
//		when(config.getRoot()).thenReturn(sftpProperties.getRoot());
//		when(config.getSessionConnectTimeout()).thenReturn(sftpProperties.getChannelConnectedTimeout());
//
//		doReturn(session).when(jsch).getSession(sftpProperties.getUsername(), sftpProperties.getHost(),
//				Integer.parseInt(sftpProperties.getPort()));
//		
//
//		doNothing().when(session).connect(Integer.parseInt(sftpProperties.getSessionConnectTimeout()));
//		File file = new File(junitPath);
//		File uploadFile = new File(junitPath + File.separator + "junittest.txt");
//		boolean a = uploadFile.createNewFile();
//		InputStream inputStream = new FileInputStream(uploadFile);
//		fileUploadService.uploadFile(junitPath + File.separator + "junittest.txt", inputStream);
//		FileUtils.deleteDirectory(file);
//
//	}
}
