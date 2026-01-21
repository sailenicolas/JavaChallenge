package com.empresa.core.dtos.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class PostHashPutRequest {
    @Size(min = 1)
    @NotEmpty
    private String point;
}
