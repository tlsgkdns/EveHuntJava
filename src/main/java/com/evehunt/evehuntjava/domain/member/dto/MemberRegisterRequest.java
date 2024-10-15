package com.evehunt.evehuntjava.domain.member.dto;

import com.evehunt.evehuntjava.domain.image.model.Image;
import com.evehunt.evehuntjava.domain.member.model.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.password.PasswordEncoder;
@NoArgsConstructor(force = true)
public class MemberRegisterRequest {
    private final String email;
    @Length(
            min = 3,
            max = 20,
            message = "이름은 3자 이상 20자 미만입니다."
    )
    @NotNull
    private final String name;
    @Length(
            min = 7,
            max = 20,
            message = "비밀번호는 7자 이상 20자 미만으로 설정해주세요"
    )
    @NotNull
    private final String password;
    private final String profileImageName;

    @NotNull
    public final Member to(PasswordEncoder encoder) {
        return new Member(email, name, encoder.encode(password), Image.from(profileImageName));
    }

    @NotNull
    public final String getEmail() {
        return this.email;
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    @NotNull
    public final String getPassword() {
        return this.password;
    }

    public final String getProfileImageName() {
        return this.profileImageName;
    }

    public MemberRegisterRequest(@Email(message = "이메일을 입력해주세요") @NotNull String email, @NotNull String name, @NotNull String password, String profileImageName) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.profileImageName = profileImageName;
    }
}
