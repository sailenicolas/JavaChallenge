package com.empresa.cache.dtos.response;

import com.empresa.cache.model.PosCostHash;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public record PosCostMin(List<PosCostHash> outgoing, List<PosCostHash> incoming) {
}
