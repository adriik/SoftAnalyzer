package testCXF;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import javax.jws.WebService;

import box.Pack;
import box.Project;

@WebService(targetNamespace = "http://testCXF/", portName = "HelloServiceImplPort", serviceName = "HelloServiceImplService")
public class HelloServiceImpl {
	 
    public String getVersion() {
        return "1.0";
    }
 
    public String hello(String user) {
        return "Hello " + user + "!";
    }
	

}