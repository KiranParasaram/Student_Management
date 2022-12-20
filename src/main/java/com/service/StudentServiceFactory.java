package com.service;

public class StudentServiceFactory {
	
	private static StudentService stdService;
	static {
		stdService = new StudentServiceImpl();
	}
	
	public static StudentService getStudentService() {
		return stdService;
	}

}
