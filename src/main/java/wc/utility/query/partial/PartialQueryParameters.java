package wc.utility.query.partial;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class PartialQueryParameters {

    @Min(0)
    @Max(Integer.MAX_VALUE)
    private int offset = 0;

    @Min(0)
    @Max(100)
    private int maxResults = 20;
}
