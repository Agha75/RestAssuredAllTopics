package pojos.create.postman.collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Lombok gives the ease to not generate Getter and Setter
@Getter
@Setter
// This is used to get rid of Empty constructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Body {
    private String mode;
    private String raw;

    public Body(String mode, String raw) {
        this.mode = mode;
        this.raw = raw;
    }
}
