package com.october03.cafe.kiosk.payment.dto

import java.time.LocalDateTime

data class Coupon(
  val id: String,
  val price: Long,
  val isUsed: Boolean,
  val issuedAt: LocalDateTime
)
