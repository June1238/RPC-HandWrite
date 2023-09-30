import API.Hello;
import API.MoonLight;

public class CallMethod {
    public static void main(String[] args){
        Hello hh = (Hello) new MethodProxy(Hello.class,"1.0").callM();
        System.out.println(hh.sayHello("World"));
        MoonLight moonLight = (MoonLight) new MethodProxy(MoonLight.class,"2.0").callM();
        System.out.println(moonLight.LoveMoon("MoonLight"));
    }
}
