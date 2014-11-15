package com.sjsu.bikeshare.web;


import com.sjsu.bikeshare.domain._
import com.sjsu.bikeshare.service._
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.{ ResponseEntity, HttpStatus }
import org.springframework.web.bind.annotation._
import org.springframework.context.annotation.{ ComponentScan, Configuration }
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import java.io._
import javax.validation.Valid
import javax.persistence.Entity
import org.springframework.web.context.request._
import java.util.{ List, ArrayList }
import com.mongodb.casbah.Imports._
import com.sjsu.bikeshare.service.UserRepository

@RestController 
@RequestMapping(value = Array("/api/v1/users"), consumes = Array("application/json"), produces = Array("application/json"))
class UserController {

@RequestMapping(method = Array(RequestMethod.POST))
@ResponseStatus(value = HttpStatus.CREATED)
def createUser(@Valid @RequestBody user:User) = {     
   
 UserRepository.populateUser(user)
 
}
  
 @RequestMapping(value=Array("/userval"),method = Array(RequestMethod.POST))
  def getUser(@Valid @RequestBody user:UserLogin) = {
    
    UserRepository.validateUser(user)      
  }  
   
@RequestMapping(value=Array("/{email}/bike"),method = Array(RequestMethod.GET))
def getBikes(@PathVariable email:String) = {
  println (email)
BikeRepository.getBikes(email)
}

	  
}
