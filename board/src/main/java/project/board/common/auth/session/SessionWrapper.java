package project.board.common.auth.session;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;



@AllArgsConstructor
@Getter
@Setter
public class SessionWrapper {
    private HttpSession session;
    private LocalDateTime connectionTime;

}
