package Register;

import API.URL_w;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*加入Nacos进行注册中心的优化
* 通过多线程进行注册-- 进行url的获取
* */

/*
* 访问该函数的分别是consumer和Provider两个进程
* 无法保证数据的一致性
* */
public class serviceRegister {

    public static HashMap<String, List<URL_w>> hashMap = new HashMap<>();
    public static void add(String interfaceName,String version,URL_w url)
    {
        List<URL_w> list_temp = new ArrayList<>();
        if(hashMap.containsKey(interfaceName+version)){
            list_temp = hashMap.get(interfaceName+version);
        }
        list_temp.add(url);
        hashMap.put(interfaceName+version,list_temp);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./service.txt"));
            oos.writeObject(hashMap);
            oos.close();
            System.out.println("对象写入完毕--");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static List<URL_w> getURL(String interfaceName,String version){
        //返回列表网址
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./service.txt"));
            HashMap<String,List<URL_w>> hashMap = (HashMap<String, List<URL_w>>) ois.readObject();
            ois.close();
            return hashMap.get(interfaceName+version);

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
//        return hashMap.get(interfaceName+version);
    }
}
