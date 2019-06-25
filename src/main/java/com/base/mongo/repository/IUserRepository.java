package com.base.mongo.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.base.mongo.models.User;

public interface IUserRepository extends MongoRepository<User, String> {

	User findBy_id(ObjectId _id);
	User findByUsername(String username);
	User findByEmail(String email);
	
	@Query("{email:'?0'}")
	User findUserByEmail(String email);
	
}
