package com.sjsu.bikeshare.web

import java.io._
import com.sjsu.bikeshare.service._
import com.sjsu.bikeshare.domain._
import org.springframework.web.context.request._
import org.springframework.web.bind.annotation._
import javax.validation.Valid
import javax.persistence.Entity
import java.util.{ List, ArrayList }
import org.springframework.ui.Model
import com.sjsu.bikeshare.exception.UserNotFoundException
import org.springframework.http.{ ResponseEntity, HttpStatus }
import org.springframework.context.annotation.{ ComponentScan, Configuration }
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.stereotype.Controller

@Controller
@RequestMapping(value = Array("/api/v1/bike"))
class BikeController {
@RequestMapping(value=Array("/save"),method = Array(RequestMethod.POST))
def createBikes(@ModelAttribute bike:Bike, model:Model,@ModelAttribute userLogin:UserLogin) = {
   println("I am in save api ")
   println("got hidden value"+bike.userEmail);
   //model.addAttribute("userLogin",userLogin)
   println("bike values from html"+bike.longitude)
   println("bike date"+bike.fromDate)
   println("bike location"+bike.latitude)
  BikeRepository.InsertBikes(bike)
   "homepage"

}
 @RequestMapping(value=Array("/List"),method = Array(RequestMethod.POST))
  def userLoginForm( model:Model,@ModelAttribute bike:Bike) = {
      println("here I am in list api ")
  // println("got user email before list bike page here"+userLogin.email);
   model.addAttribute("Bike", new Bike())
   model.addAttribute("userEmail", bike.userEmail)
   println("got user email before list bike page here"+bike.userEmail);
 //model.addAttribute("userLogin",userLogin),@ModelAttribute userLogin:UserLogin
  "ListBike"
   }  

  
 @RequestMapping(value = Array("/{latitude}/{longitude}/{bikeType}/{range}/{fromDate}/{toDate}"),method = Array(RequestMethod.GET))
  @ResponseStatus(value = HttpStatus.CREATED  )
 def getAllBikes(@PathVariable("latitude") latitude: String,
		  		  @PathVariable("longitude") longitude: String,
		  		  @PathVariable("bikeType") bikeType: String,
		  		  @PathVariable("range") range: String,
		  		  @PathVariable("fromDate") fromDate: String,
		  		  @PathVariable("toDate") toDate: String) = {
  var bikeList = BikeRepository.getAllBikes(latitude,longitude,bikeType,range,fromDate,toDate)
    bikeList
  }


 @RequestMapping(value = Array("/bikeTypes"), method = Array(RequestMethod.GET))
 @ResponseBody
  def getAllBikeTypes() = {
       var bikeTypeList = BikeTypesRepository.getAllBikeTypes()
       bikeTypeList  
  }
 
@RequestMapping(value=Array("/{bike_id}"),method = Array(RequestMethod.PUT))
def updateBikes(@PathVariable email:String,@PathVariable bike_id:String,@RequestBody bike:Bike) = {
BikeRepository.updateBikes(email,bike_id,bike)
}

    //GET BIKE 
    @RequestMapping(value = Array("/{bike_id}"), method = Array(RequestMethod.GET))
    @ResponseBody
    def getBike(@PathVariable bike_id:String) = {
         var bike = BikeRepository.getBike(bike_id)
         println(bike)
         bike
    }


    // Put Review to Bike 
    @RequestMapping(value=Array("/{bike_id}/review"),method = Array(RequestMethod.PUT))
    def updateBikes(@PathVariable bike_id:String,
                    @RequestBody review:Review) = {
        var bike = BikeRepository.addReviewToBike(bike_id,review)
        bike
    }



}
