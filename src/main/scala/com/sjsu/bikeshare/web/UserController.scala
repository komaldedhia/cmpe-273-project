package com.sjsu.bikeshare.web;


import com.sjsu.bikeshare.domain._
import com.sjsu.bikeshare.service._
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.stereotype.Controller
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
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.beans.factory.annotation.Autowired
import com.twilio.sdk.resource.instance.Account 
import com.twilio.sdk.TwilioRestClient 
import com.twilio.sdk.TwilioRestException 
import com.twilio.sdk.resource.factory.MessageFactory 
import com.twilio.sdk.resource.instance.Message 
import org.apache.http.NameValuePair 
import org.apache.http.message.BasicNameValuePair
import javax.servlet.http.HttpSession
import java.util._
import java.lang._

@Controller 
@RequestMapping(value = Array("/api/v1/users"))
class UserController {
  

private var UserObj: User = new User()
private var twilioCode : String = ""
 
@RequestMapping(method = Array(RequestMethod.POST))
@ResponseStatus(value = HttpStatus.CREATED)
def createUser(@Valid @RequestBody user:User) = {     
   
 UserRepository.populateUser(user)
 
}
/*** Kokil Awasthi ***/
 @RequestMapping(value=Array("/signup"),method = Array(RequestMethod.GET))
  def SignUpForm( model:Model) = {
  model.addAttribute("User", new User())
  "SignUp"
  } 
 
 
 @RequestMapping(value=Array("/signupnow"),method = Array(RequestMethod.POST))
  def SignUpSubmit(@ModelAttribute user:User,@ModelAttribute code:String,model:Model) = {
  model.addAttribute("User", user)
  //model.addAttribute("code",1234)
  
 println("twiliocde: " +twilioCode)
 println("user.getTwiliocode : " + user.getTwiliocode )
 println("user.getname : " + user.getFirstName )
 println("user.email : " + user.getEmail )
  if ( twilioCode == user.getTwiliocode.toString ){
    createUser(user)
    "reservations"
  }
  else
  {
  "redirect:/error"
  }
  }  
  
 
  @RequestMapping(value=Array("/sendcode"),method = Array(RequestMethod.POST))
 def sendSMS(@RequestBody contactNo:User) = {
    val AUTH_TOKEN = "b62e99e1cc3899f53f48e8a5f89d1628"  
    val ACCOUNT_SID = "AC164368f1f5629e34ddb91d0378d9bd47" 
    var PHONE_NUMBER = contactNo.getContactNo
    
   val client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN) 
    val account = client.getAccount 
    val messageFactory = account.getMessageFactory 
    val params = new ArrayList[NameValuePair]() 
    val tempCode = 5678
    twilioCode = tempCode.toString()
   
    params.add(new BasicNameValuePair("To", PHONE_NUMBER)) 
    params.add(new BasicNameValuePair("From", "+13095175765"))
    params.add(new BasicNameValuePair("Body", "Congrats! Your code# is " +tempCode))
    val sms = messageFactory.create(params) 
    
   "redirect:/Signup"
 }
 
 @RequestMapping(value=Array("/userlogin"),method = Array(RequestMethod.GET))
  def userLoginForm( model:Model) = {
   model.addAttribute("userLogin", new UserLogin())
  "login"}  
 
  
 @RequestMapping(value=Array("/userval"),method = Array(RequestMethod.POST))
  def getUser(@ModelAttribute userLogin:UserLogin,model:Model) = {
 
  if (UserRepository.validateUser(userLogin).equalsIgnoreCase("Success"))
   {
    model.addAttribute("userLogin", userLogin)
   "homepage"
   }
  else 
  {
    println("Not a success case,so returning to login page again")
    model.addAttribute("userLogin", new UserLogin())
    "login"
        
  }
  } 
   
 
@RequestMapping(value=Array("/{email}/bike"),method = Array(RequestMethod.GET))
def getBikes(@PathVariable email:String) = {
  println (email)
BikeRepository.getBikes(email)
}
   
}
