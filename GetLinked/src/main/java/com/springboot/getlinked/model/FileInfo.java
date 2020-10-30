package com.springboot.getlinked.model;

public class FileInfo {
  private String basename;
  private String extension;
  private String subDirectory;
  private String baseDirectory;

  
  public FileInfo(String basename, String extension, String baseDirectory) {

	this.basename = basename;
	this.extension = extension;
	this.baseDirectory = baseDirectory;
}


public String getBasename() {
	return basename;
}


public void setBasename(String basename) {
	this.basename = basename;
}


public String getExtension() {
	return extension;
}


public void setExtension(String extension) {
	this.extension = extension;
}


public String getSubDirectory() {
	return subDirectory;
}


public void setSubDirectory(String subDirectory) {
	this.subDirectory = subDirectory;
}


public String getBaseDirectory() {
	return baseDirectory;
}


public void setBaseDirectory(String baseDirectory) {
	this.baseDirectory = baseDirectory;
}
  
  
  
  
}
