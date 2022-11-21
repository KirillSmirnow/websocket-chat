package wc.service.chatmember;

import lombok.RequiredArgsConstructor;
import wc.model.Chat;
import wc.model.ChatMember;
import wc.utility.query.QueryBuilder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@RequiredArgsConstructor
public class ChatMembersQueryBuilder implements QueryBuilder<ChatMember> {

    private final Chat chat;

    @Override
    public Class<ChatMember> getEntityClass() {
        return ChatMember.class;
    }

    @Override
    public Predicate getFilter(Root<ChatMember> entity, CriteriaBuilder builder) {
        return builder.equal(entity.get("chat"), chat);
    }

    @Override
    public List<Order> getOrders(Root<ChatMember> entity, CriteriaBuilder builder) {
        return List.of(
                builder.asc(entity.get("user").get("nickname"))
        );
    }
}
