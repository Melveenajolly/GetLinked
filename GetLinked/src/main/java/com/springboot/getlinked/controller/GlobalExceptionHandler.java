package com.springboot.getlinked.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(MultipartException.class)
	@ResponseBody
	String fileUploadHandler(Exception e) {
		e.printStackTrace();
		return "Error occured uploadind photo";
		
	}

}
