/**
 * ScoreServcieServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pakiet;

public class ScoreServcieServiceLocator extends org.apache.axis.client.Service implements pakiet.ScoreServcieService {

    public ScoreServcieServiceLocator() {
    }


    public ScoreServcieServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ScoreServcieServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ScoreServcie
    private java.lang.String ScoreServcie_address = "http://13.80.1.8:8080/SoftAnalyzer/services/ScoreServcie";

    public java.lang.String getScoreServcieAddress() {
        return ScoreServcie_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ScoreServcieWSDDServiceName = "ScoreServcie";

    public java.lang.String getScoreServcieWSDDServiceName() {
        return ScoreServcieWSDDServiceName;
    }

    public void setScoreServcieWSDDServiceName(java.lang.String name) {
        ScoreServcieWSDDServiceName = name;
    }

    public pakiet.ScoreServcie getScoreServcie() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ScoreServcie_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getScoreServcie(endpoint);
    }

    public pakiet.ScoreServcie getScoreServcie(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            pakiet.ScoreServcieSoapBindingStub _stub = new pakiet.ScoreServcieSoapBindingStub(portAddress, this);
            _stub.setPortName(getScoreServcieWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setScoreServcieEndpointAddress(java.lang.String address) {
        ScoreServcie_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (pakiet.ScoreServcie.class.isAssignableFrom(serviceEndpointInterface)) {
                pakiet.ScoreServcieSoapBindingStub _stub = new pakiet.ScoreServcieSoapBindingStub(new java.net.URL(ScoreServcie_address), this);
                _stub.setPortName(getScoreServcieWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("ScoreServcie".equals(inputPortName)) {
            return getScoreServcie();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://pakiet", "ScoreServcieService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://pakiet", "ScoreServcie"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ScoreServcie".equals(portName)) {
            setScoreServcieEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
