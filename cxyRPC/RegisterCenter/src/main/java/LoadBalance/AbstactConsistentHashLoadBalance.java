package LoadBalance;

import java.util.List;

/*关于抽象基类--实现哈希一致性算法*/
public abstract class AbstactConsistentHashLoadBalance {
    public MyServer select(String clientIP, List<MyServer> serverList){
        return doSelect(clientIP,serverList);
    }
    public abstract MyServer doSelect(String clientIP,List<MyServer> serverList);
}
