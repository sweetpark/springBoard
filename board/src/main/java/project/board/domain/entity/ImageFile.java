package project.board.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ImageFile {
    private Long id;
    private Long boardId;
    private String storeFilename;
    private String uploadFilename;

}
