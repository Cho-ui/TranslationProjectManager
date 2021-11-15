package hh.swd20.courseproject;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import hh.swd20.courseproject.domain.Client;
import hh.swd20.courseproject.domain.ClientRepository;
import hh.swd20.courseproject.domain.Freelancer;
import hh.swd20.courseproject.domain.FreelancerRepository;
import hh.swd20.courseproject.domain.Language;
import hh.swd20.courseproject.domain.LanguageRepository;
import hh.swd20.courseproject.domain.Offer;
import hh.swd20.courseproject.domain.OfferRepository;
import hh.swd20.courseproject.domain.User;
import hh.swd20.courseproject.domain.UserRepository;

@SpringBootApplication
public class CourseprojectApplication {
	
	private static final Logger log = LoggerFactory.getLogger(CourseprojectApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CourseprojectApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner demo(ClientRepository clientRepository, OfferRepository offerRepository, 
			LanguageRepository languageRepository, FreelancerRepository freelancerRepository, UserRepository userRepository) {
		return (args) -> {
			
			log.info("Create and save Broker- and Manager-users");
			
			User u1 = new User("broker", "$2a$10$1ryWb4oVWtrexfqET428J.IcPL3e2yJ1PrPAoAnYGPMhHsGynW9KW", "BROKER");
			User u2 = new User("manager", "$2a$10$wvwV0h4Fz3JtQCHUSKj9puz94/gbqnBIBeEN0/isYRHt.PR8BeKiC", "MANAGER");
			
			userRepository.save(u1);
			userRepository.save(u2);
			
			log.info("Save some clients");
			
			Client c1 = new Client("Adidas", "Adi", "Dassler", "+3581234563", "adi.dassler@adidas.com");
			Client c2 = new Client("Nike", "Niko", "Kessler", "+5551234422", "niko.kessler@nike.com");
			Client c3 = new Client("Karhu", "Kari", "Hukka", "+756333444", "kari.hukka@karhu.com");
			
			clientRepository.save(c1);
			clientRepository.save(c2);
			clientRepository.save(c3);
			
			log.info("Fetch clients");
			for (Client client : clientRepository.findAll()) {
				log.info(client.toString());
			}
			
			log.info("Save some offers");
			
			Offer o1 = new Offer(c1, 560, 112.56, "Sneaker specs manual", "English", "Finnish");
			Offer o2 = new Offer(c2, 1300, 270.11, "Marketing materials", "Finnish", "English");
			Offer o3 = new Offer(c2, 11400, 2800.56, "Sales report", "German", "French");
			Offer o4 = new Offer(c3, 400, 91.02, "Product pitch doc", "Finnish", "Spanish");
			Offer o5 = new Offer(c3, 703, 180.22, "Technical specifications", "Finnish", "German");
			Offer o6 = new Offer(c2, 1560, 300.88, "Technical specifications", "German", "Finnish");
			
			offerRepository.save(o1);
			offerRepository.save(o2);
			offerRepository.save(o3);
			offerRepository.save(o4);
			offerRepository.save(o5);
			offerRepository.save(o6);
			
			log.info("Fetch offers");
			for (Offer offer : offerRepository.findAll()) {
				log.info(offer.toString());
			}			
			
			log.info("Create languages");
			
			Language l1 = new Language("German");
			Language l2 = new Language("Spanish");
			Language l3 = new Language("Finnish");
			Language l4 = new Language("English");
			
			log.info("Save languages");
			
			languageRepository.save(l1);
			languageRepository.save(l2);
			languageRepository.save(l3);
			languageRepository.save(l4);
			
			log.info("Create freelancers and add language proficiencies");
			
			Freelancer f1 = new Freelancer("Gunther", "Muller", "+44011223355", "Käännöstie 2 Espoo", "gunther.muller@gmail.com");
			Freelancer f2 = new Freelancer("Karin", "Muller", "+44011333485", "Käännöstie 2 Espoo", "karin.muller@gmail.com");
			Freelancer f3 = new Freelancer("Juan", "Gonzalez", "+55011223344", "Käännöstie 15 Helsinki", "juan.gonzalez@gmail.com");
			
			Set<Language> f1Languages = new HashSet<>();
			f1Languages.add(l3);
			f1Languages.add(l1);
			
			f1.setLanguages(f1Languages);
			
			Set<Language> f2Languages = new HashSet<>();
			f2Languages.add(l3);
			f2Languages.add(l1);
			
			f2.setLanguages(f2Languages);
			
			Set<Language> f3Languages = new HashSet<>();
			f3Languages.add(l3);
			f3Languages.add(l2);
			f3Languages.add(l4);
			
			f3.setLanguages(f3Languages);
			
			log.info("Save freelancers");
			
			freelancerRepository.save(f1);
			freelancerRepository.save(f2);
			freelancerRepository.save(f3);
			
		};
	}

}
