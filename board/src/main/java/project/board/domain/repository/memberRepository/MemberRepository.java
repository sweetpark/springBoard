package project.board.domain.repository.memberRepository;

import project.board.domain.dto.Member;

import java.util.List;

public interface MemberRepository {

    public void save(Member member);
    public void delete(String loginId);
    public Member findByLoginId(String loginId);
    public List<Member> findAll();
    public void updateMember(String loginId, Member member);
    public void clear();

}