package pojos.create.postman.collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//This will ignore the properties which are not mentioned in POJOs but appears in response
@JsonIgnoreProperties(ignoreUnknown = true)
public class CollectionRoot {
    Collection collection;

    public CollectionRoot() {

    }

    public CollectionRoot(Collection collection) {
        this.collection = collection;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }
}
