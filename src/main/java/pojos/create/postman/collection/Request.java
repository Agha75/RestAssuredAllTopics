package pojos.create.postman.collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Request {
   /* Url in payload is String and in response its Object due to which jackson throws error,
    so we will keep it Object on both places to not face error*/
    private Object url;
    private String method;
    List<Header> header;
    Body body;
    private String description;

    public Request() {

    }

    public Request(Object url, String method, List<Header> header, Body body, String description) {
        this.url = url;
        this.method = method;
        this.header = header;
        this.body = body;
        this.description = description;
    }

    public Object getUrl() {
        return url;
    }

    public void setUrl(Object url) {
        this.url = url;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<Header> getHeader() {
        return header;
    }

    public void setHeader(List<Header> header) {
        this.header = header;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
