package com.evehunt.evehuntjava.domain.member.dto;

import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
@NoArgsConstructor(force = true)
public class MemberPasswordEditRequest {
    private final String currentPassword;
    @Length(
            min = 7,
            max = 20,
            message = "비밀번호는 7자 이상 20자 미만으로 설정해주세요"
    )
    private final String newPassword;
    private final String passwordCheck;

    public final String getCurrentPassword() {
        return this.currentPassword;
    }

    public final String getNewPassword() {
        return this.newPassword;
    }

    public final String getPasswordCheck() {
        return this.passwordCheck;
    }

    public MemberPasswordEditRequest(String currentPassword, String newPassword, String passwordCheck) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.passwordCheck = passwordCheck;
    }
}
