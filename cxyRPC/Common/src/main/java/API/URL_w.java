package API;

import lombok.Data;

import java.io.Serializable;

@Data
public class URL_w implements Serializable {
    private int port;
    private String hostname;
    public URL_w(String hostname,int port){
        this.hostname = hostname;
        this.port = port;
    }

}
