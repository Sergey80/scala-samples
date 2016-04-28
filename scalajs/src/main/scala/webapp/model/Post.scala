package webapp.model

// http://stackoverflow.com/questions/36879109/upickle-read-from-scalajs-upickle-invaliddata-string-data-1

case class Post(userId: Int, id: Int, title: String, body: String)