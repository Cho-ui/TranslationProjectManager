package hh.swd20.courseproject.web;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	/** Manually built REST endpoints **/
	
	// RESTful service for getting all offers
	@GetMapping("/offers")
	public @ResponseBody List<Offer> offerListRest() {
		return (List<Offer>) offerRepository.findAll();
	}
	
	// RESTful service for getting unassigned offers
	@GetMapping("/offers/unassigned")
	public @ResponseBody List<Offer> unassignedOfferListRest() {
		return (List<Offer>) offerRepository.findByAssignedFalseAndCompletedFalse();
	}
	
	// RESTful service for getting assigned offers
	@GetMapping("/offers/assigned")
	public @ResponseBody List<Offer> assignedOfferListRest() {
		return (List<Offer>) offerRepository.findByAssignedTrueAndCompletedFalse();
	}
	
	// RESTful service for getting completed offers
	@GetMapping("/offers/completed")
	public @ResponseBody List<Offer> completedOfferListRest() {
		return (List<Offer>) offerRepository.findByAssignedFalseAndCompletedTrue();
	}
	
	// RESTful service for getting a single offer by id
	@GetMapping("/offers/{id}")
	public @ResponseBody Optional<Offer> getOfferRest(@PathVariable("id") Long offerId) {
		return offerRepository.findById(offerId);
	}
	
	// RESTful service for adding an offer
	@PostMapping("/offers")
	public @ResponseBody Offer saveOfferRest(@RequestBody Offer offer) {
		return offerRepository.save(offer);
	}
	
	/* RESTful service for updating a client, maps out traits to an existing
	 * client, or if the clientId is not found, saves the json-object as a new client
	 */
	
	@PutMapping("/offers/{id}")
	public @ResponseBody Offer updateOfferRest(@PathVariable("id") Long offerId,
			@RequestBody Offer updatedOffer) {
		return offerRepository.findById(offerId)
				.map(offer -> {
					offer.setClient(updatedOffer.getClient());
					offer.setWordCount(updatedOffer.getWordCount());
					offer.setPrice(updatedOffer.getPrice());
					offer.setSubject(updatedOffer.getSubject());
					offer.setSourceLanguage(updatedOffer.getSourceLanguage());
					offer.setTargetLanguage(updatedOffer.getTargetLanguage());
					offer.setRequirements(updatedOffer.getRequirements());
					offer.setAssigned(updatedOffer.isAssigned());
					offer.setCompleted(updatedOffer.isCompleted());
					offer.setDeadlineDate(updatedOffer.getDeadlineDate());
					offer.setCompletionDate(updatedOffer.getCompletionDate());
					return offerRepository.save(offer);
				})
				.orElseGet(() -> {
					return offerRepository.save(updatedOffer);
				});
	}
	
	// RESTful service for deleting an offer and returning an updated list of offers
	@DeleteMapping("/offers/{id}")
	public @ResponseBody List<Offer> deleteOfferRest(@PathVariable("id") Long offerId) {
		offerRepository.deleteById(offerId);
		return (List<Offer>) offerRepository.findAll();
	}
	
	/** Manually built test endpoints for database construction **/
	
	// not in use
	@GetMapping("/offerlist")
	public String offerList(Model model) {
		model.addAttribute("offers", offerRepository.findAll());
		return "offerlist"; // offerlist.html
	}
	
	// returns the add offer form, and provides it with a new offer object
	@GetMapping("/addoffer")
	public String addOffer(Model model) {
		model.addAttribute("offer", new Offer());
		model.addAttribute("clients", clientRepository.findAll());
		return "addoffer"; // addoffer.html
	}
	
	// saves an offer, if valid
	@PostMapping("/saveoffer")
	public String saveOffer(@Valid Offer offer, BindingResult bindingResult, Model model) {
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("clients", clientRepository.findAll());
			return "addoffer";
		} else {
		
			/* gets the initial form deadline date value,
			 * converts it to GMT +2(Helsinki) and saves it to the offer
			 */
			
			offer.setDeadlineDate(ZonedDateTime.of
					(LocalDateTime.parse(offer.getFormDeadline()),
					ZoneId.of("Europe/Helsinki")));	
			
			offerRepository.save(offer);
			return "redirect:brokermain"; //brokermain.html
			
		}
	}
	
	// updates an offer, if valid
	@PostMapping("/updateoffer")
	public String updateOffer(@Valid Offer offer, BindingResult bindingResult, Model model) {
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("clients", clientRepository.findAll());
			return "editoffer";
		} else {
			
			Offer updatedOffer = offerRepository.findById(offer.getOfferId()).get();
			
			/* gets the initial form deadline date value,
			 * converts it to GMT +2(Helsinki) and saves it to the offer
			 */
			updatedOffer.setDeadlineDate(ZonedDateTime.of
					(LocalDateTime.parse(offer.getFormDeadline()),
					ZoneId.of("Europe/Helsinki")));
			
			offerRepository.save(updatedOffer);
			
			return "redirect:brokermain"; //brokermain.html			
		}

	}
	
	// returns the edit form for an offer specified by an id
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
		toRelease.setCompleted(false);
		offerRepository.save(toRelease);
		
		return "redirect:brokermain"; //brokermain.html
		
	}
	
	// tidy up?
	@PostMapping("/completeoffer/{id}")
	public String completeOffer(@PathVariable("id") Long offerId) {
		
		Offer offer = offerRepository.findById(offerId).get();
		offer.setAssigned(false);
		offer.setCompleted(true);
		offerRepository.save(offer);
		
		return "redirect:../brokermain"; //brokermain.html
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
