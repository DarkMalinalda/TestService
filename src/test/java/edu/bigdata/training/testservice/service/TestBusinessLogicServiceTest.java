package edu.bigdata.training.testservice.service;

import edu.bigdata.training.testservice.controller.model.Person;
import edu.bigdata.training.testservice.dao.TestServiceRepository;
import edu.bigdata.training.testservice.model.PersonEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestBusinessLogicServiceTest.TestBusinessLogicServiceTestConfiguration.class})
public class TestBusinessLogicServiceTest {

    @Autowired
    private TestBusinessLogicService testBusinessLogicService;

    @Autowired
    private TestServiceRepository testServiceRepository;

    @Test
    public void testCreate(){
        Person person = new Person("test");
		
        PersonEntity personEntity = testBusinessLogicService.processCreate(person);
		
        Assert.assertEquals(person.getName(), personEntity.getName());
        Mockito.verify(testServiceRepository, Mockito.times(1)).save(personEntity);
    }
	
	@Test
    public void testGet(){
        UUID id=UUID.randomUUID();
		
        PersonEntity personEntity  = testBusinessLogicService.processGet(id.toString());

        Assert.assertEquals("name", personEntity.getName());
        Mockito.verify(testServiceRepository, Mockito.times(1)).get(id);
    }
	
	@Test
    public void testGetAll(){
        List<PersonEntity> personEntityList = testBusinessLogicService.processGetAll();

        Assert.assertEquals("name1", personEntityList.get(0).getName());
        Assert.assertEquals("name2", personEntityList.get(1).getName());
        Mockito.verify(testServiceRepository, Mockito.times(1)).getAll();
    }

	@Test
    public void testUpdate(){
        UUID id=UUID.randomUUID();
        Person person = new Person("new name");

        PersonEntity personEntity = testBusinessLogicService.processUpdate(id.toString(), person);

        Assert.assertEquals(person.getName(), personEntity.getName());
		Assert.assertEquals(id, personEntity.getId());
        Mockito.verify(testServiceRepository, Mockito.times(1)).save(personEntity);
    }
	
	@Test
    public void testDel(){
        UUID id=UUID.randomUUID();
		
        testBusinessLogicService.processDel(id.toString());

        Mockito.verify(testServiceRepository, Mockito.times(1)).del(id);
    }
	

    @Configuration
    static class TestBusinessLogicServiceTestConfiguration {

        @Bean
        TestServiceRepository testServiceRepository() {
            TestServiceRepository testServiceRepository = mock(TestServiceRepository.class);
            when(testServiceRepository.get(any())).thenReturn(new PersonEntity("name"));
            when(testServiceRepository.getAll())
                    .thenReturn(Arrays.asList(new PersonEntity("name1"),new PersonEntity("name2")));
            return testServiceRepository;
        }

        @Bean
        TestBusinessLogicService testBusinessLogicService(TestServiceRepository testServiceRepository){
            return new TestBusinessLogicService(testServiceRepository);
        }
    }

}
