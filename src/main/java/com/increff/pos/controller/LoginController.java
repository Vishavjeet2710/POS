package com.increff.pos.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Objects;
import com.increff.pos.model.ApiException;
import com.increff.pos.model.InfoData;
import com.increff.pos.model.LoginForm;
import com.increff.pos.pojo.UserPojo;
import com.increff.pos.service.UserService;
import com.increff.pos.util.SecurityUtil;
import com.increff.pos.util.UserPrincipal;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/session")
public class LoginController {
	
	@Autowired
	private UserService service;
	@Autowired
	private InfoData info;
	
	@ApiOperation(value = "Logs in a user")
	@PostMapping(path = "/login",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ModelAndView login(HttpServletRequest req, LoginForm form) throws ApiException {
		UserPojo p = service.getEmailCheck(form.getEmail());
		boolean authenticated = (p != null && Objects.equal(form.getPassword(), p.getPassword()));
		System.out.println("I was called");
		if(!authenticated) {
			info.setMessage("PassWord is not valid");
			return new ModelAndView("redirect:/site/login");
		}
		Authentication authentication = convert(p);
		HttpSession session = req.getSession(true);
		SecurityUtil.createContext(session);
		SecurityUtil.setAuthentication(authentication);
		return new ModelAndView("redirect:/ui/home");
	}

	@ApiOperation(value = "Logs out a user")
	@GetMapping(path = "/logout")
	public ModelAndView logout(HttpServletRequest req,HttpServletResponse res) {
		req.getSession().invalidate();
		return new ModelAndView("redirect:/site/logout");
	}
	
	private Authentication convert(UserPojo p) {
		UserPrincipal principal = new UserPrincipal();
		principal.setEmail(p.getEmail());
		principal.setId(p.getId());
		
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal, null);
		return token;
	}
}
