package project.board.common.exceptionPage;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class CustomExceptionPage implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/errors/custom/404");
        ErrorPage errorPage502 = new ErrorPage(HttpStatus.BAD_GATEWAY, "/errors/custom/502");
        ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/errors/custom/500");
        ErrorPage errorPageEx500 = new ErrorPage(RuntimeException.class, "/errors/custom/500");

        factory.addErrorPages(errorPage404,errorPage500, errorPage502, errorPageEx500);
    }
}
