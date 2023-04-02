package mvc_olx.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import mvc_olx.dao.CustomerDao;
import mvc_olx.dto.Customer;
import mvc_olx.dto.Product;

@Component
public class CustomerService {

	@Autowired
	Product product;
	
	@Autowired
	Customer customer;

	@Autowired
	CustomerDao dao;

	public ModelAndView load() {
		ModelAndView andView = new ModelAndView("Signup.jsp");
		andView.addObject("customer", customer);

		return andView;
	}

	public ModelAndView signup(Customer customer, String date) {
		ModelAndView andView = new ModelAndView();

		Date dob = Date.valueOf(date);
		int age = Period.between(dob.toLocalDate(), LocalDate.now()).getYears();
		if (age < 18) {
			andView.setViewName("Signup.jsp");
			andView.addObject("customer", customer);
			andView.addObject("msg", "You have to be 18+ to create Account");
		} else {
			if (dao.find(customer.getMobile()).isEmpty()) {
				customer.setAge(age);
				customer.setDob(dob);
				dao.save(customer);
				andView.setViewName("Home.jsp");
				andView.addObject("msg", "Account Created Successfully Your Customer Id is : " + customer.getId());

			} else {
				andView.setViewName("Signup.jsp");
				andView.addObject("customer", customer);
				andView.addObject("msg", "Mobile Number already Exists");
			}
		}

		return andView;
	}

	public ModelAndView login(int cid, String password, HttpSession session) {
		ModelAndView andView = new ModelAndView();
		Customer customer = dao.find(cid);
		if (customer == null) {
			andView.setViewName("Login.jsp");
			andView.addObject("msg", "Incorrect Customer Id");
		} else {
			if (customer.getPassword().equals(password)) {
				session.setAttribute("customer", customer);
				andView.setViewName("Home.jsp");
				andView.addObject("msg", "Login Success");
			} else {
				andView.setViewName("Login.jsp");
				andView.addObject("msg", "Incorrect Password");
			}
		}
		return andView;
	}

	public ModelAndView logout(HttpSession session) {
		ModelAndView andView=new ModelAndView("Home.jsp");
		if(session.getAttribute("customer")==null)
		{
			andView.addObject("msg", "Session Expired");
		}
		else {
		session.invalidate();
		andView.addObject("msg", "Logout Success");
		}
		return andView;
	}

	public ModelAndView addProduct(String pname, double pprice, MultipartFile pimage,HttpSession session) throws IOException {
		Customer customer=(Customer) session.getAttribute("customer");
		
		product.setName(pname);
		product.setPrice(pprice);
		product.setSold(true);
		
		byte[] pic = null;
		if (pimage != null) {
			InputStream inputStream = pimage.getInputStream();
			pic = new byte[inputStream.available()];
			inputStream.read(pic);
		}
		
		product.setImage(pic);
		
		List<Product> list=customer.getProducts();
		if(list==null)
		{
			list=new ArrayList<Product>();
		}
		list.add(product);
		
		customer.setProducts(list);
			
		dao.update(customer);
		
		ModelAndView andView=new ModelAndView("Home.jsp");
		andView.addObject("msg","Added Product for Sale");
		
		return andView;
	}

	public ModelAndView addMoney(double money, HttpSession session) {
		Customer customer=(Customer) session.getAttribute("customer");
		customer.setWallet(customer.getWallet()+money);
		dao.update(customer);
		
		ModelAndView andView=new ModelAndView("Home.jsp");
		andView.addObject("msg","Added Money in wallet");
		
		return andView;
	}

	public ModelAndView displayProducts(HttpSession session) {
		
		ModelAndView andView=new ModelAndView();
		
		Customer customer = (Customer) session.getAttribute("customer");
		List<Product> products1 = dao.fetchProduct();
		List<Product> products3 = new ArrayList<Product>();

		if (customer != null) {
			List<Product> products2 = customer.getProducts();
			for (Product product1 : products1) {
				for (Product product2 : products2) {
			if (product1 != product2) {
				products3.add(product1);
			}
				}
			}
		} else {
			products3 = products1;
		}

		if (products3.isEmpty()) {
			andView.addObject("msg","No Products You can Purchase");
			andView.setViewName("Home.jsp");
			} else {
			andView.addObject("list", products3);
			andView.setViewName("Buy.jsp");
		}
		
		return andView;
	}

}
