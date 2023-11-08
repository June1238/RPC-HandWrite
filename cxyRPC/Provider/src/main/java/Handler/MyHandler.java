package Handler;

import API.Invocation;
import Register.LoadBalance;
import Register.ServerRegister;

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
            /* 读取反射传来的对象-- readObject() */
            Invocation invocation = (Invocation) objectInputStream.readObject();
            System.out.println(invocation.getInterfaceName());
            /*
            * 根据对象反射得到类
            * 根据类反射得到方法 拿到方法之后传入类实例和参数得到调用结果-- 传回(class_my.newInstance())
            * invocation.getParameters();
            * */
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
