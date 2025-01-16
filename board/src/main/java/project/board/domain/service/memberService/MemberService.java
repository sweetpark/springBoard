package project.board.domain.service.memberService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.board.domain.entity.Member;
import project.board.domain.repository.memberRepository.MemberRepository;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    //회원가입
    public void save(Member member){
        if (checkDuplication(member.getLoginId())){
            throw new RuntimeException("아이디가 중복됩니다.");
        }
        memberRepository.save(member);
    }

    //id중복
    public boolean checkDuplication(String loginId){
        List<Member> members =memberRepository.findAll();
        for (Member member : members) {
            if(member.getLoginId().equals(loginId)){
                return true;
            }
        }
        return false;
    }

    //로그인 아이디 찾기
    public Member getMemberByLoginId(String loginId){
        return memberRepository.findByLoginId(loginId);
    }

    //회원탈퇴
    public void deleteId(Long id){
        memberRepository.delete(id);
    }


    //[신규기능]
    //아이디찾기 (휴대폰인증)
    //비밀번호 찾기 (이메일인증)


}
