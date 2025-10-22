package br.ifsp.arsw.esdemo.presentation.dto;

import java.time.Instant;
import java.util.Map;

public record DomainEventDto (
        String aggregateId,
        long sequenceNumber,
        String payloadType,
        Object payload,
        Map<String, Object> metadata,
        Instant timestamp
){}
