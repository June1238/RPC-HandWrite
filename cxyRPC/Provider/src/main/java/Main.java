import API.URL_w;
import Handler.MyHandler;
import Impl.HelloImpl_1;
import Impl.HelloImpl_2;
import Impl.MoonImpl_1;
import Impl.MoonImpl_2;
import Register.ServerRegister;
import Register.serviceRegister;
import org.apache.catalina.*;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.Tomcat;

import java.util.List;


public class Main {
    public final static int PORT = 9000;
    public static void main(String[] args){
        //全部向同一个网址进行注册 可以进行单个注册或者批量注册 都可以--
        URL_w url = new URL_w("localhost",9000);
        SingleLocalRegister("Hello","1.0",HelloImpl_1.class);
        SingleLocalRegister("Hello","2.0",HelloImpl_2.class);
        SingleLocalRegister("MoonLight","1.0", MoonImpl_1.class);
        SingleLocalRegister("MoonLight","2.0", MoonImpl_2.class);
        start("localhost",9000);
    }
    public static void start(String hostName,int port){

        //在此处启动一个tomcat服务
        Tomcat tomcat = new Tomcat();

        Server server = tomcat.getServer();
        //找到tomcat的服务 发现服务的时候应该注意tomcat的大小写
        Service service = server.findService("Tomcat");
        Connector connector = new Connector();
        connector.setPort(port);

        Engine engine = new StandardEngine();
        engine.setDefaultHost(hostName);

        Host host = new StandardHost();
        host.setName(hostName);

        String contextPath = "";
        Context context = new StandardContext();
        context.setPath(contextPath);
        context.addLifecycleListener(new Tomcat.FixContextListener());

        host.addChild(context);
        engine.addChild(host);
        service.setContainer(engine);
        service.addConnector(connector);

        //添加处理器
        tomcat.addServlet(contextPath,"dispatcher", new MyHandler());
        //将所有的请求都交给该处理器进行处理
        context.addServletMappingDecoded("/*","dispatcher");
        //开启tomcat的服务器
        try{
            tomcat.start();
            tomcat.getServer().await();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    /*两种注册方式-- 单个接口、类和版本的注册 以及批量注册*/
    /*让服务更加灵活--注册的是服务及其对应网址--批量注册*/
    public static void BatchServiceRegister(List<String> interfaceNames,List<String> versions,List<URL_w> urls){
        //向网络中心进行注册--
        try{
            for (int i = 0;i<interfaceNames.size();i++)
                serviceRegister.add(interfaceNames.get(i),versions.get(i),urls.get(i));
            System.out.println("向网络中心注册成功！");
        }catch (Exception e){
            System.out.println("向网络中心注册失败。。");
        }

    }
    /*注册的是接口及其对应实现类*/
    public static void BatchLocalRegister(List<String> interfaceNames,List<String> versions,List<Class> implclazz){
        //根据接口+版本号 得到其对应的实现类
        try {
            for (int i = 0; i < interfaceNames.size(); i++)
                ServerRegister.registerServer(interfaceNames.get(i), versions.get(i), implclazz.get(i));
            System.out.println("服务注册成功。");
        }catch (Exception e){
            System.out.println("服务注册失败啦啊哈");
        }
    }

    /*暂时将注册服务写死 --后面加入package进行灵活配置--单个注册*/
    public static void SingleLocalRegister(String interfaceName,String version,Class clazz){
        ServerRegister.registerServer(interfaceName,version,clazz);
    }
}
