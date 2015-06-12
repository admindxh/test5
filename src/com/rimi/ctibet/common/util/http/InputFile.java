package com.rimi.ctibet.common.util.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public class InputFile {

	private String name; // 变量名

	private InputStream fis;

	private String contentType; // 文件类型

	private String fileName; // 文件名

	public InputFile() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * spring-MVC support
	 * 
	 * @param name
	 * @param file
	 * @throws IOException
	 */
	public InputFile(MultipartFile file, String name) throws IOException {
		this.name = name;
		this.fis = file.getInputStream();
		this.contentType = file.getContentType();
		this.fileName = file.getOriginalFilename();

	}

	public InputFile(File file, String name, String contentType) throws IOException {
		this.name = name;
		this.fis = new FileInputStream(file);
		this.contentType = contentType;
		this.fileName = file.getName();
	}

	public String getName() {
		return name;
	}

	public InputStream getInputStream() {
		return fis;
	}

	public String getContentType() {
		return contentType;
	}

	public String getFileName() {
		return fileName;
	}
}
