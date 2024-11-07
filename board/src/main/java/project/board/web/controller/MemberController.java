package project.board.web.controller;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import project.board.domain.dto.Board;
import project.board.domain.dto.LoginForm;
import project.board.domain.dto.Member;
import project.board.domain.repository.boardRepository.BoardRepository;
import project.board.domain.repository.memberRepository.MemberRepository;
import project.board.web.service.login.LoginService;
import project.board.web.validate.bindingResultValidation.BindigResultValidate;

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

    @GetMapping("/{loginId}")
    public String home(@PathVariable("loginId") String loginId, Model model){


        Member member = memberRepository.findByLoginId(loginId);
        List<Board> boards = boardRepository.findBoardsByMember(loginId);

        model.addAttribute("member", member);
        model.addAttribute("boards", boards);
        return "home";
    }

//    @PostConstruct
//    public void initData(){
//        Member member1 = new Member("admin","admin","admin","admin");
//        Member member2 = new Member("test","test","test","seoul");
//        Member member3 = new Member("test2","spring","test2","suwon");
//
//        memberRepository.save(member1);
//        memberRepository.save(member2);
//        memberRepository.save(member3);
//
//    }

    @InitBinder
    private void init(WebDataBinder webDataBinder){
        webDataBinder.addValidators(bindingResultValidate);
    }
}
