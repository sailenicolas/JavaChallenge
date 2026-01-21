package com.empresa.pos.dtos.response;

import com.empresa.core.dtos.responses.PosCostBHash;
import java.util.List;

public record PosCostMin(List<PosCostBHash> outgoing, List<PosCostBHash> incoming) {
}
