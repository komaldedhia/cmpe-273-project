package com.sjsu.bikeshare.service
import java.util.Calendar
import java.util.Date
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.TimeZone
import com.mongodb.casbah.MongoClient
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.DBObject
import com.mongodb.casbah.commons._
import com.sjsu.bikeshare.domain._
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus

object UserRepository {

  def populateUser(user:User) = {
    
     val today = Calendar.getInstance().getTime()
     val dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
     val formattedDate:String =dateFormat.format(today)
     val random= (Math.round(Math.random() * 89999) + 10000).toString()
     
      val userObj=MongoDBObject("_id"->user.userId,"first_name"->user.firstName,"last_name"->user.lastName,"email"->(user.email),"password"->(user.password),"contact_no"->(user.contactNo),"created_at"->(formattedDate),"updated_at"->(formattedDate))
      MongoFactory.userCollection.save(userObj) 
      val q = MongoDBObject("_id"->0)
      val r = MongoDBObject("email"->user.email)
      println("in here2")
      val printUser = MongoFactory.userCollection.findOne(r,q).get
      printUser
    }
  
  def validateUser(userLogin:UserLogin)={
      val q = MongoDBObject("_id"->0)
      val r = MongoDBObject("email"->userLogin.email)
      val printUser = MongoFactory.userCollection.findOne(r,q).get
      val originalPassword =printUser.get("password").toString()
      if(printUser !=null)
      {
       if(userLogin.password.equalsIgnoreCase(originalPassword))
       {
          new ResponseEntity(HttpStatus.OK)
       }
      }
  }
  
}