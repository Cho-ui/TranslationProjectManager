package hh.swd20.courseproject.domain;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Offer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long offerId;
	
	private int wordCount;
	private double price;
	private String subject;
	private String sourceLanguage;
	private String targetLanguage;
	private String formDeadline;
	private String requirements; // not in constructor yet
	private boolean assigned; // not in constructor yet
	private boolean completed; // not in constructor yet

	private ZonedDateTime deadlineDate;
	
	@DateTimeFormat(pattern = "yyyy/MM/dd hh:mm") // not in constructor yet
	private LocalDateTime completionDate;
	
	@ManyToOne
	@JoinColumn(name = "freelancerId")
	private Freelancer freelancer; // not in constructor yet
	
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

	@Override // not updated, update when necessary
	public String toString() {
		return "Offer [wordCount=" + wordCount + ", price=" + price + ", subject=" + subject + ", sourceLanguage="
				+ sourceLanguage + ", targetLanguage=" + targetLanguage + ", client=" + client + "]";
	}

}
