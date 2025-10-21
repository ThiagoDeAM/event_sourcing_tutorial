package br.ifsp.arsw.esdemo.audit;

import br.ifsp.arsw.esdemo.api.dto.DomainEventDto;
import org.axonframework.eventsourcing.eventstore.DomainEventStream;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.hibernate.boot.Metadata;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccountEventReader {

    private final EventStore eventStore;

    public AccountEventReader(EventStore eventStore) {
        this.eventStore = eventStore;
    }

    public List<DomainEventDto> list(String aggregateId) {
        List<DomainEventDto> out = new ArrayList<>();
        DomainEventStream stream = eventStore.readEvents(aggregateId);

        while (stream.hasNext()) {
            var msg = stream.next();

            Map<String, Object> metadata = new LinkedHashMap<>(msg.getMetaData());

            out.add(new DomainEventDto(
                    msg.getAggregateIdentifier(),
                    msg.getSequenceNumber(),
                    msg.getPayloadType().getName(),
                    msg.getPayload(),
                    metadata,
                    msg.getTimestamp()
            ));
        }
        return out;
    }
}
