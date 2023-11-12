package org.tagwonder.controllers

import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.tagwonder.oAuth.AuthToken
import org.tagwonder.oAuth.kakao.SignInKakaoOauth
import org.tagwonder.usecases.commands.SignInOAuthCommandExecutor

@RestController
class MemberController(
    private val signInOAuthCommandExecutor: SignInOAuthCommandExecutor
) {
    @Operation(summary = "카카오 로그인", description = "로그인 요청 후 있으면 로그인, 없으면 회원가입합니다.")
    @PostMapping("/sign-in/kakao")
    fun signInKakao(
        @RequestBody command: SignInKakaoOauth
    ): ResponseEntity<AuthToken> {
        return ResponseEntity.ok(signInOAuthCommandExecutor.execute(command))
    }
}
