package project.board.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import project.board.auth.session.SessionStore;
import project.board.web.SessionConst;


@RequiredArgsConstructor
@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    private final SessionStore sessionStore;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();

        log.info("인증 체크 인터셉트 실행 {}", requestURI);
        HttpSession session = request.getSession(false);

        if(session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
            log.info("미인증 사용자 요청");

            response.sendRedirect("/members/login?redirectURI=" + requestURI);
            return false;

        }else if(sessionStore.getStore().get(session.getId()) == null) {
            log.info("로그인이 만료되었거나, 미인증 요청");

            response.sendRedirect("/members/login?redirectURI=" + requestURI);
            return false;
        }
        else{
            sessionStore.updateTime(session.getId());
            return true;
        }
    }


}
