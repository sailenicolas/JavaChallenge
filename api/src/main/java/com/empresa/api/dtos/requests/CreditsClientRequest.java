package com.empresa.api.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreditsClientRequest extends CreditsRequest{
    private String pointName;

    public CreditsClientRequest(CreditsRequest id) {
        this.setAmount(id.getAmount());
        this.setPointId(id.getPointId());
    }
}
