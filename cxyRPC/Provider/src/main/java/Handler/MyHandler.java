package Handler;

import API.Invocation;
import Register.LoadBalance;
import Register.ServerRegister;
import com.sun.corba.se.impl.presentation.rmi.StubInvocationHandlerImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

//调用相应方法对其进行关闭--
public class MyHandler extends HttpServlet {
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) {
        try
        {
            ObjectInputStream objectInputStream = new ObjectInputStream(request.getInputStream());
            //读取反射的到的消息
            Invocation invocation = (Invocation) objectInputStream.readObject();
            System.out.println(invocation.getInterfaceName());
            //得到想要得到的类
            Class class_my = ServerRegister.getClazz(invocation.getInterfaceName(),invocation.getVersion());
            System.out.println(invocation.getParameterTypes());
            //根据要求的类得到方法
            Method method = class_my.getMethod(invocation.getMethodName(),invocation.getParameterTypes());
            //根据拿到的类得到方法
            Object answer = method.invoke(class_my.newInstance(),invocation.getParameters());
            System.out.println(answer);
            //将获取到的结果返回给请求--写入到oos
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(response.getOutputStream());
            objectOutputStream.writeObject(answer);
            //关闭objectOutputStream
            objectOutputStream.close();

        }
        catch (Exception e){
            //写出异常信息
            e.printStackTrace();
        }
    }
}
