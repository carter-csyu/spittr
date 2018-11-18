package com.devchun.spittr.web.exception;

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
	
	@ExceptionHandler(DuplicatedSpitterException.class)
	public ModelAndView duplicatedSpitterHandler(DuplicatedSpitterException e) {
	  ModelAndView mv = new ModelAndView("error");
	  mv.addObject("message", e.getMessage());
	  
	  return mv;
	}
	
	@ExceptionHandler(SpittleNotFoundException.class)
  public ModelAndView spittleNotFoundHandler(SpittleNotFoundException e) {
    ModelAndView mv = new ModelAndView("error");
    mv.addObject("message", e.getMessage());
    
    return mv;
  }
}
