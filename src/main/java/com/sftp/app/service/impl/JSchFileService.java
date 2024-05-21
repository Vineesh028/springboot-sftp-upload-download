package com.sftp.app.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import com.sftp.app.service.FileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JSchFileService implements FileService{
	
	private final ChannelSftp channelSftp;
	

	@Override
	public File download(String fileName, String sourcePath, String targetPath) {
		  File downloaded = new File(targetPath+"/"+fileName);
	        String sftpFolderPath = sourcePath+"/";
			try (OutputStream outputStream = new FileOutputStream(downloaded)) {
				if (isDirectoryExists(channelSftp, sftpFolderPath)) {
	                String filePath = sftpFolderPath.concat(fileName);
	                if (isFileExists(channelSftp, filePath)) {
	                	channelSftp.get(filePath, outputStream);
	                }
	            } else {
	                throw new RuntimeException("Not able to detect directory " + sftpFolderPath);
	            }
			}catch(Exception e) {
				throw new RuntimeException("Exception while downloading from SFTP: " + e.getMessage());
			}
	       
	        return downloaded;
	}
	
	private final boolean isDirectoryExists(ChannelSftp channel, @NonNull String folderPath) {
        try {
            SftpATTRS attribs = channel.lstat(folderPath);
            return attribs.isDir();
        } catch (Exception e) {
            throw new RuntimeException("Folder is not found: " + folderPath.concat(e.getMessage()));
        }
    }
	
	protected final boolean isFileExists(ChannelSftp channelSftp, @NonNull String filePath) {
        boolean isFileExists = false;
        try {
            SftpATTRS attribs = channelSftp.lstat(filePath);
            if (Objects.nonNull(attribs)) {
                isFileExists = true;
            }
        } catch (Exception e) {
            throw new RuntimeException("File not found: "+ filePath.concat(e.getMessage()));
        }
        return isFileExists;
    }
	
	@Override
	public String upload(String fileName, String sourcePath, String targetPath){
		
         try {
        	File file  = new File(sourcePath+"/"+fileName);
     		InputStream is = new FileInputStream(file);
     		
			channelSftp.put(is, targetPath+"/"+fileName);
		} catch (SftpException e) {
			  throw new RuntimeException("File upload failed "+e.getLocalizedMessage());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
		
		return "Uploaded";
	}

}
