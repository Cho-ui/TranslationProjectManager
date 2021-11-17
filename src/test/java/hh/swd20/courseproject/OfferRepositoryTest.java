package hh.swd20.courseproject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import hh.swd20.courseproject.domain.Client;
import hh.swd20.courseproject.domain.ClientRepository;
import hh.swd20.courseproject.domain.Freelancer;
import hh.swd20.courseproject.domain.FreelancerRepository;
import hh.swd20.courseproject.domain.Offer;
import hh.swd20.courseproject.domain.OfferRepository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class OfferRepositoryTest {
	
	@Autowired
	private OfferRepository offerRepository;
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private FreelancerRepository freelancerRepository;
	
	@Test
	public void findByFreelancerAndAssignedFalseAndCompletedTrue() {
		
		Freelancer freelancer = new Freelancer("John", "Wayne", "+35812345678", "Mannerheimintie 1 Helsinki", "john.wayne@helsinki.fi");
		freelancerRepository.save(freelancer);
		Client client = new Client("Knorr", "Jim", "Knorrison", "+3581234567", "lizardking@knorr.com");
		clientRepository.save(client);
		Offer offer = new Offer(client, 450, 45.00, "Product info", "English", "Finnish");
		offer.setFreelancer(freelancer);
		offer.setAssigned(false);
		offer.setCompleted(true);
		offerRepository.save(offer);
		
		List<Offer> byFreelancerAndAssignedFalseAndCompletedTrue = offerRepository.findByFreelancerAndAssignedFalseAndCompletedTrue(freelancer);
		assertThat(byFreelancerAndAssignedFalseAndCompletedTrue.size()).isGreaterThan(0);
	}
	
	@Test
	public void findByFreelancerAndAssignedTrue() {
		
		Freelancer freelancer = new Freelancer("John", "Wayne", "+35812345678", "Mannerheimintie 1 Helsinki", "john.wayne@helsinki.fi");
		freelancerRepository.save(freelancer);
		Client client = new Client("Knorr", "Jim", "Knorrison", "+3581234567", "lizardking@knorr.com");
		clientRepository.save(client);
		Offer offer = new Offer(client, 450, 45.00, "Product info", "English", "Finnish");
		offer.setFreelancer(freelancer);
		offer.setAssigned(true);
		offerRepository.save(offer);
		
		List<Offer> byFreelancerAndAssignedTrue = offerRepository.findByFreelancerAndAssignedTrue(freelancer);
		assertThat(byFreelancerAndAssignedTrue.size()).isGreaterThan(0);
	}
	
	@Test
	public void findByAssignedTrueAndCompletedFalse() {
		
		Client client = new Client("Knorr", "Jim", "Knorrison", "+3581234567", "lizardking@knorr.com");
		clientRepository.save(client);
		Offer offer = new Offer(client, 450, 45.00, "Product info", "English", "Finnish");
		offerRepository.save(offer);
		
		offerRepository.findById(offer.getOfferId()).get().setAssigned(true);
		offerRepository.findById(offer.getOfferId()).get().setCompleted(false);
		List<Offer> assignedTrueAndCompletedFalse = offerRepository.findByAssignedTrueAndCompletedFalse();
		assertThat(assignedTrueAndCompletedFalse.size()).isGreaterThan(0);		
	}
	
	@Test
	public void findByAssignedFalseAndCompletedFalse() {
		
		Client client = new Client("Knorr", "Jim", "Knorrison", "+3581234567", "lizardking@knorr.com");
		clientRepository.save(client);
		Offer offer = new Offer(client, 450, 45.00, "Product info", "English", "Finnish");
		offerRepository.save(offer);
		
		offerRepository.findById(offer.getOfferId()).get().setAssigned(false);
		offerRepository.findById(offer.getOfferId()).get().setCompleted(false);
		List<Offer> assignedFalseAndCompletedFalse = offerRepository.findByAssignedFalseAndCompletedFalse();
		assertThat(assignedFalseAndCompletedFalse.size()).isGreaterThan(0);
		
	}
	
	@Test
	public void findByAssignedFalseAndCompletedTrue() {
		
		Client client = new Client("Knorr", "Jim", "Knorrison", "+3581234567", "lizardking@knorr.com");
		clientRepository.save(client);
		Offer offer = new Offer(client, 450, 45.00, "Product info", "English", "Finnish");
		offerRepository.save(offer);
		
		offerRepository.findById(offer.getOfferId()).get().setAssigned(false);
		offerRepository.findById(offer.getOfferId()).get().setCompleted(true);
		List<Offer> assignedFalseAndCompletedTrue = offerRepository.findByAssignedFalseAndCompletedTrue();
		assertThat(assignedFalseAndCompletedTrue.size()).isGreaterThan(0);

	}
	
	@Test
	public void findByAssignedFalse() {
		
		Client client = new Client("Knorr", "Jim", "Knorrison", "+3581234567", "lizardking@knorr.com");
		clientRepository.save(client);
		Offer offer = new Offer(client, 450, 45.00, "Product info", "English", "Finnish");
		offerRepository.save(offer);
		
		List<Offer> assignedFalse = offerRepository.findByAssignedFalse();		
		assertThat(assignedFalse.size()).isGreaterThan(0);		
	}
	
	@Test
	public void findByAssignedTrue() {
		
		Client client = new Client("Knorr", "Jim", "Knorrison", "+3581234567", "lizardking@knorr.com");
		clientRepository.save(client);
		Offer offer = new Offer(client, 450, 45.00, "Product info", "English", "Finnish");
		offerRepository.save(offer);
				
		offerRepository.findById(offer.getOfferId()).get().setAssigned(true);
		List<Offer> assignedTrue = offerRepository.findByAssignedTrue();
		assertThat(assignedTrue.size()).isGreaterThan(0);
	}
	
	
	@Test
	public void createNewOffer() {
		
		Client client = new Client("Knorr", "Jim", "Knorrison", "+3581234567", "lizardking@knorr.com");
		Offer offer = new Offer(client, 450, 45.00, "Product info", "English", "Finnish");
		offerRepository.save(offer);
		assertThat(offer.getOfferId()).isNotNull();		
	}
	
	@Test
	public void updateOffer() {
		
		Client client = new Client("Knorr", "Jim", "Knorrison", "+3581234567", "lizardking@knorr.com");
		Offer offer = new Offer(client, 450, 45.00, "Product info", "English", "Finnish");
		offerRepository.save(offer);
	
		Offer toUpdate = offerRepository.findById(offer.getOfferId()).get();
		toUpdate.setSubject("UpdateJunitTest");
		offerRepository.save(toUpdate);
		assertThat(offerRepository.findById(offer.getOfferId()).get().getSubject().equals("UpdateJunitTest"));
	}
	
	@Test
	public void deleteOffer() {
		
		Client client = new Client("Knorr", "Jim", "Knorrison", "+3581234567", "lizardking@knorr.com");
		Offer offer = new Offer(client, 450, 45.00, "Product info", "English", "Finnish");
		offerRepository.save(offer);
		
		List<Offer> numberOfBefore = new ArrayList<Offer>();
		numberOfBefore.add(offerRepository.findById(offer.getOfferId()).get());
		
		offerRepository.deleteById(offer.getOfferId());
		
		List<Offer> numberOfAfter = new ArrayList<Offer>();
		
		for (Offer toCheck : offerRepository.findAll()) {
			if (toCheck.getOfferId() == offer.getOfferId()) {
				numberOfAfter.add(toCheck);
			}
		}
		assertThat(numberOfBefore.size()).isGreaterThan(0);
		assertThat(numberOfAfter.isEmpty());
	}

}
