package wc.utility.query.partial;

import lombok.Data;

@Data
public class PartialQueryParameters {
    private final int offset;
    private final int maxResults;
}
