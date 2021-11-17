package hh.swd20.courseproject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import hh.swd20.courseproject.domain.Language;
import hh.swd20.courseproject.domain.LanguageRepository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class LanguageRepositoryTest {
	
	@Autowired
	private LanguageRepository languageRepository;
	
	
	@Test
	public void findByLanguageName() {
		
		Language language = new Language("French");
		languageRepository.save(language);
		
		assertThat(language.getLanguageId()).isEqualTo(languageRepository.findByLanguageName("French").getLanguageId());
	}

	@Test
	public void createNewLanguage() {
		
		Language language = new Language("French");
		languageRepository.save(language);
		assertThat(language.getLanguageId()).isNotNull();		
	}
	
	@Test
	public void updateLanguage() {
		
		Language language = new Language("French");
		languageRepository.save(language);
		Language toUpdate = languageRepository.findById(language.getLanguageId()).get();
		toUpdate.setLanguageName("Italian");
		languageRepository.save(toUpdate);
		assertThat(languageRepository.findById(language.getLanguageId()).get().getLanguageName().equals("Italian"));
	}
	
	@Test
	public void deleteLanguage() {
		
		Language language = new Language("French");
		languageRepository.save(language);
		
		List<Language> numberOfBefore = new ArrayList<Language>();
		numberOfBefore.add(languageRepository.findById(language.getLanguageId()).get());
		
		languageRepository.deleteById(language.getLanguageId());
		
		List<Language> numberOfAfter = new ArrayList<Language>();
		
		for (Language toCheck : languageRepository.findAll()) {
			if (toCheck.getLanguageId() == language.getLanguageId()) {
				numberOfAfter.add(toCheck);
			}
		}
		assertThat(numberOfBefore.size()).isGreaterThan(0);
		assertThat(numberOfAfter.isEmpty());
	}

}
