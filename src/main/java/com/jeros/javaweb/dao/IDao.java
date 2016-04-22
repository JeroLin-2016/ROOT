package com.jeros.javaweb.dao;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


public interface IDao 
{
	@Select("select * from person where userName=#{userName} and password=#{password}")
	public Person getUser(@Param("userName") String userName, 
			   		      @Param("password") String password);
	
	
	@Insert("insert into content (price, title, image, summary, detail) "
			+ "values (#{price}, #{title}, #{image}, #{summary}, #{detail})")
	public void publishContent(@Param("price") BigInteger price,
							   @Param("title") String title, 
							   @Param("image") byte[] image,
							   @Param("summary") String summary, 
							   @Param("detail") byte[] detail);
	
	
	@Select("select * from content where price=#{price} and title=#{title} and image=#{image}"
			+ " and summary=#{summary} and detail=#{detail}")
	public Product selectProduct(@Param("price") BigInteger price,
								 @Param("title") String title,
								 @Param("image") byte[] image,
								 @Param("summary") String summary, 
								 @Param("detail") byte[] detail);
	
	
	@Select("Select * from content")
	public List<Product> getProductList();
	
	
	@Select("select * from content where id=#{id}")
	public Product selectProductId(@Param("id") int id);
	
	
	@Results({@Result(property = "id", column = "contentId"),
			  @Result(property = "buyPrice", column = "price")})
	@Select("select trx.contentId, trx.price from trx, content where content.id=trx.contentId")
	public List<Product> selectTrx();
	
	
	@Insert("insert into trx (contentId, personId, price, time) "
			+ "values (#{contentId}, #{personId}, #{price}, #{time})")
	public void Trx(@Param("contentId") int contentId,
					@Param("personId") int personId, 
					@Param("price") BigInteger price,
					@Param("time") BigInteger time);
	
	
	@Results({@Result(property = "id", column = "id"), 
			  @Result(property = "title", column = "title"),
			  @Result(property = "image", column = "image"),
			  @Result(property = "buyPrice", column = "price"),
			  @Result(property = "buyTime", column = "time")})
	@Select("Select content.id, content.title, content.image, trx.price, trx.time "
			+ "from trx, content where content.id=trx.contentId")
	public List<Account> getBuyedList();
	
	
	@Update("update content set "
			+ "price = #{price}, title = #{title}, image = #{image}, "
			+ "summary = #{summary}, detail = #{detail} "
			+ "where id = #{id}")
	public void updateContent(@Param("id") int id,
							  @Param("price") BigInteger price,
							  @Param("title") String title, 
							  @Param("image") byte[] image,
							  @Param("summary") String summary, 
							  @Param("detail") byte[] detail);
	
	@Delete("delete from content where id=#{id}")
	public void deleteProduct(@Param("id") int id);
}
