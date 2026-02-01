package com.empresa.api.dtos.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreditsRequest {
    @Pattern(regexp = "^\\d+$")
    @NotEmpty
    private String pointId;
    @Min(value = 0)
    @NotNull
    private BigDecimal amount;
}
