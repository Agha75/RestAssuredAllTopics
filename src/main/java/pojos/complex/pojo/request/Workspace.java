package pojos.complex.pojo.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;


// NOTE: All of them can be used on both class and variable level
// @JsonInclude(JsonInclude.Include.NON_NULL) //Annotation is used to ignore null value
// @JsonInclude(JsonInclude.Include.NON_DEFAULT) //this will ignore both null and default values
// @JsonInclude(JsonInclude.Include.NON_EMPTY) //this will ignore the empty value of hashmap or list it also works for null value
// @JsonIgnore //this will ignore the fields on both serialization and de-serialization

/*This can be only set on class level and pass the fields
  @JsonIgnoreProperties(value = "id", allowSetters = true)
  this is used when want to that field should be used for serialization or de-serialization
  Note: If you want property to be used in Serialization -->then allowGetters  OR
  If you want property to be used in DeSerialization -->then allowSetters*/
public class Workspace {
    private String id;
    private String name;
    private String type;
    private String description;

    //This is create because jackson throws error if there is no by default constructor
    public Workspace(){

    }
    public Workspace(String name, String type, String description){
        this.name = name;
        this.type = type;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
