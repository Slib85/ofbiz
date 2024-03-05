
/**
 * FilterOperator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:34:40 IST)
 */

            
                package com.bronto.api.v4;
            

            /**
            *  FilterOperator bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class FilterOperator
        implements org.apache.axis2.databinding.ADBBean{
        
                public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
                "http://api.bronto.com/v4",
                "filterOperator",
                "ns1");

            

                        /**
                        * field for FilterOperator
                        */

                        
                                    protected java.lang.String localFilterOperator ;
                                
                            private static java.util.HashMap _table_ = new java.util.HashMap();

                            // Constructor
                            
                                protected FilterOperator(java.lang.String value, boolean isRegisterValue) {
                                    localFilterOperator = value;
                                    if (isRegisterValue){
                                        
                                               _table_.put(localFilterOperator, this);
                                           
                                    }

                                }
                            
                                    public static final java.lang.String _EqualTo =
                                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString("EqualTo");
                                
                                    public static final java.lang.String _NotEqualTo =
                                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString("NotEqualTo");
                                
                                    public static final java.lang.String _StartsWith =
                                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString("StartsWith");
                                
                                    public static final java.lang.String _EndsWith =
                                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString("EndsWith");
                                
                                    public static final java.lang.String _DoesNotStartWith =
                                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString("DoesNotStartWith");
                                
                                    public static final java.lang.String _DoesNotEndWith =
                                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString("DoesNotEndWith");
                                
                                    public static final java.lang.String _GreaterThan =
                                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString("GreaterThan");
                                
                                    public static final java.lang.String _LessThan =
                                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString("LessThan");
                                
                                    public static final java.lang.String _GreaterThanEqualTo =
                                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString("GreaterThanEqualTo");
                                
                                    public static final java.lang.String _LessThanEqualTo =
                                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString("LessThanEqualTo");
                                
                                    public static final java.lang.String _Contains =
                                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString("Contains");
                                
                                    public static final java.lang.String _DoesNotContain =
                                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString("DoesNotContain");
                                
                                    public static final java.lang.String _SameYear =
                                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString("SameYear");
                                
                                    public static final java.lang.String _NotSameYear =
                                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString("NotSameYear");
                                
                                    public static final java.lang.String _SameDay =
                                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString("SameDay");
                                
                                    public static final java.lang.String _NotSameDay =
                                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString("NotSameDay");
                                
                                    public static final java.lang.String _Before =
                                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString("Before");
                                
                                    public static final java.lang.String _After =
                                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString("After");
                                
                                    public static final java.lang.String _BeforeOrSameDay =
                                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString("BeforeOrSameDay");
                                
                                    public static final java.lang.String _AfterOrSameDay =
                                        org.apache.axis2.databinding.utils.ConverterUtil.convertToString("AfterOrSameDay");
                                
                                public static final FilterOperator EqualTo =
                                    new FilterOperator(_EqualTo,true);
                            
                                public static final FilterOperator NotEqualTo =
                                    new FilterOperator(_NotEqualTo,true);
                            
                                public static final FilterOperator StartsWith =
                                    new FilterOperator(_StartsWith,true);
                            
                                public static final FilterOperator EndsWith =
                                    new FilterOperator(_EndsWith,true);
                            
                                public static final FilterOperator DoesNotStartWith =
                                    new FilterOperator(_DoesNotStartWith,true);
                            
                                public static final FilterOperator DoesNotEndWith =
                                    new FilterOperator(_DoesNotEndWith,true);
                            
                                public static final FilterOperator GreaterThan =
                                    new FilterOperator(_GreaterThan,true);
                            
                                public static final FilterOperator LessThan =
                                    new FilterOperator(_LessThan,true);
                            
                                public static final FilterOperator GreaterThanEqualTo =
                                    new FilterOperator(_GreaterThanEqualTo,true);
                            
                                public static final FilterOperator LessThanEqualTo =
                                    new FilterOperator(_LessThanEqualTo,true);
                            
                                public static final FilterOperator Contains =
                                    new FilterOperator(_Contains,true);
                            
                                public static final FilterOperator DoesNotContain =
                                    new FilterOperator(_DoesNotContain,true);
                            
                                public static final FilterOperator SameYear =
                                    new FilterOperator(_SameYear,true);
                            
                                public static final FilterOperator NotSameYear =
                                    new FilterOperator(_NotSameYear,true);
                            
                                public static final FilterOperator SameDay =
                                    new FilterOperator(_SameDay,true);
                            
                                public static final FilterOperator NotSameDay =
                                    new FilterOperator(_NotSameDay,true);
                            
                                public static final FilterOperator Before =
                                    new FilterOperator(_Before,true);
                            
                                public static final FilterOperator After =
                                    new FilterOperator(_After,true);
                            
                                public static final FilterOperator BeforeOrSameDay =
                                    new FilterOperator(_BeforeOrSameDay,true);
                            
                                public static final FilterOperator AfterOrSameDay =
                                    new FilterOperator(_AfterOrSameDay,true);
                            

                                public java.lang.String getValue() { return localFilterOperator;}

                                public boolean equals(java.lang.Object obj) {return (obj == this);}
                                public int hashCode() { return toString().hashCode();}
                                public java.lang.String toString() {
                                
                                        return localFilterOperator.toString();
                                    

                                }

                        

     
     
        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{


        
               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,MY_QNAME);
               return factory.createOMElement(dataSource,MY_QNAME);
            
        }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       javax.xml.stream.XMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               javax.xml.stream.XMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
            
                
                //We can safely assume an element has only one type associated with it
                
                            java.lang.String namespace = parentQName.getNamespaceURI();
                            java.lang.String _localName = parentQName.getLocalPart();
                        
                            writeStartElement(null, namespace, _localName, xmlWriter);

                            // add the type details if this is used in a simple type
                               if (serializeType){
                                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://api.bronto.com/v4");
                                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                                           namespacePrefix+":filterOperator",
                                           xmlWriter);
                                   } else {
                                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                                           "filterOperator",
                                           xmlWriter);
                                   }
                               }
                            
                                          if (localFilterOperator==null){
                                            
                                                     throw new org.apache.axis2.databinding.ADBException("filterOperator cannot be null !!");
                                                
                                         }else{
                                        
                                                       xmlWriter.writeCharacters(localFilterOperator);
                                            
                                         }
                                    
                            xmlWriter.writeEndElement();
                    

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://api.bronto.com/v4")){
                return "ns1";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

        /**
         * Utility method to write an element start tag.
         */
        private void writeStartElement(java.lang.String prefix, java.lang.String namespace, java.lang.String localPart,
                                       javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
            if (writerPrefix != null) {
                xmlWriter.writeStartElement(namespace, localPart);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }
        
        /**
         * Util method to write an attribute with the ns prefix
         */
        private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (xmlWriter.getPrefix(namespace) == null) {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            xmlWriter.writeAttribute(namespace,attName,attValue);
        }

        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeAttribute(java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName,attValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(namespace,attName,attValue);
            }
        }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                java.lang.String attributeNamespace = qname.getNamespaceURI();
                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                java.lang.String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


        /**
         * Register a namespace prefix
         */
        private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);
            if (prefix == null) {
                prefix = generatePrefix(namespace);
                javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
                while (true) {
                    java.lang.String uri = nsContext.getNamespaceURI(prefix);
                    if (uri == null || uri.length() == 0) {
                        break;
                    }
                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            return prefix;
        }


  
        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{


        
                
                //We can safely assume an element has only one type associated with it
                 return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(MY_QNAME,
                            new java.lang.Object[]{
                            org.apache.axis2.databinding.utils.reader.ADBXMLStreamReader.ELEMENT_TEXT,
                            org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFilterOperator)
                            },
                            null);

        }

  

     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{

        
        
                public static FilterOperator fromValue(java.lang.String value)
                      throws java.lang.IllegalArgumentException {
                    FilterOperator enumeration = (FilterOperator)
                       
                               _table_.get(value);
                           

                    if ((enumeration == null) && !((value == null) || (value.equals("")))) {
                        throw new java.lang.IllegalArgumentException();
                    }
                    return enumeration;
                }
                public static FilterOperator fromString(java.lang.String value,java.lang.String namespaceURI)
                      throws java.lang.IllegalArgumentException {
                    try {
                       
                                       return fromValue(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(value));
                                   

                    } catch (java.lang.Exception e) {
                        throw new java.lang.IllegalArgumentException();
                    }
                }

                public static FilterOperator fromString(javax.xml.stream.XMLStreamReader xmlStreamReader,
                                                                    java.lang.String content) {
                    if (content.indexOf(":") > -1){
                        java.lang.String prefix = content.substring(0,content.indexOf(":"));
                        java.lang.String namespaceUri = xmlStreamReader.getNamespaceContext().getNamespaceURI(prefix);
                        return FilterOperator.Factory.fromString(content,namespaceUri);
                    } else {
                       return FilterOperator.Factory.fromString(content,"");
                    }
                }
            

        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static FilterOperator parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            FilterOperator object = null;
                // initialize a hash map to keep values
                java.util.Map attributeMap = new java.util.HashMap();
                java.util.List extraAttributeList = new java.util.ArrayList<org.apache.axiom.om.OMAttribute>();
            

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix ="";
            java.lang.String namespaceuri ="";
            try {
                
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                   
                while(!reader.isEndElement()) {
                    if (reader.isStartElement()  || reader.hasText()){
                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"filterOperator" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                        if (content.indexOf(":") > 0) {
                                            // this seems to be a Qname so find the namespace and send
                                            prefix = content.substring(0, content.indexOf(":"));
                                            namespaceuri = reader.getNamespaceURI(prefix);
                                            object = FilterOperator.Factory.fromString(content,namespaceuri);
                                        } else {
                                            // this seems to be not a qname send and empty namespace incase of it is
                                            // check is done in fromString method
                                            object = FilterOperator.Factory.fromString(content,"");
                                        }
                                        
                                        
                             } else {
                                reader.next();
                             }  
                           }  // end of while loop
                        



            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }

        }//end of factory class

        

        }
           
    