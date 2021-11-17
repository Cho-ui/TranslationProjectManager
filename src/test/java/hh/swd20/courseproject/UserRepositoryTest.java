package hh.swd20.courseproject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import hh.swd20.courseproject.domain.User;
import hh.swd20.courseproject.domain.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserRepositoryTest {
	
	@Autowired
	private UserRepository userRepository;
		
		@Test
		public void createNewUser() {
			
			User user = new User("JunitTestUser", "nnn", "NONEXISTENT");
			userRepository.save(user);
			assertThat(user.getId()).isNotNull();		
		}
		
		@Test
		public void updateUser() {
			
			User user = new User("JunitTestUser", "nnn", "NONEXISTENT");
			userRepository.save(user);
			User toUpdate = userRepository.findById(user.getId()).get();
			toUpdate.setUsername("Marion");
			userRepository.save(toUpdate);
			assertThat(userRepository.findById(user.getId()).get().getUsername().equals("Marion"));
		}
		
		@Test
		public void deleteUser() {
			
			User user = new User("JunitTestUser", "nnn", "NONEXISTENT");
			userRepository.save(user);
			
			List<User> numberOfBefore = new ArrayList<User>();
			numberOfBefore.add(userRepository.findById(user.getId()).get());
			
			userRepository.deleteById(user.getId());
			
			List<User> numberOfAfter = new ArrayList<User>();
			
			for (User toCheck : userRepository.findAll()) {
				if (toCheck.getId() == user.getId()) {
					numberOfAfter.add(toCheck);
				}
			}
			assertThat(numberOfBefore.size()).isGreaterThan(0);
			assertThat(numberOfAfter.isEmpty());
		}

}
