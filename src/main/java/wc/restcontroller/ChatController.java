package wc.restcontroller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import wc.security.annotation.AuthenticationRequired;
import wc.service.chat.ChatCreation;
import wc.service.chat.ChatService;
import wc.service.chat.ChatUpdate;
import wc.service.chat.ChatView;
import wc.service.chatmember.ChatJoining;
import wc.service.chatmember.ChatMemberService;
import wc.service.message.MessageSending;
import wc.service.message.MessageService;
import wc.service.message.MessageView;
import wc.service.user.UserView;
import wc.utility.query.partial.PartialQueryParameters;
import wc.utility.query.partial.PartialQueryResult;

@RestController
@RequestMapping("/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final ChatMemberService chatMemberService;
    private final MessageService messageService;

    @PostMapping
    @AuthenticationRequired
    public ChatView createChat(@RequestBody ChatCreation creation) {
        var id = chatService.createChat(creation);
        return chatService.getChat(id.value());
    }

    @PutMapping("/{id}")
    @AuthenticationRequired
    @PreAuthorize("@chatMemberAuthorizer.isCurrentUserMemberOfChat(#id)")
    public ChatView updateChat(@PathVariable long id, @RequestBody ChatUpdate update) {
        chatService.updateChat(id, update);
        return chatService.getChat(id);
    }

    @PostMapping("/{id}/invitation-code")
    @AuthenticationRequired
    @PreAuthorize("@chatMemberAuthorizer.isCurrentUserMemberOfChat(#id)")
    public ChatView updateInvitationCode(@PathVariable long id) {
        chatService.updateInvitationCode(id);
        return chatService.getChat(id);
    }

    @GetMapping("/{id}")
    @AuthenticationRequired
    @PreAuthorize("@chatMemberAuthorizer.isCurrentUserMemberOfChat(#id)")
    public ChatView getChat(@PathVariable long id) {
        return chatService.getChat(id);
    }

    @GetMapping
    @AuthenticationRequired
    public PartialQueryResult<ChatView> getCurrentUserChats(PartialQueryParameters parameters) {
        return chatService.getCurrentUserChats(parameters);
    }

    @PutMapping("/joining")
    @AuthenticationRequired
    public ChatView joinChat(@RequestBody ChatJoining joining) {
        var id = chatMemberService.joinChat(joining);
        return chatService.getChat(id.value());
    }

    @PutMapping("/{id}/leaving")
    @AuthenticationRequired
    public ChatView leaveChat(@PathVariable long id) {
        chatMemberService.leaveChat(id);
        return chatService.getChat(id);
    }

    @GetMapping("/{id}/members")
    @AuthenticationRequired
    @PreAuthorize("@chatMemberAuthorizer.isCurrentUserMemberOfChat(#id)")
    public PartialQueryResult<UserView> getChatMembers(@PathVariable long id, PartialQueryParameters parameters) {
        return chatMemberService.getChatMembers(id, parameters);
    }

    @PostMapping("{id}/messages")
    @AuthenticationRequired
    @PreAuthorize("@chatMemberAuthorizer.isCurrentUserMemberOfChat(#id)")
    public MessageView sendMessage(@PathVariable long id, @RequestBody MessageSending sending) {
        var messageId = messageService.sendMessage(id, sending);
        return messageService.getMessage(messageId.value());
    }

    @GetMapping("{id}/messages")
    @AuthenticationRequired
    @PreAuthorize("@chatMemberAuthorizer.isCurrentUserMemberOfChat(#id)")
    public PartialQueryResult<MessageView> getChatMessages(@PathVariable long id, PartialQueryParameters parameters) {
        return messageService.getChatMessages(id, parameters);
    }
}
