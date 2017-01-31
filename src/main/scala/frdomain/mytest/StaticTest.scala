package frdomain.mytest

import java.util.Date

import cats.Id
import cats.data.{Kleisli, Reader}

import scala.util.{Success, Try}


trait Account {
  def number: String
  def name: String
  def money: Int
  //..
}
case class CheckingAccount(number:String,name:String,money:Int) extends Account

trait InterestBearingAccount extends Account {
  def rateOfInterest: BigDecimal
}
case class SavingsAccount(number:String,name:String,money:Int,rateOfInterest: BigDecimal) extends InterestBearingAccount

case class MoneyMarketAccount(number:String,name:String,money:Int,rateOfInterest: BigDecimal) extends InterestBearingAccount {
  type DateRange = Date
  def calculateInterest[A <: InterestBearingAccount](account: A, period: DateRange) = {

  }
}

trait AccountService {
  type DateRange = Date
  def calculateInterest(account: Account,
                                                     period: DateRange): Try[BigDecimal] = {
    def calculateInterest() : Reader[Account,BigDecimal] = Reader{
      case account:Account =>  account.money * 2
    }

    def calculateInterest2() : Reader[Account,BigDecimal] = Reader{
      case account:Account =>  account.money * 3
    }

    val interest: Kleisli[Id, Account, BigDecimal] = for{
      a <- calculateInterest()
      b <- calculateInterest2()
    } yield(a+b)

    val result: Id[BigDecimal] = interest.run(account)
    Success(result)
  }
}


