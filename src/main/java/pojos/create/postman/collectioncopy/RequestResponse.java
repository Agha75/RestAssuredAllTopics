package pojos.create.postman.collectioncopy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestResponse extends RequestBase {
    URL url;

    public RequestResponse() {

    }

    public RequestResponse(URL url, String method, List<HeaderCopy> header, BodyCopy body, String description) {
        super(method, header, body, description);
        this.url = url;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }
}
