package LoadBalance.Strategy;

import LoadBalance.AbstractLoadBalance;
import LoadBalance.MyServer;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobinBalance extends AbstractLoadBalance {
    /*使用静态代码块*/
    private static AtomicInteger atomicIntdex = new AtomicInteger(0);
    @Override
    public  MyServer doSelect(List<MyServer> serverList) {
        int index = atomicIntdex.getAndIncrement()%serverList.size();
        return serverList.get(index);
    }
}
