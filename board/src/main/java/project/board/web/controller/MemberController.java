package project.board.web.controller;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import project.board.auth.session.AuthenticatedLoginId;
import project.board.domain.dto.Board;
import project.board.domain.dto.LoginForm;
import project.board.domain.dto.Member;
import project.board.domain.repository.boardRepository.BoardRepository;
import project.board.domain.repository.memberRepository.MemberRepository;
import project.board.web.SessionConst;
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
    public String home(@PathVariable("loginId") String loginId, Model model, HttpServletRequest request, @AuthenticatedLoginId String tmp){
        System.out.println(tmp);
        HttpSession session = request.getSession(false);

        if (session == null){
            return "redirect:/members/login";
        }

        String auth = (String)session.getAttribute(SessionConst.LOGIN_MEMBER);

//        Member member = memberRepository.findByLoginId(loginId);
        Member member = memberRepository.findByLoginId(auth);
//        List<Board> boards = boardRepository.findBoardsByMember(loginId);
        List<Board> boards = boardRepository.findBoardsByMember(auth);

        model.addAttribute("member", member);
        model.addAttribute("boards", boards);
        return "home";
    }

    @InitBinder
    private void init(WebDataBinder webDataBinder){
        webDataBinder.addValidators(bindingResultValidate);
    }
}
