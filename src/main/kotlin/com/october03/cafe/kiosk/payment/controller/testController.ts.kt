package com.october03.cafe.kiosk.payment.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("")
class TestController {
  @RequestMapping("/test")
  fun test(): String {
    return "Hello World"
  }
}