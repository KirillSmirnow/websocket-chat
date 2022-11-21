package wc.utility.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public interface QueryBuilder<E> {

    Class<E> getEntityClass();

    Predicate getFilter(Root<E> entity, CriteriaBuilder builder);

    List<Order> getOrders(Root<E> entity, CriteriaBuilder builder);
}
