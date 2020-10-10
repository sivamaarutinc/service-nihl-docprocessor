package com.wsib.nihl.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


@Primary
@Configuration
public class SftpProperties {
	
    public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getPassphrase() {
		return passphrase;
	}

	public void setPassphrase(String passphrase) {
		this.passphrase = passphrase;
	}

	public String getSessionStrictHostKeyChecking() {
		return sessionStrictHostKeyChecking;
	}

	public void setSessionStrictHostKeyChecking(String sessionStrictHostKeyChecking) {
		this.sessionStrictHostKeyChecking = sessionStrictHostKeyChecking;
	}

	public String getSessionConnectTimeout() {
		return sessionConnectTimeout;
	}

	public void setSessionConnectTimeout(String sessionConnectTimeout) {
		this.sessionConnectTimeout = sessionConnectTimeout;
	}

	public String getChannelConnectedTimeout() {
		return channelConnectedTimeout;
	}

	public void setChannelConnectedTimeout(String channelConnectedTimeout) {
		this.channelConnectedTimeout = channelConnectedTimeout;
	}
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
}