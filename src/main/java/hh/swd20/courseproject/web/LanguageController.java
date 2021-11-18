package hh.swd20.courseproject.web;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import hh.swd20.courseproject.domain.Freelancer;
import hh.swd20.courseproject.domain.Language;
import hh.swd20.courseproject.domain.LanguageRepository;

//TODO Specified CrossOrigin annotation(s) when REST endpoints in actual use
@Controller
public class LanguageController {
	
	@Autowired
	private LanguageRepository languageRepository;
	
	/** Manually built REST endpoints **/
	
	// RESTful service for getting all languages
	@GetMapping("/languages")
	public @ResponseBody List<Language> languageListRest() {
		return (List<Language>) languageRepository.findAll();
	}
	
	// RESTful service for getting a single language by id
	@GetMapping("/languages/{id}")
	public @ResponseBody Optional<Language> getLanguageRest(@PathVariable("id") Long languageId) {
		return languageRepository.findById(languageId);
	}
	
	// RESTful service for adding a language
	@PostMapping("/languages")
	public @ResponseBody Language saveLanguageRest(@RequestBody Language language) {
		return languageRepository.save(language);
	}
	
	/* RESTful service for updating a client, maps out traits to an existing
	 * client, or if the clientId is not found, saves the json-object as a new client
	 */
	
	@PutMapping("/languages/{id}")
	public @ResponseBody Language updateLanguageRest(@PathVariable("id") Long languageId,
			@RequestBody Language updatedLanguage) {
		return languageRepository.findById(languageId)
				.map(language -> {
					language.setLanguageName(updatedLanguage.getLanguageName());
					return languageRepository.save(language);
				})
				.orElseGet(() -> {
					return languageRepository.save(updatedLanguage);
				});
	}
	
	// RESTful service for deleting a language
	@DeleteMapping("/languages/{id}")
	public @ResponseBody List<Language> deleteLanguageRest(@PathVariable("id") Long languageId) {

		// gets all the freelancers that have the language as a proficiency
		Set<Freelancer> freelancers = languageRepository.findById(languageId).get().getFreelancers();
		
		// removes the proficiency from all of the found freelancers
		for (Freelancer freelancer : freelancers) {
			Set<Language> languages = freelancer.getLanguages();
			languages.remove(languageRepository.findById(languageId).get());
			freelancer.setLanguages(languages);
		}
		
		// deletes the language and returns an updated list of languages in use
		languageRepository.deleteById(languageId);
		return (List<Language>) languageRepository.findAll();
	}
	
	/** manually built test endpoints for database construction **/
	
	// lists languages
	@GetMapping("/languagelist")
	@PreAuthorize("hasAuthority('BROKER')")
	public String getLanguages(Model model) {
		model.addAttribute("languages", languageRepository.findAll());
		return "languagelist"; // languagelist.html
	}
	
	// gets the add language form, and gives it an empty new language
	@GetMapping("/addlanguage")
	@PreAuthorize("hasAuthority('BROKER')")
	public String addLanguage(Model model) {
		
		model.addAttribute("language", new Language());
		
		return "addlanguage"; //addlanguage.html		
	}
	
	// saves a new language, if valid
	@PostMapping("/savelanguage")
	@PreAuthorize("hasAuthority('BROKER')")
	public String saveLanguage(@Valid Language language, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			return "addlanguage";
		} else {
			languageRepository.save(language);
			return "redirect:languagelist"; // languagelist.html	
		}
	}
	
	// gets the edit language form for the language specified by the id
	@GetMapping("/editlanguage/{id}")
	@PreAuthorize("hasAuthority('BROKER')")
	public String editLanguage(@PathVariable("id") Long languageId, Model model) {
		
		model.addAttribute("language", languageRepository.findById(languageId).get());
		
		return "editlanguage"; //editlanguage.html
	}
	
	// updates a specified language, if valid
	@PostMapping("/updatelanguage")
	@PreAuthorize("hasAuthority('BROKER')")
	public String updateLanguage(@Valid Language language, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			return "editlanguage";
		} else {
			Language updateLanguage = language;
			languageRepository.save(updateLanguage);
			return "redirect:languagelist"; // languagelist.html	
		}
	}
	
	// deletes a specified language
	@GetMapping("/deletelanguage/{id}")
	@PreAuthorize("hasAuthority('BROKER')")
	public String deleteLanguage(@PathVariable("id") Long languageId) {
		languageRepository.deleteById(languageId);
		return "redirect:../languagelist"; // languagelist.html	
	}

}
