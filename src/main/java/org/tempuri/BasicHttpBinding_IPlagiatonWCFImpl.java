
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package org.tempuri;

import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 3.2.7
 * 2018-12-04T20:55:17.093+01:00
 * Generated source version: 3.2.7
 *
 */

@javax.jws.WebService(
                      serviceName = "PlagiatonWCF",
                      portName = "BasicHttpBinding_IPlagiatonWCF",
                      targetNamespace = "http://tempuri.org/",
                      wsdlLocation = "http://plagiaton-wcf.azurewebsites.net/PlagiatonWCF.svc?wsdl",
                      endpointInterface = "org.tempuri.IPlagiatonWCF")

public class BasicHttpBinding_IPlagiatonWCFImpl implements IPlagiatonWCF {

    private static final Logger LOG = Logger.getLogger(BasicHttpBinding_IPlagiatonWCFImpl.class.getName());

    /* (non-Javadoc)
     * @see org.tempuri.IPlagiatonWCF#setAvailableMethods(java.lang.String project, com.microsoft.schemas._2003._10.serialization.arrays.ArrayOfstring methods)*
     */
    public java.lang.String setAvailableMethods(java.lang.String project, com.microsoft.schemas._2003._10.serialization.arrays.ArrayOfstring methods) {
        LOG.info("Executing operation setAvailableMethods");
        System.out.println(project);
        System.out.println(methods);
        try {
            java.lang.String _return = "_return2023740472";
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

}
