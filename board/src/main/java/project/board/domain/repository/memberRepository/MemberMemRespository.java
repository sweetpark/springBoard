package project.board.domain.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.board.domain.dto.Member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MemberMemRespository implements MemberRepository{


    private Map<String, Member> store = new HashMap<>();


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
    public Member findById(String loginId) {
        return store.get(loginId);
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void clear() {
        store.clear();
    }
}
