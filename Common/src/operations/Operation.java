package operations;

import java.io.Serializable;

public class Operation implements Serializable {

    private OperationType type;
    private Object request;
    private Object response;
    private boolean success;
    private String errorMessage;

    public Operation() {
    }

    public Operation(OperationType type) {
        this.type = type;
    }

    public Operation(OperationType type, Object request) {
        this.type = type;
        this.request = request;
    }

    public OperationType getType() { return type; }
    public void setType(OperationType type) { this.type = type; }

    public Object getRequest() { return request; }
    public void setRequest(Object request) { this.request = request; }

    public Object getResponse() { return response; }
    public void setResponse(Object response) { this.response = response; }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}
