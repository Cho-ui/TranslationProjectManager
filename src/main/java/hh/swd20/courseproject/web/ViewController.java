package hh.swd20.courseproject.web;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//TODO CrossOrigins? Might not be in use in a front end-based solution
@Controller
public class ViewController {
	
	@RequestMapping("/login")
	public String login() {
		return "login"; // login.html
	}
	
	@GetMapping("/index")
	public String index() {
		return "index"; // index.html
	}

}
