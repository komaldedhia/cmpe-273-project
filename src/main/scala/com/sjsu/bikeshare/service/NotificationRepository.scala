package com.sjsu.bikeshare.service

import com.sjsu.bikeshare.domain.Notification
import com.mongodb.util.JSON
import com.mongodb.casbah.Imports._
import com.mongodb.util.JSON
import java.util.{ List, ArrayList }
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;
import java.util.{ TimeZone, Formatter }

 object NotificationRepository {

      val dateFormat: DateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
      dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"))


def save(notification: Notification): Notification = {

    var dbObject = MongoDBObject("collName" -> "userid")
    val fieldsMDBO = MongoDBObject("seq" -> 1, "_id" -> 0)
    val counterRes = MongoFactory.counterCollection.findAndModify(dbObject, update = $inc("seq" -> 1), upsert = true, fields = fieldsMDBO, sort = null, remove = false, returnNew = true).get
    val id = counterRes.get("seq").asInstanceOf[Int]

    var dbObject1 = MongoDBObject("noteId"->id,"ownerId" -> notification.getOwnerId, "requesterId" -> notification.requestorId, 
    "fromDate" -> notification.fromDate,"toDate"->notification.toDate,"status" -> notification.status,"bikeId" -> notification.bikeId)

    MongoFactory.notificationCollection.insert(dbObject1,WriteConcern.Safe)
      
    println("Saved")
    notification
  }
      
    

}