package project.board.domain.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class BoardForm {
    private Long id;
    private String title;
    private String body;
    private Member memberInfo;
    private List<MultipartFile> imageFiles;
    private MultipartFile attachFile;
}
