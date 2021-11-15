package hh.swd20.courseproject.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

import hh.swd20.courseproject.domain.Freelancer;
import hh.swd20.courseproject.domain.FreelancerRepository;
import hh.swd20.courseproject.domain.Language;
import hh.swd20.courseproject.domain.LanguageRepository;
import hh.swd20.courseproject.domain.Offer;
import hh.swd20.courseproject.domain.OfferRepository;

@Controller
public class FreelancerController {
	
	@Autowired
	private FreelancerRepository freelancerRepository;
	
	@Autowired
	private OfferRepository offerRepository;
	
	@Autowired
	private LanguageRepository languageRepository;
	
	/** Manually built REST endpoints **/
	
	// RESTful service for getting all freelancers
	@GetMapping("/freelancers")
	public @ResponseBody List<Freelancer> freelancerListRest() {
		return (List<Freelancer>) freelancerRepository.findAll();
	}
	
	// RESTful service for getting a single freelancer by id
	@GetMapping("/freelancers/{id}")
	public @ResponseBody Optional<Freelancer> getFreelancerRest(@PathVariable("id") Long freelancerId) {
		return freelancerRepository.findById(freelancerId);
	}
	
	// RESTful service for adding a freelancer
	@PostMapping("/freelancers")
	public @ResponseBody Freelancer saveFreelancerRest(@RequestBody Freelancer freelancer) {
		return freelancerRepository.save(freelancer);
	}
	
	// RESTful service for updating a freelancer
	@PutMapping("/freelancers/{id}")
	public @ResponseBody Freelancer updateFreelancerRest(@PathVariable("id") Long freelancerId, 
			@RequestBody Freelancer updatedFreelancer) {
		return freelancerRepository.findById(freelancerId)
				.map(freelancer -> {
					freelancer.setFreelancerFName(updatedFreelancer.getFreelancerFName());
					freelancer.setFreelancerLName(updatedFreelancer.getFreelancerLName());
					freelancer.setFreelancerPhone(updatedFreelancer.getFreelancerPhone());
					freelancer.setFreelancerEmail(updatedFreelancer.getFreelancerEmail());
					return freelancerRepository.save(freelancer);
				})
				.orElseGet(() -> {
					return freelancerRepository.save(updatedFreelancer);
				});	
	}
	
	// RESTful service for deleting a freelancer and returning an updated list of freelancers
	@DeleteMapping("/freelancers/{id}")
	public @ResponseBody List<Freelancer> deleteFreelancerRest(@PathVariable("id") Long freelancerId) {
		freelancerRepository.deleteById(freelancerId);
		return (List<Freelancer>) freelancerRepository.findAll();
	}
	
	/** Manually built test endpoints for database construction **/
	
	// returns the add freelancer form and passes a new freelancer to it
	@GetMapping("/addfreelancer")
	public String addFreelancer(Model model) {
		model.addAttribute("freelancer", new Freelancer());
		return "addfreelancer"; //addfreelancer.html
	}
	
	// handles the addition of a language to a freelancer
	@PostMapping("/addfreelancerlanguage/{id}")
	public String addFreeLancerLanguage(@PathVariable("id") Long freelancerId,
			@ModelAttribute Freelancer freelancer, Model model) {
		
		/* This saves the added language to the freelancer in the db
		 * if the dropdown selection isn't empty */
		
		Language toAdd = languageRepository.findByLanguageName(freelancer.getFreelanceLanguageField());
		
		if (toAdd != null) {
		Freelancer fromRepo = freelancerRepository.findById(freelancerId).get();
		fromRepo.getLanguages().add(toAdd);
		freelancerRepository.save(fromRepo);
		}
		
		/* add:
		 * get the language proficiencies the freelancer has,
		 * compare them to all available languages
		 * and add a list of proficiencies the freelancer doesn't have
		 * to the model
		 * 
		 * remove:
		 * get the language proficiencies the freelancer has,
		 * compare them to all available languages
		 * and create a Language object list to be passed in the model
		 */
		
		List<Language> addLanguages = addLanguages(freelancerId);
		List<Language> removeLanguages = removeLanguages(freelancerId);
		
		// get all work offers assigned to freelancer
		
		List<Offer> assignedOffers = offerRepository.findByFreelancerAndAssignedTrue(
		freelancerRepository.findById(freelancerId).get());
									
		// get all completed work offers
							
		List<Offer> completedOffers = offerRepository.findByFreelancerAndAssignedFalseAndCompletedTrue(
		freelancerRepository.findById(freelancerId).get());
		
		model.addAttribute("freelancer", freelancerRepository.findById(freelancerId).get());
		model.addAttribute("addlanguages", addLanguages);
		model.addAttribute("removelanguages", removeLanguages);
		model.addAttribute("assignedoffers", assignedOffers);
		model.addAttribute("completedoffers", completedOffers);
		
		return "editfreelancer"; //editfreelancer.html
	}
	
	// handles the deletion of a language from a freelancer's proficiencies
	@PostMapping("/deletefreelancerlanguage/{id}")
	public String deleteFreeLancerLanguage(@PathVariable("id") Long freelancerId,
			@ModelAttribute Freelancer freelancer, Model model) {
		
		/* This deletes the chosen language from the freelancer in the db
		 * if the language selected for deletion is not empty */
		
		Language toDelete = languageRepository.findByLanguageName(freelancer.getFreelanceLanguageField());
		
		if (toDelete != null) {
		Freelancer fromRepo = freelancerRepository.findById(freelancerId).get();
		fromRepo.getLanguages().remove(toDelete);
		freelancerRepository.save(fromRepo);
		}
		
		/* add:
		 * get the language proficiencies the freelancer has,
		 * compare them to all available languages
		 * and add a list of proficiencies the freelancer doesn't have
		 * to the model
		 * 
		 * remove:
		 * get the language proficiencies the freelancer has,
		 * compare them to all available languages
		 * and create a Language object list to be passed in the model
		 */
		
		List<Language> addLanguages = addLanguages(freelancerId);
		List<Language> removeLanguages = removeLanguages(freelancerId);
		
		// get all work offers assigned to freelancer
		
		List<Offer> assignedOffers = offerRepository.findByFreelancerAndAssignedTrue(
		freelancerRepository.findById(freelancerId).get());
							
		// get all completed work offers
					
		List<Offer> completedOffers = offerRepository.findByFreelancerAndAssignedFalseAndCompletedTrue(
		freelancerRepository.findById(freelancerId).get());
		
		model.addAttribute("freelancer", freelancerRepository.findById(freelancerId).get());
		model.addAttribute("addlanguages", addLanguages);
		model.addAttribute("removelanguages", removeLanguages);
		model.addAttribute("assignedoffers", assignedOffers);
		model.addAttribute("completedoffers", completedOffers);
		
		return "editfreelancer"; //editfreelancer.html
	}
	
	// deletes a specified freelancer
	@GetMapping("/deletefreelancer/{id}")
	public String deleteFreelancer(@PathVariable("id") Long freelancerId) {
		freelancerRepository.deleteById(freelancerId);
		
		return "redirect:../brokermain"; //brokermain.html
	}
	
	// saves a freelancer, if valid
	@PostMapping("/savefreelancer")
	public String saveFreelancer(@Valid Freelancer freelancer, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			return "addfreelancer";
		} else {
			freelancerRepository.save(freelancer);
			return "redirect:brokermain"; //brokermain.html
		}
	}
	
	// saves an updated freelancer, if valid
	@PostMapping("/updatefreelancer") // oli modelattribute
	public String updateFreelancer(@Valid Freelancer freelancer, BindingResult bindingResult, Model model) {
		
		if (bindingResult.hasErrors()) {
			
			/* add:
			 * get the language proficiencies the freelancer has,
			 * compare them to all available languages
			 * and add a list of proficiencies the freelancer doesn't have
			 * to the model
			 * 
			 * remove:
			 * get the language proficiencies the freelancer has,
			 * compare them to all available languages
			 * and create a Language object list to be passed in the model
			 */
							
			List<Language> addLanguages = addLanguages(freelancer.getFreelancerId());
			List<Language> removeLanguages = removeLanguages(freelancer.getFreelancerId());
			
			// get all work offers assigned to freelancer
			
			List<Offer> assignedOffers = offerRepository.findByFreelancerAndAssignedTrue(
					freelancerRepository.findById(freelancer.getFreelancerId()).get());
					
			// get all completed work offers
			
			List<Offer> completedOffers = offerRepository.findByFreelancerAndAssignedFalseAndCompletedTrue(
					freelancerRepository.findById(freelancer.getFreelancerId()).get());
			
			model.addAttribute("addlanguages", addLanguages);
			model.addAttribute("removelanguages", removeLanguages);
			model.addAttribute("assignedoffers", assignedOffers);
			model.addAttribute("completedoffers", completedOffers);
				
			return "editfreelancer";
		} else {
		
			/* Gets existing freelancer language set, which is stored
			 * separately. Sets the language set to the updated freelancer,
			 * so as to not overwrite an empty set when saving the updated
			 * freelancer
			 */
			Set<Language> freelancerLanguages = freelancerRepository.findById(freelancer.getFreelancerId()).get().getLanguages();
			Freelancer updatedFreelancer = freelancer;
			updatedFreelancer.setLanguages(freelancerLanguages);
			freelancerRepository.save(freelancer);
			
			return "redirect:brokermain"; // brokermain.html
			
		}
	}
	
	@GetMapping("/editfreelancer/{id}")
	public String editFreelancer(@PathVariable("id") Long freelancerId, Model model) {
		
		/* add:
		 * get the language proficiencies the freelancer has,
		 * compare them to all available languages
		 * and add a list of proficiencies the freelancer doesn't have
		 * to the model
		 * 
		 * remove:
		 * get the language proficiencies the freelancer has,
		 * compare them to all available languages
		 * and create a Language object list to be passed in the model
		 */
						
		List<Language> addLanguages = addLanguages(freelancerId);
		List<Language> removeLanguages = removeLanguages(freelancerId);	
		
		// get all work offers assigned to freelancer
		
		List<Offer> assignedOffers = offerRepository.findByFreelancerAndAssignedTrue(
				freelancerRepository.findById(freelancerId).get());
				
		// get all completed work offers
		
		List<Offer> completedOffers = offerRepository.findByFreelancerAndAssignedFalseAndCompletedTrue(
				freelancerRepository.findById(freelancerId).get());
		
		model.addAttribute("freelancer", freelancerRepository.findById(freelancerId).get());
		model.addAttribute("addlanguages", addLanguages);
		model.addAttribute("removelanguages", removeLanguages);
		model.addAttribute("assignedoffers", assignedOffers);
		model.addAttribute("completedoffers", completedOffers);
				
		return "editfreelancer"; //editfreelancer.html
	}
	
	@PostMapping("/assignfreelancer") //
	public String assignFreelancer(@ModelAttribute Offer offer, 
			@ModelAttribute Freelancer freelancer) {
		
		if (freelancer.getFreelancerId() != null) {
		Long offerId = offer.getOfferId();
		Offer offerAssign = offerRepository.findById(offerId).get();	
		Long freelancerId = freelancer.getFreelancerId();
		Freelancer freelancerAssign = freelancerRepository.findById(freelancerId).get();
		
		offerAssign.setFreelancer(freelancerAssign);
		offerAssign.setAssigned(true);
		
		offerRepository.save(offerAssign);
		}
		
		return "redirect:brokermain"; // brokermain.html
	}
	
	// addLanguages- and removeLanguages-list methods
	private List<Language> addLanguages(Long freelancerId) {
		
		ArrayList<String> freelancerLanguages = 
				freelancerRepository.findById(freelancerId).get().getLanguagesAsStringArray();

		List<Language> addLanguages = new ArrayList<Language>();
		
		for (Language language : languageRepository.findAll()) {
			if (!freelancerLanguages.contains(language.getLanguageName())) {
				addLanguages.add(language);
			}
		}
		
		return addLanguages;
	}
	
	private List<Language> removeLanguages(Long freelancerId) {
		
		ArrayList<String> freelancerLanguages = 
				freelancerRepository.findById(freelancerId).get().getLanguagesAsStringArray();
		
		List<Language> removeLanguages = new ArrayList<Language>();
		
		for (Language language : languageRepository.findAll()) {
			if (freelancerLanguages.contains(language.getLanguageName())) {
				removeLanguages.add(language);
			}
		}
		
		return removeLanguages;
	}

}
