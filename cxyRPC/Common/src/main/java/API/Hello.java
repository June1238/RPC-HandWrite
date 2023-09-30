package API;

import java.io.Serializable;

//作为公共接口--
public interface Hello extends Serializable {
    public String sayHello(String name);
}
