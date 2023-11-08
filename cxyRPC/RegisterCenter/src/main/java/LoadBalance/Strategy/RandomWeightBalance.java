package LoadBalance.Strategy;

import LoadBalance.AbstractLoadBalance;
import LoadBalance.MyServer;

import java.util.List;
import java.util.Random;

/*加权随机负载均衡算法*/
public class RandomWeightBalance extends AbstractLoadBalance {
    @Override
    public MyServer doSelect(List<MyServer> serverList) {
        int totalweight = 0;
        boolean isSameWeight = true;
        int first_wt = serverList.get(0).getWeight();
        for(MyServer myServer:serverList){
            totalweight += myServer.getWeight();
            if(isSameWeight&&first_wt!= myServer.getWeight())
                isSameWeight = false;
        }
        if(!isSameWeight){
            int random_weight = new Random().nextInt(totalweight);
            for(MyServer myServer:serverList){
                random_weight -= myServer.getWeight();
                if(random_weight<=0)
                    return myServer;
            }
        }
        return serverList.get(new Random().nextInt(serverList.size()));
    }
}

