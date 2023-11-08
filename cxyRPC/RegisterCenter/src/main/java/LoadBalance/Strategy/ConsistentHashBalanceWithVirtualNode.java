package LoadBalance.Strategy;

import LoadBalance.AbstactConsistentHashLoadBalance;
import LoadBalance.MyServer;

import java.util.List;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;

public class ConsistentHashBalanceWithVirtualNode extends AbstactConsistentHashLoadBalance {
    private ConsistentHashSelector consistentHashSelector;
    @Override
    public MyServer doSelect(String clientIP, List<MyServer> serverList) {
        initialSelector(serverList);
        return consistentHashSelector.select(clientIP);
    }

    private class ConsistentHashSelector {
        private Integer identityHashCode;
        //使用16个虚拟节点-- virtual_nodes = 16
        private Integer VIRTUAL_NODES_NUM = 16;
        private TreeMap<Integer /* hashcode */, MyServer> serverNodes = new TreeMap<Integer, MyServer>();

        public ConsistentHashSelector(Integer identityHashCode, List<MyServer> serverList) {
            this.identityHashCode = identityHashCode;
            TreeMap<Integer, MyServer> newServerNodes = new TreeMap<Integer, MyServer>();
            for (MyServer server : serverList) {
                // 虚拟节点
                for (int i = 0; i < VIRTUAL_NODES_NUM; i++) {
                    int virtualKey = hashCode(server.getIp() + "_" + i);
                    newServerNodes.put(virtualKey, server);
                }
            }
            serverNodes = newServerNodes;
        }

        public MyServer select(String clientIP) {
            int clientHashCode = hashCode(clientIP);
            SortedMap<Integer, MyServer> tailMap = serverNodes.tailMap(clientHashCode, false);
            if (tailMap.isEmpty()) {
                Integer firstKey = serverNodes.firstKey();
                return serverNodes.get(firstKey);
            }
            Integer firstKey = tailMap.firstKey();
            return tailMap.get(firstKey);
        }

        private int hashCode(String key) {
            return Objects.hashCode(key);
        }

        public Integer getIdentityHashCode() {
            return identityHashCode;
        }
    }

    private void initialSelector(List<MyServer> serverList) {
        Integer newIdentityHashCode = System.identityHashCode(serverList);
        if (null != consistentHashSelector && (null != consistentHashSelector.getIdentityHashCode() && newIdentityHashCode == consistentHashSelector.getIdentityHashCode())) {
            return;
        }
        consistentHashSelector = new ConsistentHashSelector(newIdentityHashCode, serverList);
    }
}
