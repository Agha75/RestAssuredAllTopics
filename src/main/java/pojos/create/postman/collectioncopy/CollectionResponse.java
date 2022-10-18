package pojos.create.postman.collectioncopy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CollectionResponse extends CollectionBase{
    List<FolderResponse> item;

    public CollectionResponse() {

    }

    public CollectionResponse(InfoCopy info, List<FolderResponse> myItem) {
        super(info);
        this.item = myItem;
    }

    public List<FolderResponse> getItem() {
        return item;
    }

    public void setItem(List<FolderResponse> myItem) {
        this.item = myItem;
    }
}
