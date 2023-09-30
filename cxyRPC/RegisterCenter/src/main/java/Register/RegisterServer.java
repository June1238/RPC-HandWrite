package Register;

import API.URL_w;
import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

//应当支持批量注册的;
public class RegisterServer {
    public static HashMap<String, List<URL_w>> server_center = new HashMap<>();

    public static void BatchserverRegister(List<String> interfaceNames,List<String> versions,List<URL_w> url_ws) {
        for (int i = 0; i < interfaceNames.size(); i++)
        {
            System.out.println(interfaceNames.get(i)+versions.get(i));
            SingleserverRegister(interfaceNames.get(i), versions.get(i), url_ws.get(i));
        }
        System.out.println(server_center.get("Hello"+"1.0").size());
    }

    /*单个中心地址注册*/
    public static void SingleserverRegister(String interfacename,String version,URL_w url_w){
        String interfaceAndVersion = interfacename+version;
        List<URL_w> listofURL = server_center.getOrDefault(interfaceAndVersion,new ArrayList<>());
        listofURL.add(url_w);
        server_center.put(interfaceAndVersion,listofURL);
    }

    public static List<URL_w> getListOfURL(String interfaceName,String version){
        System.out.println("Hallo!");
        String interfaceKey = interfaceName+version;
        return server_center.get(interfaceKey);
    }

    //暂时的随机查询
    public static URL_w LoadBalance(List<URL_w> list_url){
        int index = new Random().nextInt(list_url.size());
        return list_url.get(index);
    }
}
