package com.sjsu.bikeshare.exception

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST , reason = "No such Routing No.")
case class InValidInput (rn:String) extends 
RuntimeException ("Invalid Routing Number or Account Name : "+rn)