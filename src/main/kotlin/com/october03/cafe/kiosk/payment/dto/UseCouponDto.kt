package com.october03.cafe.kiosk.payment.dto

data class UseCouponDto(
  var authToken: String,
  val couponId: String
)
