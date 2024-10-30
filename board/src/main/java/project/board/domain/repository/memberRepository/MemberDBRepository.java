package project.board.domain.repository.memberRepository;

import project.board.domain.dto.Member;

import java.util.List;

public class MemberDBRepository implements MemberRepository{
    @Override
    public void save(Member member) {

    }

    @Override
    public void delete(String loginId) {

    }

    @Override
    public Member findByLoginId(String loginId) {
        return null;
    }

    @Override
    public List<Member> findAll() {
        return List.of();
    }

    @Override
    public void updateMember(String loginId,Member member) {

    }

    @Override
    public void clear() {

    }
}
