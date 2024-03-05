
/**
 * AccountObject.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:34:40 IST)
 */

            
                package com.bronto.api.v4;
            

            /**
            *  AccountObject bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class AccountObject
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = accountObject
                Namespace URI = http://api.bronto.com/v4
                Namespace Prefix = ns1
                */
            

                        /**
                        * field for Id
                        */

                        
                                    protected java.lang.String localId ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localIdTracker = false ;

                           public boolean isIdSpecified(){
                               return localIdTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getId(){
                               return localId;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Id
                               */
                               public void setId(java.lang.String param){
                            localIdTracker = param != null;
                                   
                                            this.localId=param;
                                    

                               }
                            

                        /**
                        * field for Name
                        */

                        
                                    protected java.lang.String localName ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localNameTracker = false ;

                           public boolean isNameSpecified(){
                               return localNameTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getName(){
                               return localName;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Name
                               */
                               public void setName(java.lang.String param){
                            localNameTracker = param != null;
                                   
                                            this.localName=param;
                                    

                               }
                            

                        /**
                        * field for Status
                        */

                        
                                    protected java.lang.String localStatus ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localStatusTracker = false ;

                           public boolean isStatusSpecified(){
                               return localStatusTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getStatus(){
                               return localStatus;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Status
                               */
                               public void setStatus(java.lang.String param){
                            localStatusTracker = param != null;
                                   
                                            this.localStatus=param;
                                    

                               }
                            

                        /**
                        * field for GeneralSettings
                        */

                        
                                    protected com.bronto.api.v4.GeneralSettings localGeneralSettings ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localGeneralSettingsTracker = false ;

                           public boolean isGeneralSettingsSpecified(){
                               return localGeneralSettingsTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.bronto.api.v4.GeneralSettings
                           */
                           public  com.bronto.api.v4.GeneralSettings getGeneralSettings(){
                               return localGeneralSettings;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param GeneralSettings
                               */
                               public void setGeneralSettings(com.bronto.api.v4.GeneralSettings param){
                            localGeneralSettingsTracker = param != null;
                                   
                                            this.localGeneralSettings=param;
                                    

                               }
                            

                        /**
                        * field for ContactInformation
                        */

                        
                                    protected com.bronto.api.v4.ContactInformation localContactInformation ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localContactInformationTracker = false ;

                           public boolean isContactInformationSpecified(){
                               return localContactInformationTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.bronto.api.v4.ContactInformation
                           */
                           public  com.bronto.api.v4.ContactInformation getContactInformation(){
                               return localContactInformation;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ContactInformation
                               */
                               public void setContactInformation(com.bronto.api.v4.ContactInformation param){
                            localContactInformationTracker = param != null;
                                   
                                            this.localContactInformation=param;
                                    

                               }
                            

                        /**
                        * field for FormatSettings
                        */

                        
                                    protected com.bronto.api.v4.FormatSettings localFormatSettings ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localFormatSettingsTracker = false ;

                           public boolean isFormatSettingsSpecified(){
                               return localFormatSettingsTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.bronto.api.v4.FormatSettings
                           */
                           public  com.bronto.api.v4.FormatSettings getFormatSettings(){
                               return localFormatSettings;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param FormatSettings
                               */
                               public void setFormatSettings(com.bronto.api.v4.FormatSettings param){
                            localFormatSettingsTracker = param != null;
                                   
                                            this.localFormatSettings=param;
                                    

                               }
                            

                        /**
                        * field for BrandingSettings
                        */

                        
                                    protected com.bronto.api.v4.BrandingSettings localBrandingSettings ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localBrandingSettingsTracker = false ;

                           public boolean isBrandingSettingsSpecified(){
                               return localBrandingSettingsTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.bronto.api.v4.BrandingSettings
                           */
                           public  com.bronto.api.v4.BrandingSettings getBrandingSettings(){
                               return localBrandingSettings;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param BrandingSettings
                               */
                               public void setBrandingSettings(com.bronto.api.v4.BrandingSettings param){
                            localBrandingSettingsTracker = param != null;
                                   
                                            this.localBrandingSettings=param;
                                    

                               }
                            

                        /**
                        * field for RepliesSettings
                        */

                        
                                    protected com.bronto.api.v4.RepliesSettings localRepliesSettings ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localRepliesSettingsTracker = false ;

                           public boolean isRepliesSettingsSpecified(){
                               return localRepliesSettingsTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.bronto.api.v4.RepliesSettings
                           */
                           public  com.bronto.api.v4.RepliesSettings getRepliesSettings(){
                               return localRepliesSettings;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param RepliesSettings
                               */
                               public void setRepliesSettings(com.bronto.api.v4.RepliesSettings param){
                            localRepliesSettingsTracker = param != null;
                                   
                                            this.localRepliesSettings=param;
                                    

                               }
                            

                        /**
                        * field for Allocations
                        */

                        
                                    protected com.bronto.api.v4.AccountAllocations localAllocations ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAllocationsTracker = false ;

                           public boolean isAllocationsSpecified(){
                               return localAllocationsTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.bronto.api.v4.AccountAllocations
                           */
                           public  com.bronto.api.v4.AccountAllocations getAllocations(){
                               return localAllocations;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Allocations
                               */
                               public void setAllocations(com.bronto.api.v4.AccountAllocations param){
                            localAllocationsTracker = param != null;
                                   
                                            this.localAllocations=param;
                                    

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
                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName);
               return factory.createOMElement(dataSource,parentQName);
            
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
            
                


                java.lang.String prefix = null;
                java.lang.String namespace = null;
                

                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();
                    writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
                
                  if (serializeType){
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://api.bronto.com/v4");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":accountObject",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "accountObject",
                           xmlWriter);
                   }

               
                   }
                if (localIdTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "id", xmlWriter);
                             

                                          if (localId==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("id cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localId);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localNameTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "name", xmlWriter);
                             

                                          if (localName==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("name cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localName);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localStatusTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "status", xmlWriter);
                             

                                          if (localStatus==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("status cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localStatus);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localGeneralSettingsTracker){
                                            if (localGeneralSettings==null){
                                                 throw new org.apache.axis2.databinding.ADBException("generalSettings cannot be null!!");
                                            }
                                           localGeneralSettings.serialize(new javax.xml.namespace.QName("","generalSettings"),
                                               xmlWriter);
                                        } if (localContactInformationTracker){
                                            if (localContactInformation==null){
                                                 throw new org.apache.axis2.databinding.ADBException("contactInformation cannot be null!!");
                                            }
                                           localContactInformation.serialize(new javax.xml.namespace.QName("","contactInformation"),
                                               xmlWriter);
                                        } if (localFormatSettingsTracker){
                                            if (localFormatSettings==null){
                                                 throw new org.apache.axis2.databinding.ADBException("formatSettings cannot be null!!");
                                            }
                                           localFormatSettings.serialize(new javax.xml.namespace.QName("","formatSettings"),
                                               xmlWriter);
                                        } if (localBrandingSettingsTracker){
                                            if (localBrandingSettings==null){
                                                 throw new org.apache.axis2.databinding.ADBException("brandingSettings cannot be null!!");
                                            }
                                           localBrandingSettings.serialize(new javax.xml.namespace.QName("","brandingSettings"),
                                               xmlWriter);
                                        } if (localRepliesSettingsTracker){
                                            if (localRepliesSettings==null){
                                                 throw new org.apache.axis2.databinding.ADBException("repliesSettings cannot be null!!");
                                            }
                                           localRepliesSettings.serialize(new javax.xml.namespace.QName("","repliesSettings"),
                                               xmlWriter);
                                        } if (localAllocationsTracker){
                                            if (localAllocations==null){
                                                 throw new org.apache.axis2.databinding.ADBException("allocations cannot be null!!");
                                            }
                                           localAllocations.serialize(new javax.xml.namespace.QName("","allocations"),
                                               xmlWriter);
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


        
                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                 if (localIdTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "id"));
                                 
                                        if (localId != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localId));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("id cannot be null!!");
                                        }
                                    } if (localNameTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "name"));
                                 
                                        if (localName != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localName));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("name cannot be null!!");
                                        }
                                    } if (localStatusTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "status"));
                                 
                                        if (localStatus != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localStatus));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("status cannot be null!!");
                                        }
                                    } if (localGeneralSettingsTracker){
                            elementList.add(new javax.xml.namespace.QName("",
                                                                      "generalSettings"));
                            
                            
                                    if (localGeneralSettings==null){
                                         throw new org.apache.axis2.databinding.ADBException("generalSettings cannot be null!!");
                                    }
                                    elementList.add(localGeneralSettings);
                                } if (localContactInformationTracker){
                            elementList.add(new javax.xml.namespace.QName("",
                                                                      "contactInformation"));
                            
                            
                                    if (localContactInformation==null){
                                         throw new org.apache.axis2.databinding.ADBException("contactInformation cannot be null!!");
                                    }
                                    elementList.add(localContactInformation);
                                } if (localFormatSettingsTracker){
                            elementList.add(new javax.xml.namespace.QName("",
                                                                      "formatSettings"));
                            
                            
                                    if (localFormatSettings==null){
                                         throw new org.apache.axis2.databinding.ADBException("formatSettings cannot be null!!");
                                    }
                                    elementList.add(localFormatSettings);
                                } if (localBrandingSettingsTracker){
                            elementList.add(new javax.xml.namespace.QName("",
                                                                      "brandingSettings"));
                            
                            
                                    if (localBrandingSettings==null){
                                         throw new org.apache.axis2.databinding.ADBException("brandingSettings cannot be null!!");
                                    }
                                    elementList.add(localBrandingSettings);
                                } if (localRepliesSettingsTracker){
                            elementList.add(new javax.xml.namespace.QName("",
                                                                      "repliesSettings"));
                            
                            
                                    if (localRepliesSettings==null){
                                         throw new org.apache.axis2.databinding.ADBException("repliesSettings cannot be null!!");
                                    }
                                    elementList.add(localRepliesSettings);
                                } if (localAllocationsTracker){
                            elementList.add(new javax.xml.namespace.QName("",
                                                                      "allocations"));
                            
                            
                                    if (localAllocations==null){
                                         throw new org.apache.axis2.databinding.ADBException("allocations cannot be null!!");
                                    }
                                    elementList.add(localAllocations);
                                }

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
            
            

        }

  

     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{

        
        

        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static AccountObject parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            AccountObject object =
                new AccountObject();

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix ="";
            java.lang.String namespaceuri ="";
            try {
                
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                
                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    java.lang.String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
                    
                            if (!"accountObject".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (AccountObject)com.bronto.api.v4.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","id").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"id" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setId(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","name").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"name" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setName(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","status").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"status" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setStatus(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","generalSettings").equals(reader.getName())){
                                
                                                object.setGeneralSettings(com.bronto.api.v4.GeneralSettings.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","contactInformation").equals(reader.getName())){
                                
                                                object.setContactInformation(com.bronto.api.v4.ContactInformation.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","formatSettings").equals(reader.getName())){
                                
                                                object.setFormatSettings(com.bronto.api.v4.FormatSettings.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","brandingSettings").equals(reader.getName())){
                                
                                                object.setBrandingSettings(com.bronto.api.v4.BrandingSettings.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","repliesSettings").equals(reader.getName())){
                                
                                                object.setRepliesSettings(com.bronto.api.v4.RepliesSettings.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","allocations").equals(reader.getName())){
                                
                                                object.setAllocations(com.bronto.api.v4.AccountAllocations.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                  
                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();
                            
                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                            



            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }

        }//end of factory class

        

        }
           
    