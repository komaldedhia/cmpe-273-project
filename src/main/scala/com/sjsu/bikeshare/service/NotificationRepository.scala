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

    var dbObject = MongoDBObject("ownerID" -> notification.ownerId, "requestorId" -> notification.requestorId, "RequestorNotificationSent" -> notification.RequestorNotificationSent, "CodeRecieved" -> notification.CodeRecieved, "OwnerApproves" -> notification.OwnerApproves,
    "fromDate" -> notification.fromDate,"toDate"->notification.toDate)

    MongoFactory.notificationCollection.insert(dbObject,WriteConcern.Safe)
      
    println("Saved")
    notification
  }

}