package com.rimi.ctibet.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rimi.ctibet.common.controller.BaseController;

@Controller
@RequestMapping("web/strategyViewController")
public class StrategyViewController extends BaseController {
	@RequestMapping("test")
	public void test(){
		
	}
}
