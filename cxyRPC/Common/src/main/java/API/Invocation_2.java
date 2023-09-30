package API;

import java.io.Serializable;
import java.util.List;

public class Invocation_2 implements Serializable {
    private String interfaceName;
    private String version;
    private URL_w url_w;
    private List<String> interfaceNames;
    private List<String> versions;
    private List<URL_w> url_ws;
    /*选择在注册中心中直接讲逻辑写死 不从使用端获取*/
    private boolean flag;
    private boolean isBatch;
    //用于consumer抓取URL flag--检查通信标准 consumer端不进行is_batch的传递
    public Invocation_2(String interfaceName,String version){
        this.interfaceName = interfaceName;
        this.version = version;
        this.flag = false;
        this.isBatch = false;
    }
    public Invocation_2(String interfaceName,String version,URL_w url_w){
        this.interfaceName = interfaceName;
        this.version = version;
        this.url_w = url_w;
        this.flag = true;
        this.isBatch = false;
    }

    public Invocation_2(List<String> interfaceNames,List<String> versions,List<URL_w> url_ws){
        this.interfaceNames = interfaceNames;
        this.versions = versions;
        this.url_ws = url_ws;
        this.flag = true;
        this.isBatch = true;
    }

    //获取不同的设置--方便处理逻辑调用

    public String getInterfaceName() {
        return interfaceName;
    }

    public URL_w getUrl_w() {
        return url_w;
    }

    public String getVersion(){
        return version;
    }

    public boolean getFlag(){
        return flag;
    }

    public boolean getisBatch(){
        return this.isBatch;
    }

    public List<String> getInterfaceNames(){
        return this.interfaceNames;
    }

    public List<String> getVersions() {
        return versions;
    }

    public List<URL_w> getUrl_ws() {
        return url_ws;
    }
}
