package com.ucustakvimi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ucustakvimi.model.FlightUser;
import com.ucustakvimi.service.SecurityService;
import com.ucustakvimi.service.UserService;
import com.ucustakvimi.util.SessionIdentifierGenerator;
import com.ucustakvimi.validator.UserValidator;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private UserValidator userValidator;

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registration(Model model) {

		model.addAttribute("userForm", new FlightUser());

		return "registration";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String registration(
			@ModelAttribute("userForm") FlightUser userForm,
			BindingResult bindingResult, Model model) {
		userValidator.validate(userForm, bindingResult);

		if (bindingResult.hasErrors()) {
			return "registration";
		}

		SessionIdentifierGenerator sig = new SessionIdentifierGenerator();

		String license = sig.nextSessionId();

		userService.save(userForm);

		securityService.autologin(userForm.getEmail(),
				userForm.getPasswordConfirm());

		return "redirect:/welcome";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, String error, String logout) {
		if (error != null)
			model.addAttribute("error",
					"Your username and password is invalid.");

		if (logout != null)
			model.addAttribute("message",
					"You have been logged out successfully.");

		return "login";
	}

	@RequestMapping(value = { "/", "/welcome" }, method = RequestMethod.GET)
	public String welcome(Model model) {

		/*
		 * FlightUser user = (FlightUser) SecurityContextHolder.getContext()
		 * .getAuthentication().getPrincipal(); String email = user.getEmail();
		 * // get logged in username model.addAttribute("useremail", email);
		 */
		return "welcome";
	}
}
