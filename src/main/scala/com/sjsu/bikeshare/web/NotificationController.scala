package com.sjsu.bikeshare.web;


import com.sjsu.bikeshare.domain.Notification
import com.sjsu.bikeshare.service.NotificationRepository
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
import java.util.Map 
import java.util.HashMap 
import com.twilio.sdk.resource.instance.Account 
import com.twilio.sdk.TwilioRestClient 
import com.twilio.sdk.TwilioRestException 
import com.twilio.sdk.resource.factory.MessageFactory 
import com.twilio.sdk.resource.instance.Message 
import org.apache.http.NameValuePair 
import org.apache.http.message.BasicNameValuePair

    

@RestController 
@RequestMapping(value = Array("/api/v1/users"), consumes = Array("application/json"), produces = Array("application/json"))
class NotificationController {

//3.5 Post
 // @ResponseStatus(value = HttpStatus.CREATED  )
  @RequestMapping(value = Array("/notify"),method =Array(RequestMethod.POST))
  def saveNotification(@RequestBody @Valid notification: Notification): Notification = {

  	 
    var newNotification: Notification = NotificationRepository.save(notification)
    newNotification
  }
	  

@RequestMapping(value = Array("/notify"),method =Array(RequestMethod.PUT)) 
 @ResponseBody 
    def notifyThroughTwilio()= {  
    val ACCOUNT_SID = "AC164368f1f5629e34ddb91d0378d9bd47" 
    val AUTH_TOKEN = "b62e99e1cc3899f53f48e8a5f89d1628"   
    val client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN) 
    val account = client.getAccount 
    val messageFactory = account.getMessageFactory 
    val params = new ArrayList[NameValuePair]() 
    params.add(new BasicNameValuePair("To", "+13097497022")) 
    params.add(new BasicNameValuePair("From", "+13095175765"))
    params.add(new BasicNameValuePair("Body", "Congrats! Your request is approved"))
 
    val sms = messageFactory.create(params) 
    "A message has been sent to the requestor."
    //CodeRecieved = true
    //OwnerApproves = true
    }
}
