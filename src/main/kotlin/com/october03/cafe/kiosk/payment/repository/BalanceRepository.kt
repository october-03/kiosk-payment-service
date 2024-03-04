package com.october03.cafe.kiosk.payment.repository

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.jpa.repository.JpaRepository

@Entity
@Table(name = "balances")
data class Balance (
  @Id
  val id: Long,
  val balance: Long
) {
  constructor() : this(
    0,
    0,
  )
}

interface BalanceRepository: JpaRepository<Balance, Long> {
}