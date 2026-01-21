package com.empresa.cache.dtos.response;

import com.empresa.core.dtos.responses.PosCostBHash;
import java.util.List;

public record PosCostMinBase(List<PosCostBHash> outgoing, List<PosCostBHash> incoming) {
}
