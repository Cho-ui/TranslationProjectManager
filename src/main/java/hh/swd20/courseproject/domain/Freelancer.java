package hh.swd20.courseproject.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Freelancer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long freelancerId;
	private String freelancerFName;
	private String freelancerLName;
	private String freelancerPhone;
	private String freelancerAddress;
	private String freelancerEmail;
	
	@ManyToMany(cascade = { CascadeType.MERGE }) // changed from ALL
	@JoinTable(name = "Freelancer_Proficiency",
	joinColumns = { @JoinColumn(name = "freelancerId") },
	inverseJoinColumns =  { @JoinColumn(name = "languageId") }
	)
	Set<Language> languages = new HashSet<>();
	
	public Freelancer(String freelancerFName, String freelancerLName, String freelancerPhone, String freelancerAddress,
			String freelancerEmail) {
		super();
		this.freelancerFName = freelancerFName;
		this.freelancerLName = freelancerLName;
		this.freelancerPhone = freelancerPhone;
		this.freelancerAddress = freelancerAddress;
		this.freelancerEmail = freelancerEmail;
	}

	public Freelancer() {
		super();
		this.freelancerFName = null;
		this.freelancerLName = null;
		this.freelancerPhone = null;
		this.freelancerAddress = null;
		this.freelancerEmail = null;
	}

	public Long getFreelancerId() {
		return freelancerId;
	}

	public void setFreelancerId(Long freelancerId) {
		this.freelancerId = freelancerId;
	}

	public String getFreelancerFName() {
		return freelancerFName;
	}

	public void setFreelancerFName(String freelancerFName) {
		this.freelancerFName = freelancerFName;
	}

	public String getFreelancerLName() {
		return freelancerLName;
	}

	public void setFreelancerLName(String freelancerLName) {
		this.freelancerLName = freelancerLName;
	}

	public String getFreelancerPhone() {
		return freelancerPhone;
	}

	public void setFreelancerPhone(String freelancerPhone) {
		this.freelancerPhone = freelancerPhone;
	}

	public String getFreelancerAddress() {
		return freelancerAddress;
	}

	public void setFreelancerAddress(String freelancerAddress) {
		this.freelancerAddress = freelancerAddress;
	}

	public String getFreelancerEmail() {
		return freelancerEmail;
	}

	public void setFreelancerEmail(String freelancerEmail) {
		this.freelancerEmail = freelancerEmail;
	}

	public Set<Language> getLanguages() {
		return languages;
	}

	public void setLanguages(Set<Language> languages) {
		this.languages = languages;
	}

	@Override
	public String toString() {
		return "Freelancer [freelancerFName=" + freelancerFName + ", freelancerLName=" + freelancerLName
				+ ", freelancerPhone=" + freelancerPhone + ", freelancerAddress=" + freelancerAddress
				+ ", freelancerEmail=" + freelancerEmail + "]";
	}

}
