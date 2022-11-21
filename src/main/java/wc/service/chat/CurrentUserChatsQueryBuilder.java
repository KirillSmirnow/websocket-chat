package wc.service.chat;

import lombok.RequiredArgsConstructor;
import wc.model.ChatMember;
import wc.model.User;
import wc.utility.query.QueryBuilder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@RequiredArgsConstructor
public class CurrentUserChatsQueryBuilder implements QueryBuilder<ChatMember> {

    private final User currentUser;

    @Override
    public Class<ChatMember> getEntityClass() {
        return ChatMember.class;
    }

    @Override
    public Predicate getFilter(Root<ChatMember> entity, CriteriaBuilder builder) {
        return builder.equal(entity.get("user"), currentUser);
    }

    @Override
    public List<Order> getOrders(Root<ChatMember> entity, CriteriaBuilder builder) {
        return List.of(
                builder.asc(entity.get("chat").get("name"))
        );
    }
}
