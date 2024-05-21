package com.sftp.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;

@Configuration
public class SftpConfig {

	@Value("${sftp.host.ip}")
	String hostIP;

	@Value("${sftp.port}")
	Integer port;

	@Value("${sftp.username}")
	String userName;

	@Value("${sftp.password}")
	String password;

	@Bean
	public DefaultSftpSessionFactory sftpSessionFactory() {

		DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory();
		factory.setHost(hostIP);
		factory.setPort(port);
		factory.setUser(userName);
		factory.setPassword(password);

		factory.setAllowUnknownKeys(true);
		return factory;
	}

	
}
