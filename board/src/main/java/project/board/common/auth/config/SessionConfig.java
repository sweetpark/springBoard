package project.board.common.auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import project.board.common.auth.session.AuthArgumentResolver;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SessionConfig implements WebMvcConfigurer {
    private final AuthArgumentResolver resolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(resolver);
    }
}
