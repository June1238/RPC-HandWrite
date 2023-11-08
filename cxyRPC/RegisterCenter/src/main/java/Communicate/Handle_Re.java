package Communicate;

import API.*;
import LoadBalance.MyServer;
import LoadBalance.Strategy.RandomBalance;
import Register.RegisterServer;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
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
            FlagInterface invoke2 = (FlagInterface) ois.readObject();
            boolean total_f = invoke2.isConsumer();
            if(total_f==true)
                BatchRegister(invoke2,res);
            else if(total_f==false)
                ReturnURL(invoke2,res);
        }catch (Exception e){
            System.out.print("注册中心的处理逻辑出错...");
        }
    }

    public void BatchRegister(FlagInterface invocation,ServletResponse resp){
        try{
            String ans;
            BatchRInvocation batchRInvocation = (BatchRInvocation)invocation;
            RegisterServer.CreateURL2Server(batchRInvocation.getClass2Urls(),batchRInvocation.getWeightMap());
            ans = "负载路由 批量注册成功..";
            ObjectOutputStream oos = new ObjectOutputStream(resp.getOutputStream());
            oos.writeObject(ans);
            oos.close();
            System.out.print("服务端负载路由注册成功！！");
        }catch (Exception e){
            System.out.print("服务端注册失败");
        }
    }
    public void ReturnURL(FlagInterface invocation,ServletResponse resp){
        try{
            Invocation_2 invocation2 = (Invocation_2) invocation;
            String InvokedMethod = invocation2.getInterfaceName()+invocation2.getVersion();
            List<MyServer> serverList = RegisterServer.GetConsumerURLS(InvokedMethod);
            URL_w url_w = new RandomBalance().doSelect(serverList).getIp();
            ObjectOutputStream oos = new ObjectOutputStream(resp.getOutputStream());
            oos.writeObject(url_w);
            oos.close();
            System.out.print("Consumer GET URL SUCCESS!");

        }catch (Exception e){
            System.out.print("Consumer GET URL FAILUER...");
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
        /*加入负载均衡策略
        * 轮询策略
        * 随机策略
        * 哈希一致性策略
        *
        * */
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
