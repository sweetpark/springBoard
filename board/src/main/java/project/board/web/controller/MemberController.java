package project.board.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import project.board.common.auth.session.AuthenticatedLoginId;
import project.board.domain.entity.Board;
import project.board.domain.entity.Member;
import project.board.domain.repository.boardRepository.BoardRepository;
import project.board.domain.repository.memberRepository.MemberRepository;
import project.board.domain.service.loginService.LoginService;
import project.board.common.validate.bindingResultValidation.BindigResultValidate;

import java.util.List;


@Slf4j
@Controller
@RequestMapping(value="/members")
public class MemberController {

    private final MemberRepository memberRepository;
    private final LoginService loginService;
    private final BoardRepository boardRepository;
    private final BindigResultValidate bindingResultValidate;

    @Autowired
    public MemberController(MemberRepository memberRepository,
                            LoginService loginService,
                            BoardRepository boardRepository,
                            BindigResultValidate bindingResultValidate){
        this.memberRepository = memberRepository;
        this.loginService = loginService;
        this.boardRepository = boardRepository;
        this.bindingResultValidate = bindingResultValidate;
    }


    @GetMapping("/form")
    public String formView(Model model){
        model.addAttribute("member",new Member());
        return "member/member-form";
    }

    @PostMapping("/form")
    public String registryMember(@Validated @ModelAttribute Member member, BindingResult bindingResult, Model model){


        if (bindingResult.hasErrors()){
            return "member/member-form";
        }

        memberRepository.save(member);
        return "redirect:/members/login";
    }

    @GetMapping
    public String home(@AuthenticatedLoginId String loginId, Model model){

        Member member = memberRepository.findByLoginId(loginId);
        List<Board> boards = boardRepository.findBoardsByMember(loginId);

        model.addAttribute("member", member);
        model.addAttribute("boards", boards);
        return "home";
    }

    @InitBinder
    private void init(WebDataBinder webDataBinder){
        webDataBinder.addValidators(bindingResultValidate);
    }
}
