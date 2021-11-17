package hh.swd20.courseproject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;

import hh.swd20.courseproject.web.ClientController;
import hh.swd20.courseproject.web.FreelancerController;
import hh.swd20.courseproject.web.LanguageController;
import hh.swd20.courseproject.web.OfferController;
import hh.swd20.courseproject.web.UserDetailServiceImpl;
import hh.swd20.courseproject.web.ViewController;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CourseprojectApplicationTests {
	
	@Autowired
	private ClientController clientController;
	@Autowired
	private FreelancerController freelancerController;
	@Autowired
	private LanguageController languageController;
	@Autowired
	private OfferController offerController;
	@Autowired
	private ViewController viewController;
	@Autowired
	private UserDetailServiceImpl userDetail;

	@Test
	void contextLoads() throws Exception {
		
		// smoketests
		assertThat(clientController).isNotNull();
		assertThat(freelancerController).isNotNull();
		assertThat(languageController).isNotNull();
		assertThat(offerController).isNotNull();
		assertThat(viewController).isNotNull();
		assertThat(userDetail).isNotNull();
	}

}
