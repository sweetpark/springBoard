package project.board;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import project.board.domain.ConnectionConst;
import project.board.domain.repository.boardRepository.BoardDBRepository;
import project.board.domain.repository.boardRepository.BoardMemRepository;
import project.board.domain.repository.boardRepository.BoardRepository;
import project.board.domain.repository.memberRepository.MemberDBRepository;
import project.board.domain.repository.memberRepository.MemberMemRespository;
import project.board.domain.repository.memberRepository.MemberRepository;
import project.board.web.filter.LogFilter;
import project.board.web.filter.LoginCheckFilter;
import project.board.web.interceptor.LogInterceptor;
import project.board.web.interceptor.LoginCheckInterceptor;


@Configuration
@ComponentScan
public class AppConfig implements WebMvcConfigurer {

    @Bean
    public MemberRepository getMemberRepository() {
//        return new MemberMemRespository();
         return new MemberDBRepository(DataSourceConfig.dataSource());
    }

    @Bean
    public BoardRepository boardRepository() {
//        return new BoardMemRepository();
         return new BoardDBRepository(DataSourceConfig.dataSource());
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

        registry.addInterceptor(new LoginCheckInterceptor())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/members/*/boards/*","/members/login", "/members/form", "/members/logout", "/css/**", "/*.ico", "/error","/errors/custom/**");

    }
}
