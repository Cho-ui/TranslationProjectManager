package hh.swd20.courseproject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import hh.swd20.courseproject.domain.Client;
import hh.swd20.courseproject.domain.ClientRepository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ClientRepositoryTest {
	
	@Autowired 
	private ClientRepository clientRepository;
	
	@Test
	public void createNewClient() {
		
		Client client = new Client("Knorr", "Jim", "Knorrison", "+3581234567", "lizardking@knorr.com");
		clientRepository.save(client);
		assertThat(client.getClientId()).isNotNull();		
	}
	
	@Test
	public void updateClient() {
		
		Client client = new Client("Knorr", "Jim", "Knorrison", "+3581234567", "lizardking@knorr.com");
		clientRepository.save(client);
		Client toUpdate = clientRepository.findById(client.getClientId()).get();
		toUpdate.setClientName("Doors");
		clientRepository.save(toUpdate);
		assertThat(clientRepository.findById(client.getClientId()).get().getClientName().equals("Doors"));
	}
	
	@Test
	public void deleteclient() {
		
		Client client = new Client("Knorr", "Jim", "Knorrison", "+3581234567", "lizardking@knorr.com");
		clientRepository.save(client);
		
		List<Client> numberOfBefore = new ArrayList<Client>();
		numberOfBefore.add(clientRepository.findById(client.getClientId()).get());
		
		clientRepository.deleteById(client.getClientId());
		
		List<Client> numberOfAfter = new ArrayList<Client>();
		
		for (Client toCheck : clientRepository.findAll()) {
			if (toCheck.getClientId() == client.getClientId()) {
				numberOfAfter.add(toCheck);
			}
		}
		assertThat(numberOfBefore.size()).isGreaterThan(0);
		assertThat(numberOfAfter.isEmpty());
	}
	
}
