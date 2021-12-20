package id.co.vasystem.frontend.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller 
public class UserManagementController {
	
	private ModelAndView modelAndView;
	
	@GetMapping("/usermanagement")
    public ModelAndView getHome(HttpServletRequest request, HttpServletResponse response){
    	
		HttpSession session = request.getSession();
		
		//modelAndView.setViewName("/user");
		
		return new ModelAndView("/user");
    }

}
