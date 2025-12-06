package com.empresa.pos.dtos.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
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
public class PosCostRequest {
    @Pattern(regexp = "^\\d+$")
    @NotEmpty
    private String idPointA;
    @Pattern(regexp = "^\\d+$")
    @NotEmpty
    private String idPointB;
    @Min(value = 1)
    private BigDecimal cost;
}
