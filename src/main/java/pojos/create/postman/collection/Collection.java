package pojos.create.postman.collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Collection {
    Info info;
    List<Folder> item;

    public Collection() {

    }

    public Collection(Info info, List<Folder> myItem) {
        this.info = info;
        this.item = myItem;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public List<Folder> getItem() {
        return item;
    }

    public void setItem(List<Folder> myItem) {
        this.item = myItem;
    }
}
