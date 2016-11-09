import slick.dbio.{DBIOAction, NoStream}
import slick.driver.H2Driver.api._

import scala.concurrent.ExecutionContext.Implicits.global
import slick.lifted.TableQuery

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object CaseClassMapping extends App {

  final def dbRun[R](a: DBIOAction[R, NoStream, Nothing]): R = Await.result(db.run(a), Duration.Inf)

  // the base query for the Users table
  val users = TableQuery[Users]

  val db = Database.forURL("jdbc:h2:mem:hello", driver = "org.h2.Driver")

  Await.result(db.run(DBIO.seq(
    // create the schema
    users.schema.create,
    // insert two User instances
    users ++= Seq(
      User("John Doe"),
      User("Fred Smith")
    )

  )), Duration.Inf)

  println(dbRun(users.result))
  
}

case class User(name: String, id: Option[Int] = None)

class Users(tag: Tag) extends Table[User](tag, "USERS") {
  // Auto Increment the id primary key column
  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  // The name can't be null
  def name = column[String]("NAME")//O.NotNull
  // the * projection (e.g. select * ...) auto-transforms the tupled
  // column values to / from a User
  def * = (name, id.?) <> (User.tupled, User.unapply)
}
