package mvc_olx.service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import mvc_olx.dao.CustomerDao;
import mvc_olx.dto.Customer;

@Component
public class CustomerService {

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
		session.invalidate();
		ModelAndView andView=new ModelAndView("Home.jsp");
		andView.addObject("msg", "Logout Success");
		
		return andView;
	}

}
