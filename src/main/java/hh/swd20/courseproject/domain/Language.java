package hh.swd20.courseproject.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Language {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long languageId;
	private String languageName;
		
	@ManyToMany(mappedBy = "languages")
	private Set<Freelancer> freelancers = new HashSet<>();
	
	public Language(String languageName) {
		super();
		this.languageName = languageName;
	}
	
	public Language() {
		super();
		this.languageName = null;
	}

	public Long getLanguageId() {
		return languageId;
	}
	public void setLanguageId(Long languageId) {
		this.languageId = languageId;
	}
	public String getLanguageName() {
		return languageName;
	}
	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	public Set<Freelancer> getFreelancers() {
		return freelancers;
	}

	public void setFreelancers(Set<Freelancer> freelancers) {
		this.freelancers = freelancers;
	}

	@Override
	public String toString() {
		return "Language [languageName=" + languageName + "]";
	}

}
