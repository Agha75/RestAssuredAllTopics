package pojos.create.postman.collectioncopy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//This will ignore the properties which are not mentioned in POJOs but appears in response
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class CollectionRootBase {

    public CollectionRootBase() {

    }
}
