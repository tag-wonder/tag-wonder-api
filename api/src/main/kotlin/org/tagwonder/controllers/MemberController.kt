package org.tagwonder.controllers

import io.swagger.annotations.ApiImplicitParam
import io.swagger.annotations.ApiOperation
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

    @ApiOperation(value = "카카오 로그인", notes = "로그인 요청 후 있으면 로그인, 없으면 회원가입합니다.")
    @ApiImplicitParam(name = "command", value = "카카오 인가(authorization) 코드")
    @PostMapping("/sign-in/kakao")
    fun signInKakao(
        @RequestBody command: SignInKakaoOauth
    ): ResponseEntity<AuthToken> {
        return ResponseEntity.ok(signInOAuthCommandExecutor.execute(command))
    }
}
