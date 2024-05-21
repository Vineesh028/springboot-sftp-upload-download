package com.sftp.app.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sftp.app.service.FileService;

@RestController
public class FileController {

	@Autowired
	@Qualifier("integrationFileService")
	private FileService fileService ;
	
	@Autowired
	@Qualifier("JSchFileService")
	private FileService fileService2 ;
	
    @GetMapping("/v1/download")
    public File downloadFile(@RequestParam String fileName, @RequestParam String sourceDestination, @RequestParam String targetDestination){

    	long t1 = System.currentTimeMillis();
    	File file = fileService.download(fileName, sourceDestination, targetDestination);
    	
    	System.err.println(System.currentTimeMillis()-t1);
       
    	return file;
    }
    
    @PostMapping("/v1/upload")
    public String uploadFile(@RequestParam String fileName, @RequestParam String sourceDestination, @RequestParam String targetDestination){

    	long t1 = System.currentTimeMillis();
    	String response = fileService.upload(fileName, sourceDestination, targetDestination);
    	
    	System.err.println(System.currentTimeMillis()-t1);
       
    	return response;
    }
    
    @GetMapping("/v2/download")
    public File downloadFile2(@RequestParam String fileName, @RequestParam String sourceDestination, @RequestParam String targetDestination){

    	long t1 = System.currentTimeMillis();
    	File file = fileService2.download(fileName, sourceDestination, targetDestination);
    	
    	System.err.println(System.currentTimeMillis()-t1);
       
    	return file;
    }
    
    @PostMapping("/v2/upload")
    public String uploadFile2(@RequestParam String fileName, @RequestParam String sourceDestination, @RequestParam String targetDestination){

    	long t1 = System.currentTimeMillis();
    	String response = fileService2.upload(fileName, sourceDestination, targetDestination);
    	
    	System.err.println(System.currentTimeMillis()-t1);
       
    	return response;
    }

}