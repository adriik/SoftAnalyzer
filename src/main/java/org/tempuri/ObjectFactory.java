
package org.tempuri;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import com.microsoft.schemas._2003._10.serialization.arrays.ArrayOfstring;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.tempuri package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SetAvailableMethodsProject_QNAME = new QName("http://tempuri.org/", "project");
    private final static QName _SetAvailableMethodsMethods_QNAME = new QName("http://tempuri.org/", "methods");
    private final static QName _SetAvailableMethodsResponseSetAvailableMethodsResult_QNAME = new QName("http://tempuri.org/", "setAvailableMethodsResult");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.tempuri
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SetAvailableMethods }
     * 
     */
    public SetAvailableMethods createSetAvailableMethods() {
        return new SetAvailableMethods();
    }

    /**
     * Create an instance of {@link SetAvailableMethodsResponse }
     * 
     */
    public SetAvailableMethodsResponse createSetAvailableMethodsResponse() {
        return new SetAvailableMethodsResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "project", scope = SetAvailableMethods.class)
    public JAXBElement<String> createSetAvailableMethodsProject(String value) {
        return new JAXBElement<String>(_SetAvailableMethodsProject_QNAME, String.class, SetAvailableMethods.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfstring }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "methods", scope = SetAvailableMethods.class)
    public JAXBElement<ArrayOfstring> createSetAvailableMethodsMethods(ArrayOfstring value) {
        return new JAXBElement<ArrayOfstring>(_SetAvailableMethodsMethods_QNAME, ArrayOfstring.class, SetAvailableMethods.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tempuri.org/", name = "setAvailableMethodsResult", scope = SetAvailableMethodsResponse.class)
    public JAXBElement<String> createSetAvailableMethodsResponseSetAvailableMethodsResult(String value) {
        return new JAXBElement<String>(_SetAvailableMethodsResponseSetAvailableMethodsResult_QNAME, String.class, SetAvailableMethodsResponse.class, value);
    }

}
