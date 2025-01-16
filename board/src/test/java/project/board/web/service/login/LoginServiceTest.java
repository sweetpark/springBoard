package project.board.web.service.login;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import project.board.domain.entity.Member;
import project.board.domain.repository.memberRepository.MemMemberRespository;
import project.board.domain.repository.memberRepository.MemberRepository;
import project.board.domain.service.loginService.LoginService;

class LoginServiceTest {

    private MemberRepository memberRepository = new MemMemberRespository();
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