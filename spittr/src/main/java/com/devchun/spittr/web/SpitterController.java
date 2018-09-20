package com.devchun.spittr.web;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.devchun.spittr.Spitter;
import com.devchun.spittr.data.SpitterRepository;

@Controller
@RequestMapping("/spitter")
public class SpitterController {
	private static final Logger logger = LoggerFactory.getLogger(SpitterController.class);
	
	private SpitterRepository spitterRepository;
	
	public SpitterController() {}
	
	@Autowired
	public SpitterController(SpitterRepository spitterRepository) {
		this.spitterRepository = spitterRepository;
	}

	@RequestMapping(value="/register", method=RequestMethod.GET)
	public String showRegistrationForm() {
		return "registerForm";
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String processRegistration(
			@Valid Spitter spitter,
			Errors errors) {
		
		if (errors.hasErrors()) {
			List<ObjectError> list = errors.getAllErrors();
			
			for (ObjectError e : list) {
				FieldError fe = (FieldError)e;
				logger.error(fe.getField() + ", " + e.getDefaultMessage());
			}
			
			return "registerForm";
		}
		
		spitterRepository.save(spitter);
		return "redirect:/spitter/" + spitter.getUsername();
	}

	@RequestMapping(value="/{username}", method=RequestMethod.GET)
	public String showSpitterProfile(
			@PathVariable String username,
			Model model) {
		Spitter spitter = spitterRepository.findByUsername(username);
		model.addAttribute("spitter", spitter);
		return "profile";
	}
	
}
