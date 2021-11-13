package hh.swd20.courseproject.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Client {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long clientId;
	
	// not null (pattern?)
	private String clientName;
	
	// size (pattern?)
	private String clientContactFName;
	
	// size (pattern?)
	private String clientContactLName;
	
	// size 
	private String clientContactPhone;
	
	// size (pattern?)
	private String clientContactEmail;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "client")
	private List<Offer> offers;
		
	public Client(String clientName, String clientContactFName, String clientContactLName, String clientContactPhone,
			String clientContactEmail) {
		super();
		this.clientName = clientName;
		this.clientContactFName = clientContactFName;
		this.clientContactLName = clientContactLName;
		this.clientContactPhone = clientContactPhone;
		this.clientContactEmail = clientContactEmail;
	}
	
	public Client() {
		super();
		this.clientName = null;
		this.clientContactFName = null;
		this.clientContactLName = null;
		this.clientContactPhone = null;
		this.clientContactEmail = null;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientContactFName() {
		return clientContactFName;
	}

	public void setClientContactFName(String clientContactFName) {
		this.clientContactFName = clientContactFName;
	}

	public String getClientContactLName() {
		return clientContactLName;
	}

	public void setClientContactLName(String clientContactLName) {
		this.clientContactLName = clientContactLName;
	}

	public String getClientContactPhone() {
		return clientContactPhone;
	}

	public void setClientContactPhone(String clientContactPhone) {
		this.clientContactPhone = clientContactPhone;
	}

	public String getClientContactEmail() {
		return clientContactEmail;
	}

	public void setClientContactEmail(String clientContactEmail) {
		this.clientContactEmail = clientContactEmail;
	}

	public List<Offer> getOffers() {
		return offers;
	}

	public void setOffers(List<Offer> offers) {
		this.offers = offers;
	}
	
	@JsonIgnore // palaa muuten REST-getissä mukana
	public int getOffersSize() { 
		return this.offers.size();
	}

	@Override
	public String toString() {
		return "Client [clientName=" + clientName + ", clientContactFName=" + clientContactFName
				+ ", clientContactLName=" + clientContactLName + ", clientContactPhone=" + clientContactPhone
				+ ", clientContactEmail=" + clientContactEmail + "]";
	}
	
	

}
