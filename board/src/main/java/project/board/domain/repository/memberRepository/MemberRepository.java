package project.board.domain.repository.memberRepository;

import org.apache.ibatis.annotations.Mapper;
import project.board.domain.entity.Member;

import java.util.List;

public interface MemberRepository {

    public void save(Member member);
    public void delete(Long id);
    public Member findById(Long id);
    public Member findByLoginId(String loginId);
    public List<Member> findAll();
    public void updateMember(Long id, Member member);
    public void clear();

}