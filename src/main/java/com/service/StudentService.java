package com.service;

import com.bean.StudentBean;

public interface StudentService {
	
	public String addStudent(StudentBean stdbean);
	public StudentBean searchStudent(String stdid);
	public String updateStudent(StudentBean bean);
	public String deleteStudent(String id);

}
