package wyjax.techblog.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {
    private String email;
    private String password;
    private String confirm_password;
}