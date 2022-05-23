package com.epam.payments.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.epam.payments.model.Request;
import com.epam.payments.model.User;
import com.epam.payments.model.UserAccount;
import com.epam.payments.service.UserRequestService;
import com.epam.payments.service.UserService;

@Controller
public class UserRequestsController {
	private UserRequestService requestService;
	private UserService userService;
		
	@Autowired
	public UserRequestsController(UserRequestService requestService, UserService userService) {
		this.requestService = requestService;
		this.userService = userService;
	}
	
	@GetMapping("/requests")
	public String getRequests(Model model, @RequestParam(required = false, defaultValue = "true") String ascending,
			@RequestParam(defaultValue = "status", required = false) String orderBy,
			@RequestParam(defaultValue = "1", required = false) Integer page) {
		Pageable pageable = PageRequest.of(page - 1, 10, ascending.equals("true")?Sort.by(orderBy):Sort.by(orderBy).descending());
		Page<Request> requests = requestService.findAll(pageable);
		model.addAttribute("locale", LocaleContextHolder.getLocale());
		model.addAttribute("page", page);
		model.addAttribute("requests", requests.toList());
		model.addAttribute("size", requests.getTotalPages());
		model.addAttribute("orderBy", orderBy);
		model.addAttribute("ascending", ascending);
		return "requests";
	}
	
	@GetMapping("/dismissRequest")
	public String dismissUserUnlockRequest(@RequestParam int id) {
		requestService.changeRequestStatusToClosed(id);
		return "redirect:/";	
	}
	
	@GetMapping("/acceptRequest")
	public String acceptUserUnlockRequest(@RequestParam int id) {
		requestService.acceptUserRequest(id);
		return "redirect:/";	
	}
	
	@GetMapping("requests/dismissRequest")
	public String dismissUserUnlockRequestFromRequestPage(@RequestParam int id) {
		requestService.changeRequestStatusToClosed(id);
		return "redirect:/requests";	
	}
	
	@GetMapping("requests/acceptRequest")
	public String acceptUserUnlockRequestFromRequestPage(@RequestParam int id) {
		requestService.acceptUserRequest(id);
		return "redirect:/requests";	
	}
	
	@PostMapping("/unblockCard")
	public String createUserUnlockRequest(@RequestParam(name = "id") int accountId, Authentication authentication) {
		User user = userService.findByEmail(authentication.getName());
		UserAccount account = user.getAccounts().stream().filter(a -> a.getId() == accountId).findFirst().orElse(new UserAccount());
		String result = requestService.createUserUnlockRequest(user, account);
		return "redirect:/"+result;
	}
}
