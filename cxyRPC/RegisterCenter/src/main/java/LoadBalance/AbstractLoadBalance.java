package LoadBalance;

import java.util.List;

public abstract class AbstractLoadBalance {
    public MyServer select(List<MyServer> serverList){
        return doSelect(serverList);
    }
    public abstract MyServer doSelect(List<MyServer> serverList);
}
