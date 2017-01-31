package myowntest

import java.util.Date

import frdomain.mytest.{Account, AccountService, MoneyMarketAccount}
import org.scalactic.Fail
import org.scalatest.FunSuite

import scala.runtime.Nothing$
import scala.util.{Success, Try}

class MyOwnTest extends FunSuite {

  test("Interest Test") {
    val account: MoneyMarketAccount = MoneyMarketAccount("1234","rick",1,BigDecimal(100))
    object AccountService extends AccountService
    import AccountService._
    val date: Date = new Date
    val result: Try[BigDecimal] =calculateInterest[MoneyMarketAccount](account,date)
    assert(result == Success(BigDecimal(5)))
  }

}