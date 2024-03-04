package com.october03.cafe.kiosk.payment.config

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.Date
import javax.crypto.spec.SecretKeySpec

@PropertySource("classpath:jwt.yml")
@Service
class TokenProvider(
  @Value("\${secret-key}")
  private val secretKey: String,
  @Value("\${access-expiration-hours}")
  private val accessExpirationHours: Long,
  @Value("\${refresh-expiration-hours}")
  private val refreshExpirationHours: Long,
) {
  private var key = SecretKeySpec(secretKey.toByteArray(), SignatureAlgorithm.HS256.jcaName)

  fun getAuthentication (token: String) : Authentication {
    val claims = getClaims(token)

    val auth = claims["auth"] ?: throw IllegalArgumentException("Invalid token")

    val authorities: Collection<GrantedAuthority> = (auth as String)
      .split(",")
      .map { SimpleGrantedAuthority(it) }

    val principal: UserDetails = User(claims.subject, "", authorities)

    return UsernamePasswordAuthenticationToken(principal, token, authorities)
  }

  fun getClaims(token: String): Claims =
    Jwts.parserBuilder()
      .setSigningKey(key)
      .build()
      .parseClaimsJws(token)
      .body

  fun validateToken(token: String): Boolean {
    try {
      getClaims(token)
      return true
    } catch (e: Exception) {
      return false
    }
  }
}