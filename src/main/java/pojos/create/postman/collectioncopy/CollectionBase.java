package pojos.create.postman.collectioncopy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class CollectionBase {
    InfoCopy info;

    public CollectionBase() {

    }

    public CollectionBase(InfoCopy info) {
        this.info = info;
    }

    public InfoCopy getInfo() {
        return info;
    }

    public void setInfo(InfoCopy info) {
        this.info = info;
    }
}
