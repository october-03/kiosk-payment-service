package com.october03.cafe.kiosk.payment.dto

import java.time.LocalDateTime

data class User(
  val id: Long,
  val name: String,
  val email: String,
  val password: String,
  val phoneNumber: String,
  val authToken: String,
  val fcmToken: String,
  val createdAt: LocalDateTime
)
