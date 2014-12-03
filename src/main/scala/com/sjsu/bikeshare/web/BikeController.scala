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
import java.text.DateFormat
import java.util.Date
import java.util.Calendar
import java.text.SimpleDateFormat
import org.springframework.validation.BindingResult

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

     @RequestMapping(value = Array("/rent"), method = Array(RequestMethod.GET))
  def getRentForm(model: Model) = {
   // println("In rent "+ bike.getUserEmail)
       model.addAttribute("bike", new Bike())
    println("In rent")
    "rent"
  }
  
    @RequestMapping(method = Array(RequestMethod.PUT))
  @ResponseStatus(value = HttpStatus.CREATED)
  def getAllBikes(@Valid bike: Bike, bindingResult: BindingResult, model: Model) = {
    println("In get All bikes")
    if (bindingResult.hasErrors()) {
      "rent"
    } else {
      var bikeList = BikeRepository.getAllBikes(bike.getLatitude, bike.getLongitude, bike.getBikeType, bike.getSearchRange, bike.getFromDate, bike.getToDate)
      println("After get All bikes")
      model.addAttribute("bikeList", bikeList)
      model.addAttribute("userLatitude", bike.getLatitude)
      model.addAttribute("userLongitude", bike.getLongitude)
      model.addAttribute("userFromDate", bike.getFromDate)
      model.addAttribute("userToDate", bike.getToDate)
      model.addAttribute("rentedBike", new Bike())
      "distance"
    }

  }


@RequestMapping(value = Array("/bikeTypes"), method = Array(RequestMethod.GET))
 @ResponseBody
  def getAllBikeTypes() = {
       var bikeTypeList = BikeTypesRepository.getAllBikeTypes()
       bikeTypeList  
  }
 
@RequestMapping(value=Array("/{bike_id}"),method = Array(RequestMethod.PUT))
def updateBikes(@PathVariable email:String,@PathVariable bike_id:String,@RequestBody bike:Bike) = {
//BikeRepository.updateBikes(email,bike_id,bike) -- Commented by Komal due to giving compilation error
  BikeRepository.updateBikes(bike)
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

//Rent a Bike will have ownerId,requestorId,fromDate and toDate.
    //update the bike collection
    //update the notification collection
    @RequestMapping(value=Array("/rent"),method = Array(RequestMethod.PUT))
    def rentBike(@ModelAttribute bike:Bike,model:Model)=
              {
                println("I am in rentBike")
                val dateFormat:DateFormat   = new SimpleDateFormat("MM-dd-yyyy");
                var fromDate: Date = dateFormat.parse(bike.getToDate)
                println(fromDate)
                println(bike.bikeId)
                val c = Calendar.getInstance()
                c.setTime(fromDate)
                c.add(Calendar.DATE,1)
                
                var newFromDate = dateFormat.format(c.getTime())
                println(newFromDate)
                bike.setFromDate(newFromDate) 
                
                //bike.setBikeId(bike.bikeId)
                //bike.setAccessories(bike.accessories)
              BikeRepository.updateBikes(bike)
              
              var notification:Notification = new Notification
              notification.setFromDate(bike.getFromDate)
              notification.setToDate(bike.getToDate)
              notification.setOwnerId(bike.getUserEmail)
              notification.setRequestorId(bike.getRequesterEmail)
            //have to setRequestorId
              //notification.setRequestorNotificationSent(true)
            
              NotificationRepository.save(notification)
              }
    



}
