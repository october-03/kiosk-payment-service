package com.october03.cafe.kiosk.payment.service

import com.october03.cafe.kiosk.payment.dto.ChargeBalanceDto
import com.october03.cafe.kiosk.payment.dto.Coupon
import com.october03.cafe.kiosk.payment.dto.User
import com.october03.cafe.kiosk.payment.repository.Balance
import com.october03.cafe.kiosk.payment.repository.BalanceRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import java.util.*

@Service
class PaymentService(
  @Value("\${api.base-url}")
  private val baseUrl: String,

  private val balanceRepository: BalanceRepository,

  webClientBuilder: WebClient.Builder
) {
  private var webClient = webClientBuilder.baseUrl(baseUrl).build()

  fun chargeBalance(req: ChargeBalanceDto): Optional<Balance> {
    val user = getAuthToken(req.authToken)
    val coupon = useCoupon(req)

    val balance = balanceRepository.findById(user.id).orElse(null)

    if (balance != null) {
      balanceRepository.save(balance.copy(balance = balance.balance + coupon.price))
    } else {
      balanceRepository.save(Balance(user.id, coupon.price))
    }

    return balanceRepository.findById(user.id)
  }

  fun getAuthToken(authToken: String): User {
    val user = webClient.get()
      .uri("/v1/user/auth-token/$authToken")
      .retrieve()
      .bodyToMono(User::class.java)

    return user.block()!!
  }

  fun useCoupon(req: ChargeBalanceDto): Coupon {
    try {
      val coupon = webClient.post()
        .uri("/v1/coupon/use-coupon/${req.couponId}")
        .header("Authorization", req.token)
        .retrieve()
        .bodyToMono(Coupon::class.java)

      return coupon.block()!!
    } catch (e: Exception) {
      throw Exception("Coupon is not valid")
    }
  }
}