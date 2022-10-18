package pojos.create.postman.collectioncopy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//This will ignore the properties which are not mentioned in POJOs but appears in response
@JsonIgnoreProperties(ignoreUnknown = true)
public class CollectionRootResponse extends CollectionRootBase {
    CollectionResponse collection;

    public CollectionRootResponse() {

    }

    public CollectionRootResponse(CollectionResponse collection) {
        this.collection = collection;
    }

    public CollectionResponse getCollection() {
        return collection;
    }

    public void setCollection(CollectionResponse collection) {
        this.collection = collection;
    }
}
