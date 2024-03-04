package com.october03.cafe.kiosk.payment.dto

data class ChargeBalanceDto(
  var authToken: String,
  val couponId: String,
  var token: String
)
