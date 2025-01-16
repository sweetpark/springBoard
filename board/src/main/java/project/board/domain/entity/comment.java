package project.board.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class comment {
    private Long id;
    private Long boardId;
    private Long memberId;
    private String body;
    private LocalDateTime createTime;
    private Long good;
    private Long bad;
}
