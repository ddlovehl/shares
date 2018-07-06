package com.fourkings.shares.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *  测试
 * @author dd
 * @time 2018-7-5 21:49:15
 *
 */
@Controller
public class TestController {
	
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@RequestMapping("/front/test")
	public String test() {
		logger.info("test....");
		logger.info("test....提交测试---zlb");
		return "/demo/index";
	}
	
}
