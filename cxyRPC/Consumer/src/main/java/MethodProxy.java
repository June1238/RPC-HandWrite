import API.Invocation;
import API.Invocation_2;
import API.URL_w;
import Register.LoadBalance;
import Register.serviceRegister;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

//实现jdk动态代理--
public class MethodProxy {
    /*
    * RPC框架一般都有3个角色 服务提供者、服务消费者以及注册中心 服务消费者从注册中心拉取服务地址-根据地址向服务提供者发起RPC调用
    * 动态代理：对于服务消费者，一般只会依赖服务接口，而服务的具体实现是在服务提供者这端的 服务消费者和服务提供者分别部署在不同的服务器上
    * 服务消费者调用接口中的方法时--得到结果，使用JDK的动态代理
    *
    * */

    //传入类 实现类的动态代理
    /*入参分别是 类 和 版本*/
    private Class object_;
    private String version;
    public MethodProxy(Class object_1,String version){
        object_ = object_1;
        this.version = version;
    }
    //重写方法 返回
    public Object callM(){
        /*关于类加载器和接口名称的问题*/
        //args: 类加载器 父接口数组 处理器
        return Proxy.newProxyInstance(MethodProxy.class.getClassLoader(), new Class[]{object_},
                new InvocationHandler()
        {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object object = null;
                //调用服务发现方法--得到能够访问该方法的网址是which
                URL_w url_w_ = getServiceURL(object_.getSimpleName(),version);
                //实现访问
                //说明通过url进行数据写  flag设置为true
                Invocation invocation = new Invocation(object_.getSimpleName(),version,new Class[]{args[0].getClass()},args,method.getName());

                //不必刷新 flush close()之前就直接会刷新

                /*
                * public void close() throws IOException {
                    flush();
                    clear();
                    bout.close();
                    }
                */

                //读取信息并返回
                return getServerMethodResult(invocation,url_w_);
            }
        });
    }

    public URL_w getServiceURL(String interfaceName,String version){
        try {
            //向服务器注册-- 得到url
            URL url = new URL("http://localhost:9001");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            Invocation_2 invocation_2 = new Invocation_2(interfaceName,version);
            conn.setDoOutput(true);
            ObjectOutputStream oos = new ObjectOutputStream(conn.getOutputStream());
            oos.writeObject(invocation_2);
            oos.close();
            ObjectInputStream ois = new ObjectInputStream(conn.getInputStream());
            URL_w url_w = (URL_w) ois.readObject();
            return url_w;
        }catch (Exception e){
            System.out.println("向注册中心拉取网址失败");
        }
        return null;
    }

    public Object getServerMethodResult(Invocation invocation,URL_w url_w){
        try{
            URL url = new URL("http://"+url_w.getHostname()+":"+url_w.getPort());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //说明通过url进行数据写  将布尔值设置为true
            conn.setDoOutput(true);
            OutputStream oos = new ObjectOutputStream(conn.getOutputStream());
            ((ObjectOutputStream) oos).writeObject(invocation);

                //不必刷新 flush close()之前就直接会刷新
                /*
                * public void close() throws IOException {
                    flush();
                    clear();
                    bout.close();
                    }
                */
            oos.close();

            //读取信息并返回
            Object res = new ObjectInputStream(conn.getInputStream()).readObject();
            return res;
        }catch (Exception e){
            System.out.println("跟服务器通信失败");
        }
        return null;
    }
}
