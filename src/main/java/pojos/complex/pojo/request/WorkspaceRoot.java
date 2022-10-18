package pojos.complex.pojo.request;

public class WorkspaceRoot {
    //This is create because jackson throws error if there is no by default constructor
    public WorkspaceRoot(){

    }
    public WorkspaceRoot(Workspace workspace){
        this.workspace = workspace;
    }
    Workspace workspace;

    public Workspace getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Workspace workspace) {
        this.workspace = workspace;
    }
}
