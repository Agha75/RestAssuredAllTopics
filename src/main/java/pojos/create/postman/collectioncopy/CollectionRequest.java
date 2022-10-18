package pojos.create.postman.collectioncopy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CollectionRequest extends CollectionBase{
    List<FolderRequest> item;

    public CollectionRequest() {

    }

    public CollectionRequest(InfoCopy info, List<FolderRequest> myItem) {
        super(info);
        this.item = myItem;
    }

    public List<FolderRequest> getItem() {
        return item;
    }

    public void setItem(List<FolderRequest> myItem) {
        this.item = myItem;
    }
}
