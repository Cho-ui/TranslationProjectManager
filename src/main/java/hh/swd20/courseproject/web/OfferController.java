package hh.swd20.courseproject.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import hh.swd20.courseproject.domain.ClientRepository;
import hh.swd20.courseproject.domain.FreelancerRepository;
import hh.swd20.courseproject.domain.Offer;
import hh.swd20.courseproject.domain.OfferRepository;

@Controller
public class OfferController {

	@Autowired
	private OfferRepository offerRepository;
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private FreelancerRepository freelancerRepository;
	
	/** Manually built test endpoints for database construction **/
	
	@GetMapping("/offerlist")
	public String offerList(Model model) {
		model.addAttribute("offers", offerRepository.findAll());
		return "offerlist"; // offerlist.html
	}
	
	@GetMapping("/brokermain") // omaksi controllerikseen?
	public String brokerMain(Model model) {
		model.addAttribute("offers", offerRepository.findAll());
		model.addAttribute("clients", clientRepository.findAll());
		model.addAttribute("freelancers", freelancerRepository.findAll());
		return "brokermain"; // brokermain.html
	}
	
	@GetMapping("/assign/{id}")
	public String assignOffer(@PathVariable("id") Long offerId, Model model) {
		Offer offer = offerRepository.findById(offerId).get();
		
		// kysy kielet offerista, laita ne settiin/collectioniin
		model.addAttribute("offer", offer);
		// vaihda alle findall uuteen findby-metodiin
		model.addAttribute("freelancers", freelancerRepository.findAll());
		return "assignoffer";
	}
	
}
