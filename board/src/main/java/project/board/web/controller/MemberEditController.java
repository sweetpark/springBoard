package project.board.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.board.auth.session.AuthenticatedLoginId;
import project.board.domain.dto.Member;
import project.board.domain.dto.memberUpdateForm;
import project.board.domain.repository.boardRepository.BoardRepository;
import project.board.domain.repository.memberRepository.MemberRepository;

@Slf4j
@Controller
@RequestMapping(value = "/members")
public class MemberEditController {
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    @Autowired
    public MemberEditController(MemberRepository memberRepository,
                            BoardRepository boardRepository){
        this.memberRepository = memberRepository;
        this.boardRepository = boardRepository;
    }

    @GetMapping("/edit")
    public String getEditForm(@AuthenticatedLoginId String loginId, Model model){

        Member findMember = memberRepository.findByLoginId(loginId);
        memberUpdateForm form = new memberUpdateForm();
        form.setId(findMember.getId());
        form.setName(findMember.getName());
        form.setPasswd(findMember.getPasswd());
        form.setAddress(findMember.getAddress());
        form.setLoginId(loginId);
        model.addAttribute("member", form);
        return "member/edit-form";
    }


    @PostMapping("/edit")
    public String postEditForm(@AuthenticatedLoginId String loginId,
                               @Validated @ModelAttribute("member") memberUpdateForm form,
                               BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            return "member/edit-form";
        }
        Member member = new Member();
        member.setId(form.getId());
        member.setLoginId(loginId);
        member.setName(form.getName());
        member.setPasswd(form.getPasswd());
        member.setAddress(form.getAddress());


        memberRepository.updateMember(loginId, member);

        //수정 완료 알림
        return "redirect:/members";
    }



}
