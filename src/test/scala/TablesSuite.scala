
import org.scalatest._
import slick.dbio.{DBIOAction, NoStream}
import slick.driver.H2Driver.api._
import slick.jdbc.meta._
import slick.lifted.TableQuery

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.concurrent.ExecutionContext.Implicits.global


class TablesSuite extends FunSuite with BeforeAndAfter {
  val suppliers = TableQuery[Suppliers]
  val coffees = TableQuery[Coffees]

  var db: Database = _

  def dbRun[R](a: DBIOAction[R, NoStream, Nothing]): R = {
    Await.result(db.run(a), Duration.Inf)
  }

  def createSchema() = dbRun((suppliers.schema ++ coffees.schema).create)

  def insertSupplier(): Int = dbRun(suppliers += (101, "Acme, Inc.", "99 Market Street", "Groundsville", "CA", "95199"))

  before {
    db = Database.forURL("jdbc:h2:mem:test1", driver = "org.h2.Driver")
    db.createSession().conn.setAutoCommit(true)
  }

  test("Creating the Schema works") {
    createSchema()
    Thread.sleep(2*1000)

    Await.result(db.run(MTable.getTables(Some(""), Some(""), None, None)), Duration.Inf)

    val tables = dbRun(MTable.getTables)

    assert(tables.size == 2)
    assert(tables.count(_.name.name.equalsIgnoreCase("suppliers")) == 1)
    assert(tables.count(_.name.name.equalsIgnoreCase("coffees")) == 1)
  }

  test("Inserting a Supplier works") {
    createSchema()
    val insertCount = insertSupplier()
    assert(insertCount == 1)
  }

  test("Query Suppliers works") {
    createSchema()
    insertSupplier()
    val results = dbRun(suppliers.result)
    assert(results.size == 1)
    assert(results.head._1 == 101)
  }

  after {
    db.close()
  }

}