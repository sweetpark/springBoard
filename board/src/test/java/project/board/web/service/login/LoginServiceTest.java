package project.board.web.service.login;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import project.board.domain.dto.Member;
import project.board.domain.repository.memberRepository.MemberMemRespository;
import project.board.domain.repository.memberRepository.MemberRepository;

class LoginServiceTest {

    private MemberRepository memberRepository = new MemberMemRespository();
    private LoginService loginService = new LoginService(memberRepository);

    @Test
    void loginCheck(){
        Member member = new Member();
        member.setLoginId("test");
        member.setPasswd("test");

        memberRepository.save(member);

        Assertions.assertThat(loginService.loginCheck("test","test")).isEqualTo(true);


    }

}