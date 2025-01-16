package project.board.config.appConfig;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import project.board.common.auth.session.SessionStore;
import project.board.domain.repository.boardRepository.BoardRepository;
import project.board.domain.repository.boardRepository.MemBoardRepository;
import project.board.domain.repository.commentRepository.CommentRepository;
import project.board.domain.repository.commentRepository.MemCommentRepository;
import project.board.domain.repository.fileRepsitory.FileRepository;
import project.board.domain.repository.fileRepsitory.MemFileRepository;
import project.board.domain.repository.memberRepository.MemMemberRespository;
import project.board.domain.repository.memberRepository.MemberRepository;
import project.board.common.filter.LogFilter;
import project.board.common.filter.LoginCheckFilter;
import project.board.common.interceptor.LogInterceptor;
import project.board.common.interceptor.LoginCheckInterceptor;


@Configuration
@ComponentScan
@RequiredArgsConstructor
public class AppConfig implements WebMvcConfigurer {

    private final SessionStore sessionStore;

    @Bean
    public MemberRepository MemberRepository() {
        return new MemMemberRespository();
//         return new JDBCMemberRepository(DataSourceConfig.dataSource());
    }

    @Bean
    public BoardRepository boardRepository() {
        return new MemBoardRepository();
//         return new JDBCBoardRepository(DataSourceConfig.dataSource());
    }

    @Bean
    public CommentRepository commentRepository(){
        return new MemCommentRepository();
    }

    @Bean
    public FileRepository fileRepository(){
        return new MemFileRepository();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /***
     * 서블릿 필터 적용
     */
    @Bean
    public FilterRegistrationBean logFilter(){
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new LogFilter());
        filterFilterRegistrationBean.setOrder(1);
        filterFilterRegistrationBean.addUrlPatterns("/*");
        filterFilterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST);
        return filterFilterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean loginCheckFilter(){
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new LoginCheckFilter());
        filterFilterRegistrationBean.setOrder(2);
        filterFilterRegistrationBean.addUrlPatterns("/*");
        filterFilterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST);
        return filterFilterRegistrationBean;
    }

    /***
     * 인터셉트 적용
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error", "/errors/custom/**");

        registry.addInterceptor(new LoginCheckInterceptor(sessionStore))
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/members/*/boards/*","/members/login", "/members/form", "/members/logout", "/css/**", "/*.ico", "/error","/errors/custom/**");

    }
}
