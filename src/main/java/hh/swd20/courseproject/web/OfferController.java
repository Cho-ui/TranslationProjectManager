package hh.swd20.courseproject.web;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import hh.swd20.courseproject.domain.ClientRepository;
import hh.swd20.courseproject.domain.Freelancer;
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
	
	// not in use
	@GetMapping("/offerlist")
	public String offerList(Model model) {
		model.addAttribute("offers", offerRepository.findAll());
		return "offerlist"; // offerlist.html
	}
	
	@GetMapping("/addoffer")
	public String addOffer(Model model) {
		model.addAttribute("offer", new Offer());
		model.addAttribute("clients", clientRepository.findAll());
		return "addoffer"; // addoffer.html
	}
	
	@PostMapping("/saveoffer")
	public String saveOffer(Offer offer) {
		
		/* gets the initial form deadline date value,
		 * converts it to GMT +2(Helsinki) and saves it to the offer
		 */
		offer.setDeadlineDate(ZonedDateTime.of
				(LocalDateTime.parse(offer.getFormDeadline()),
				ZoneId.of("Europe/Helsinki")));		
		
		offerRepository.save(offer);
		return "redirect:brokermain"; //brokermain.html
	}
	
	@PostMapping("/updateoffer")
	public String updateOffer(@ModelAttribute Offer offer) {
		
		Offer updatedOffer = offer;
		
		/* gets the initial form deadline date value,
		 * converts it to GMT +2(Helsinki) and saves it to the offer
		 */
		updatedOffer.setDeadlineDate(ZonedDateTime.of
				(LocalDateTime.parse(offer.getFormDeadline()),
				ZoneId.of("Europe/Helsinki")));
		
		offerRepository.save(updatedOffer);
		
		return "redirect:brokermain"; //brokermain.html
	}
	
	@GetMapping("/editoffer/{id}")
	public String editOffer(@PathVariable("id") Long offerId, Model model) {
		
		model.addAttribute("offer", offerRepository.findById(offerId).get());
		model.addAttribute("clients", clientRepository.findAll());
		
		return "editoffer"; //editoffer.html
		
	}
	
	@GetMapping("/deleteoffer/{id}")
	public String deleteOffer(@PathVariable("id") Long offerId) {
		offerRepository.deleteById(offerId);
		
		return "redirect:../brokermain"; //brokermain.html
	}
	
	@PostMapping("/releaseoffer") 
	public String releaseOffer(@ModelAttribute Offer offer) {
		
		Offer toRelease = offerRepository.findById(offer.getOfferId()).get();
		toRelease.setAssigned(false);
		toRelease.setFreelancer(null);
		offerRepository.save(toRelease);
		
		return "redirect:brokermain"; //brokermain.html
		
	}
	
	@GetMapping("/unassign/{id}")
	public String unassignOffer(@PathVariable("id") Long offerId, Model model) {
		
		Offer offer = offerRepository.findById(offerId).get();
		
		model.addAttribute("offer", offer);
		
		return "unassignoffer"; //unassignoffer.html
	}

	
	@GetMapping("/assign/{id}")
	public String assignOffer(@PathVariable("id") Long offerId, Model model) {
		
		/* get offer languages and put them in a comparable form
		 * to retrieve freelancers from FreelancerRepository
		 */
		
		Offer offer = offerRepository.findById(offerId).get();
		String src = offer.getSourceLanguage();
		String trgt = offer.getTargetLanguage();
		
		List<Freelancer> suitableFreelancers = new ArrayList<Freelancer>();
		
		for (Freelancer freelancer : freelancerRepository.findAll()) {
			
			if (freelancer.getLanguagesAsStringArray().contains(src) &&
					freelancer.getLanguagesAsStringArray().contains(trgt)) {
				suitableFreelancers.add(freelancer);
			}
		}
		
		model.addAttribute("offer", offer);
		model.addAttribute("freelancers", suitableFreelancers);
		return "assignoffer";
	}
}
