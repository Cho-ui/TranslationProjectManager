package hh.swd20.courseproject.domain;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Offer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long offerId;
	
	@NotNull(message = "An offer must have a word count")
	private int wordCount;
	
	@NotNull(message = "An offer must have a set price")
	private double price;

	@NotEmpty(message = "An offer must have a subject")
	@Size(min=2, max=30, message = "Subject must be stated in 2-30 characters")
	private String subject;
	
	@NotEmpty(message = "An offer must have a source language")
	@Size(min=2, max=30, message = "Language name must be 2-30 characters long")
	// any word character with Scandic characters included, once or more times
	@Pattern(regexp = "[a-zA-ZüåäöæøÜÅÄÖÆØ()\\.\\s]+", message = "Characters A-Z, ÜÅÄÖÆØ, and ( ) . are permitted")
	private String sourceLanguage;
	
	@NotEmpty(message = "An offer must have a target language")
	@Size(min=2, max=30, message = "Language name must be 2-30 characters long")
	// any word character with Scandic characters included, once or more times
	@Pattern(regexp = "[a-zA-ZüåäöæøÜÅÄÖÆØ()\\.\\s]+", message = "Characters A-Z, ÜÅÄÖÆØ, and ( ) . are permitted")
	private String targetLanguage;
	
	@JsonIgnore
	private String formDeadline; // thymeleaf workaround DB variable
	
	@Size(max=255, message = "Requirements need to be specified in 255 characters")
	private String requirements;
	
	private boolean assigned; // not in constructor
	private boolean completed; // not in constructor

	private ZonedDateTime deadlineDate; // not in constructor
	
	// not in use, edit according to deadlinedate later
	@DateTimeFormat(pattern = "yyyy/MM/dd hh:mm") 
	private LocalDateTime completionDate;
	
	@ManyToOne
	@JoinColumn(name = "freelancerId")
	private Freelancer freelancer; // not in constructor
	
	@ManyToOne
	@JoinColumn(name = "clientId")
	private Client client;
	
	public Offer(Client client, int wordCount, double price, 
			String subject, String sourceLanguage, String targetLanguage) {
		super();
		this.client = client;
		this.wordCount = wordCount;
		this.price = price;
		this.subject = subject;
		this.sourceLanguage = sourceLanguage;
		this.targetLanguage = targetLanguage;
	}
	
	public Offer(Client client, int wordCount, double price,
			String subject, String sourceLanguage, String targetLanguage,
			String requirements, String formDeadline) {
		super();
		this.client = client;
		this.wordCount = wordCount;
		this.price = price;
		this.subject = subject;
		this.sourceLanguage = sourceLanguage;
		this.targetLanguage = targetLanguage;
		this.requirements = requirements;
		this.formDeadline = formDeadline;					
	}

	public Offer() {
		super();
	}
	
	public Long getOfferId() {
		return offerId;
	}

	public void setOfferId(Long offerId) {
		this.offerId = offerId;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public int getWordCount() {
		return wordCount;
	}

	public void setWordCount(int wordCount) {
		this.wordCount = wordCount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSourceLanguage() {
		return sourceLanguage;
	}

	public void setSourceLanguage(String sourceLanguage) {
		this.sourceLanguage = sourceLanguage;
	}

	public String getTargetLanguage() {
		return targetLanguage;
	}

	public void setTargetLanguage(String targetLanguage) {
		this.targetLanguage = targetLanguage;
	}

	public String getRequirements() {
		return requirements;
	}

	public void setRequirements(String requirements) {
		this.requirements = requirements;
	}

	public boolean isAssigned() {
		return assigned;
	}

	public void setAssigned(boolean assigned) {
		this.assigned = assigned;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public ZonedDateTime getDeadlineDate() {
		return deadlineDate;
	}

	public void setDeadlineDate(ZonedDateTime deadlineDate) {
		this.deadlineDate = deadlineDate;
	}

	public LocalDateTime getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(LocalDateTime completionDate) {
		this.completionDate = completionDate;
	}

	public String getFormDeadline() {
		return formDeadline;
	}

	public void setFormDeadline(String formDeadline) {
		this.formDeadline = formDeadline;
	}

	public Freelancer getFreelancer() {
		return freelancer;
	}

	public void setFreelancer(Freelancer freelancer) {
		this.freelancer = freelancer;
	}
	
	public String offerStatus() {
		if (!assigned && completed) {
			return "Completed";
		} else if (assigned) {
			return "Assigned";
		} else {
			return "Unassigned";
		}
	}
	
	public String formattedDeadline() {
		if (deadlineDate != null) {
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm z");
			String formattedDeadline = deadlineDate.format(formatter);
			
			return formattedDeadline;
		}
		
		return "";
	}

	@Override // not updated, update when necessary, at least with deadline
	public String toString() {
		return "Offer [wordCount=" + wordCount + ", price=" + price + ", subject=" + subject + ", sourceLanguage="
				+ sourceLanguage + ", targetLanguage=" + targetLanguage + ", client=" + client + "]";
	}

}
