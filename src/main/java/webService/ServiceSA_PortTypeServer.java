
package webService;

import javax.xml.ws.Endpoint;

/**
 * This class was generated by Apache CXF 3.2.7
 * 2018-12-02T16:36:01.939+01:00
 * Generated source version: 3.2.7
 *
 */

public class ServiceSA_PortTypeServer{

    protected ServiceSA_PortTypeServer() throws Exception {
        System.out.println("Starting Server");
        Object implementor = new webService.ServiceSA();
        String address = "http://localhost:9090/ServiceSAPort";
        Endpoint.publish(address, implementor);
    }

    public static void main(String args[]) throws Exception {
        new ServiceSA_PortTypeServer();
        System.out.println("Server ready...");

        Thread.sleep(5 * 60 * 1000);
        System.out.println("Server exiting");
        System.exit(0);
    }
}

