package wc.service.session;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class SessionView {
    private final UUID id;
    private final LocalDateTime openedAt;
}
