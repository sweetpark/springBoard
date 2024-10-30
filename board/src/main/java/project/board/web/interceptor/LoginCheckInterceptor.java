package project.board.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import project.board.domain.dto.Member;
import project.board.web.SessionConst;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        Pattern pattern = Pattern.compile("/members/([^/]+)");
        Matcher matcher = pattern.matcher(requestURI);


        log.info("인증 체크 인터셉트 실행 {}", requestURI);
        HttpSession session = request.getSession(false);

        if(session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
            log.info("미인증 사용자 요청");

            response.sendRedirect("/members/login?redirectURI=" + requestURI);
            return false;

        }else if(matcher.find()){
            String account = matcher.group(1);

            Member authAccount = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);

            if (authAccount.getLoginId() == null ||  !authAccount.getLoginId().equals(account)){
                response.sendRedirect("/members/login?redirectURI="+requestURI);
                log.info("Session loginId: {}, URL account: {}", authAccount.getLoginId(), account);
                return false;
            }

        }else{
            response.sendRedirect("/errors/custom/404");
            log.info("Not matcher loginId, Error 404!");
            return false;
        }

        return true;
    }
}
