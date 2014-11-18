package com.sjsu.bikeshare.web

import org.springframework.web.bind.annotation.RequestMapping
import com.sjsu.bikeshare.service._
import com.sjsu.bikeshare.domain.Bike
import com.sjsu.bikeshare.domain.Review
import com.sjsu.bikeshare.exception.UserNotFoundException
import org.springframework.http.{ ResponseEntity, HttpStatus }
import org.springframework.web.bind.annotation._
import org.springframework.context.annotation.{ ComponentScan, Configuration }
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import java.io._
import javax.validation.Valid
import javax.persistence.Entity
import org.springframework.web.context.request._
import java.util.{ List, ArrayList }


@RestController 
@RequestMapping(value = Array("/api/v1/bike"),consumes = Array("application/json"), produces = Array("application/json"))
class BikeController {

@RequestMapping(method = Array(RequestMethod.POST))
@ResponseStatus(value = HttpStatus.CREATED)
def createBikes(@Valid @RequestBody bike:Bike) = {
BikeRepository.InsertBikes(bike)
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
