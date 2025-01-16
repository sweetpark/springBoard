package project.board.domain.entity;

import lombok.Getter;
import lombok.Setter;
import project.board.web.dto.UploadFile;

import java.util.List;

@Getter
@Setter
public class Board {

    private Long id;
    private String title;
    private String body; //  최대 2000자 (한글 1000자)
    private Member memberInfo;
//    private Long memberId;
    private List<UploadFile> imageFiles;
    private UploadFile attachFile;
    //image 및 파일들

    public Board(){}
    public Board(String title, String body, Member memberInfo){
        this.title = title;
        this.body = body;
        this.memberInfo = memberInfo;
    }

//    public Board(String title, String body, Long memberId){
//        this.title = title;
//        this.body = body;
//        this.memberId = memberId;
//    }
}
