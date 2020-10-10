package com.wsib.nihl.configtest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.wsib.nihl.config.SftpProperties;

@SpringBootTest(classes = SftpProperties.class)
public class SftpPropertiesTest {

	@Autowired
	SftpProperties sftpProperties;

	@Value("${sftp.client.host}")
	private String host;

	@Value("${sftp.client.port}")
	private String port;

	@Value("${sftp.client.protocol}")
	private String protocol;

	@Value("${sftp.client.username}")
	private String username;

	@Value("${sftp.client.password}")
	private String password;

	@Value("${sftp.client.root}")
	private String root;

	@Value("${sftp.client.privateKey}")
	private String privateKey;

	@Value("${sftp.client.passphrase}")
	private String passphrase;

	@Value("${sftp.client.sessionStrictHostKeyChecking}")
	private String sessionStrictHostKeyChecking;

	@Value("${sftp.client.sessionConnectTimeout}")
	private String sessionConnectTimeout;

	@Value("${sftp.client.channelConnectedTimeout}")
	private String channelConnectedTimeout;

	SftpProperties sftpProperties2;

	@BeforeEach
	void setData() {
		sftpProperties2 = new SftpProperties();
		sftpProperties2.setChannelConnectedTimeout(channelConnectedTimeout);
		sftpProperties2.setHost(host);
		sftpProperties2.setPassphrase(passphrase);
		sftpProperties2.setPassword(password);
		sftpProperties2.setPort(port);
		sftpProperties2.setPrivateKey(privateKey);
		sftpProperties2.setProtocol(protocol);
		sftpProperties2.setRoot(root);
		sftpProperties2.setSessionConnectTimeout(sessionConnectTimeout);
		sftpProperties2.setSessionStrictHostKeyChecking(sessionStrictHostKeyChecking);
		sftpProperties2.setUsername(username);

	}

	@Test
	void testGetters() {
		assertEquals(channelConnectedTimeout, sftpProperties2.getChannelConnectedTimeout());
		assertEquals(host, sftpProperties2.getHost());
		assertEquals(passphrase, sftpProperties2.getPassphrase());
		assertEquals(password, sftpProperties2.getPassword());
		assertEquals(port, sftpProperties2.getPort());
		assertEquals(privateKey, sftpProperties2.getPrivateKey());
		assertEquals(protocol, sftpProperties2.getProtocol());
		assertEquals(root, sftpProperties2.getRoot());
		assertEquals(sessionConnectTimeout, sftpProperties2.getSessionConnectTimeout());
		assertEquals(sessionStrictHostKeyChecking, sftpProperties2.getSessionStrictHostKeyChecking());
		assertEquals(username, sftpProperties2.getUsername());
	}
}
