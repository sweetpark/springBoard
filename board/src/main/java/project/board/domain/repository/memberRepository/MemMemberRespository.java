package project.board.domain.repository.memberRepository;

import lombok.extern.slf4j.Slf4j;
import project.board.domain.entity.Member;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
public class MemMemberRespository implements MemberRepository{


    private Map<Long, Member> store = new ConcurrentHashMap<>();
    private static Long sequence = 0L;

    @Override
    public void save(Member member) {
        store.put(sequence++, member);
    }

    @Override
    public void delete(Long id) {
        store.remove(id);
    }

    @Override
    public Member findById(Long id) {
        return store.get(id);
    }

    @Override
    public Member findByLoginId(String loginId) {
        Collection<Member> members = store.values();
        for (Member member : members) {
            if (member.getLoginId().equals(loginId)){
                return store.get(member.getId());
            }
        }
        throw new IllegalStateException("아이디가 존재하지 않습니다");
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public synchronized void updateMember(Long id, Member member) {

        Member findMember = store.get(id);
        if(findMember != null){
            findMember.setName(member.getName());
            findMember.setPasswd(member.getPasswd());
            findMember.setAddress(member.getAddress());
            store.put(id,findMember);
        }else{
            log.warn("No member found for loginId: " + member.getLoginId());
            throw new IllegalStateException("Member not found with loginId: " + member.getLoginId());
        }

    }
    @Override
    public void clear() {
        store.clear();
    }


}
