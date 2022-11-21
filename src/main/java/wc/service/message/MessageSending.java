package wc.service.message;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class MessageSending {

    @NotNull
    @NotBlank
    @Size(max = 5000)
    private final String text;
}
