package com.sjsu.bikeshare.service

import com.sjsu.bikeshare.domain.Notification
import com.mongodb.util.JSON
import com.mongodb.casbah.Imports._
import java.util.{ List, ArrayList }
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;
import java.util.{ TimeZone, Formatter }

 object NotificationRepository {

      val dateFormat: DateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
      dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"))


def save(notification: Notification): Notification = {

    var dbObject = MongoDBObject("ownerId" -> notification.getOwnerId, "requesterId" -> notification.requestorId, 
    "fromDate" -> notification.fromDate,"toDate"->notification.toDate,"status" -> notification.status,"bikeId" -> notification.bikeId)

    MongoFactory.notificationCollection.insert(dbObject,WriteConcern.Safe)
      
    println("Saved")
    notification
  }

}