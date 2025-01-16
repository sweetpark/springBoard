package project.board.common.auth.session;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import project.board.common.constant.SessionConst;

@RequiredArgsConstructor
@Component
@Slf4j
public class AuthArgumentResolver implements HandlerMethodArgumentResolver {

    private final SessionStore sessionStore;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticatedLoginId.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession(false);

        HttpServletResponse response = (HttpServletResponse )webRequest.getNativeResponse();
        if (session == null){
            response.sendRedirect("members/login");
            log.info("세션이 존재하지 않습니다");
        }

        SessionWrapper sessionWrapper = sessionStore.getStore().get(session.getId());
        if(sessionWrapper == null){
            response.sendRedirect("members/login");
            log.info("세션 저장소안에 세션이 존재하지 않습니다");
        }

        return sessionWrapper.getSession().getAttribute(SessionConst.LOGIN_MEMBER);
    }
}
