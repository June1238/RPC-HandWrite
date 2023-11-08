package Register;

import API.URL_w;

import java.util.*;

import LoadBalance.MyServer;

//应当支持批量注册的;
public class RegisterServer {
    public static HashMap<String, List<URL_w>> server_center = new HashMap<>();
    public static HashMap<String,List<MyServer>> interface_serverList = new HashMap<>();

    /*
    * 使用两个hashMap来实现用户到网址 以及网址到Server Weight的映射
    * */
    public static void CreateURL2Server(HashMap<String,List<URL_w>> string2url,HashMap<URL_w,Integer> URL2Weight){
        for(Map.Entry<String,List<URL_w>> entry:string2url.entrySet()){
            List<MyServer> list = new ArrayList<>();
            for(URL_w url_w:entry.getValue())
            {
                MyServer myServer = new MyServer(url_w,URL2Weight.get(url_w));
                list.add(myServer);
            }
            interface_serverList.put(entry.getKey(),list);
        }
    }

    public static List<MyServer> GetConsumerURLS(String InvokedMethod){
        return interface_serverList.get(InvokedMethod);
    }

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
