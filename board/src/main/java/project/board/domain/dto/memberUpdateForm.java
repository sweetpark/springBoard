package project.board.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import project.board.web.validate.springBeanValidation.ValidPassword;

@Getter
@Setter
public class memberUpdateForm {

    private Long id;
    private String loginId;
    @NotBlank(message="이름은 필수로 입력하여야 합니다.")
    private String name;
    @ValidPassword
    private String passwd;
    private String address;
}
