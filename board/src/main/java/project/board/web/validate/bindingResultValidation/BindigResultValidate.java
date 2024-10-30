package project.board.web.validate.bindingResultValidation;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import project.board.domain.dto.Member;

@Component
public class BindigResultValidate implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Member.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Member member = (Member) target;
        BindingResult bindingResult = (BindingResult) errors;

        //로그인ID
        if ( !StringUtils.hasText(member.getLoginId())){
            bindingResult.addError(new FieldError("member", "loginId","로그인 아이디는 필수입력입니다."));
        }

        //이름
        if (!StringUtils.hasText(member.getName())){
            bindingResult.addError(new FieldError("member", "name", "이름은 필수 입력값입니다."));
        }

        //비밀번호
        if ( member.getPasswd() != null && member.getPasswd().length() >= 8 ){
            if (member.getPasswd().matches("[a-zA-Z]+")){
                bindingResult.addError(new FieldError("member","passwd", "비밀번호는 숫자와 특수문자를 포함해야합니다."));
            }else if (member.getPasswd().matches("[0-9]+")) {
                bindingResult.addError(new FieldError("member", "passwd", "비밀번호는 문자와 특수문자를 포함해야합니다"));
            }else if (member.getPasswd().matches("[a-zA-Z0-9]+")){
                bindingResult.addError(new FieldError("member","passwd", "비밀번호는 특수문자를 포함해야합니다 "));
            }
        }else{
            bindingResult.addError(new FieldError("member","passwd", "비밀번호는 문자,숫자,특수문자를 포함하여 8자이상이어야합니다."));
        }

    }
}
