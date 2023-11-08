package LoadBalance;

import API.URL_w;

public class MyServer {
    private URL_w ip;
    private int weight;
    private int currentWeight = 0;

    /*重载 构造函数*/
    //ip地址是 url+port
    public MyServer(URL_w ip){
        this.ip = ip;
    }
    public MyServer(URL_w ip,int weight){
        this.ip = ip;
        this.weight = weight;
    }
    public URL_w getIp(){return this.ip;}
    public void setIp(URL_w newIP){
        this.ip = newIP;
    }
    public int getWeight(){
        return this.weight;
    }
    public void setWeight(int weight_){
        this.weight = weight_;
    }

    //加权一致性算法
    public int getCurrentWeight() {
        return currentWeight;
    }
    public void setCurrentWeight(int currentWeight){
        this.currentWeight = currentWeight;
    }
}

