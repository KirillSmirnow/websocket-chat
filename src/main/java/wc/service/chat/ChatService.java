package wc.service.chat;

import org.springframework.validation.annotation.Validated;
import wc.service.Id;
import wc.utility.query.partial.PartialQueryParameters;
import wc.utility.query.partial.PartialQueryResult;

import javax.validation.Valid;

@Validated
public interface ChatService {

    Id<Long> createChat(@Valid ChatCreation creation);

    void updateChat(long chatId, @Valid ChatUpdate update);

    void updateInvitationCode(long chatId);

    ChatView getChat(long id);

    PartialQueryResult<ChatView> getCurrentUserChats(@Valid PartialQueryParameters parameters);
}
