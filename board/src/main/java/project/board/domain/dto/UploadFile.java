package project.board.domain.dto;

import lombok.Data;

@Data
public class UploadFile {

    private String uploadFileName;
    private String storeFileName;

    public UploadFile(String uploadFileName, String storeFileName){
        this.storeFileName = storeFileName;
        this.uploadFileName = uploadFileName;
    }
}
