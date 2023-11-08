package LoadBalance.Strategy;

import LoadBalance.AbstractLoadBalance;
import LoadBalance.MyServer;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/*使用简单加权轮询策略实现负载均衡*/
public class RoundRobinWeightBalance extends AbstractLoadBalance {
    /*自增操作是原子性的 是静态的*/
    private static AtomicInteger atomicIndex= new AtomicInteger(0);
    @Override
    public MyServer doSelect(List<MyServer> serverList) {
        int totalWeight = 0;
        int firstWeight = serverList.get(0).getWeight();
        boolean isSame = true;
        for(MyServer myServer:serverList){
            totalWeight += myServer.getWeight();
            if(isSame&&firstWeight!=myServer.getWeight())
                isSame = false;
        }
        if(!isSame){
            /*加权轮询负载均衡算法 -- 加权*/
            int offset = atomicIndex.getAndIncrement()%totalWeight;
            for(MyServer myServer:serverList){
                offset -= myServer.getWeight();
                if(offset<=0)
                    return myServer;
            }
        }
        int index = atomicIndex.getAndIncrement()%serverList.size();
        return serverList.get(index);
    }
}

