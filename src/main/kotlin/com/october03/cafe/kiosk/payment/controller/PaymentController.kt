package com.october03.cafe.kiosk.payment.controller

import com.october03.cafe.kiosk.payment.dto.ChargeBalanceDto
import com.october03.cafe.kiosk.payment.repository.Balance
import com.october03.cafe.kiosk.payment.service.PaymentService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class PaymentController(
  private val paymentService: PaymentService
) {
  @PostMapping("/charge/{couponId}")
  fun chargeBalance(@PathVariable couponId: String, http: HttpServletRequest): Optional<Balance> {
    val authToken = http.getAttribute("authToken") as String
    val token = http.getHeader("Authorization") as String

    val req = ChargeBalanceDto(
      couponId = couponId,
      authToken = authToken,
      token = token
    )

    return paymentService.chargeBalance(req)
  }
}