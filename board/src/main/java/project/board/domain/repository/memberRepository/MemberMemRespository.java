package project.board.domain.repository.memberRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.InitBinder;
import project.board.domain.dto.Member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
public class MemberMemRespository implements MemberRepository{


    private Map<String, Member> store = new ConcurrentHashMap<>();


    @Override
    public void save(Member member) {
        //id 중복처리
        store.put(member.getLoginId(), member);
    }

    @Override
    public void delete(String loginId) {
        store.remove(loginId);
    }

    @Override
    public synchronized Member findByLoginId(String loginId) {
        return store.get(loginId);
    }


    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public synchronized void updateMember(String loginId, Member member) {

        Member findMember = store.get(loginId);
        if(findMember != null){
            findMember.setName(member.getName());
            findMember.setPasswd(member.getPasswd());
            findMember.setAddress(member.getAddress());
            store.put(loginId,findMember);
        }else{
            log.warn("No member found for loginId: " + loginId);
            throw new IllegalStateException("Member not found with loginId: " + loginId);
        }

    }

    @Override
    public void clear() {
        store.clear();
    }


}
