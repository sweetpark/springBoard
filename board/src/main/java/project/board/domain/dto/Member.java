package project.board.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Member {

    private Long id;
    private String loginId;
    private String name;
    private String passwd;
    private String address;
    //private String zipCode;
    //private String address2;

    public Member(){}
    public Member(String loginId, String name, String passwd, String address){
        this.loginId = loginId;
        this.name = name;
        this.passwd = passwd;
        this.address =address;
    }
    // image -> 대표 이미지

}
