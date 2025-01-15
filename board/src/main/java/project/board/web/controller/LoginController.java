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
import project.board.auth.session.SessionStore;
import project.board.domain.dto.LoginForm;
import project.board.domain.dto.Member;
import project.board.domain.repository.boardRepository.BoardRepository;
import project.board.domain.repository.memberRepository.MemberRepository;
import project.board.web.SessionConst;
import project.board.web.service.login.LoginService;
import project.board.web.validate.bindingResultValidation.BindigResultValidate;

import java.util.concurrent.ConcurrentHashMap;


@Slf4j
@RequestMapping(value="/members")
@Controller
public class LoginController {
    private final MemberRepository memberRepository;
    private final LoginService loginService;
    private final SessionStore sessionStore;


    @Autowired
    public LoginController(MemberRepository memberRepository,
                            LoginService loginService,
                           SessionStore sessionStore){
        this.memberRepository = memberRepository;
        this.loginService = loginService;
        this.sessionStore = sessionStore;
    }


    @GetMapping("/logout")
    public String logout(@ModelAttribute LoginForm loginForm,HttpServletRequest request){

        HttpSession session = request.getSession(false);
        sessionStore.getStore().remove(session.getId());
        session.invalidate();

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
            // 세션 생성
            HttpSession session = request.getSession(true);
            // 세션 담을때 id 값만 담기
            session.setAttribute(SessionConst.LOGIN_MEMBER, loginForm.getLoginId());
            sessionStore.save(session);

            return "redirect:/members";
        }else{
            bindigResult.addError(new ObjectError("LoginForm",null,null,"로그인에 실패했습니다"));
        }

        return "login";
    }
}
