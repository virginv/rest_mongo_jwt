package com.base.mongo.services;

import com.base.mongo.models.User;

public interface IUserService extends IGlobalService<User> {
	
	User findByUsername(String usuario);
	User findByEmail(String correo);
	
}
