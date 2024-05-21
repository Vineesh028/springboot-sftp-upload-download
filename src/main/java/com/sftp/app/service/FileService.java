package com.sftp.app.service;

import java.io.File;

public interface FileService {
	
	File download(String fileName, String sourcePath, String targetPath);

	String upload(String fileName, String sourcePath, String targetPath);
}
