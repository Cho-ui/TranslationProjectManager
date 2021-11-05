package hh.swd20.courseproject.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import hh.swd20.courseproject.domain.Client;
import hh.swd20.courseproject.domain.ClientRepository;

@Controller
public class ClientController {
	
	@Autowired
	private ClientRepository clientRepository;
	
	/** Manually built test endpoints for database construction **/
	
	// not in use
	@GetMapping("/clientlist")
	public String clientList(Model model) {
		model.addAttribute("clients", clientRepository.findAll());
		return "clientlist"; // clientlist.html
	}
	
	@GetMapping("/addclient")
	public String addClient(Model model) {
		model.addAttribute("client", new Client());
		return "addclient"; //addclient.html
	}
	
	@PostMapping("saveclient")
	public String saveClient(Client client) {
		clientRepository.save(client);
		return "redirect:brokermain"; //brokermain.html
	}

}
