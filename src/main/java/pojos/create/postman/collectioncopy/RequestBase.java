package pojos.create.postman.collectioncopy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class RequestBase {
    private String method;
    List<HeaderCopy> header;
    BodyCopy body;
    private String description;

    public RequestBase() {

    }

    public RequestBase( String method, List<HeaderCopy> header, BodyCopy body, String description) {
        this.method = method;
        this.header = header;
        this.body = body;
        this.description = description;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<HeaderCopy> getHeader() {
        return header;
    }

    public void setHeader(List<HeaderCopy> header) {
        this.header = header;
    }

    public BodyCopy getBody() {
        return body;
    }

    public void setBody(BodyCopy body) {
        this.body = body;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
