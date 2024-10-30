package project.board.web.filter;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;
import project.board.web.SessionConst;

import java.io.IOException;


@Slf4j
public class LoginCheckFilter implements Filter {

    private static final String[] whiteList = {"/members/login", "/members/form", "/members/logout", "/css/*"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try{
            log.info("인증체크 필터 시작 {}", requestURI);

            if(loginCheckPath(requestURI)){
                log.info("인증체크 로직 실행 {} ", requestURI);
                HttpSession session = httpRequest.getSession(false);

                if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null){
                    log.info("미인증 사용자 요청 {}", requestURI);
                    httpResponse.sendRedirect("/members/login?redirectURL=" + requestURI);
                    return;
                }
            }
            chain.doFilter(request,response);
        }catch (Exception e){
            throw e;
        }finally{
            log.info("인증 체크 필터 종료 {} ", requestURI);
        }
    }

    private boolean loginCheckPath(String requestURI){
        return !PatternMatchUtils.simpleMatch(whiteList, requestURI);
    }
}
