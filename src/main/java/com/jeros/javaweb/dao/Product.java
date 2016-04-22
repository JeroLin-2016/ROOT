package com.jeros.javaweb.dao;

import java.math.BigInteger;

public class Product 
{
	private int id;
	private BigInteger price;
	private String title; 
	private byte[] image;
	private String summary; 
	private byte[] detail;
	private BigInteger buyPrice;
	private boolean isBuy;
	private boolean isSell;
	private BigInteger time;
	
	
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
	 * @return the price
	 */
	public BigInteger getPrice() throws NullPointerException
	{
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(BigInteger price) 
	{
		this.price = price;
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
	 * @return the summary
	 */
	public String getSummary() throws NullPointerException
	{
		return summary;
	}

	/**
	 * @param summary the summary to set
	 */
	public void setSummary(String summary) 
	{
		this.summary = summary;
	}

	/**
	 * @return the detail
	 */
	public String getDetail() throws NullPointerException
	{
		String strdetail = new String(detail);
		return strdetail;
	}

	/**
	 * @param detail the detail to set
	 */
	public void setDetail(byte[] detail) 
	{
		this.detail = detail;
	}
	
	/**
	 * @return the isBuy
	 */
	public boolean getIsBuy() {
		return isBuy;
	}

	/**
	 * @param isbuy the isBuy to set
	 */
	public void setIsBuy(boolean isBuy) {
		this.isBuy = isBuy;
	}

	/**
	 * @return the isSell
	 */
	public boolean getIsSell() {
		return isSell;
	}

	/**
	 * @param issell the isSell to set
	 */
	public void setIsSell(boolean isSell) {
		this.isSell = isSell;
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
	 * @return the time
	 */
	public BigInteger getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(BigInteger time) {
		this.time = time;
	}

}
