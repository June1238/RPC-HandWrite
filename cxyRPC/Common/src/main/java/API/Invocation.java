package API;

import lombok.Data;

import java.io.Serializable;

/*用于consumer端--*/
@Data
public class Invocation implements Serializable {
    private String interfaceName;
    private String version;
    private Class[] parameterTypes;
    private Object[] parameters;
    private String methodName;
    public Invocation(String interfaceName, String version, Class[] parameterTypes, Object[] parameters, String methodName){
        this.interfaceName = interfaceName;
        this.version = version;
        this.parameterTypes = parameterTypes;
        this.parameters = parameters;
        this.methodName = methodName;
    }
}
