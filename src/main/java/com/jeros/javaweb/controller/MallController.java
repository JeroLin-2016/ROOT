package com.jeros.javaweb.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeros.javaweb.dao.Account;
import com.jeros.javaweb.dao.IDao;
import com.jeros.javaweb.dao.Person;
import com.jeros.javaweb.dao.Product;

import net.sf.json.JSONObject;

@Controller
public class MallController 
{
	JSONObject user = new JSONObject();
	JSONObject product = new JSONObject();
	
	
	
	//【系统入口文档】1、登录页
	@RequestMapping("/login")
	public String login()
	{	
		return "login";
	}
	
	
	
	//【系统入口文档】2、退出
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response)
	{	
		//销毁Session
		request.getSession().invalidate();
		
		user.clear();

		return "login";
	}
	
	
	
	
	//【系统入口文档】3、展示页
	@RequestMapping("/")
	public String index(ModelMap map)
	{
		if(!user.isEmpty())
		{
			map.addAttribute("user", user);
		}
		
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-mybatis.xml");
		IDao dao = context.getBean("IDao", IDao.class);
		List<Product> productList = dao.getProductList();
		List<Product> trxList = dao.selectTrx();
		((ConfigurableApplicationContext) context).close();
		
		for (Product product : productList) 
		{
			for (Product buyedproduct : trxList)
			{
				if (product.getId() == buyedproduct.getId())
				{
					product.setIsSell(true);
					product.setIsBuy(true);
				}
			}
		}
		
		if(!productList.isEmpty())
		{
			map.addAttribute("productList", productList);
		}
		
		return "index";
	}
	
	
	
	
	//【系统入口文档】4、查看页
	@RequestMapping("/show")
	public String show(@RequestParam("id") int id, ModelMap map)
	{
		if(!user.isEmpty())
		{
			map.addAttribute("user", user);
		}
		
		try
		{
			product.clear();
			
			ApplicationContext context = new ClassPathXmlApplicationContext("spring-mybatis.xml");
			IDao dao = context.getBean("IDao", IDao.class);
			Product published = dao.selectProductId(id);
			List<Product> trxList = dao.selectTrx();
			((ConfigurableApplicationContext) context).close();
			
			product.put("id", published.getId());
			product.put("price", published.getPrice());
			product.put("title", published.getTitle());
			product.put("image", published.getImage());
			product.put("summary", published.getSummary());
			product.put("detail", published.getDetail());
			for (Product buyedproduct : trxList)
			{
				if (buyedproduct.getId() == product.getInt("id"))
				{
					System.out.println(product.getInt("id"));
					product.put("buyPrice", buyedproduct.getBuyPrice());
					product.put("isBuy", true);
					product.put("isSell", true);
				}
			}
		}
		catch(NullPointerException e)
		{
			product.clear();
		}
		
		if(!product.isEmpty())
		{
			map.addAttribute("product", product);
		}

		return "show";
	}
	
	
	
	//【系统入口文档】5、账务页
	@RequestMapping("/account")
	public String account(ModelMap map)
	{
		if(user.getInt("usertype") == 0)
		{
			map.addAttribute("user", user);
			
			ApplicationContext context = new ClassPathXmlApplicationContext("spring-mybatis.xml");
			IDao dao = context.getBean("IDao", IDao.class);
			List<Account> buyList = dao.getBuyedList();
			if(!buyList.isEmpty())
			{
				map.addAttribute("buyList", buyList);
			}
			((ConfigurableApplicationContext) context).close();
			
			return "account";
		}
		return "login";
	}
	
	
	
	
	//【系统入口文档】6、发布页
	@RequestMapping("/public")
	public String publish(ModelMap map)
	{
		if(!user.isEmpty() && user.getInt("usertype") == 1)
		{
			map.addAttribute("user", user);
			return "public";
		}
		
		return "login";
	}
	
	
	
	
	//【系统入口文档】7、发布提交
	@RequestMapping("/publicSubmit")
	public String publish_submit(@RequestParam("price") BigInteger price,
								 @RequestParam("title") String title, 
								 @RequestParam("image") byte[] image,
								 @RequestParam("summary") String summary, 
								 @RequestParam("detail") byte[] detail,
								 HttpServletRequest request,
							     ModelMap map) throws ServletException, IOException
	{	
		if(!user.isEmpty() && user.getInt("usertype") == 1)
		{
			map.addAttribute("user", user);
			
			
			try
			{
				ApplicationContext context = new ClassPathXmlApplicationContext("spring-mybatis.xml");
				IDao dao = context.getBean("IDao", IDao.class);
				dao.publishContent(price, title, image, summary, detail);
				Product published = dao.selectProduct(price, title, image, summary, detail);
				((ConfigurableApplicationContext) context).close();
				
				product.put("id", published.getId());
				product.put("price", published.getPrice());
				product.put("title", published.getTitle());
				product.put("image", published.getImage());
				product.put("summary", published.getSummary());
				product.put("detail", published.getDetail());
			}
			catch(NullPointerException e)
			{
				product.clear();
			}
			
			if(!product.isEmpty())
			{
				map.addAttribute("product", product);
			}
			
			return "publicSubmit";
		}
		
		return "login";
	}
	
	
	
	
	//【系统入口文档】8、编辑页
	@RequestMapping("/edit")
	public String edit(@RequestParam("id") int id, ModelMap map)
	{
		if (user.getInt("usertype") == 1)
		{
			map.addAttribute("user", user);
			
			try
			{
				ApplicationContext context = new ClassPathXmlApplicationContext("spring-mybatis.xml");
				IDao dao = context.getBean("IDao", IDao.class);
				Product published = dao.selectProductId(id);
				((ConfigurableApplicationContext) context).close();
				
				product.put("id", published.getId());
				product.put("price", published.getPrice());
				product.put("title", published.getTitle());
				product.put("image", published.getImage());
				product.put("summary", published.getSummary());
				product.put("detail", published.getDetail());
			}
			catch(NullPointerException e)
			{
				product.clear();
			}
			
			if(!product.isEmpty())
			{
				map.addAttribute("product", product);
			}
			
			return "edit";
		}
		return "login";
	}
	
	
	
	//【系统入口文档】9、编辑提交
	@RequestMapping("/editSubmit")
	public String edit_submit(@RequestParam("id") int id,
							  @RequestParam("price") BigInteger price,
							  @RequestParam("title") String title, 
							  @RequestParam("image") byte[] image,
							  @RequestParam("summary") String summary, 
							  @RequestParam("detail") byte[] detail,
							  HttpServletRequest request,
						      ModelMap map) throws ServletException, IOException
	{	
		if(user.getInt("usertype") == 1)
		{
			map.addAttribute("user", user);
			
			try
			{
				ApplicationContext context = new ClassPathXmlApplicationContext("spring-mybatis.xml");
				IDao dao = context.getBean("IDao", IDao.class);
				dao.updateContent(id, price, title, image, summary, detail);
				Product published = dao.selectProductId(id);
				((ConfigurableApplicationContext) context).close();
				
				product.put("id", published.getId());
				product.put("price", published.getPrice());
				product.put("title", published.getTitle());
				product.put("image", published.getImage());
				product.put("summary", published.getSummary());
				product.put("detail", published.getDetail());
			}
			catch(NullPointerException e)
			{
				product.clear();
			}
			
			if(!product.isEmpty())
			{
				map.addAttribute("product", product);
			}
			
			return "editSubmit";
		}
		
		return "login";
	}
	
	
	
	
	
	//【异步数据接口文档】1、登录
	@RequestMapping("/api/login")
	@ResponseBody
	public String api_login(@RequestParam("userName") String userName,
					        @RequestParam("password") String password,
					        HttpServletRequest request,
					        HttpSession session,
					        ModelMap map) throws ServletException, IOException
	{	
		JSONObject loginJson = new JSONObject();
		
		//读取Session
		session = request.getSession();
		JSONObject userSession = (JSONObject) session.getAttribute("user");
		if(userSession != null)
		{
			user = userSession;
		}
		
		try
		{
			ApplicationContext context = new ClassPathXmlApplicationContext("spring-mybatis.xml");
			IDao dao = context.getBean("IDao", IDao.class);
			Person LoginUser = dao.getUser(userName, password);
			((ConfigurableApplicationContext) context).close();
			
			user.put("username", LoginUser.getUserName());
			user.put("usertype", LoginUser.getUserType());
			
			//保存Session
			session.setAttribute("user", user);
			session.setMaxInactiveInterval(30);
			
			loginJson.put("code", 200);
			loginJson.put("message", "");
			loginJson.put("result", true);
		}
		catch(NullPointerException e)
		{
			loginJson.put("code", 400);
			loginJson.put("message", "用户名或密码错误");
			loginJson.put("result", false);
		}

		return loginJson.toString();
		
	}
	
	
	
	//【异步数据接口文档】2、删除产品
	@RequestMapping("/api/delete")
	@ResponseBody
	public String api_delete(@RequestParam("id") int id,
					      	 HttpServletRequest request,
					      	 HttpSession session,
					      	 ModelMap map) throws ServletException, IOException
	{	
		JSONObject deleteJson = new JSONObject();
		
		try
		{
			ApplicationContext context = new ClassPathXmlApplicationContext("spring-mybatis.xml");
			IDao dao = context.getBean("IDao", IDao.class);
			dao.deleteProduct(id);
			((ConfigurableApplicationContext) context).close();
			
			deleteJson.put("code", 200);
			deleteJson.put("message", "");
			deleteJson.put("result", true);
		}
		catch(NullPointerException e)
		{
			deleteJson.put("code", 400);
			deleteJson.put("message", "删除失败");
			deleteJson.put("result", false);
		}

		return deleteJson.toString();
	}
	
	
	
	//【异步数据接口文档】3、购买
	@RequestMapping("/api/buy")
	@ResponseBody
	public String api_buy(@RequestParam("id") int id,
					      HttpServletRequest request,
					      HttpSession session,
					      ModelMap map) throws ServletException, IOException
	{	
		JSONObject buyJson = new JSONObject();
		
		try
		{
			ApplicationContext context = new ClassPathXmlApplicationContext("spring-mybatis.xml");
			int personId = user.getInt("usertype");
			BigInteger price =  BigInteger.valueOf(product.getInt("price"));
			BigInteger time = BigInteger.valueOf(System.currentTimeMillis());
			IDao dao = context.getBean("IDao", IDao.class);
			dao.Trx(id, personId, price, time);
			((ConfigurableApplicationContext) context).close();
			
			buyJson.put("code", 200);
			buyJson.put("message", "");
			buyJson.put("result", true);
		}
		catch(NullPointerException e)
		{
			buyJson.put("code", 400);
			buyJson.put("message", "出错了");
			buyJson.put("result", false);
		}
		
		return buyJson.toString();
	}
	
}
