package com.base.mongo.repository;

import static org.junit.Assert.assertNotNull;

import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.base.mongo.models.User;
import com.base.mongo.repository.IUserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTests {

	@Autowired
    private IUserRepository userRepository;
	
	private User user;
	
	@Autowired
	PasswordEncoder passwordEncoder;
    
	@Before
	public void createUser() {
		this.user = new User(new ObjectId(), "Virginia León", "virginv1", "mail@mail", "123");
		//$2a$10$25TrkBG.0CTxuHd3q5LZ..rNIkT/lQ62pKfpuHKuifN6J4y83yeKu
		System.out.println(passwordEncoder.encode("123456"));
		userRepository.save(this.user);
	}
	
	@After
	public void deletedUser() {
		userRepository.delete(this.user);
	}
	
	@Test
	public void usuarioExiste() {
		User user = userRepository.findByUsername("virginv1");	
		assertNotNull(user);
	}

}
