package hh.swd20.courseproject.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface OfferRepository extends CrudRepository<Offer, Long> {
	
	public List<Offer> findByAssignedFalse();
	public List<Offer> findByAssignedTrue();
	public List<Offer> findByFreelancerAndAssignedTrue(Freelancer freelancer);
	public List<Offer> findByFreelancerAndAssignedFalseAndCompletedTrue(Freelancer freelancer);
	public List<Offer> findByAssignedFalseAndCompletedTrue();

}
