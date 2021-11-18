package hh.swd20.courseproject.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Client {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long clientId;
	
	@NotEmpty(message = "Client needs to be named")
	// any word character with Scandic characters included, once or more times
	@Pattern(regexp = "[a-zA-ZüåäöæøÜÅÄÖÆØ_0-9\\-\\s]+", message = "Please provide a valid Client name(A-Z, 0-9, Scandic characters permitted)") 
	private String clientName;
	
	@Size(min=2, max=30, message = "Name must be 2-30 characters long")
	// any alphabet letter with Scandic characters included, once or more times
	@Pattern(regexp = "[a-zA-ZüåäöæøÜÅÄÖÆØ\\-\\s]+", message = "Characters A-Z and ÜÅÄÖÆØ permitted") 
	private String clientContactFName;
	
	@Size(min=2, max=30, message = "Name must be 2-30 characters long")
	@Pattern(regexp = "[a-zA-ZüåäöæøÜÅÄÖÆØ\\-\\.\\s]+", message = "Characters A-Z and ÜÅÄÖÆØ permitted")
	private String clientContactLName;
	
	@Size(min=2, max=30, message = "Number must be 2-30 characters long")
	// any number between 0-9 once or more times, "+" once or not at all
	@Pattern(regexp = "\\+?[0-9]+", message = "Number in format +NNN.. or NNN..") 
	private String clientContactPhone;
	
	@Size(min=2, max=50, message = "Email must be 2-50 characters long")
	// handle@address.suffix format for emails 
	@Email(regexp = "^[a-zA-Z_0-9\\.]+\\@[a-zA-Z_0-9]+\\.[a-zA-Z]+$", message = "Email must be in a valid handle@address.suffix format")
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
	
	@JsonIgnore // ignore so not returned with REST-requests
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
