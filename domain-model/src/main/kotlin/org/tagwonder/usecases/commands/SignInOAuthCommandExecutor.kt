package org.tagwonder.usecases.commands

import org.tagwonder.entities.Member
import org.tagwonder.jwt.AuthTokenGenerator
import org.tagwonder.oAuth.AuthToken
import org.tagwonder.oAuth.IOAuthInfoResponse
import org.tagwonder.oAuth.ISignInOAuth
import org.tagwonder.repositories.IMemberRepository
import org.tagwonder.usecases.queries.GetOAuthInfoQueryProcessor

class SignInOAuthCommandExecutor(
    private val memberRepository: IMemberRepository,
    private val authTokensGenerator: AuthTokenGenerator,
    private val oAuthInfoQueryProcessor: GetOAuthInfoQueryProcessor
) {
    fun execute(command: ISignInOAuth): AuthToken {
        //TODO(refactor): 추 후 ISignInOAuth에 대한 command, query 경계 확실하게 나누기
        val response = oAuthInfoQueryProcessor.process(command)
        val memberId = findOrCreateMember(response)
        return authTokensGenerator.generate(memberId)
    }

    private fun findOrCreateMember(oAuthInfoResponse: IOAuthInfoResponse): Long {
        return memberRepository.findByEmail(oAuthInfoResponse.getEmail())
            ?.let { it.id }
            ?: newMember(oAuthInfoResponse)
    }

    private fun newMember(response: IOAuthInfoResponse): Long {
        return memberRepository.create(
            Member(
                email = response.getEmail(),
                nickname = response.getNickname(),
                oAuthProvider = response.getOAuthProvider()
            )
        ).id!!
    }
}
