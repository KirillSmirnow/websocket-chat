package wc.service.message;

import org.springframework.validation.annotation.Validated;
import wc.service.Id;
import wc.utility.query.partial.PartialQueryParameters;
import wc.utility.query.partial.PartialQueryResult;

import javax.validation.Valid;
import java.util.UUID;

@Validated
public interface MessageService {

    Id<UUID> sendMessage(long chatId, @Valid MessageSending sending);

    MessageView getMessage(UUID id);

    PartialQueryResult<MessageView> getChatMessages(long chatId, @Valid PartialQueryParameters parameters);
}
