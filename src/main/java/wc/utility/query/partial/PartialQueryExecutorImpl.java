package wc.utility.query.partial;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import wc.utility.query.QueryBuilder;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class PartialQueryExecutorImpl implements PartialQueryExecutor {

    private final EntityManager entityManager;

    @Override
    public <E, V> PartialQueryResult<V> execute(
            QueryBuilder<E> queryBuilder,
            PartialQueryParameters parameters,
            Function<E, V> finalTransformer
    ) {
        var totalElements = getTotalElements(queryBuilder);
        var partialElements = getPartialElements(queryBuilder, parameters).stream()
                .map(finalTransformer)
                .toList();
        return new PartialQueryResult<>(parameters, totalElements, partialElements);
    }

    private <E> long getTotalElements(QueryBuilder<E> queryBuilder) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var query = criteriaBuilder.createQuery(Long.class);
        var entity = query.from(queryBuilder.getEntityClass());
        query.select(criteriaBuilder.count(entity));
        query.where(queryBuilder.getFilter(entity, criteriaBuilder));
        return entityManager.createQuery(query).getSingleResult();
    }

    private <E> List<E> getPartialElements(QueryBuilder<E> queryBuilder, PartialQueryParameters parameters) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var query = criteriaBuilder.createQuery(queryBuilder.getEntityClass());
        var entity = query.from(queryBuilder.getEntityClass());
        query.where(queryBuilder.getFilter(entity, criteriaBuilder));
        query.orderBy(queryBuilder.getOrders(entity, criteriaBuilder));
        return entityManager.createQuery(query)
                .setFirstResult(parameters.getOffset())
                .setMaxResults(parameters.getMaxResults())
                .getResultList();
    }
}
