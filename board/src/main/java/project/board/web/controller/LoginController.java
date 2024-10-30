package project.board.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.board.domain.dto.LoginForm;
import project.board.domain.dto.Member;
import project.board.domain.repository.boardRepository.BoardRepository;
import project.board.domain.repository.memberRepository.MemberRepository;
import project.board.web.SessionConst;
import project.board.web.service.login.LoginService;
import project.board.web.validate.bindingResultValidation.BindigResultValidate;


@Slf4j
@RequestMapping(value="/members")
@Controller
public class LoginController {
    private final MemberRepository memberRepository;
    private final LoginService loginService;
    private final BoardRepository boardRepository;
    private final BindigResultValidate bindingResultValidate;

    @Autowired
    public LoginController(MemberRepository memberRepository,
                            LoginService loginService,
                            BoardRepository boardRepository,
                            BindigResultValidate bindingResultValidate){
        this.memberRepository = memberRepository;
        this.loginService = loginService;
        this.boardRepository = boardRepository;
        this.bindingResultValidate = bindingResultValidate;
    }


    @GetMapping("/logout")
    public String logout(@ModelAttribute LoginForm loginForm,HttpServletRequest request){

        HttpSession session = request.getSession(false);
        if ( session != null){
            session.invalidate();
        }
        return "login";
    }

    @GetMapping("/login")
    public String login(@ModelAttribute("loginForm") LoginForm loginForm){
        return "login";
    }

    @PostMapping("/login")
    public String loginCheck(@Validated @ModelAttribute("loginForm") LoginForm loginForm,
                             BindingResult bindigResult,
                             HttpServletRequest request){


        if (bindigResult.hasErrors()){
            return "login";
        }


        if (memberRepository.findByLoginId(loginForm.getLoginId()) == null){
            bindigResult.addError(new FieldError("LoginForm","loginId","아이디가 존재하지 않습니다"));
        }



        if (loginService.loginCheck(loginForm.getLoginId(), loginForm.getPasswd())){
            HttpSession session = request.getSession(true);
            Member loginMember= memberRepository.findByLoginId(loginForm.getLoginId());
            session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
            return "redirect:/members/" + loginForm.getLoginId();
        }else{
            bindigResult.addError(new ObjectError("LoginForm",null,null,"로그인에 실패했습니다"));
        }



        return "login";
    }
}
