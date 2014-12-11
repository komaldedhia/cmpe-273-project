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
  
private var randomCode : String = ""
 
@RequestMapping(method = Array(RequestMethod.POST))
@ResponseStatus(value = HttpStatus.CREATED)
def createUser(@Valid @RequestBody user:User) = {     
   
 UserRepository.populateUser(user)
 
}
 
 @RequestMapping(value=Array("/signup"),method = Array(RequestMethod.GET))
  def SignUpForm( model:Model) = {
  model.addAttribute("user", new User())
  model.addAttribute("userLogin", new UserLogin())
  "SignUp"
  } 
 
 
 @RequestMapping(value=Array("/signupnow"),method = Array(RequestMethod.POST))
  def SignUpSubmit(@Valid user:User,bindingResult:BindingResult,model:Model,userLogin:UserLogin) = {
    if (bindingResult.hasErrors()) 
   {
      println("in here")
      "SignUp"
    }
   else
   {
      model.addAttribute("user",user)
      model.addAttribute("rcode",randomCode)
      userLogin.email=user.email
      userLogin.setName(user.getFirstName)
      model.addAttribute("userLogin",userLogin)
     println("randomcode: " +randomCode)
     println("user.getTwiliocode : " + user.getTwiliocode )
     println("user.getname : " + user.getFirstName )
     println("user.email : " + user.getEmail )
          if ( randomCode == user.getTwiliocode.toString ){
            createUser(user)
            "homepage"
          }
          else
          {
            println ("codes not matched")
            println("twiliocde: " +randomCode)
            println("user.getTwiliocode : " + user.getTwiliocode )
            model.addAttribute("user", new User())
            "SignUp"
           }
   }
  }  
   
  @RequestMapping(value=Array("/sendcode"),method = Array(RequestMethod.POST),produces = Array("application/json"))
 def sendSMS(@RequestBody contactNo:User,model:Model) = {
    val AUTH_TOKEN = "b62e99e1cc3899f53f48e8a5f89d1628"  
    val ACCOUNT_SID = "AC164368f1f5629e34ddb91d0378d9bd47" 
    var PHONE_NUMBER = contactNo.getContactNo
    
   val client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN) 
    val account = client.getAccount 
    val messageFactory = account.getMessageFactory 
    val params = new ArrayList[NameValuePair]() 
   //val tempCode = 5678
    val random  = new Random()
    val tempCode = random.nextInt(9999)
    randomCode = tempCode.toString()
   
    params.add(new BasicNameValuePair("To", PHONE_NUMBER)) 
    params.add(new BasicNameValuePair("From", "+13095175765"))
    params.add(new BasicNameValuePair("Body", "Congrats! Your code# is " +randomCode))
    val sms = messageFactory.create(params) 
     model.addAttribute("user", new User())
            "SignUp"
  }
 
  
 @RequestMapping(value=Array("/userlogin"),method = Array(RequestMethod.GET))
  def userLoginForm( model:Model) = {
   model.addAttribute("userLogin", new UserLogin())
   
  "login"}  
 
  
 @RequestMapping(value=Array("/userval"),method = Array(RequestMethod.POST))
  def getUser(@ModelAttribute @Valid userLogin:UserLogin,bindingResult: BindingResult,model:Model) = {
   if (bindingResult.hasErrors()) 
   {
      "login"
    }
   else
   {
      val userName=UserRepository.validateUser(userLogin)
      if (userName.equalsIgnoreCase("Invalid User or password"))   {
        println("Not a success case,so returning to login page again")
        model.addAttribute("userLogin", new UserLogin())
        "login"
       }
      else 
      {
        userLogin.setName(userName)
        println(" userName "+userName)
        model.addAttribute("userLogin", userLogin)
       "homepage"
      }
   }
  } 
   

@RequestMapping(value=Array("/{email}/bike"),method = Array(RequestMethod.GET))
def getBikes(@PathVariable email:String) = {
  println (email)
BikeRepository.getBikes(email)
}

@RequestMapping(value=Array("/dashboard"),method = Array(RequestMethod.POST))
  def dashboard(userLogin:UserLogin,model:Model) = 
  {
  model.addAttribute("userLogin",userLogin)
  println("email frm header"+userLogin.getEmail())
  "homepage"
   }  

@RequestMapping(value=Array("/bikeshare"),method = Array(RequestMethod.GET))
  def bikesharePageRedirect(model:Model) = 
  {
  "redirect:/bikeshare"
   }  
 
 
 
 

}
