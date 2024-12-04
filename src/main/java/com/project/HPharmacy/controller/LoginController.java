package com.project.HPharmacy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
// This controller simply returns login page.
	@GetMapping(value={"/login"})
	public String getLogin() {
		return "login";
	}
}
