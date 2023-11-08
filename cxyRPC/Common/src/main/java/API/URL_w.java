package API;

import lombok.Data;

import java.io.Serializable;

@Data
public class URL_w implements Serializable {
    private int port;
    private String hostname;

    /*设置网址和端口号：url+port*/
    public URL_w(String hostname){
        this.hostname = hostname;
    }
    public void setPort(int port){
        this.port = port;
    }
    public URL_w(String hostname,int port){
        this.hostname = hostname;
        this.port = port;
    }
    public String toString(){
        return hostname+":"+port;
    }
}
