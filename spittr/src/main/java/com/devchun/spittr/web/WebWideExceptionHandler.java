package com.devchun.spittr.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class WebWideExceptionHandler {
	
	@ExceptionHandler(DuplicatedSpittleException.class)
	public ModelAndView duplicateSpittleHandler(DuplicatedSpittleException e) {
		ModelAndView mv = new ModelAndView("error");
		mv.addObject("message", e.getMessage());
		
		return mv;
	}
}
