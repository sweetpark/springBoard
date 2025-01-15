package project.board.auth.session;


import jakarta.servlet.http.HttpSession;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@RequiredArgsConstructor
@Component
@Slf4j
public class SessionStore {
    private final Map<String, SessionWrapper> store = new ConcurrentHashMap<>();
    private static final Duration SESSION_TIMEOUT = Duration.ofDays(1);


    public void save(HttpSession session){
        log.info("세션저장");
        store.put(session.getId(), new SessionWrapper(session, LocalDateTime.now()));
    }


    //스케줄러 (1분마다)
    @Scheduled(fixedRate=60000)
    private void expiredDelete(){
        log.info("세션 삭제 갱신");
        store.entrySet().removeIf(entry -> {
            LocalDateTime storedTime = entry.getValue().getConnectionTime();
            return Duration.between(storedTime, LocalDateTime.now()).compareTo(SESSION_TIMEOUT) > 0;
        });
    }

    //접속시 자동 갱신
    public void updateTime(String sessionId){
        SessionWrapper sessionWrapper = store.get(sessionId);
        if( sessionWrapper != null){
            sessionWrapper.setConnectionTime(LocalDateTime.now());
        }
    }

}
