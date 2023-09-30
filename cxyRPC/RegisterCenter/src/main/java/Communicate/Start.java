package Communicate;

import org.apache.catalina.*;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.Tomcat;

public class Start {
    public static void main(String[] args){
        String hostName = "localhost";
        int port = 9001;
        startServer(hostName,port);
    }
    public static void startServer(String hostName,int port){
        //启动tomcat服务
        Tomcat tomcat = new Tomcat();

        //发现服务
        Server server = tomcat.getServer();
        Service service = server.findService("Tomcat");
        //设置连接
        Connector connector = new Connector();
        connector.setPort(port);

        Engine engine = new StandardEngine();
        engine.setDefaultHost(hostName);

        Host host = new StandardHost();
        host.setName(hostName);

        //--
        String contextPath = "";
        Context context = new StandardContext();
        context.setPath(contextPath);
        context.addLifecycleListener(new Tomcat.FixContextListener());

        host.addChild(context);
        engine.addChild(host);
        service.setContainer(engine);
        service.addConnector(connector);

        //添加处理器
        tomcat.addServlet(contextPath,"dispatcher",new Handle_Re());
        //设置该逻辑处理的语句00
        context.addServletMappingDecoded("/*","dispatcher");
        try {
            tomcat.start();
            //阻塞--等待发送请求--
            tomcat.getServer().await();
        }catch (Exception e){
            System.out.println("启动失败.");
        }
    }
}
