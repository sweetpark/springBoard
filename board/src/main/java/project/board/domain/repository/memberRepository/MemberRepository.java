package project.board.domain.repository;

import project.board.domain.dto.Member;

import java.util.List;

public interface MemberRepository {

    public void save(Member member);
    public void delete(String loginId);
    public Member findById(String loginId);
    public List<Member> findAll();
    public void clear();

}