package com.sjsu.bikeshare.service

import org.springframework.data.repository.CrudRepository
import com.sjsu.bikeshare.domain.Bike
import com.sjsu.bikeshare.domain.Notification
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
import com.sjsu.bikeshare.domain.Review;

object BikeRepository {
  val dateFormat:DateFormat   = new SimpleDateFormat("MM-dd-yyyy");
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

//yet to fix generating sequential bikeid's
 def InsertBikes(bike:Bike) = {

   val formatter:DateFormat = new SimpleDateFormat("yyyy-MM-dd")
   formatter.setTimeZone(TimeZone.getTimeZone("GMT"))
   
   val frmdate:Date = formatter.parse(bike.fromDate)
   val todate:Date = formatter.parse(bike.toDate)
   val longitude=Double.parseDouble(bike.longitude)
   val latitude=  Double.parseDouble(bike.latitude)
    
    /*var create_id :Int = 0; 
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
 
     println("create_id" + create_id)*/
    
    
     var dbObject = MongoDBObject("bikeId" -> "1")
     val fieldsMDBO = MongoDBObject("seq" -> 1, "_id" -> 0)
     val counterRes = MongoFactory.BikesCollection.findAndModify(dbObject, update = $inc("seq" -> 1), 
       upsert = true, fields = fieldsMDBO, sort = null, remove = false, returnNew = true).get
   
   val id = counterRes.get("seq").asInstanceOf[Int].toString()
   bike.setBikeId(id) 
     println("bikeid is"+bike.getBikeId())
 
    val loc=MongoDBObject("geometry" ->MongoDBObject("type" -> "Point","coordinates" ->(GeoCoords(longitude,latitude))))
     println("this is geosphere "+loc)
     
     val bike_info = MongoDBObject("bikeId" -> bike.bikeId,"userEmail" -> bike.userEmail, "accessories" -> bike.accessories,"address" -> bike.address,
                  "bikeType"->bike.bikeType,"description"->bike.description,"fromDate" ->frmdate,
                   "toDate"->todate,"bikeCode"->bike.bikeCode,"loc"->MongoDBObject("type" -> "Point","coordinates" ->(GeoCoords(longitude,latitude))))
      
    MongoFactory.BikesCollection.insert(bike_info)
    println("Bike Listed successfully and added to db "+bike_info.toString())
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
 
 //removed the param email id as user will have logged in user id
 def updateBikes(bike:Bike)={
  
val dbObject = MongoDBObject("bike_id"->bike.getBikeId)

if(dbObject!=null)
{
val userUpdt= MongoFactory.BikesCollection.update(dbObject,MongoDBObject("bikeId" -> bike.bikeId,
    "address" -> bike.address,
    "accessories" -> bike.accessories,
    "fromDate" ->bike.fromDate,
    "toDate" -> bike.toDate, 
    "userEmail" -> bike.userEmail,
    "bikeCode"->bike.bikeCode))
}
 /*val fetch_user = MongoDBObject("bike_id" -> bike_id)
 println(fetch_user)
 println(bike_id)
 val user_get=  MongoFactory.BikesCollection.findOne(fetch_user)
 println(user_get)
 user_get.toString()*/

 }

//Get a particular bike
def getBike(bike_id: String) = {
    println("Shwetha, Trying to get a bike")
    val bikeQuery = MongoDBObject("bikeId"->bike_id)
    val bikeObj = MongoFactory.BikesCollection.findOne(bikeQuery).get
    bikeObj
}


//add review to bike
def addReviewToBike(bike_id: String, review:Review) = {
    val fetch_bike = MongoDBObject("bikeId" -> bike_id)
    val bike =  MongoFactory.BikesCollection.findOne(fetch_bike).get
    var newReviews : String = ""
    
    /*
    val myreviews = MongoFactory.BikesCollection.findOne(fetch_bike, params).get("bikeReviews").as[String]
    println(bike.as[String]("bikeReviews"))
    var params = MongoDBObject("_id" -> 0, "bikeReviews" -> 1)
    println("printing inside for loop")
    
    var myreviews = MongoFactory.BikesCollection.findOne(fetch_bike, params).get("bikeReviews")
    for( r <- myreviews) {
        println(r)
    } 
    println("sh code")
    println(myreviews)
    */
    if (bike != null) {
        var params = MongoDBObject("_id" -> 0, "bikeReviews" -> 1)
        val oldReviews = MongoFactory.BikesCollection.findOne(fetch_bike, params).get("bikeReviews").toString()
        newReviews = newReviews.concat(review.toString())
        newReviews = newReviews.concat(oldReviews)
        println("New reviews for bike " + bike_id + newReviews)
        val userUpdt= MongoFactory.BikesCollection.update(fetch_bike, MongoDBObject(
            "bikeId" -> bike_id,
            "address" -> bike.get("address"),
            "accessories" -> bike.get("accessories"),
            "fromDate" ->bike.get("fromDate"),
            "toDate" -> bike.get("toDate"), 
            "userEmail" -> bike.get("userEmail"),
            "bikeCode"->bike.get("bikeCode"),
            "bikeReviews" -> newReviews
            ))
        
    }
    getBike(bike_id)
}
//Goudamy


def rentedBikes(email : String,notification:Notification) ={  
    var list:List[DBObject] = new ArrayList()
    val fetch_user = MongoDBObject("ownerId" -> email ,"status" -> "0")
    println(fetch_user)
    val user_get=  MongoFactory.notificationCollection.find(fetch_user)
    for(doc <- user_get) {
      list.add(doc)
          }
    println(list)
    list
  }  
 
 def returnBikes(email : String,notification:Notification) ={  
    var list:List[DBObject] = new ArrayList()
    val fetch_user = MongoDBObject("requesterId" -> email ,"status" -> "0")
    println(fetch_user)
    val user_get=  MongoFactory.notificationCollection.find(fetch_user)
    for(doc <- user_get) {
      list.add(doc)
          }
    println(list)
    list
  } 
 
  def listingBikes(email : String,bike:Bike) ={  
    var list:List[DBObject] = new ArrayList()
    println(email+"i have come a long way")
    val fetch_user = MongoDBObject("userEmail" -> email )
    println(fetch_user)
    val user_get=  MongoFactory.BikesCollection.find(fetch_user)
    for(doc <- user_get) {
      list.add(doc)
          }
    println(list)
    list
  } 
 
 def returning(note:Notification) ={  
   println(note.bikeId)
   println(note.status)
   println(note.fromDate)
   println(note.toDate)
   /*val initial = MongoDBObject("bikeId"-> note.bikeId)
   println(initial)
   val result = MongoFactory.notificationCollection.update(MongoDBObject("bikeId"-> note.bikeId), $set("status" -> "1") )
   println(result)*/
   val query = MongoDBObject("bikeId"-> note.bikeId)
   val update = $set("status" ->"1")
   val res1 = MongoFactory.notificationCollection.findAndModify(query = query, update = update)
   println("findAndModify: " + res1)
  } 
  }
