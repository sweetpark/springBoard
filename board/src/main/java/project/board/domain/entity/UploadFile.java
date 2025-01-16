package project.board.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UploadFile {
    private Long id;
    private Long boardId;
    private String storeFilename;
    private String uploadFilename;

    public UploadFile(){};
    public UploadFile(String storeFilename, String uploadFilename){
        this.storeFilename = storeFilename;
        this.uploadFilename = uploadFilename;
    }
}
