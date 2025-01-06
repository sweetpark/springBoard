package project.board.auth.session;


import jakarta.servlet.http.HttpSession;
import lombok.Data;
import lombok.RequiredArgsConstructor;
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
public class SessionStore {
    private final Map<String, SessionWrapper> store = new ConcurrentHashMap<>();
    private static final Duration SESSION_TIMEOUT = Duration.ofDays(1);

    public void save(HttpSession session){
        store.put(session.getId(), new SessionWrapper(session, LocalDateTime.now()));
    }


    //스케줄러 (1분마다) 쓰레드처리
    @Async
    @Scheduled(fixedRate=60000)
    private void expiredDelete(){
        store.entrySet().removeIf(entry -> {
            LocalDateTime storedTime = entry.getValue().getConnectionTime();
            return Duration.between(storedTime, LocalDateTime.now()).compareTo(SESSION_TIMEOUT) > 0;
        });
    }

    //접속시 자동 갱신
    private void updateTime(String sessionId){
        SessionWrapper sessionWrapper = store.get(sessionId);
        if( sessionWrapper != null){
            sessionWrapper.setConnectionTime(LocalDateTime.now());
        }
    }

}
