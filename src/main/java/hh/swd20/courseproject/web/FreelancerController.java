package hh.swd20.courseproject.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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
	
	/** Manually built test endpoints for database construction **/
	
	@GetMapping("/addfreelancer")
	public String addFreelancer(Model model) {
		model.addAttribute("freelancer", new Freelancer());
		return "addfreelancer"; //addfreelancer.html
	}
	
	@PostMapping("/addfreelancerlanguage/{id}")
	public String addFreeLancerLanguage(@PathVariable("id") Long freelancerId,
			@ModelAttribute Freelancer freelancer, Model model) {
		
		/* This saves the added language to the freelancer in the db */
		
		Language toAdd = languageRepository.findByLanguageName(freelancer.getFreelanceLanguageField());
		Freelancer fromRepo = freelancerRepository.findById(freelancerId).get();
		fromRepo.getLanguages().add(toAdd);
		freelancerRepository.save(fromRepo);
		
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
		
		model.addAttribute("freelancer", freelancerRepository.findById(freelancerId).get());
		model.addAttribute("addlanguages", addLanguages);
		model.addAttribute("removelanguages", removeLanguages);
		
		return "editfreelancer"; //editfreelancer.html
	}
	
	@PostMapping("/deletefreelancerlanguage/{id}")
	public String deleteFreeLancerLanguage(@PathVariable("id") Long freelancerId,
			@ModelAttribute Freelancer freelancer, Model model) {
		
		/* This deletes the chosen language from the freelancer in the db */
		
		Language toDelete = languageRepository.findByLanguageName(freelancer.getFreelanceLanguageField());
		Freelancer fromRepo = freelancerRepository.findById(freelancerId).get();
		fromRepo.getLanguages().remove(toDelete);
		freelancerRepository.save(fromRepo);
		
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
		
		model.addAttribute("freelancer", freelancerRepository.findById(freelancerId).get());
		model.addAttribute("addlanguages", addLanguages);
		model.addAttribute("removelanguages", removeLanguages);
		
		return "editfreelancer"; //editfreelancer.html
	}
	
	@PostMapping("/savefreelancer")
	public String saveFreelancer(Freelancer freelancer) {
		freelancerRepository.save(freelancer);
		return "redirect:brokermain"; //brokermain.html
	}
	
	@PostMapping("/updatefreelancer")
	public String updateFreelancer(@ModelAttribute Freelancer freelancer) {
		
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
		
		model.addAttribute("freelancer", freelancerRepository.findById(freelancerId).get());
		model.addAttribute("addlanguages", addLanguages);
		model.addAttribute("removelanguages", removeLanguages);
				
		return "editfreelancer"; //editfreelancer.html
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
