package Register;

import java.sql.Struct;
import java.util.HashMap;

public class ServerRegister {
    /*根据接口+版本号在哈希映射中存入对应的类*/
    public static HashMap<String,Class> hashMap = new HashMap<>();
    //根据 接口 版本控制 -> 对应的类
    public static void registerServer(String interfaceName,String version,Class ImplClass){
        hashMap.put(interfaceName+version,ImplClass);
    }
    //返回接口版本对应的类
    public static Class getClazz(String interfaceName, String version){
        return hashMap.get(interfaceName+version);
    }
}
