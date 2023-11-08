package API;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Data
public class BatchRInvocation implements Serializable,FlagInterface {
    private HashMap<String, List<URL_w>> Class2Urls = new HashMap<>();
    private HashMap<URL_w,Integer> WeightMap = new HashMap<>();
    public BatchRInvocation(HashMap<String,List<URL_w>> class2Urls,HashMap<URL_w,Integer> weightMap){
        this.Class2Urls = class2Urls;
        this.WeightMap = weightMap;
    }

    @Override
    public boolean isConsumer() {
        return false;
    }
}
