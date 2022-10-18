package pojos.create.postman.collectioncopy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FolderRequest extends FolderBase {
    List<RequestRootRequest> requestRoots;

    public FolderRequest() {

    }

    public FolderRequest(String name, List<RequestRootRequest> requestRoots) {
        super(name);
        this.requestRoots = requestRoots;
    }

    public List<RequestRootRequest> getItem() {
        return requestRoots;
    }

    public void setItem(List<RequestRootRequest> requestRoots) {
        this.requestRoots = requestRoots;
    }
}
