package LoadBalance.Strategy;

import LoadBalance.AbstractLoadBalance;
import LoadBalance.MyServer;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobinWightSmoothlyBalance extends AbstractLoadBalance {
    private static AtomicInteger atomicIndex = new AtomicInteger(0);
    @Override
    public MyServer doSelect(List<MyServer> serverList) {
        int totalWeight = 0;
        int firstWeight = serverList.get(0).getWeight();
        boolean isSameWeight = true;
        for(MyServer myServer : serverList){
            totalWeight += myServer.getWeight();
            if(isSameWeight && firstWeight!=myServer.getWeight()){
                isSameWeight = false;
            }
            //进行每台服务器的动态调整
            myServer.setCurrentWeight(myServer.getWeight()+ myServer.getCurrentWeight());
        }
        if(!isSameWeight)
        {
            MyServer maxCurrentWeightServer = serverList.stream().max((s1,s2)->s1.getCurrentWeight()-s2.getCurrentWeight()).get();
            maxCurrentWeightServer.setCurrentWeight(maxCurrentWeightServer.getCurrentWeight()-totalWeight);
            return maxCurrentWeightServer;
        }
        //如果不是加权 是平权
        int index = atomicIndex.getAndIncrement()% serverList.size();
        return serverList.get(index);
    }
}
