package project.board.common.validate.springBeanValidation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import project.board.domain.entity.Member;

@Data
public class SpringValidateBoardForm {

    @NotNull
    private Long id;

    @NotBlank
    private String title;

    private String body;
    private Member memberInfo;
}
