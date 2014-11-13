package com.sjsu.bikeshare.service

import com.mongodb.util.JSON
import com.mongodb.casbah.Imports._
import java.util.{ List, ArrayList }

 object BikeTypesRepository {

      

     def getAllBikeTypes() = {
      println("I am here")
      
     var bikeTypeList:List[DBObject]= new ArrayList()


     var dbObject = MongoDBObject()
     val fieldObject = MongoDBObject("_id" -> 0,"type"->1)
     for(x <- MongoFactory.bikeTypeCollection.find(dbObject,fieldObject)) 
      { bikeTypeList.add(x)}
   
    
    println(bikeTypeList)

    bikeTypeList
     }
  
}


