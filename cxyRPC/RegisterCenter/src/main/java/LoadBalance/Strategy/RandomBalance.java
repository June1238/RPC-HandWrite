package LoadBalance.Strategy;

import LoadBalance.AbstractLoadBalance;
import LoadBalance.MyServer;

import java.util.List;
import java.util.Random;

/*随机负载均衡算法*/
public class RandomBalance extends AbstractLoadBalance {
    @Override
    public MyServer doSelect(List<MyServer> serverList) {
        int index = new Random().nextInt(serverList.size());
        return serverList.get(index);
    }
}
