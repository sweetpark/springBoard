package project.board.web.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import project.board.domain.entity.Member;

import java.util.List;

@Data
public class BoardForm {
    private Long id;
    private String title;
    private String body;
    private Member memberInfo;
    //CHECKME memberId 로 수정
    private List<MultipartFile> imageFiles;
    private MultipartFile attachFile;
}
