package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bean.StudentBean;
import com.factory.ConnectionFactory;

public class StudentDaoImpl implements StudentDao {

	@Override
	public String add(StudentBean stdbeandao) {
		String str = "";
		try {
			Connection conDaoImpl = ConnectionFactory.getConnection();
			PreparedStatement pst = conDaoImpl.prepareStatement("select * from studentmanagement where id = ?"); // for checking record in db
			pst.setString(1, stdbeandao.getSid());

			ResultSet rs = pst.executeQuery();
			boolean flag = rs.next();

			System.out.println(flag);
			if (flag == true) { // returns existed status if record is already there
				str = "existed";
			}

			else { // Adds the student record if that perticular record is not present in db
				PreparedStatement pst2 = conDaoImpl.prepareStatement("insert into studentmanagement values(?, ?, ?, ?)");
				pst2.setString(1, stdbeandao.getSid());
				pst2.setString(2, stdbeandao.getSname());
				pst2.setLong(3, stdbeandao.getSphone());
				pst2.setString(4, stdbeandao.getSaddress());

				int num = pst2.executeUpdate();
				System.out.println(num);
				if (num == 1) { // returns success msg if record is added to db
					str = "success";
				} else { // returns failure msg if record is not added to db
					str = "failure";
				}
			}

		} catch (Exception e) {
			str = "failure";
			System.out.println(e);
		}
		return str;
	}

	@Override
	public StudentBean search(String stdiddao) {
		StudentBean bean = null;
		try {
			Connection con = ConnectionFactory.getConnection();
			PreparedStatement pst = con.prepareStatement("select * from studentmanagement where id = ?");
			pst.setString(1, stdiddao);
			
			ResultSet rs = pst.executeQuery();
			boolean flag = rs.next();
			
			if(flag == true) {
			  bean = new StudentBean();	
				bean.setSid(rs.getString(1));
				bean.setSname(rs.getString(2));
				bean.setSphone(rs.getLong(3));
				bean.setSaddress(rs.getString(4));
			}
			else {
				bean = null;
			}
		} 
		catch (Exception e) {
			System.out.println(e);
		}
		return bean;
	}

	
	@Override
	public String update(StudentBean beandao) {
		String st = "";
		
		try {
			Connection con = ConnectionFactory.getConnection();
			PreparedStatement pst = con.prepareStatement("update studentmanagement set name=?, phone=?, address=? where id=?");
			pst.setString(1, beandao.getSname());
			pst.setLong(2, beandao.getSphone());
			pst.setString(3, beandao.getSaddress());
			pst.setString(4, beandao.getSid());
			
			int n = pst.executeUpdate();
			
			if(n==1) {
				st = "success";
			}
			else {
				st="failure";
			}
		} catch (SQLException e) {
			st="failure";
			System.out.println(e);
			e.printStackTrace();
		}
		return st;
	}

	@Override
	public String delete(String iddao) {
		String st ="";
		
		StudentBean stbean =  search(iddao);
		 if(stbean == null) {
			 st = "notexist";
		 }
		 else {
			 try {
					Connection con = ConnectionFactory.getConnection();
					PreparedStatement pst = con.prepareStatement("delete from studentmanagement where id=?");
					pst.setString(1, iddao);
					
					int rowcount = pst.executeUpdate();
					System.out.println("******"+rowcount);
					if(rowcount == 1) {
						st = "success";
					}
					else {
						st = "failure";
					}
				}
				catch(Exception e) {
					st = "failure";
					System.out.println(e);
				} 
		 }
		return st ;
	}

}
