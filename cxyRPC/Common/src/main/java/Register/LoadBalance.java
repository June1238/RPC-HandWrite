package Register;

import API.URL_w;

import java.util.List;
import java.util.Random;

public class LoadBalance {
    /*服务发现*/
    //负载均衡--随机挑选
    public static URL_w balanceURL(List<URL_w> list_url){
        int len = list_url.size();
        //负载均衡 实现随机访问
        return list_url.get(new Random().nextInt(len));
    }

    /*
    * 使用轮询的方式实现负载均衡
    * 判断url是高可用的 判断 1-是否可用 2-是否均衡
    * */
}
