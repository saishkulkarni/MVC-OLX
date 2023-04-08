package mvc_olx.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

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
		ModelAndView andView = new ModelAndView("Home.jsp");
		if (session.getAttribute("customer") == null) {
			andView.addObject("msg", "Session Expired");
		} else {
			session.invalidate();
			andView.addObject("msg", "Logout Success");
		}
		return andView;
	}

	public ModelAndView addProduct(String pname, double pprice, MultipartFile pimage, HttpSession session)
			throws IOException {
		Customer customer = (Customer) session.getAttribute("customer");

		product.setName(pname);
		product.setPrice(pprice);
		product.setSold(customer.getId());

		byte[] pic = null;
		if (pimage != null) {
			InputStream inputStream = pimage.getInputStream();
			pic = new byte[inputStream.available()];
			inputStream.read(pic);
		}

		product.setImage(pic);

		List<Product> list = customer.getProducts();
		if (list == null) {
			list = new ArrayList<Product>();
		}
		list.add(product);

		customer.setProducts(list);

		dao.update(customer);

		ModelAndView andView = new ModelAndView("Home.jsp");
		andView.addObject("msg", "Added Product for Sale");

		return andView;
	}

	public ModelAndView addMoney(double money, HttpSession session) {
		Customer customer = (Customer) session.getAttribute("customer");
		customer.setWallet(customer.getWallet() + money);
		dao.update(customer);

		ModelAndView andView = new ModelAndView("Home.jsp");
		andView.addObject("msg", "Added Money in wallet");

		return andView;
	}

	public ModelAndView displayProducts(HttpSession session) {

		ModelAndView andView = new ModelAndView();

		Customer customer = (Customer) session.getAttribute("customer");

		List<Product> products = dao.fetchProduct();
		List<Product> products1 = new ArrayList<>();
		for (Product product : products) {
			if (product.getBought() == 0) {
				products1.add(product);
			}
		}

		if (customer != null) {
			List<Product> products2 = customer.getProducts();
			if(products2!=null)
			products1.removeAll(products2);
		}

		if (products1.isEmpty()) {
			andView.addObject("msg", "No Products You can Purchase");
			andView.setViewName("Home.jsp");
		} else {
			andView.addObject("list", products1);
			andView.setViewName("Buy.jsp");
		}

		return andView;
	}

	public ModelAndView buyProduct(HttpSession session, int id) {
		ModelAndView andView = new ModelAndView();
		Customer customer = (Customer) session.getAttribute("customer");
		if (customer == null) {
			andView.setViewName("Login.jsp");
			andView.addObject("msg", "First Login Then only You can Buy");
		} else {
			Product product = dao.findProduct(id);

			if (customer.getWallet() < product.getPrice()) {
				andView.addObject("msg", "Insufficient Balance");
				andView.setViewName("Home.jsp");
			} else {
				customer.setWallet(customer.getWallet() - product.getPrice());
				Customer customer2 = dao.find(product.getSold());
				customer2.setWallet(customer2.getWallet() + product.getPrice());

				product.setBought(customer.getId());
				List<Product> products = customer.getProducts();
				products.add(product);

				dao.update(customer);
				dao.update(customer2);
				andView.setViewName("Home.jsp");
				andView.addObject("msg", "Purchased Successfully and will be delivered to " + customer.getAddress());

			}
		}

		return andView;
	}

}
