package hh.swd20.courseproject.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;

public interface FreelancerRepository extends CrudRepository<Freelancer, Long> {
	
	// Could be used to clean up freelancers' language searches from brute force list work
	// public List<Freelancer> findByLanguagesIn(HashSet<Language> neededLanguages);

}
