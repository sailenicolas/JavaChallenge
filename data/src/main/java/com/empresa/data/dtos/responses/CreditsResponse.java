package com.empresa.data.dtos.responses;

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
    private String createdAt;
    private String pointName;
    private String pointId;
    private String amount;
}
