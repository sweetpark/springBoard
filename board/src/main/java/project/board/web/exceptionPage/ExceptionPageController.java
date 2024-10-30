package project.board.web.exceptionPage;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/errors/custom")
@Slf4j
public class ExceptionPageController {

    @GetMapping("500")
    public String error500(HttpServletRequest request, HttpServletResponse response){
        return "errors/custom/500";
    }

    @GetMapping("502")
    public String error502(HttpServletRequest request, HttpServletResponse response){
        return "errors/custom/502";
    }

    @GetMapping("404")
    public String error404(HttpServletRequest request, HttpServletResponse response){
        return "errors/custom/404";
    }

}
