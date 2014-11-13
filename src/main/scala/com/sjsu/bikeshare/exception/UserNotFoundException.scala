package com.sjsu.bikeshare.exception

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No such User Id")
case class UserNotFoundException (userId:String) extends 
RuntimeException ("User : "+userId + " not found")

