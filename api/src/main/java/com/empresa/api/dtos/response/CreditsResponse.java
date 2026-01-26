package com.empresa.api.dtos.response;

import com.fasterxml.jackson.annotation.JsonAlias;
import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreditsResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String id;
    @JsonAlias("created_at")
    private String createdAt;
    private String pointName;
    private String pointId;
    private String amount;
}
