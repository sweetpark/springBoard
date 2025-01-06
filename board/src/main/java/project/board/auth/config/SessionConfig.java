package project.board.auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import project.board.auth.session.AuthArgumentResolver;
import project.board.auth.session.SessionStore;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SessionConfig implements WebMvcConfigurer {
    private final AuthArgumentResolver resolver;

//    public CacheManager cacheManager(){
//        //ehcache사용
//        return null;
//    }


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(resolver);
    }
}
