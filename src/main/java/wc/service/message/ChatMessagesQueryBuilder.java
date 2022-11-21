package wc.service.message;

import lombok.RequiredArgsConstructor;
import wc.model.Chat;
import wc.model.Message;
import wc.utility.query.QueryBuilder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@RequiredArgsConstructor
public class ChatMessagesQueryBuilder implements QueryBuilder<Message> {

    private final Chat chat;

    @Override
    public Class<Message> getEntityClass() {
        return Message.class;
    }

    @Override
    public Predicate getFilter(Root<Message> entity, CriteriaBuilder builder) {
        return builder.equal(entity.get("chatMember").get("chat"), chat);
    }

    @Override
    public List<Order> getOrders(Root<Message> entity, CriteriaBuilder builder) {
        return List.of(
                builder.desc(entity.get("sentAt"))
        );
    }
}
