package com.service;

import com.bean.StudentBean;
import com.dao.StudentDao;
import com.dao.StudentDaoFactory;

public class StudentServiceImpl implements StudentService {

	@Override
	public String addStudent(StudentBean stdbean) { 
		     //Has to manupulates the data in database->so takes bean object, then returns only response->so return type is String
		  StudentDao stddao = StudentDaoFactory.getStudentDao();
		 String status =  stddao.add(stdbean);
		return status;
	}

	@Override
	public StudentBean searchStudent(String stdid) { 
		   //No need to to manuplates the data in database->so takes String value, but Has to returns the data in db->so return type is bean object 
	     StudentDao  stddao = StudentDaoFactory.getStudentDao();
	     StudentBean stdbean = stddao.search(stdid);
		return stdbean;
	}

	@Override
	public String updateStudent(StudentBean bean) { 
		   //Need to manupulates data in db->so input type is bean object, then returns only response->so return type is String
		StudentDao  stddao = StudentDaoFactory.getStudentDao();
		String str = stddao.update(bean);
		 return str;
	}

	@Override
	public String deleteStudent(String id) {  
		  //Need to manupulates database but in the case of deletion record only->so input type is String value, and returns only response->so return type is also String
		StudentDao dao = StudentDaoFactory.getStudentDao();
		String s = dao.delete(id);
		return s;
	}

}
