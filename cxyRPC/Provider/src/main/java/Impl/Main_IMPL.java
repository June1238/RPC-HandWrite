package Impl;

import API.Invocation_2;
import API.URL_w;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Main_IMPL {
    public static URL_w url = new URL_w("localhost",9000);
    public static void main(String[] args){
        try {
            //向注册中心进行注册
            URL url = new URL("http://localhost:9001");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            OutputStream oos = new ObjectOutputStream(conn.getOutputStream());
            Invocation_2 invocation = batchRegister();
            ((ObjectOutputStream) oos).writeObject(invocation);
            oos.close();

            //读取信息
            String ans = (String)new ObjectInputStream(conn.getInputStream()).readObject();
            System.out.println(ans);
        }catch (Exception e){
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
}
