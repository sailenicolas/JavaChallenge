package com.empresa.api.dtos.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreditsResponse {
    private String id;
    @JsonAlias("created_at")
    private String createdAt;
    private String pointName;
    private String pointId;
    private String amount;
}
