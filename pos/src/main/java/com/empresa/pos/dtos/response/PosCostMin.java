package com.empresa.pos.dtos.response;

import java.util.List;

public record PosCostMin(List<PosCostHash> outgoing, List<PosCostHash> incoming) {
}
