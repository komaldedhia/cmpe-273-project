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
import java.util.TimeZone

@Controller
@RequestMapping(value = Array("/api/v1/bike"))
class BikeController {
@RequestMapping(value=Array("/save"),method = Array(RequestMethod.POST))
def createBikes(@Valid bike:Bike,bindingResult:BindingResult, userLogin: UserLogin, model: Model) = {
  if (bindingResult.hasErrors()) 
   {
    println("yup have errors")
      "ListBike"
   }
  else
  {
   println("I am in save api")
  BikeRepository.InsertBikes(bike, userLogin)
   println("got hidden value"+userLogin.getEmail());
   model.addAttribute("userLogin", userLogin)
   "homepage"
  }

}
 @RequestMapping(value=Array("/List"),method = Array(RequestMethod.POST))
  def userLoginForm( model:Model,@ModelAttribute userLogin:UserLogin) = {
      println("here I am in list bike api ")
   model.addAttribute("bike", new Bike())
    model.addAttribute("userLogin", userLogin)
   println("got user email before list bike page here"+userLogin.getEmail());
  "ListBike"
   }


   @RequestMapping(value = Array("/rent"), method = Array(RequestMethod.PUT))
  def getRentForm(@ModelAttribute userLogin:UserLogin,model: Model) = {
   // added to get the user email
       println("userLogin "+userLogin.getEmail)
       model.addAttribute("rentBike", new RentBike())
       model.addAttribute("userLogin", userLogin)
    println("In rent")
    "rent"
  }
  
@RequestMapping(method = Array(RequestMethod.PUT))
  @ResponseStatus(value = HttpStatus.CREATED)
  def getAllBikes(@Valid rentBike: RentBike,bindingResult: BindingResult, userLogin: UserLogin, model: Model) = {
    println("In get All bikes")
    if (bindingResult.hasErrors()) {
      // model.addAttribute("rentBike", new RentBike())
       //model.addAttribute("userLogin", userLogin)
      "rent"
    } else {
      var bikeList = BikeRepository.getAllBikes(rentBike.getLatitude, rentBike.getLongitude, rentBike.getBikeType, rentBike.getSearchRange, rentBike.getFromDate, rentBike.getToDate)
      println("After get All bikes")
     // println("User "+userLogin.getEmail)
      model.addAttribute("bikeList", bikeList)
      model.addAttribute("userLatitude", rentBike.getLatitude)
      model.addAttribute("userLongitude", rentBike.getLongitude)
      model.addAttribute("userFromDate", rentBike.getFromDate)
      model.addAttribute("userToDate", rentBike.getToDate)
      model.addAttribute("rentedBike", new RentBike())
      model.addAttribute("userLogin", userLogin)
      "distance"
    }

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
    @RequestMapping(value=Array("/rent"),method = Array(RequestMethod.POST))
    def rentBike(rentedBike:RentBike,userLogin: UserLogin,model:Model)=
              {
                println("I am in rentBike")
             
               val formatter:DateFormat = new SimpleDateFormat("yyyy-MM-dd")
              
                  println("Input To date " + rentedBike.getToDate)
                  println("Input From date " + rentedBike.getFromDate)
    
                val c = Calendar.getInstance()
                c.setTime(formatter.parse(rentedBike.getToDate))
                c.add(Calendar.DATE,1)
                var newFromDate = formatter.format(c.getTime()); 
                println("newFromDate "+newFromDate)
                println("bike code "+rentedBike.getBikeCode )
               
                BikeRepository.updateRentBikes(rentedBike.getBikeId,newFromDate)
              
              var notification:Notification = new Notification
              notification.setFromDate(rentedBike.getFromDate)
              notification.setToDate(rentedBike.getToDate)
              notification.setOwnerId(rentedBike.getUserEmail)
              notification.setRequestorId(userLogin.getEmail)
              notification.setStatus("0")
              notification.setBikeId(rentedBike.getBikeId)
           
            
              NotificationRepository.save(notification)
               model.addAttribute("userLogin", userLogin)
               model.addAttribute("bikecode", rentedBike.bikeCode)
               model.addAttribute("code","alert")
               "homepage"
              }
    

 @RequestMapping(value = Array("/getListedBike"), method = Array(RequestMethod.POST))
 def List(model:Model,@ModelAttribute userLogin: UserLogin) = {
       println("here I am ")
       val  email = userLogin.email
       println(email)
       val list =  BikeRepository.listingBikes(email)
       val rented =  BikeRepository.rentedBikes(email)
       model.addAttribute("bike",list) 
       model.addAttribute("owner",rented)
       model.addAttribute("userLogin", userLogin)
       model.addAttribute("bike1", new Bike())
       //println(list)
       //println(rented)
      "bikeList"
    }
  
@RequestMapping(value = Array("/return"), method = Array(RequestMethod.POST))
 def returnBike(model:Model,@ModelAttribute userLogin: UserLogin) = {
       println("here I am ")
       val  email = userLogin.getEmail().toString()
       val list =  BikeRepository.returnBikes(email)
       model.addAttribute("notification",list)
       model.addAttribute("userLogin", userLogin)
       model.addAttribute("note", new Notification())
       model.addAttribute("mode",1)
       //println(list)
      "homepage"
    }

@RequestMapping(value = Array("/returning"), method = Array(RequestMethod.POST))
def returningBike(note:Notification,userLogin:UserLogin, model:Model ) = {
      println("In Returning")
      val  email = userLogin.getEmail()
      println("Note Id "+note.noteId)
      println("loaded from returning"+userLogin.getEmail())
     
       
      val ret =  BikeRepository.returning(note)
       var sdfDate = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
      sdfDate.setTimeZone(TimeZone.getTimeZone("GMT"))
      var now = new Date();
      var strDate = sdfDate.format(now);
      println("In Returning Date "+strDate);
       BikeRepository.updateReturnBikes(note.getNoteId,strDate)
       val list =  BikeRepository.returnBikes(email)
      model.addAttribute("notification",list)
       model.addAttribute("note",new Notification())
      model.addAttribute("userLogin", userLogin)
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


@RequestMapping(value=Array("/update/{bike_id}"),method = Array(RequestMethod.GET))
def updateBikeForm( @PathVariable bike_id:String,model:Model) = {
      print("update bike id api called ")
      println(bike_id)
     var oldBike= BikeRepository.getBike(bike_id)
     
      val df:DateFormat = new SimpleDateFormat("MM-dd-yyyy'T'HH:mm:ss.SSSZ");  
     val frm = df.format(oldBike.get("fromDate"));    
     val to = df.format(oldBike.get("toDate"))
     val fromResult:String = frm.substring(0, 10)
     val toResult:String = to.substring(0,10)
      oldBike.put("fromDate",fromResult)
      oldBike.put("toDate",toResult)
      model.addAttribute("oldBike", oldBike)
      model.addAttribute("newBike", new Bike())
      "UpdateBike"
  }

@RequestMapping(value = Array("/listing"), method = Array(RequestMethod.POST))
  def listingBike(@ModelAttribute @RequestBody  bike1:Bike, model:Model,userLogin: UserLogin) = {
       
            
      var oldBike= BikeRepository.getBike(bike1.bikeId)
      val df:DateFormat = new SimpleDateFormat("MM-dd-yyyy'T'HH:mm:ss.SSSZ");  
     val frm = df.format(oldBike.get("fromDate"));    
     val to = df.format(oldBike.get("toDate"))
     val fromResult:String = frm.substring(0, 10)
     val toResult:String = to.substring(0,10)
      oldBike.put("fromDate",fromResult)
      oldBike.put("toDate",toResult)
      model.addAttribute("oldBike", oldBike)
      model.addAttribute("userLogin",userLogin)
      model.addAttribute("newBike", new Bike())
      "UpdateBike"
    }

@RequestMapping(value = Array("/userUpdateBike"),method = Array(RequestMethod.POST))
def userUpdateBike(@ModelAttribute newBike:Bike,model:Model,userLogin:UserLogin) = {
      
      BikeRepository.userUpdateBike(newBike,userLogin)
      model.addAttribute("userLogin", userLogin)
      "homepage"
}


}
