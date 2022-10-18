package pojos.create.postman.collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Folder {
    private String name;
    List<RequestRoot> requestRoots;

    public Folder() {

    }

    public Folder(String name, List<RequestRoot> requestRoots) {
        this.name = name;
        this.requestRoots = requestRoots;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RequestRoot> getItem() {
        return requestRoots;
    }

    public void setItem(List<RequestRoot> requestRoots) {
        this.requestRoots = requestRoots;
    }
}
