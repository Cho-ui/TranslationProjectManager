package hh.swd20.courseproject.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import hh.swd20.courseproject.domain.OfferRepository;

@Controller
public class OfferController {

	@Autowired
	private OfferRepository offerRepository;
	
	/** Manually built test endpoints for database construction **/
	
	@GetMapping("/offerlist")
	public String offerList(Model model) {
		model.addAttribute("offers", offerRepository.findAll());
		return "offerlist"; // offerlist.html
	}
	
}
