package hh.swd20.courseproject.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import hh.swd20.courseproject.domain.ClientRepository;
import hh.swd20.courseproject.domain.FreelancerRepository;
import hh.swd20.courseproject.domain.LanguageRepository;
import hh.swd20.courseproject.domain.OfferRepository;

@Controller
public class BrokerViewController {
	
	@Autowired
	private OfferRepository offerRepository;
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private FreelancerRepository freelancerRepository;
	
	@Autowired
	private LanguageRepository languageRepository;
	
	
	@GetMapping("/brokermain")
	public String brokerMain(Model model) {
		
		model.addAttribute("languages", languageRepository.findAll());
		model.addAttribute("unassignedOffers", offerRepository.findByAssignedFalse());
		model.addAttribute("assignedOffers", offerRepository.findByAssignedTrue());
		model.addAttribute("clients", clientRepository.findAll());
		model.addAttribute("freelancers", freelancerRepository.findAll());
		return "brokermain"; // brokermain.html
	}

}
