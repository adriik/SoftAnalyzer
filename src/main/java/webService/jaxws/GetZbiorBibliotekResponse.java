
package webService.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 3.2.7
 * Sun Dec 23 11:31:50 CET 2018
 * Generated source version: 3.2.7
 */

@XmlRootElement(name = "getZbiorBibliotekResponse", namespace = "http://webService/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getZbiorBibliotekResponse", namespace = "http://webService/")

public class GetZbiorBibliotekResponse {

    @XmlElement(name = "return")
    private java.util.ArrayList<java.lang.String> _return;

    public java.util.ArrayList<java.lang.String> getReturn() {
        return this._return;
    }

    public void setReturn(java.util.ArrayList<java.lang.String> new_return)  {
        this._return = new_return;
    }

}

