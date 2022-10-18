package pojos.create.postman.collectioncopy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//This will ignore the properties which are not mentioned in POJOs but appears in response
@JsonIgnoreProperties(ignoreUnknown = true)
public class CollectionRootRequest extends CollectionRootBase {
    CollectionRequest collection;

    public CollectionRootRequest() {

    }

    public CollectionRootRequest(CollectionRequest collection) {
        this.collection = collection;
    }

    public CollectionRequest getCollection() {
        return collection;
    }

    public void setCollection(CollectionRequest collection) {
        this.collection = collection;
    }
}
