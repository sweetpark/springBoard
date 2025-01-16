package project.board.common.validate.springBeanValidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
    }

    @Override
    public boolean isValid(String passwd, ConstraintValidatorContext context) {
        if (passwd == null || passwd.length() < 8) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("비밀번호는 8자 이상이어야 합니다.")
                    .addConstraintViolation();
            return false;
        }

        // 문자만 포함
        if (passwd.matches("[a-zA-Z]+")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("비밀번호는 숫자와 특수문자를 포함해야합니다.")
                    .addConstraintViolation();
            return false;
        }

        // 숫자만 포함
        if (passwd.matches("[0-9]+")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("비밀번호는 문자와 특수문자를 포함해야합니다.")
                    .addConstraintViolation();
            return false;
        }

        // 문자와 숫자만 포함
        if (passwd.matches("[a-zA-Z0-9]+")) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("비밀번호는 특수문자를 포함해야합니다.")
                    .addConstraintViolation();
            return false;
        }

        // 모든 조건을 만족
        return true;
    }
}
