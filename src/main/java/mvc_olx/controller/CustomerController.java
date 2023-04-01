package mvc_olx.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.cj.Session;

import mvc_olx.dto.Customer;
import mvc_olx.service.CustomerService;

@Controller
public class CustomerController {

	@Autowired
	CustomerService service;

	@RequestMapping("load")
	public ModelAndView load() {
		return service.load();
	}

	@PostMapping("signup")
	public ModelAndView signup(@ModelAttribute Customer customer, @RequestParam String bdate) {
		return service.signup(customer, bdate);
	}
	
	@PostMapping("login")
	public ModelAndView login(@RequestParam int cid,@RequestParam String password,HttpSession session)
	{
		return service.login(cid,password,session);
	}
	
	@RequestMapping("logout")
	public ModelAndView logout(HttpSession session)
	{
		return service.logout(session);
	}
}
