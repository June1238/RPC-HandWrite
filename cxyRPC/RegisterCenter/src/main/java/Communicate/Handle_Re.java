package Communicate;

import API.Invocation;
import API.Invocation_2;
import API.URL_w;
import Register.RegisterServer;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.xml.ws.spi.http.HttpHandler;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class Handle_Re extends HttpServlet {
    /*设置处理逻辑*/
    @Override
    public void service(ServletRequest req, ServletResponse res) {
        try {
            ObjectInputStream ois = new ObjectInputStream(req.getInputStream());
            Invocation_2 invoke2 = (Invocation_2) ois.readObject();
            boolean total_f = invoke2.getFlag();
            if(total_f==true)
                Register(invoke2,res);
            else if(total_f==false)
                ReturnURL(invoke2,res);
        }catch (Exception e){
            System.out.println("注册中心的处理逻辑出错啦！");
        }
    }
    /*只是设置了单个类和网址的对应关系*/
    //根据服务端发来的类和网址进行注册
    public void Register(Invocation_2 invocation,ServletResponse resp){
        try{
            String ans;
            if(invocation.getisBatch()){
                //判断是否是批量注册
                RegisterServer.BatchserverRegister(invocation.getInterfaceNames(),invocation.getVersions(),invocation.getUrl_ws());
                ans = "批量服务注册成功.";
            }
            else {
                RegisterServer.SingleserverRegister(invocation.getInterfaceName(), invocation.getVersion(), invocation.getUrl_w());
                ans = "单个服务注册成功.";
            }
            ObjectOutputStream oos = new ObjectOutputStream(resp.getOutputStream());
            oos.writeObject(ans);
            oos.close();
            System.out.println("服务端注册成功.");
        }catch (Exception e){
            System.out.println("服务端注册失败.");
        }
    }
    //根据消费端的需求进行消费返回
    public void ReturnURL(Invocation_2 invocation,ServletResponse resp){
        List<URL_w> url_ws = RegisterServer.getListOfURL(invocation.getInterfaceName(),invocation.getVersion());
        URL_w url_w = RegisterServer.LoadBalance(url_ws);
        try{
            ObjectOutputStream oos = new ObjectOutputStream(resp.getOutputStream());
            oos.writeObject(url_w);
            oos.close();
            System.out.println("消费者拉取网址成功.");
        }catch (Exception e){
            System.out.println("消费者拉取网址失败.");
        }
    }
}
