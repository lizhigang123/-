package com.bojin.api.web.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bojin.api.web.controller.BaseController;

@Controller
@RequestMapping("admin")
public class IndexController extends BaseController {

	private String viewPrefix = "admin/";

	@RequestMapping("index")
	public String index() {
		return viewPrefix + "index";
	}

	@RequestMapping("")
	public String index1() {
		return viewPrefix + "index";
	}
	
}
