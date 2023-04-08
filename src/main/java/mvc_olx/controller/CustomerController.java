package mvc_olx.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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
	public ModelAndView login(@RequestParam int cid, @RequestParam String password, HttpSession session) {
		return service.login(cid, password, session);
	}

	@RequestMapping("logout")
	public ModelAndView logout(HttpSession session) {
		return service.logout(session);
	}

	@PostMapping("addproduct")
	public ModelAndView addProduct(@RequestParam String pname, @RequestParam double pprice,
			@RequestParam MultipartFile pimage, HttpSession session) throws IOException {
		return service.addProduct(pname, pprice, pimage, session);
	}

	@RequestMapping("addmoney")
	public ModelAndView addMoney(@RequestParam double money, HttpSession session) {
		return service.addMoney(money, session);
	}

	@RequestMapping("displayproducts")
	public ModelAndView displayProducts(HttpSession session) {
		return service.displayProducts(session);
	}

	@RequestMapping("buy")
	public ModelAndView buyProducts(@RequestParam int id, HttpSession session) {
		return service.buyProduct(session, id);
	}

}
