package com.jeros.javaweb.dao;

import java.math.BigInteger;

public class Account 
{
	private int id;
	private String title; 
	private byte[] image;
	private BigInteger buyPrice;
	private BigInteger buyTime;
	
	
	/**
	 * @return the id
	 */
	public int getId() throws NullPointerException
	{
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) 
	{
		this.id = id;
	}


	/**
	 * @return the title
	 */
	public String getTitle() throws NullPointerException
	{
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) 
	{
		this.title = title;
	}

	/**
	 * @return the image
	 */
	public String getImage() throws NullPointerException
	{
		String strimage = new String(image);
		return strimage;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(byte[] image) 
	{
		this.image = image;
	}

	
	/**
	 * @return the buyPrice
	 */
	public BigInteger getBuyPrice() {
		return buyPrice;
	}

	/**
	 * @param buyPrice the buyPrice to set
	 */
	public void setBuyPrice(BigInteger buyPrice) {
		this.buyPrice = buyPrice;
	}
	
	
	/**
	 * @return the buyTime
	 */
	public BigInteger getBuyTime() {
		return buyTime;
	}

	/**
	 * @param time the buyTime to set
	 */
	public void setBuyTime(BigInteger buyTime) {
		this.buyTime = buyTime;
	}

}
