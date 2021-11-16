package hh.swd20.courseproject.web;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import hh.swd20.courseproject.domain.Client;
import hh.swd20.courseproject.domain.ClientRepository;


// CrossOrigin-annotaatio?
@Controller
public class ClientController {
	
	@Autowired
	private ClientRepository clientRepository;
	
	/** Manually built REST endpoints **/
	
	// RESTful service for getting all clients
	@GetMapping("/clients")
	public @ResponseBody List<Client> clientListRest() {
		return (List<Client>) clientRepository.findAll();
	}
	
	// RESTful service for getting a single client by id
	@GetMapping("/clients/{id}")
	public @ResponseBody Optional<Client> getClientRest(@PathVariable("id") Long clientId) {
		return clientRepository.findById(clientId);
	}
	
	// RESTful service for adding a client
	@PostMapping("/clients")
	public @ResponseBody Client saveClientRest(@RequestBody Client client) {
		return clientRepository.save(client);
	}
	
	/* RESTful service for updating a client, maps out traits to an existing
	 * client, or if the clientId is not found, saves the json-object as a new client
	 */
	
	@PutMapping("/clients/{id}")
	public @ResponseBody Client updateClientRest(@PathVariable("id") Long clientId, 
			@RequestBody Client updatedClient) {
		return clientRepository.findById(clientId)
				.map(client -> {
					client.setClientName(updatedClient.getClientName());
					client.setClientContactFName(updatedClient.getClientContactFName());
					client.setClientContactLName(updatedClient.getClientContactLName());
					client.setClientContactPhone(updatedClient.getClientContactPhone());
					client.setClientContactEmail(updatedClient.getClientContactEmail());
					return clientRepository.save(client);
				})
				.orElseGet(() -> {
					return clientRepository.save(updatedClient);
				});
	}
	
	// RESTful service for deleting a client and returning an updated list of clients
	@DeleteMapping("/clients/{id}")
	public @ResponseBody List<Client> deleteClientRest(@PathVariable("id") Long clientId) {
		clientRepository.deleteById(clientId);
		return (List<Client>) clientRepository.findAll();
	}
	
	/** Manually built test endpoints for database construction **/
	
	// lists clients to the clientlist page
	@GetMapping("/clientlist")
	@PreAuthorize("hasAuthority('BROKER')")
	public String clientList(Model model) {
		model.addAttribute("clients", clientRepository.findAll());
		return "clientlist"; // clientlist.html
	}
	
	// redirects to add client form with a new client object
	@GetMapping("/addclient")
	@PreAuthorize("hasAuthority('BROKER')")
	public String addClient(Model model) {
		model.addAttribute("client", new Client());
		return "addclient"; //addclient.html
	}
	
	// saves a new client from the add client form, if valid
	@PostMapping("/saveclient")
	@PreAuthorize("hasAuthority('BROKER')")
	public String saveClient(@Valid Client client, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			return "addclient";
		} else {
		clientRepository.save(client);
		return "redirect:clientlist"; //clientlist.html
		}
	}
	
	// opens the client edit-form, sends a client object based on client id to it
	@GetMapping("/editclient/{id}")
	@PreAuthorize("hasAuthority('BROKER')")
	public String editClient(@PathVariable("id") Long clientId, Model model) {
		
		model.addAttribute("client", clientRepository.findById(clientId).get());
		
		return "editclient"; //editclient.html
		
	}
	
	// updates an existing client through the edit form, if valid
	@PostMapping("/updateclient")
	@PreAuthorize("hasAuthority('BROKER')")
	public String updateClient(@Valid Client client, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			return "editclient";
		} else {
			Client updatedClient = client;
			clientRepository.save(updatedClient);
			return "redirect:clientlist"; //clientlist.html	
		}		
	}
	
	// deletes a client
	@GetMapping("/deleteclient/{id}")
	@PreAuthorize("hasAuthority('BROKER')")
	public String deleteClient(@PathVariable("id") Long clientId) {
		clientRepository.deleteById(clientId);
		return "redirect:../clientlist"; //clientlist.html
	}

}
