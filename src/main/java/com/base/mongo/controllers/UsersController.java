package com.base.mongo.controllers;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.mongo.config.exception.BusinessServiceException;
import com.base.mongo.models.User;
import com.base.mongo.services.IUserService;
import com.base.mongo.util.ErrorCode;
import com.base.mongo.util.ResponseUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/api/v1/user")
@Api(value="/api/v1/user",produces="application/json",consumes="application/json")
public class UsersController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsersController.class);
    
	@Autowired
	private IUserService service;
	
	/**
	 * Versión del controlador
	 * 
	 * @return
	 */
	@GetMapping("/version")
	public String version() {
		LOGGER.debug("REST request to get version");
		return "1.0";
	}
	
	@ApiOperation(value = "get usuarios", notes = "", response = User.class)
	@GetMapping(value = "/")
	public ResponseEntity<List<User>> getAll() throws BusinessServiceException {
		LOGGER.info("/user/ GET status=start");
		List<User> response;
		try {
			response = service.findAll();
		} catch (Exception e) {
			if (e instanceof BusinessServiceException) {
                throw e;
            } else {
                throw new BusinessServiceException(ErrorCode.GENERAL_ERROR_ON_SAVE, e);
            }
		}
		LOGGER.info("/user/ GET status=end");
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(response));
	}
	
	@ApiOperation(value = "get user by username", notes = "", response = User.class)
	@GetMapping(value = "/{username}")
	public ResponseEntity<User> getByUsername(@PathVariable String username) throws BusinessServiceException {
		LOGGER.info("/user/{username} GET status=start");
		User response;
		try {
			response = service.findByUsername(username);
		} catch (Exception e) {
			if (e instanceof BusinessServiceException) {
                throw e;
            } else {
                throw new BusinessServiceException(ErrorCode.GENERAL_ERROR_ON_SAVE, e);
            }
		}
		LOGGER.info("/user/{username} GET status=end");
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(response));
	}
	
	@ApiOperation(value = "get user by email", notes = "", response = User.class)
	@GetMapping(value = "/email/{email}")
	public ResponseEntity<User> getByEmail(@PathVariable String email) throws BusinessServiceException {
		LOGGER.info("/user/email/{email} GET status=start");
		User response;
		try {
			response = service.findByEmail(email);
		} catch (Exception e) {
			if (e instanceof BusinessServiceException) {
                throw e;
            } else {
                throw new BusinessServiceException(ErrorCode.GENERAL_ERROR_ON_SAVE, e);
            }
		}
		LOGGER.info("/user/email/{email} GET status=end");
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(response));
	}
	
}
