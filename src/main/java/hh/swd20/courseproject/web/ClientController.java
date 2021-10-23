package hh.swd20.courseproject.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import hh.swd20.courseproject.domain.ClientRepository;

@Controller
public class ClientController {
	
	@Autowired
	private ClientRepository clientRepository;
	
	/** Manually built test endpoints for database construction **/
	
	@GetMapping("/clientlist")
	public String clientList(Model model) {
		model.addAttribute("clients", clientRepository.findAll());
		return "clientlist"; // clientlist.html
	}

}
