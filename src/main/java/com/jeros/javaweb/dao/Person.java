package com.jeros.javaweb.dao;

public class Person 
{
	private int id;
	private int userType;
	private String userName;
	private String nickName;
	
	public int getId() throws NullPointerException
	{
		return id;
	}
	
	public int getUserType() throws NullPointerException
	{
		return userType;
	}
	
	public String getUserName() throws NullPointerException
	{
		return userName;
	}
	
	public String getNickName() throws NullPointerException
	{
		return nickName;
	}
}
