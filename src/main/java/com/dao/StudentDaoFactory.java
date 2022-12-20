package com.dao;

public class StudentDaoFactory {

	private static StudentDao stdDao;
	static {
		stdDao = new StudentDaoImpl();
	}
	
	public static StudentDao getStudentDao() {
		return stdDao;
	}
	
}
