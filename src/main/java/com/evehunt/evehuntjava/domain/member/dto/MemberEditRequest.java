package com.evehunt.evehuntjava.domain.member.dto;

import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
@NoArgsConstructor(force = true)
public class MemberEditRequest {
    @Length(
            min = 3,
            max = 20,
            message = "이름은 3자 이상 20자 미만입니다."
    )
    private final String newName;
    private final String newProfileImage;

    public final String getNewName() {
        return this.newName;
    }

    public final String getNewProfileImage() {
        return this.newProfileImage;
    }

    public MemberEditRequest(String newName, String newProfileImage) {
        this.newName = newName;
        this.newProfileImage = newProfileImage;
    }
}
