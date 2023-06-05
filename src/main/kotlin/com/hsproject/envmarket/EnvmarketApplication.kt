package com.hsproject.envmarket

import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.*
import javax.crypto.SecretKey

@SpringBootApplication
class EnvmarketApplication

fun main(args: Array<String>) {
	runApplication<EnvmarketApplication>(*args)

//		val key: SecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512)
//		val encodedKey: String = Base64.getEncoder().encodeToString(key.encoded)
//		println("Encoded key: $encodedKey")

}
