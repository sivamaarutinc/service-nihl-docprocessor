package com.wsib.nihl.service;

import java.io.InputStream;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.wsib.nihl.config.SftpProperties;

import ch.qos.logback.classic.Logger;

@Service("fileUploadService")
public class FileUploadService {
	private Logger logger = (Logger) LoggerFactory.getLogger(FileUploadService.class);

	@Autowired
	private SftpProperties config;

	// Set the prompt when logging in for the first time. Optional value: (ask | yes
	// | no)
	private static final String SESSION_CONFIG_STRICT_HOST_KEY_CHECKING = "StrictHostKeyChecking";

	/**
	 * Create an SFTP connection
	 * 
	 * @return
	 * @throws Exception
	 */
	private ChannelSftp createSftp() throws Exception {
		JSch jsch = new JSch();

		Session session = createSession(jsch, config.getHost(), config.getUsername(), config.getPort());

		session.setPassword(config.getPassword());
		session.setConfig("StrictHostKeyChecking", "no");
		session.connect(Integer.parseInt(config.getSessionConnectTimeout()));

		Channel channel = session.openChannel(config.getProtocol());
		channel.connect(Integer.parseInt(config.getChannelConnectedTimeout()));
		logger.info("Created SFTP connection at :" + config.getHost());
		logger.info("Session session = createSession(jsch, " + config.getHost() + ", " + config.getUsername() + ", "
				+ config.getPort());
		return (ChannelSftp) channel;
	}

	/**
	 * Create session
	 * 
	 * @param jsch
	 * @param host
	 * @param username
	 * @param port
	 * @return
	 * @throws Exception
	 */
	private Session createSession(JSch jsch, String host, String username, String port) throws Exception {
		Session session = null;

		if (Integer.parseInt(port) <= 0) {
			session = jsch.getSession(username, host);
		} else {
			session = jsch.getSession(username, host, Integer.parseInt(port));
		}

		if (session == null) {
			throw new Exception(host + " session is null");
		}

		session.setConfig(SESSION_CONFIG_STRICT_HOST_KEY_CHECKING, config.getSessionStrictHostKeyChecking());
		return session;
	}

	/**
	 * Close the connection
	 * 
	 * @param sftp
	 */
	private void disconnect(ChannelSftp sftp) throws Exception {
		try {
			if (sftp != null) {
				if (sftp.isConnected()) {
					sftp.disconnect();
				} else if (sftp.isClosed()) {

				}
				if (null != sftp.getSession()) {
					sftp.getSession().disconnect();
				}
			}
			logger.info("SFTP Connection closed");
		} catch (JSchException e) {
			logger.error(e.getLocalizedMessage());
			throw e;
		}
	}

	/**
	 * @param targetPath
	 * @param inputStream
	 * @return
	 * @throws Exception
	 */
	public boolean uploadFile(String targetPath, InputStream inputStream) throws Exception {
		ChannelSftp sftp = this.createSftp();
		// targetPath = config.getRoot();
		try {
			sftp.cd(config.getRoot());
			int index = targetPath.lastIndexOf("\\");
			// String fileDir = targetPath.substring(0, index);
			String fileName = targetPath.substring(index + 1);
			// boolean dirs = this.createDirs(fileDir, sftp);
			/*
			 * if (!dirs) { throw new Exception("Upload File failure"); }
			 */
			sftp.put(inputStream, fileName);

			logger.info("Files copied to SFTP location at:" + config.getRoot());
			logger.info("Files copied to SFTP file name" + fileName);
			logger.info("sftp.getHome()" + sftp.getHome());
			logger.info("sftp.getServerVersion()" + sftp.getServerVersion());
			logger.info("sftp.getId()" + sftp.getId());
			return true;
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			throw e;
		} finally {
			this.disconnect(sftp);
		}
	}
}