package com.increff.pos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.increff.pos.model.InfoData;
import com.increff.pos.util.SecurityUtil;
import com.increff.pos.util.UserPrincipal;

@Controller
public class SiteUiController {
	// Website pages

	@Autowired
	private InfoData info;

	@Value("${app.baseUrl}")
	private String baseUrl;

	@RequestMapping(value = "")
	public ModelAndView index() {
		return mav("index.html");
	}

	@RequestMapping(value = "/site/login")
	public ModelAndView login() {
		return mav("login.html");
	}

	@RequestMapping(value = "/site/logout")
	public ModelAndView logout() {
		return mav("logout.html");
	}
	
	@RequestMapping(value = "/ui/home")
	public ModelAndView home() {
		return mav("home.html");
	}
	
	@RequestMapping(value = "/ui/admin")
	public ModelAndView admin() {
		return mav("admin.html");
	}

	@RequestMapping(value = "/ui/brand")
	public ModelAndView brand() {
		return mav("brand.html");
	}

	@RequestMapping(value = "/ui/product")
	public ModelAndView product() {
		return mav("product.html");
	}

	@RequestMapping(value = "/ui/inventory")
	public ModelAndView inventory() {
		return mav("inventory.html");
	}
	
	@RequestMapping(value = "/ui/order")
	public ModelAndView order() {
		return mav("order.html");
	}
	
	private ModelAndView mav(String page) {
		UserPrincipal principal = SecurityUtil.getPrincipal();
		info.setEmail(principal == null ? "" : principal.getEmail());

		ModelAndView mav = new ModelAndView(page);
		mav.addObject("info", info);
		mav.addObject("baseUrl", baseUrl);
		return mav;
	}
}
