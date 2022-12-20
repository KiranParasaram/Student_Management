package com.dao;

import com.bean.StudentBean;

public interface StudentDao {
	
	public String add(StudentBean stdbeandao);
	public StudentBean search(String stdiddao);
	public String update(StudentBean beandao);
	public String delete(String iddao);
 
}
