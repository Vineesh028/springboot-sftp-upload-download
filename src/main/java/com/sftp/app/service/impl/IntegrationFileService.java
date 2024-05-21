package com.sftp.app.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

import org.springframework.integration.sftp.session.DefaultSftpSessionFactory;
import org.springframework.integration.sftp.session.SftpSession;
import org.springframework.stereotype.Service;

import com.sftp.app.service.FileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IntegrationFileService implements FileService{
	
	private final DefaultSftpSessionFactory sftpSessionFactory;
	
	
	@Override
	public File download(String fileName, String sourcePath, String targetPath) {
		 SftpSession session = sftpSessionFactory.getSession();
	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	        File downloaded = new File(targetPath+"/"+fileName);
	        try {
	            session.read(sourcePath+"/"+fileName, outputStream);
	            try (FileOutputStream fos = new FileOutputStream(downloaded)) {
	                fos.write(outputStream.toByteArray());
	            } catch (IOException ioe) {
	                ioe.printStackTrace();
	            }
	        } catch (IOException e) {
	            throw new RuntimeException(e);
	        }
	        
	        return downloaded;
	}

	
	@Override
	public String upload(String fileName, String sourcePath, String targetPath){
		SftpSession session = sftpSessionFactory.getSession();
		

        try {
        	  File file  = new File(sourcePath+"/"+fileName);
        	  InputStream is = new FileInputStream(file);
     		
        	  String filename = String.format(fileName, LocalDateTime.now());
              String destination = String.format(targetPath+"/beinguploaded/%s", filename);

              session.write(is, destination);
              String donedestination = String.format(targetPath+"/%s", filename);
              session.rename(destination, donedestination);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
		return "Uploaded";
	}

	
}
