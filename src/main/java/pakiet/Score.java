/**
 * Score.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package pakiet;

public class Score  implements java.io.Serializable {
    private int wins;

    private int losses;

    private int ties;

    public Score() {
    }

    public Score(
           int wins,
           int losses,
           int ties) {
           this.wins = wins;
           this.losses = losses;
           this.ties = ties;
    }


    /**
     * Gets the wins value for this Score.
     * 
     * @return wins
     */
    public int getWins() {
        return wins;
    }


    /**
     * Sets the wins value for this Score.
     * 
     * @param wins
     */
    public void setWins(int wins) {
        this.wins = wins;
    }


    /**
     * Gets the losses value for this Score.
     * 
     * @return losses
     */
    public int getLosses() {
        return losses;
    }


    /**
     * Sets the losses value for this Score.
     * 
     * @param losses
     */
    public void setLosses(int losses) {
        this.losses = losses;
    }


    /**
     * Gets the ties value for this Score.
     * 
     * @return ties
     */
    public int getTies() {
        return ties;
    }


    /**
     * Sets the ties value for this Score.
     * 
     * @param ties
     */
    public void setTies(int ties) {
        this.ties = ties;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Score)) return false;
        Score other = (Score) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.wins == other.getWins() &&
            this.losses == other.getLosses() &&
            this.ties == other.getTies();
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        _hashCode += getWins();
        _hashCode += getLosses();
        _hashCode += getTies();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Score.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://pakiet", "Score"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("wins");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pakiet", "wins"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("losses");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pakiet", "losses"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ties");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pakiet", "ties"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
