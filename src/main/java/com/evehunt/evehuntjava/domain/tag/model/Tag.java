package com.evehunt.evehuntjava.domain.tag.model;

import com.evehunt.evehuntjava.domain.event.model.Event;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public final class Tag {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private final Long id = null;
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @OnDelete(
            action = OnDeleteAction.CASCADE
    )

    private Event event;

    private String tagName;


    public final Long getId() {
        return this.id;
    }


    public final Event getEvent() {
        return this.event;
    }

    public final void setEvent(Event event) {
        this.event = event;
    }

    public final String getTagName() {
        return this.tagName;
    }

    public final void setTagName(String var1) {
        this.tagName = var1;
    }

    public Tag(Event event, String tagName) {
        this.event = event;
        this.tagName = tagName;
    }

    public Tag() {
    }
}