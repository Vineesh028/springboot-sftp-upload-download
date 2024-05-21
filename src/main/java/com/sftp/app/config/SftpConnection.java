package com.sftp.app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

@Configuration
public class SftpConnection {
	
	@Value("${sftp.host.ip}")
	String hostIP;

	@Value("${sftp.port}")
    Integer port;

	@Value("${sftp.username}")
	String userName;
	
	@Value("${sftp.password}")
	String password;
	
	public static final String  SFTP= "sftp";
	
	@Bean
	public ChannelSftp channelSftp(){		
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(userName, hostIP, port);
            if(password == null || password.isEmpty()) {
            	throw new RuntimeException("No password is provided to connect to SFTP");
            }
            java.util.Properties config = new java.util.Properties(); 
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.setPassword(password);
                        session.connect();
            ChannelSftp channelSftp = (ChannelSftp) session.openChannel(SFTP);
            channelSftp.connect();
            return channelSftp;
        } catch (Exception e) {
        	throw new RuntimeException("Error connecting to SFTP server: "+ e.getMessage());
        }
    }
	
}