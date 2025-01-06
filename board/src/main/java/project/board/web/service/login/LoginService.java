package project.board.web.service.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.board.auth.enc.AES256Enc;
import project.board.domain.dto.Member;
import project.board.domain.repository.memberRepository.MemberRepository;

@Service
public class LoginService {

    private final MemberRepository memberRepository;

    @Autowired
    public LoginService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    public boolean loginCheck(String loginId, String passwd){
        Member findMember = memberRepository.findByLoginId(loginId);
        if (findMember != null){
            if (AES256Enc.decrypt(findMember.getPasswd()).equals(passwd)){
                return true;
            }
        }
        return false;
    }


}
