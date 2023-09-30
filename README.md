# RPC-HandWrite
手写了一个简单的RPC框架cxyRPC，架构包括四部分：Common、Consumer、Provider以及RegisterCenter.
* Common
  Common包中提供其他包可用的公共接口和类，分为API-package和Register-package。
  * API-package中包含为consumer和provier通信方提供调用方法的公共接口以及通信时用到的消息类invocation:invocation用于consumer和provider之间传递消息；invocation_2用于register_center跟被使用端传递消息；而Hello和MoonLight接口供consumer类和provier类使用,进行方法调用；
  * Register-package中是old-version RPC无注册中心时的公共类，方便服务端和消费端进行服务的注册、负载均衡，本项目中未用到该package下的类

* RegisterCenter
  RegisterCenter用于实现服务的注册，provider端需要向服务中心注册对应服务、consumer端远程调用之前从注册中心中拿到要调用方法的所在网址；包括Communicate-package和Register-package.
  * Communicate-package用于实现注册中心跟服务端和消费者端的通信
  * Register-package用于实现provider的服务注册功能和consumer端的服务发现功能。其中服务注册功能分为单个注册和批量注册两种
 
* Provider
  Provider实现服务端功能，包括Impl、Handler以及Register三个模块
  * Impl模块包括MoonLight和Hello接口实现类的两个版本 除此 还有MainIMPl文件，用于跟注册中心通信实现服务注册
  * Register模块实现了接口和不同版本类的对应关系，便于Handler逻辑处理
  * Handler模块中实现了消息的处理逻辑
  * Main文件负责开启服务，将请求转交给Handler模块中MyHandler处理
 
* Consumer
  Consumer实现的是消费者功能，包括CallMethod和MethodProxy两个文件。CallMethod文件负责实现接口方法的调用发起；MethodProxy文件使用了动态代理机制。
