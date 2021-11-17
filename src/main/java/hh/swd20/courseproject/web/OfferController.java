package hh.swd20.courseproject.web;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
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
	@CrossOrigin(origins = "http://localhost:3000/")
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
	
	// lists unassigned, assigned and completed offers on the offerlist page
	@GetMapping("/offerlist")
	@PreAuthorize("hasAnyAuthority('BROKER', 'MANAGER')")
	public String offerList(Model model) {
		model.addAttribute("unassignedOffers", offerRepository.findByAssignedFalseAndCompletedFalse());
		model.addAttribute("assignedOffers", offerRepository.findByAssignedTrueAndCompletedFalse());
		model.addAttribute("completedOffers", offerRepository.findByAssignedFalseAndCompletedTrue());
		return "offerlist"; // offerlist.html
	}
	
	// returns the add offer form, and provides it with a new offer object
	@GetMapping("/addoffer")
	@PreAuthorize("hasAuthority('BROKER')")
	public String addOffer(Model model) {
		model.addAttribute("offer", new Offer());
		model.addAttribute("clients", clientRepository.findAll());
		return "addoffer"; // addoffer.html
	}
	
	// saves an offer, if valid
	@PostMapping("/saveoffer")
	@PreAuthorize("hasAuthority('BROKER')")
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
			return "redirect:offerlist"; // offerlist.html
			
		}
	}
	
	// updates an offer, if valid
	@PostMapping("/updateoffer")
	@PreAuthorize("hasAnyAuthority('BROKER', 'MANAGER')")
	public String updateOffer(@Valid Offer offer, BindingResult bindingResult, Model model) {
		
		if (bindingResult.hasErrors()) {
			model.addAttribute("clients", clientRepository.findAll());
			return "editoffer";
		} else {
			
			Offer updatedOffer = offer;
			
			// if offer has a freelancer attached to it
			if (!(updatedOffer.getFreelancer() == null)) {
							
				/* if either the source- or target language are missing from the
				 * freelancer, release the edited offer
				 */
				if (!(updatedOffer.getFreelancer()
						.getLanguagesAsStringArray()
						.contains(updatedOffer.getSourceLanguage()))) {
					updatedOffer.setAssigned(false);
					updatedOffer.setFreelancer(null);
					updatedOffer.setCompleted(false); 
				} else if(!(updatedOffer.getFreelancer()
						.getLanguagesAsStringArray()
						.contains(updatedOffer.getTargetLanguage()))) {
					updatedOffer.setAssigned(false);
					updatedOffer.setFreelancer(null);
					updatedOffer.setCompleted(false);
				}
				
			}
			
			/* gets the initial form deadline date value,
			 * converts it to GMT +2(Helsinki) and saves it to the offer
			 */
			updatedOffer.setDeadlineDate(ZonedDateTime.of
					(LocalDateTime.parse(offer.getFormDeadline()),
					ZoneId.of("Europe/Helsinki")));
			
			offerRepository.save(updatedOffer);
			
			return "redirect:offerlist"; // offerlist.html			
		}

	}
	
	// returns the edit form for an offer specified by an id
	@GetMapping("/editoffer/{id}")
	@PreAuthorize("hasAnyAuthority('BROKER', 'MANAGER')")
	public String editOffer(@PathVariable("id") Long offerId, Model model) {
		
		model.addAttribute("offer", offerRepository.findById(offerId).get());
		model.addAttribute("clients", clientRepository.findAll());
		
		return "editoffer"; //editoffer.html
		
	}
	
	@GetMapping("/deleteoffer/{id}")
	@PreAuthorize("hasAuthority('BROKER')")
	public String deleteOffer(@PathVariable("id") Long offerId) {
		offerRepository.deleteById(offerId);
		
		return "redirect:../offerlist"; // offerlist.html
	}
	
	@PostMapping("/releaseoffer")
	@PreAuthorize("hasAnyAuthority('BROKER', 'MANAGER')")
	public String releaseOffer(@ModelAttribute Offer offer) {
		
		Offer toRelease = offerRepository.findById(offer.getOfferId()).get();
		toRelease.setAssigned(false);
		toRelease.setFreelancer(null);
		toRelease.setCompleted(false);
		offerRepository.save(toRelease);
		
		return "redirect:offerlist"; //offerlist.html
		
	}
	
	@PostMapping("/completeoffer/{id}")
	@PreAuthorize("hasAnyAuthority('BROKER', 'MANAGER')")
	public String completeOffer(@PathVariable("id") Long offerId) {
		
		Offer offer = offerRepository.findById(offerId).get();
		
		if (!(offer.getFreelancer() == null)) {
		offer.setAssigned(false);
		offer.setCompleted(true);
		offerRepository.save(offer); 
		}
		
		return "redirect:../offerlist"; // offerlist.html
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
