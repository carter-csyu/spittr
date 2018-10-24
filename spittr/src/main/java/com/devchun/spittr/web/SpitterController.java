package com.devchun.spittr.web;

import java.io.File;
import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String showRegistrationForm(Model model) {
		model.addAttribute(new Spitter());
		return "registerForm";
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String processRegistration (
			@RequestPart("profilePicture") MultipartFile profilePicture,
			@Valid Spitter spitter,
			Errors errors,
			RedirectAttributes model) {
		if (errors.hasErrors()) {
			List<ObjectError> list = errors.getAllErrors();
			
			for (ObjectError e : list) {
				FieldError fe = (FieldError)e;
				logger.error(fe.getField() + ", " + e.getDefaultMessage());
			}
			
			return "registerForm";
		}
		
		saveImage(profilePicture);
		spitterRepository.save(spitter);
		
		model.addAttribute("username", spitter.getUsername());
		model.addFlashAttribute("spitter", spitter);
		
		return "redirect:/spitter/{username}";
	}

	@RequestMapping(value="/{username}", method=RequestMethod.GET)
	public String showSpitterProfile(
			@PathVariable String username,
			Model model) {
		if (!model.containsAttribute("spitter")) {
			Spitter spitter = spitterRepository.findByUsername(username);
			model.addAttribute("spitter", spitter);
			logger.info("created new Spitter.");
		}
		return "profile";
	}
	
	public void saveImage(MultipartFile image) {
		if (image == null) {
			logger.info("image file not uploaded.");
			return;
		}
		
		try {
			logger.info(image.getOriginalFilename());
			logger.info(Long.toString(image.getSize()));
			image.transferTo(new File("/tmp/spittr/files/" + image.getOriginalFilename()));
		} catch (IllegalStateException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}
}
