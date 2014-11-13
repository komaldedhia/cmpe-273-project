package com.sjsu.bikeshare.service

import org.springframework.data.repository.CrudRepository
import com.sjsu.bikeshare.domain.Bike
import scala.collection.mutable._
import java.util.{ List, ArrayList }
import com.mongodb.casbah.Imports._
//import com.mongodb.casbah.
import com.mongodb.casbah.commons.Imports.DBObject 
import com.sjsu.bikeshare.exception.{InValidInput,MongoException}
import java.util.Date;
import java.util.{List,TimeZone}
import java.lang._
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.lang.Double


object BikeRepository {
  val dateFormat:DateFormat   = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"))
  
 
   def getAllBikes(latitude: String,longitude: String,bikeType: String
       ,range: String,fromDate: String,toDate: String) = {

    var bikeList: List[DBObject] = new ArrayList()
    var fromdate: Date = dateFormat.parse(fromDate)
    var todate: Date = dateFormat.parse(toDate)
    val long = Double.parseDouble(longitude);
    val lat = Double.parseDouble(latitude);
    val maxRange:Int =  range.toInt
    println("fromDate "+fromDate+" bikeType "+bikeType + "long "+long+" lat "+lat + " range "+ maxRange);
    val q1 = MongoDBObject("loc" -> MongoDBObject("$nearSphere" -> MongoDBObject( 
        "type" -> "Point","coordinates" ->  MongoDBList(long,lat)), "$maxDistance" -> maxRange)) 
      
    val query = ("fromDate" $lte fromdate) ++("toDate" $gte todate) ++ q1++( "bikeType" -> bikeType)
    //val dbObject = MongoDBObject("toDate" -> MongoDBObject("$gte"-> todate),"fromDate" ->MongoDBObject( "$lte" -> fromdate), "bikeType" -> bikeType,"loc" -> MongoDBObject("$nearSphere" -> MongoDBObject( 
       // "type" -> "Point","coordinates" ->  MongoDBList(long,lat)), "$maxDistance" -> range))
    val fieldObject = MongoDBObject("_id" -> 0)
    for (x <- MongoFactory.BikesCollection.find(query, fieldObject)) { bikeList.add(x) }
    bikeList
  
   
  }

 def InsertBikes(bike:Bike) = {

    var create_id :Int = 0; 
    var id : String = null
    val fetch = MongoDBObject("bike_id" -> "1")
    println(fetch)   
    val u1= MongoFactory.BikesCollection.findOne(fetch)
    println(u1)
    u1 match{
    case None => create_id = 1
    case Some(message) => 
         val doc =MongoFactory.BikesCollection.find().sort(Map("bike_id" -> -1)).limit(1)  

    for( doc1 <- doc) {
      println(doc1.get("bike_id"))
       //create_id =doc1.as[Int]("bike_id")    
      id = doc1.get("bike_id").toString()     
     }
     println(create_id) 
     create_id = id.toInt
     create_id = create_id +1; 
    }
    val bike_id = create_id.toString()
 
     println("create_id" + create_id)
      
     
     val bike_info = MongoDBObject("bikeId" -> bike_id,"address" -> bike.address, "accessories" -> bike.accessories,"fromDate" ->bike.fromDate,
      "toDate"->bike.toDate, "userEmail" -> bike.userEmail,"bikeCode"->bike.bikeCode)
      
     MongoFactory.BikesCollection.insert( bike_info )
     println("Saved")
     bike_info.toString()

 }
 
 def getBikes(email :String) ={  
   /* val q = MongoDBObject("_id"->0)
    val r = MongoDBObject("user_email" -> email)
    val printUser = coll.findOne(r,q).get
    println(printUser)
    printUser*/
   println(email)
    val fetch_user = MongoDBObject("user_email" -> email)
    println(fetch_user)
    val user_get=  MongoFactory.BikesCollection.findOne(fetch_user)
    println(user_get)
     println(email)
    /** for( info_details <- user_get) {
     val email = info_details.as[String]("user_email") 
     val password = info_details.as[String]("bike_id")
      println(email + password)
      }*/
    user_get.toString()
    
  } 
 def updateBikes(email:String,bike_id: String,bike:Bike)={
  
val dbObject = MongoDBObject("bike_id"->bike_id)

if(dbObject!=null)
{
val userUpdt= MongoFactory.BikesCollection.update(dbObject,MongoDBObject("bikeId" -> bike_id,
    "address" -> bike.address,
    "accessories" -> bike.accessories,
    "fromDate" ->bike.fromDate,
    "toDate" -> bike.toDate, 
    "userEmail" -> bike.userEmail,
    "bikeCode"->bike.bikeCode))
}
 val fetch_user = MongoDBObject("bike_id" -> bike_id)
 println(fetch_user)
 println(bike_id)
 val user_get=  MongoFactory.BikesCollection.findOne(fetch_user)
 println(user_get)
 user_get.toString()

 }
  }