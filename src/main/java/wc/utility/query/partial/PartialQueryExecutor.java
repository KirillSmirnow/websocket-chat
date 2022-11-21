package wc.utility.query.partial;

import wc.utility.query.QueryBuilder;

import java.util.function.Function;

public interface PartialQueryExecutor {

    <E, V> PartialQueryResult<V> execute(
            QueryBuilder<E> queryBuilder,
            PartialQueryParameters parameters,
            Function<E, V> finalTransformer
    );
}
