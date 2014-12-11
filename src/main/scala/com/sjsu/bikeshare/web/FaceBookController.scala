package com.sjsu.bikeshare.web

import javax.inject.Inject
import org.springframework.social.facebook.api.Facebook
import org.springframework.social.facebook.api.PagedList
import org.springframework.social.facebook.api.Post
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
//remove if not needed
import scala.collection.JavaConversions._
import com.sjsu.bikeshare.domain._
@Controller 
@RequestMapping(value = Array("/api/v1/connect/facebook"))
class FaceBookController@Inject
(private var facebook: Facebook) {
  

  @RequestMapping(method = Array( RequestMethod.GET))
  def helloFacebook(model: Model): String = {
    if (!facebook.isAuthorized) {
      return "redirect:/api/v1/users/userlogin"
    }
   var userLogin=new UserLogin()
   //userLogin.s
   println("HI")
   var email=facebook.userOperations().getUserProfile().getEmail();
   var name=facebook.userOperations().getUserProfile().getName();
   println(facebook.userOperations().getUserProfile().getEmail())
   println(facebook.userOperations().getUserProfile().getId())
   println(facebook.userOperations().getUserProfile().getName())
   // model.addAttribute(facebook.userOperations().getUserProfile)
   // val homeFeed = facebook.feedOperations().getHomeFeed
    //model.addAttribute("feed", homeFeed)
  
   	userLogin.setEmail(email)
   	userLogin.setName(name)
    model.addAttribute("userLogin", userLogin)
    "homepage"
  }
  
 }