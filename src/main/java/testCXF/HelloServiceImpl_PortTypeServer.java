
package testCXF;

import javax.xml.ws.Endpoint;

/**
 * This class was generated by Apache CXF 3.2.7
 * 2018-11-29T16:18:17.417+01:00
 * Generated source version: 3.2.7
 *
 */

public class HelloServiceImpl_PortTypeServer{

    protected HelloServiceImpl_PortTypeServer() throws Exception {
        System.out.println("Starting Server");
        Object implementor = new testCXF.HelloServiceImpl();
        String address = "http://localhost:9090/HelloServiceImplPort";
        Endpoint.publish(address, implementor);
    }

    public static void main(String args[]) throws Exception {
        new HelloServiceImpl_PortTypeServer();
        System.out.println("Server ready...");

        Thread.sleep(5 * 60 * 1000);
        System.out.println("Server exiting");
        System.exit(0);
    }
}

