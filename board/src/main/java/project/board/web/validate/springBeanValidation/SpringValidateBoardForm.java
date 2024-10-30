package project.board.web.validate.springBeanValidation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import project.board.domain.dto.Member;

@Data
public class SpringValidateBoardForm {

    @NotNull
    private Long id;

    @NotBlank
    private String title;

    private String body;
    private Member memberInfo;
}
