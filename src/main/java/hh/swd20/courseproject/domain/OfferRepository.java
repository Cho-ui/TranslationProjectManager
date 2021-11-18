package hh.swd20.courseproject.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface OfferRepository extends CrudRepository<Offer, Long> {
	
	public List<Offer> findByAssignedFalse(); // not in use currently, tested nonetheless
	public List<Offer> findByAssignedTrue(); // not in use currently tested nonetheless
	public List<Offer> findByFreelancerAndAssignedTrue(Freelancer freelancer);
	public List<Offer> findByFreelancerAndAssignedFalseAndCompletedTrue(Freelancer freelancer);
	public List<Offer> findByAssignedFalseAndCompletedTrue();
	public List<Offer> findByAssignedFalseAndCompletedFalse();
	public List<Offer> findByAssignedTrueAndCompletedFalse();

}
