package hh.swd20.courseproject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import hh.swd20.courseproject.domain.Freelancer;
import hh.swd20.courseproject.domain.FreelancerRepository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class FreelancerRepositoryTest {
	
	@Autowired
	private FreelancerRepository freelancerRepository;
	
	@Test
	public void createNewFreelancer() {
		
		Freelancer freelancer = new Freelancer("John", "Wayne", "+3587654321", "Mannerheimintie 1 0100 Helsinki", "john.wayne@helsinki.fi");
		freelancerRepository.save(freelancer);
		assertThat(freelancer.getFreelancerId()).isNotNull();
	}
	
	@Test
	public void updateFreelancer() {
		
		Freelancer freelancer = new Freelancer("John", "Wayne", "+3587654321", "Mannerheimintie 1 0100 Helsinki", "john.wayne@helsinki.fi");
		freelancerRepository.save(freelancer);
		Freelancer toUpdate = freelancerRepository.findById(freelancer.getFreelancerId()).get();
		toUpdate.setFreelancerFName("Marion");
		freelancerRepository.save(toUpdate);
		assertThat(freelancerRepository.findById(freelancer.getFreelancerId()).get().getFreelancerFName().equals("Marion"));
	}
	
	@Test
	public void deleteFreelancer() {
		
		Freelancer freelancer = new Freelancer("John", "Wayne", "+3587654321", "Mannerheimintie 1 Helsinki", "john.wayne@helsinki.fi");
		freelancerRepository.save(freelancer);
		
		List<Freelancer> numberOfBefore = new ArrayList<Freelancer>();
		numberOfBefore.add(freelancerRepository.findById(freelancer.getFreelancerId()).get());
		
		freelancerRepository.deleteById(freelancer.getFreelancerId());
		
		List<Freelancer> numberOfAfter = new ArrayList<Freelancer>();
		
		for (Freelancer toCheck : freelancerRepository.findAll()) {
			if (toCheck.getFreelancerId() == freelancer.getFreelancerId()) {
				numberOfAfter.add(toCheck);
			}
		}
		
		assertThat(numberOfBefore.size()).isGreaterThan(0);
		assertThat(numberOfAfter).isEmpty();
	}

}
