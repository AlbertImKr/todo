package me.albert.todo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;

@Table(name = "notification_setting")
@Entity
public class NotificationSetting {

    @Id
    @GeneratedValue
    private Long id;
    @Getter
    private LocalDateTime notifyAt;

    public NotificationSetting() {
    }

    public NotificationSetting(LocalDateTime notifyAt) {
        this.notifyAt = notifyAt;
    }
}
