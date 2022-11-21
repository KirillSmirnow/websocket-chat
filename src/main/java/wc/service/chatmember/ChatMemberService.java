package wc.service.chatmember;

import org.springframework.validation.annotation.Validated;
import wc.service.Id;
import wc.service.user.UserView;
import wc.utility.query.partial.PartialQueryParameters;
import wc.utility.query.partial.PartialQueryResult;

import javax.validation.Valid;

@Validated
public interface ChatMemberService {

    Id<Long> joinChat(@Valid ChatJoining joining);

    void leaveChat(long chatId);

    PartialQueryResult<UserView> getChatMembers(long chatId, @Valid PartialQueryParameters parameters);
}
