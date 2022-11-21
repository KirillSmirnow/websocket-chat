package wc.utility.query.partial;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PartialQueryResult<T> {
    private final PartialQueryParameters parameters;
    private final long totalElements;
    private final List<T> content;
}
