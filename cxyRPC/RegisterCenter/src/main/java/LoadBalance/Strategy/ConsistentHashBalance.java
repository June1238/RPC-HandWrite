package LoadBalance.Strategy;

import LoadBalance.AbstactConsistentHashLoadBalance;
import LoadBalance.MyServer;

import java.util.List;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;

public class ConsistentHashBalance extends AbstactConsistentHashLoadBalance {
    private ConsistentHashSelector consistentHashSelector;
    @Override
    public MyServer doSelect(String clientIP, List<MyServer> serverList) {
        initialConsistentHashSelector(serverList);
        return consistentHashSelector.select(clientIP);
    }
    //构建哈希环
    public void initialConsistentHashSelector(List<MyServer> serverList){
        // 计算提供者列表哈希值
        Integer newIdentityHashCode = System.identityHashCode(serverList);

        // 提供者列表哈希值没有变化则无需重新构建哈希环
        if (null != consistentHashSelector && (null != consistentHashSelector.getIdentityHashCode() && newIdentityHashCode == consistentHashSelector.getIdentityHashCode())) {
            return;
        }
        // 提供者列表哈希值发生变化则重新构建哈希环
        consistentHashSelector = new ConsistentHashSelector(newIdentityHashCode, serverList);
    }
    private class ConsistentHashSelector{
        //构建treeMap
        private Integer identityHashCode;
        private TreeMap<Integer, MyServer> serverNodes = new TreeMap<Integer,MyServer>();

        //构建哈希环
        public ConsistentHashSelector(Integer identityHashCode, List<MyServer> serverList){
            this.identityHashCode = identityHashCode;
            TreeMap<Integer,MyServer> newServerNodes = new TreeMap<Integer,MyServer>();
            for(MyServer server:serverList)
                newServerNodes.put(hashCode(server.getIp().toString()),server);
            serverNodes = newServerNodes;
        }
        public MyServer select(String clientIP){
            //计算客户端的ip哈希值
            int clientHashKey = hashCode(clientIP);
            //找到第一个大于客户端哈希值的服务器
            SortedMap<Integer,MyServer> tailMap = serverNodes.tailMap(clientHashKey,false);
            //如果找不到 那么就选择第一个节点
            if(tailMap.isEmpty()){
                Integer firstKey = serverNodes.firstKey();
                return serverNodes.get(firstKey);
            }
            return serverNodes.get(tailMap.firstKey());
        }
        //使用对象自带的hashCode得到对应的哈希码
        private int hashCode(String key){
            return Objects.hashCode(key);
        }

        //提供者列表哈希值 --> 如果新增或者删除提供者将会发生变化
        public Integer getIdentityHashCode(){
            return identityHashCode;
        }
    }

}
