package com.microservices.contentservice.core.payload;

import com.microservices.contentservice.core.type.CompletionStatus;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class EnrollmentRequestDto {
    @NonNull
    private String userId;
    @NonNull
    private String courseId;
    private LocalDate enrollmentDate = LocalDate.now();
    private CompletionStatus completionStatus = CompletionStatus.IN_PROGRESS;
    private boolean isActive = true;
}
