package wc.service.message;

import org.springframework.validation.annotation.Validated;
import wc.utility.query.partial.PartialQueryParameters;
import wc.utility.query.partial.PartialQueryResult;

import javax.validation.Valid;

@Validated
public interface MessageService {

    void sendMessage(long chatId, @Valid MessageSending sending);

    PartialQueryResult<MessageView> getChatMessages(long chatId, @Valid PartialQueryParameters parameters);
}
