package hh.swd20.courseproject.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import hh.swd20.courseproject.domain.Freelancer;
import hh.swd20.courseproject.domain.FreelancerRepository;
import hh.swd20.courseproject.domain.Offer;
import hh.swd20.courseproject.domain.OfferRepository;

@Controller
public class FreelancerController {
	
	@Autowired
	private FreelancerRepository freelancerRepository;
	
	@Autowired
	private OfferRepository offerRepository;
	
	/** Manually built test endpoints for database construction **/
	
	@GetMapping("/addfreelancer")
	public String addFreelancer(Model model) {
		model.addAttribute("freelancer", new Freelancer());
		return "addfreelancer"; //addfreelancer.html
	}
	
	@PostMapping("/savefreelancer")
	public String saveFreelancer(Freelancer freelancer) {
		freelancerRepository.save(freelancer);
		return "redirect:brokermain"; //brokermain.html
	}
	
	@PostMapping("/assignfreelancer") //
	public String assignFreelancer(@ModelAttribute Offer offer, 
			@ModelAttribute Freelancer freelancer) {
		
		Long offerId = offer.getOfferId();
		Offer offerAssign = offerRepository.findById(offerId).get();	
		Long freelancerId = freelancer.getFreelancerId();
		Freelancer freelancerAssign = freelancerRepository.findById(freelancerId).get();
		
		offerAssign.setFreelancer(freelancerAssign);
		offerAssign.setAssigned(true);
		
		offerRepository.save(offerAssign);
		
		return "redirect:brokermain"; // brokermain.html
	}

}
