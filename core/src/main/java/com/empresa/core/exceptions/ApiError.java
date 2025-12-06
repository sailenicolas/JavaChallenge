package com.empresa.core.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {
    private String message;
    private List<String> errors;
    private String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    public ApiError(String message, String errors) {
        this.message = message;
        this.errors = Collections.singletonList(errors);
    }

    public ApiError() {
        this.message = "An error occurred";
        this.errors = List.of("Server internal Error");
    }
    public ApiError(HttpStatusCode status, String missingParameter) {
        this("An Error Occurred: %s".formatted(status), missingParameter);
    }

    public ApiError(ServiceException e) {
        this.message = e.getMessage();
        this.errors = e.getErrors();
    }
}
