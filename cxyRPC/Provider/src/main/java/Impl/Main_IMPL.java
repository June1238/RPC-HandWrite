package Impl;

import API.BatchRInvocation;
import API.Invocation_2;
import API.URL_w;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main_IMPL {
    public static URL_w url = new URL_w("localhost");
    public static void main(String[] args){
        try
        {
            //向注册中心进行注册
            URL url = new URL("http://localhost:9001");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            OutputStream oos = new ObjectOutputStream(conn.getOutputStream());
            /*批量注册--*/
            BatchRInvocation batchRInvocation = batchRegister_II();
            Invocation_2 invocation = batchRegister();
            ((ObjectOutputStream) oos).writeObject(invocation);
            oos.close();

            /*发送消息完毕 开始接收消息*/
            String ans = (String)new ObjectInputStream(conn.getInputStream()).readObject();
            System.out.println(ans);
        }
        catch (Exception e){
            System.out.println("向服务端注册失败.");
        }
    }
    //
    public static Invocation_2 singleRegister(){
        /*
        */
        return new Invocation_2("Hello","1.0",url);
    }
    /*
    * 优化：作为类进行封装即可--
    * */
    public static Invocation_2 batchRegister(){
        url.setPort(9000);
        List<String> interfaceNames = new ArrayList<>();
        List<String> versions = new ArrayList<>();
        List<URL_w> url_ws = new ArrayList<>();
        interfaceNames.add("Hello");
        interfaceNames.add("Hello");
        interfaceNames.add("MoonLight");
        interfaceNames.add("MoonLight");
        versions.add("1.0");
        versions.add("2.0");
        versions.add("1.0");
        versions.add("2.0");
        for(int i = 0;i<4;i++)
            url_ws.add(url);
        return new Invocation_2(interfaceNames,versions,url_ws);
    }

    public static BatchRInvocation batchRegister_II(){
        //设置四个端口号
        int[] portMap = new int[]{9000,9001,9002,9003,9005};
        List<URL_w> urlWList = new ArrayList<URL_w>();
        HashMap<URL_w,Integer> weightMap = new HashMap<>();
        //设置5个不一样的url
        for(int i = 0;i<5;i++){
            URL_w urlw = new URL_w(url.getHostname(),portMap[i]);
            urlWList.add(urlw);
            weightMap.put(urlw,i+1);
        }
        String hello1 = "Hello"+"1.0";
        String hello2 = "Hello"+"2.0";
        String moon1 = "MoonLight"+"1.0";
        String moon2 = "MoonLight"+"2.0";
        HashMap<String,List<URL_w>> FMap = new HashMap<>();
        FMap.put(hello1,urlWList);
        FMap.put(hello2,urlWList);
        FMap.put(moon1,urlWList);
        FMap.put(moon2,urlWList);
        return new BatchRInvocation(FMap,weightMap);
    }
}
