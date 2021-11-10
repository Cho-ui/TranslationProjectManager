package hh.swd20.courseproject.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import hh.swd20.courseproject.domain.Language;
import hh.swd20.courseproject.domain.LanguageRepository;

@Controller
public class LanguageController {
	
	@Autowired
	private LanguageRepository languageRepository;
	
	@GetMapping("/addlanguage")
	public String addLanguage(Model model) {
		
		model.addAttribute("language", new Language());
		
		return "addlanguage"; //addlanguage.html		
	}
	
	@PostMapping("/savelanguage")
	public String saveLanguage(Language language) {
		
		languageRepository.save(language);
		
		return "redirect:brokermain"; //brokermain.html
	}
	
	@GetMapping("/editlanguage/{id}")
	public String editLanguage(@PathVariable("id") Long languageId, Model model) {
		
		model.addAttribute("language", languageRepository.findById(languageId).get());
		
		return "editlanguage"; //editlanguage.html
	}
	
	@PostMapping("/updatelanguage")
	public String updateLanguage(@ModelAttribute Language language) {
		
		Language updateLanguage = language;
		
		languageRepository.save(updateLanguage);
		
		return "redirect:brokermain"; //brokermain.html
	}
	
	@GetMapping("/deletelanguage/{id}")
	public String deleteLanguage(@PathVariable("id") Long languageId) {
		languageRepository.deleteById(languageId);
		return "redirect:../brokermain"; //brokermain.html	
	}

}
