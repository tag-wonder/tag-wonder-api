package org.tagwonder.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.tagwonder.oAuth.kakao.SignInKakaoOauth
import org.tagwonder.usecases.commands.SignInOAuthCommandExecutor

@RestController
class MemberController(
    private val signInOAuthCommandExecutor: SignInOAuthCommandExecutor
) {

    @PostMapping("/sign-in/kakao")
    fun signInKakao(
        @RequestBody command: SignInKakaoOauth
    ): ResponseEntity<*> {
        return ResponseEntity.ok(signInOAuthCommandExecutor.execute(command))
    }
}
