package com.sjsu.bikeshare.service
import com.mongodb.casbah.MongoCollection
import com.mongodb.casbah.MongoConnection
import com.mongodb.casbah.MongoConnection._
import com.mongodb.casbah.{MongoClientURI,MongoClient}

object MongoFactory {

private val DATABASE = "bikeshare"

private val USER = "users"
private val BIKES = "bikes"
private val BikeTypes = "bikeTypes"
private val Notification = "notification"
private val COUNTERS = "counters"
  
val uriString = "mongodb://cmpe273_team12:Cmpe273team@ds053090.mongolab.com:53090/bikeshare"
val userName = "cmpe273_team12"
val passWord= "Cmpe273team"

val uri = MongoClientURI(uriString)
val mongoClient = MongoClient(uri)
val dbName = mongoClient(DATABASE)
dbName.authenticate(userName,passWord )

  val userCollection = dbName(USER)
 val BikesCollection = dbName(BIKES)
 val bikeTypeCollection = dbName(BikeTypes)
 val notificationCollection = dbName(Notification)
 val counterCollection = dbName(COUNTERS)
 
 
/*val userCollection = connection(USER)
 val BikesCollection = connection(BIKES)
 val bikeTypeCollection = connection(BikeTypes)
 val notificationCollection = connection(Notification)*/


}