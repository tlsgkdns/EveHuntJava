package com.evehunt.evehuntjava.domain.event.dto;

import com.evehunt.evehuntjava.domain.event.model.Event;
import com.evehunt.evehuntjava.domain.event.model.EventStatus;
import com.evehunt.evehuntjava.domain.image.model.Image;
import com.evehunt.evehuntjava.domain.member.model.Member;
import com.evehunt.evehuntjava.domain.tag.dto.TagAddRequest;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
@NoArgsConstructor(force = true)
public class EventHostRequest {
    @Length(
            min = 3,
            max = 20,
            message = "제목의 길이는 3자 이상, 20자 미만으로 설정해주세요"
    )
    @NotNull(
            message = "제목을 입력해주세요."
    )
    private final String title;
    @Length(
            min = 3,
            max = 1000,
            message = "설명은 3자 이상 1000자 미만으로 설정해주세요."
    )
    @NotNull(
            message = "설명을 입력해주세요."
    )
    private final String description;
    @Length(
            min = 3,
            max = 1000,
            message = "당첨 메시지는 3자 이상 1000자 미만으로 설정해주세요."
    )
    @NotNull(
            message = "당첨 메시지를 입력해주세요."
    )
    private final String winMessage;
    private final String eventImage;
    @Min(
            value = 1L,
            message = "최소 한명은 참여해주세여."
    )
    @Max(
            value = 1000L,
            message = "참여 인원은 1000명을 넘길 수 없습니다."
    )
    @NotNull(
            message = "정원을 입력해주세요"
    )
    private final int capacity;
    @Future(
            message = "종료 날짜는 미래 날짜로 설정해주세요."
    )
    @NotNull(
            message = "종료 날짜를 입력해주세요."
    )

    private final LocalDateTime closeAt;

    private final String question;

    private final List<TagAddRequest> tagAddRequests;


    public final Event to(Member member) {
        return new Event(this.title, this.description, Image.from(this.eventImage),
                this.capacity, EventStatus.PROCEED, this.winMessage, this.question, this.closeAt.atZone(ZoneId.of("Asia/Seoul")), member);
    }

    public final String getTitle() {
        return this.title;
    }


    public final String getDescription() {
        return this.description;
    }


    public final String getWinMessage() {
        return this.winMessage;
    }


    public final String getEventImage() {
        return this.eventImage;
    }

    public final int getCapacity() {
        return this.capacity;
    }


    public final LocalDateTime getCloseAt() {
        return this.closeAt;
    }


    public final String getQuestion() {
        return this.question;
    }


    public final List<TagAddRequest> getTagAddRequests() {
        return this.tagAddRequests;
    }

    public EventHostRequest(String title, String description, String winMessage, String eventImage, int capacity,
                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime closeAt, String question, List<TagAddRequest> tagAddRequests) {
        this.title = title;
        this.description = description;
        this.winMessage = winMessage;
        this.eventImage = eventImage;
        this.capacity = capacity;
        this.closeAt = closeAt;
        this.question = question;
        this.tagAddRequests = tagAddRequests;
    }
}
