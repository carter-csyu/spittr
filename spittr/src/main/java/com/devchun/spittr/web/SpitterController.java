package com.devchun.spittr.web;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.devchun.spittr.SpitterForm;
import com.devchun.spittr.data.SpitterRepository;
import com.devchun.spittr.domain.Spitter;

@Controller
@RequestMapping("/spitters")
public class SpitterController {
	private static final Logger logger = LoggerFactory.getLogger(SpitterController.class);
	
	private SpitterRepository spitterRepository;
	
	public SpitterController() {}
	
	@Autowired
	public SpitterController(@Qualifier("hibernateSpitterRepository") SpitterRepository spitterRepository) {
		this.spitterRepository = spitterRepository;
	}
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public void printAllSpitters() {
	  List<Spitter> spitterList = spitterRepository.findAll();
	  
	  for (Spitter s :  spitterList) {
	    logger.info(s.toString());
	  }
	}

	@RequestMapping(value="/register", method=RequestMethod.GET)
	public String showRegistrationForm(Model model) {
		model.addAttribute(new Spitter());
		return "registerForm";
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
  public String processRegistration (
      @Valid SpitterForm spitterForm,
      Errors errors,
      RedirectAttributes model) throws IllegalStateException, IOException {
    if (errors.hasErrors()) {
      List<ObjectError> list = errors.getAllErrors();
      
      for (ObjectError e : list) {
        FieldError fe = (FieldError)e;
        logger.error(fe.getField() + ", " + e.getDefaultMessage());
      }
      
      return "registerForm";
    }
    
    Spitter spitter = spitterForm.toSpitter();
    spitterRepository.save(spitter);
    
    MultipartFile profilePicture = spitterForm.getProfilePicture();
    profilePicture.transferTo(new File("/tmp/spittr/files/" + spitter.getUsername() + ".jpg"));
    
    model.addAttribute("username", spitter.getUsername());
    model.addFlashAttribute("spitter", spitter);
    
    return "redirect:/spitters/{username}";
  }

	@RequestMapping(value="/me", method=RequestMethod.GET)
	public String me() {
	  logger.info("/spitters/me called");
	   return "home";
	} 
	
	@RequestMapping(value="/{username}", method=RequestMethod.GET)
	public String showSpitterProfile(
			@PathVariable String username,
			Model model) {
		if (!model.containsAttribute("spitter")) {
			Spitter spitter = spitterRepository.findByUsername(username);
			model.addAttribute("spitter", spitter);
		}
		return "profile";
	}
}
