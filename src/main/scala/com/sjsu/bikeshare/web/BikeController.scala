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
   model.addAttribute("userEmail", bike.userEmail)
   BikeRepository.InsertBikes(bike)
   "homepage"

}
 @RequestMapping(value=Array("/List"),method = Array(RequestMethod.POST))
  def userLoginForm( model:Model,@ModelAttribute bike:Bike) = {
      println("here I am in list bike api ")
   model.addAttribute("Bike", new Bike())
   model.addAttribute("userEmail", bike.userEmail)
   println("got user email before list bike page here"+bike.userEmail);
  "ListBike"
   }


     @RequestMapping(value = Array("/rent"), method = Array(RequestMethod.GET))
  def getRentForm(model: Model,@ModelAttribute bike:Bike) = {
   // added to get the user email
       model.addAttribute("bike", new Bike())
       model.addAttribute("userEmail", bike.userEmail)
    println("In rent")
    "rent"
  }
  
//shwetha cod e
   @RequestMapping(value = Array("/getAllBikes"),method = Array(RequestMethod.PUT))
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
//  BikeRepository.updateBikes(bike)
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
    def reviewBikes(@PathVariable bike_id:String,
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
		var bikeId=bike.bikeId
                val c = Calendar.getInstance()
                c.setTime(fromDate)
                c.add(Calendar.DATE,1)
                
                var newFromDate = dateFormat.format(c.getTime())
                println(newFromDate)
                bike.setFromDate(newFromDate) 
                
                //bike.setBikeId(bike.bikeId)
                //bike.setAccessories(bike.accessories)
              //BikeRepository.updateBikes(bikeId,bike)
              
              var notification:Notification = new Notification
              notification.setFromDate(bike.getFromDate)
              notification.setToDate(bike.getToDate)
              notification.setOwnerId(bike.getUserEmail)
              notification.setRequestorId(bike.getRequesterEmail)
            //have to setRequestorId
              //notification.setRequestorNotificationSent(true)
            
              NotificationRepository.save(notification)
              }
    
//Goudamy
//listing bikes
 @RequestMapping(value = Array("/getListedBike"), method = Array(RequestMethod.GET))
 def List(@ModelAttribute bike:Bike, model:Model) = {
       println("here I am ")
       val  email = "test1@gmail.com"
        println(email)
       val list =  BikeRepository.listingBikes(email,bike)       
       model.addAttribute("bike",list) 
       model.addAttribute("bike1", new Bike())
       println(list)
      "bikeList"
    }
  @RequestMapping(value = Array("/listing"), method = Array(RequestMethod.POST))
  def listingBike(@ModelAttribute @RequestBody  bike1:Bike, model:Model ) = {
       println("here I am ")
       println(bike1.bikeId)
     
   //Kiran plz start from here
       "homepage"  
    }
//Returning
 @RequestMapping(value = Array("/return"), method = Array(RequestMethod.GET))
 def returnBike(@ModelAttribute notification:Notification, model:Model) = {
       println("here I am ")
       val  email = "ram"
       val list =  BikeRepository.returnBikes(email,notification)
       val rented =  BikeRepository.rentedBikes(email,notification)
       model.addAttribute("notification",list)
       model.addAttribute("owner",rented)
       model.addAttribute("note", new Notification())
       println(list)
      "bikeReturn"
    }

 @RequestMapping(value = Array("/returning"), method = Array(RequestMethod.POST))
 def returningBike(@ModelAttribute @RequestBody  note:Notification, model:Model ) = {
       println("here I am ")
       println(note.bikeId)     
       val ret =  BikeRepository.returning(note)
       "homepage"  
    }


/**_---------------------------review bike starts here--------**/
//shwetha code 
@RequestMapping(value=Array("/review/{bike_id}"),method = Array(RequestMethod.GET))
def reviewBikeForm( @PathVariable bike_id:String,model:Model) = {
      print("-------------review bike id api called --------------")
      println(bike_id)
      var oldBike= BikeRepository.getBike(bike_id)
      println("old bike"+oldBike)
      //var bike = getBike(bike_id)
      //get bike from bike id
      model.addAttribute("oldBike", oldBike)
      model.addAttribute("review", new Review())
     // model.addAttribute("newBike", new Bike())
     // var newBike = new Bike()
      "review"
  } 

@RequestMapping(value = Array("/reviewBike"),method = Array(RequestMethod.POST))
@ResponseStatus(value = HttpStatus.CREATED)
//@ModelAttribute review: Review,model:Model @RequestBody newBike: Bike| @RequestBody review: Review
def reviewABike(@ModelAttribute review: Review,model:Model) = {
      println("---In update bike contorller---")
      println("bike id "+ review.bikeId)
      println("userEmail "+review.getUserEmail)
      println("comment "+review.comment)
      BikeRepository.addReviewToBike(review.bikeId, review)
      println("---end of bike contorller function ----")
      var userLogin = new UserLogin()
      model.addAttribute("userLogin", new UserLogin())
      "homepage"
}
/**_---------------------------review bike ends here--------**/

@RequestMapping(value = Array("/updateBike"),method = Array(RequestMethod.POST))
@ResponseStatus(value = HttpStatus.CREATED)
//@ModelAttribute newBike: Bike,model:Model
def updateBike(@RequestBody newBike: Bike) = {
      println("---In update bike contorller---")
      println("bike id "+newBike.bikeId)
      println("address "+newBike.address)
      println(" accessories"+newBike.accessories)
      println("userEmail "+newBike.getUserEmail)
      println("fromDate"+newBike.getFromDate)
      println("toDate"+newBike.getToDate)
      println("Bike code "+newBike.getBikeCode)
      BikeRepository.updateBikesv1(newBike.bikeId, newBike)
      println("---end of bike contorller function ----")
      //var userLogin = new UserLogin()
      //model.addAttribute("userLogin", userLogin)
     //"homepage"
      //newBike
}

@RequestMapping(value=Array("/update/{bike_id}"),method = Array(RequestMethod.GET))
def updateBikeForm( @PathVariable bike_id:String,model:Model) = {
      print("update bike id api called ")
      println(bike_id)
     var oldBike= BikeRepository.getBike(bike_id)
     // var bike = getBike(bike_id)
      //get bike from bike id
      model.addAttribute("oldBike", oldBike)
      model.addAttribute("newBike", new Bike())
     // var newBike = new Bike()
      "UpdateBike"
  }

}
