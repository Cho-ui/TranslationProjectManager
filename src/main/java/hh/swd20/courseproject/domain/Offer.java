package hh.swd20.courseproject.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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

	@Override
	public String toString() {
		return "Offer [wordCount=" + wordCount + ", price=" + price + ", subject=" + subject + ", sourceLanguage="
				+ sourceLanguage + ", targetLanguage=" + targetLanguage + ", client=" + client + "]";
	}

}
