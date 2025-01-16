package project.board.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Member {

    private Long id;
    private String loginId; // 필수값
    private String name; // 필수값
    private String passwd; // 필수값 (패스워드 암호화 진행 + 패스워드 특수문자 제한)
    private String address;
    private String phone; // 필수값 (010-1111-2222 형태)
    private String email; // 필수값


    public Member(){}
    public Member(String loginId, String name, String passwd, String address, String phone, String email){
        this.loginId = loginId;
        this.name = name;
        this.passwd = passwd;
        this.address =address;
        this.phone = phone;
        this.email = email;
    }

}
