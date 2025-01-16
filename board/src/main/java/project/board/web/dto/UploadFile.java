package project.board.web.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UploadFile {

    private String uploadFileName;
    private String storeFileName;

    public UploadFile(){}

    @JsonCreator
    public UploadFile(@JsonProperty("uploadFileName") String uploadFileName, @JsonProperty("storeFileName") String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }

//    public UploadFile(String uploadFileName, String storeFileName){
//        this.storeFileName = storeFileName;
//        this.uploadFileName = uploadFileName;
//    }
}
