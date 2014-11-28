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
  
  /* This method validates if the email and the password
   * entered during the login is vaid
   */
  def validateUser(userLogin:UserLogin):String={
      val q = MongoDBObject("_id"->0)
      val r = MongoDBObject("email"->userLogin.email);
     
      var returnValue:String=""
     val printUser = MongoFactory.userCollection.findOne(r,q)match
     {
     
     case Some(printUser) => {
       
        val originalPassword =printUser.get("password").toString()
        println(userLogin.password);
           if(userLogin.password.equalsIgnoreCase(originalPassword))
           {
             println("Success");
            returnValue= "Success"
           }
           else
           {
             println("Invalid User or password")
             returnValue="Invalid User or password"
           }
       }
  case None => 
       {
         println("In case none Invalid User or password")
         returnValue="Invalid User or password"
       }
    }
     returnValue
  }
  
}