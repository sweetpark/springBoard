package project.board.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import project.board.web.dto.UploadFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Board {

    private Long id;
    private Long member_id;
    private String title;
    private String body; //  최대 2000자 (한글 1000자)
    private LocalDateTime create_time;
    private Long good;
    private Long bad;

    
    
    //삭제예정
    private Member memberInfo;
    private List<UploadFile> imageFiles;
    private UploadFile attachFile;
    
}
