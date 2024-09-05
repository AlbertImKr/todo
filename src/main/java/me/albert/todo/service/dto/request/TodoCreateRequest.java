package me.albert.todo.service.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public record TodoCreateRequest(
        @Size(min = 1, max = 100, message = "할 일 제목은 1자 이상 100자 이하로 입력해야 합니다.")
        String title,
        @NotBlank
        @Size(max = 1000, message = "할 일 설명은 1000자를 넘을 수 없습니다.")
        String description,
        @NotNull(message = "마감일은 필수 입력값입니다.")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        @Future(message = "과거 시간은 입력할 수 없습니다.")
        LocalDateTime dueDate
) {

}
