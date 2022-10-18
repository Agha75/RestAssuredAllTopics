package pojos.create.postman.collectioncopy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FolderResponse extends FolderBase{
    List<RequestRootResponse> requestRoots;

    public FolderResponse() {

    }

    public FolderResponse(String name, List<RequestRootResponse> requestRoots) {
        super(name);
        this.requestRoots = requestRoots;
    }

    public List<RequestRootResponse> getItem() {
        return requestRoots;
    }

    public void setItem(List<RequestRootResponse> requestRoots) {
        this.requestRoots = requestRoots;
    }
}
