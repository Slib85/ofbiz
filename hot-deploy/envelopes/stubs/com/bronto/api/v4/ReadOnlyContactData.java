
/**
 * ReadOnlyContactData.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:34:40 IST)
 */

            
                package com.bronto.api.v4;
            

            /**
            *  ReadOnlyContactData bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class ReadOnlyContactData
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = readOnlyContactData
                Namespace URI = http://api.bronto.com/v4
                Namespace Prefix = ns1
                */
            

                        /**
                        * field for GeoIPCity
                        */

                        
                                    protected java.lang.String localGeoIPCity ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localGeoIPCityTracker = false ;

                           public boolean isGeoIPCitySpecified(){
                               return localGeoIPCityTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getGeoIPCity(){
                               return localGeoIPCity;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param GeoIPCity
                               */
                               public void setGeoIPCity(java.lang.String param){
                            localGeoIPCityTracker = param != null;
                                   
                                            this.localGeoIPCity=param;
                                    

                               }
                            

                        /**
                        * field for GeoIPStateRegion
                        */

                        
                                    protected java.lang.String localGeoIPStateRegion ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localGeoIPStateRegionTracker = false ;

                           public boolean isGeoIPStateRegionSpecified(){
                               return localGeoIPStateRegionTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getGeoIPStateRegion(){
                               return localGeoIPStateRegion;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param GeoIPStateRegion
                               */
                               public void setGeoIPStateRegion(java.lang.String param){
                            localGeoIPStateRegionTracker = param != null;
                                   
                                            this.localGeoIPStateRegion=param;
                                    

                               }
                            

                        /**
                        * field for GeoIPZip
                        */

                        
                                    protected java.lang.String localGeoIPZip ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localGeoIPZipTracker = false ;

                           public boolean isGeoIPZipSpecified(){
                               return localGeoIPZipTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getGeoIPZip(){
                               return localGeoIPZip;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param GeoIPZip
                               */
                               public void setGeoIPZip(java.lang.String param){
                            localGeoIPZipTracker = param != null;
                                   
                                            this.localGeoIPZip=param;
                                    

                               }
                            

                        /**
                        * field for GeoIPCountry
                        */

                        
                                    protected java.lang.String localGeoIPCountry ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localGeoIPCountryTracker = false ;

                           public boolean isGeoIPCountrySpecified(){
                               return localGeoIPCountryTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getGeoIPCountry(){
                               return localGeoIPCountry;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param GeoIPCountry
                               */
                               public void setGeoIPCountry(java.lang.String param){
                            localGeoIPCountryTracker = param != null;
                                   
                                            this.localGeoIPCountry=param;
                                    

                               }
                            

                        /**
                        * field for GeoIPCountryCode
                        */

                        
                                    protected java.lang.String localGeoIPCountryCode ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localGeoIPCountryCodeTracker = false ;

                           public boolean isGeoIPCountryCodeSpecified(){
                               return localGeoIPCountryCodeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getGeoIPCountryCode(){
                               return localGeoIPCountryCode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param GeoIPCountryCode
                               */
                               public void setGeoIPCountryCode(java.lang.String param){
                            localGeoIPCountryCodeTracker = param != null;
                                   
                                            this.localGeoIPCountryCode=param;
                                    

                               }
                            

                        /**
                        * field for PrimaryBrowser
                        */

                        
                                    protected java.lang.String localPrimaryBrowser ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPrimaryBrowserTracker = false ;

                           public boolean isPrimaryBrowserSpecified(){
                               return localPrimaryBrowserTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getPrimaryBrowser(){
                               return localPrimaryBrowser;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PrimaryBrowser
                               */
                               public void setPrimaryBrowser(java.lang.String param){
                            localPrimaryBrowserTracker = param != null;
                                   
                                            this.localPrimaryBrowser=param;
                                    

                               }
                            

                        /**
                        * field for MobileBrowser
                        */

                        
                                    protected java.lang.String localMobileBrowser ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMobileBrowserTracker = false ;

                           public boolean isMobileBrowserSpecified(){
                               return localMobileBrowserTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getMobileBrowser(){
                               return localMobileBrowser;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MobileBrowser
                               */
                               public void setMobileBrowser(java.lang.String param){
                            localMobileBrowserTracker = param != null;
                                   
                                            this.localMobileBrowser=param;
                                    

                               }
                            

                        /**
                        * field for PrimaryEmailClient
                        */

                        
                                    protected java.lang.String localPrimaryEmailClient ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPrimaryEmailClientTracker = false ;

                           public boolean isPrimaryEmailClientSpecified(){
                               return localPrimaryEmailClientTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getPrimaryEmailClient(){
                               return localPrimaryEmailClient;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PrimaryEmailClient
                               */
                               public void setPrimaryEmailClient(java.lang.String param){
                            localPrimaryEmailClientTracker = param != null;
                                   
                                            this.localPrimaryEmailClient=param;
                                    

                               }
                            

                        /**
                        * field for MobileEmailClient
                        */

                        
                                    protected java.lang.String localMobileEmailClient ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMobileEmailClientTracker = false ;

                           public boolean isMobileEmailClientSpecified(){
                               return localMobileEmailClientTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getMobileEmailClient(){
                               return localMobileEmailClient;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MobileEmailClient
                               */
                               public void setMobileEmailClient(java.lang.String param){
                            localMobileEmailClientTracker = param != null;
                                   
                                            this.localMobileEmailClient=param;
                                    

                               }
                            

                        /**
                        * field for OperatingSystem
                        */

                        
                                    protected java.lang.String localOperatingSystem ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localOperatingSystemTracker = false ;

                           public boolean isOperatingSystemSpecified(){
                               return localOperatingSystemTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getOperatingSystem(){
                               return localOperatingSystem;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param OperatingSystem
                               */
                               public void setOperatingSystem(java.lang.String param){
                            localOperatingSystemTracker = param != null;
                                   
                                            this.localOperatingSystem=param;
                                    

                               }
                            

                        /**
                        * field for FirstOrderDate
                        */

                        
                                    protected java.util.Calendar localFirstOrderDate ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localFirstOrderDateTracker = false ;

                           public boolean isFirstOrderDateSpecified(){
                               return localFirstOrderDateTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.util.Calendar
                           */
                           public  java.util.Calendar getFirstOrderDate(){
                               return localFirstOrderDate;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param FirstOrderDate
                               */
                               public void setFirstOrderDate(java.util.Calendar param){
                            localFirstOrderDateTracker = param != null;
                                   
                                            this.localFirstOrderDate=param;
                                    

                               }
                            

                        /**
                        * field for LastOrderDate
                        */

                        
                                    protected java.util.Calendar localLastOrderDate ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localLastOrderDateTracker = false ;

                           public boolean isLastOrderDateSpecified(){
                               return localLastOrderDateTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.util.Calendar
                           */
                           public  java.util.Calendar getLastOrderDate(){
                               return localLastOrderDate;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param LastOrderDate
                               */
                               public void setLastOrderDate(java.util.Calendar param){
                            localLastOrderDateTracker = param != null;
                                   
                                            this.localLastOrderDate=param;
                                    

                               }
                            

                        /**
                        * field for LastOrderTotal
                        */

                        
                                    protected java.math.BigDecimal localLastOrderTotal ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localLastOrderTotalTracker = false ;

                           public boolean isLastOrderTotalSpecified(){
                               return localLastOrderTotalTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.math.BigDecimal
                           */
                           public  java.math.BigDecimal getLastOrderTotal(){
                               return localLastOrderTotal;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param LastOrderTotal
                               */
                               public void setLastOrderTotal(java.math.BigDecimal param){
                            localLastOrderTotalTracker = param != null;
                                   
                                            this.localLastOrderTotal=param;
                                    

                               }
                            

                        /**
                        * field for TotalOrders
                        */

                        
                                    protected long localTotalOrders ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTotalOrdersTracker = false ;

                           public boolean isTotalOrdersSpecified(){
                               return localTotalOrdersTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return long
                           */
                           public  long getTotalOrders(){
                               return localTotalOrders;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param TotalOrders
                               */
                               public void setTotalOrders(long param){
                            
                                       // setting primitive attribute tracker to true
                                       localTotalOrdersTracker =
                                       param != java.lang.Long.MIN_VALUE;
                                   
                                            this.localTotalOrders=param;
                                    

                               }
                            

                        /**
                        * field for TotalRevenue
                        */

                        
                                    protected java.math.BigDecimal localTotalRevenue ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localTotalRevenueTracker = false ;

                           public boolean isTotalRevenueSpecified(){
                               return localTotalRevenueTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.math.BigDecimal
                           */
                           public  java.math.BigDecimal getTotalRevenue(){
                               return localTotalRevenue;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param TotalRevenue
                               */
                               public void setTotalRevenue(java.math.BigDecimal param){
                            localTotalRevenueTracker = param != null;
                                   
                                            this.localTotalRevenue=param;
                                    

                               }
                            

                        /**
                        * field for AverageOrderValue
                        */

                        
                                    protected java.math.BigDecimal localAverageOrderValue ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAverageOrderValueTracker = false ;

                           public boolean isAverageOrderValueSpecified(){
                               return localAverageOrderValueTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.math.BigDecimal
                           */
                           public  java.math.BigDecimal getAverageOrderValue(){
                               return localAverageOrderValue;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AverageOrderValue
                               */
                               public void setAverageOrderValue(java.math.BigDecimal param){
                            localAverageOrderValueTracker = param != null;
                                   
                                            this.localAverageOrderValue=param;
                                    

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
                           namespacePrefix+":readOnlyContactData",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "readOnlyContactData",
                           xmlWriter);
                   }

               
                   }
                if (localGeoIPCityTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "geoIPCity", xmlWriter);
                             

                                          if (localGeoIPCity==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("geoIPCity cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localGeoIPCity);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localGeoIPStateRegionTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "geoIPStateRegion", xmlWriter);
                             

                                          if (localGeoIPStateRegion==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("geoIPStateRegion cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localGeoIPStateRegion);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localGeoIPZipTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "geoIPZip", xmlWriter);
                             

                                          if (localGeoIPZip==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("geoIPZip cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localGeoIPZip);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localGeoIPCountryTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "geoIPCountry", xmlWriter);
                             

                                          if (localGeoIPCountry==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("geoIPCountry cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localGeoIPCountry);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localGeoIPCountryCodeTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "geoIPCountryCode", xmlWriter);
                             

                                          if (localGeoIPCountryCode==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("geoIPCountryCode cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localGeoIPCountryCode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPrimaryBrowserTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "primaryBrowser", xmlWriter);
                             

                                          if (localPrimaryBrowser==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("primaryBrowser cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPrimaryBrowser);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localMobileBrowserTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "mobileBrowser", xmlWriter);
                             

                                          if (localMobileBrowser==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("mobileBrowser cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localMobileBrowser);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localPrimaryEmailClientTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "primaryEmailClient", xmlWriter);
                             

                                          if (localPrimaryEmailClient==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("primaryEmailClient cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPrimaryEmailClient);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localMobileEmailClientTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "mobileEmailClient", xmlWriter);
                             

                                          if (localMobileEmailClient==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("mobileEmailClient cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localMobileEmailClient);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localOperatingSystemTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "operatingSystem", xmlWriter);
                             

                                          if (localOperatingSystem==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("operatingSystem cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localOperatingSystem);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localFirstOrderDateTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "firstOrderDate", xmlWriter);
                             

                                          if (localFirstOrderDate==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("firstOrderDate cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFirstOrderDate));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localLastOrderDateTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "lastOrderDate", xmlWriter);
                             

                                          if (localLastOrderDate==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("lastOrderDate cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLastOrderDate));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localLastOrderTotalTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "lastOrderTotal", xmlWriter);
                             

                                          if (localLastOrderTotal==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("lastOrderTotal cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLastOrderTotal));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localTotalOrdersTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "totalOrders", xmlWriter);
                             
                                               if (localTotalOrders==java.lang.Long.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("totalOrders cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTotalOrders));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localTotalRevenueTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "totalRevenue", xmlWriter);
                             

                                          if (localTotalRevenue==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("totalRevenue cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTotalRevenue));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localAverageOrderValueTracker){
                                    namespace = "";
                                    writeStartElement(null, namespace, "averageOrderValue", xmlWriter);
                             

                                          if (localAverageOrderValue==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("averageOrderValue cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAverageOrderValue));
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
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

                 if (localGeoIPCityTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "geoIPCity"));
                                 
                                        if (localGeoIPCity != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localGeoIPCity));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("geoIPCity cannot be null!!");
                                        }
                                    } if (localGeoIPStateRegionTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "geoIPStateRegion"));
                                 
                                        if (localGeoIPStateRegion != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localGeoIPStateRegion));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("geoIPStateRegion cannot be null!!");
                                        }
                                    } if (localGeoIPZipTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "geoIPZip"));
                                 
                                        if (localGeoIPZip != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localGeoIPZip));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("geoIPZip cannot be null!!");
                                        }
                                    } if (localGeoIPCountryTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "geoIPCountry"));
                                 
                                        if (localGeoIPCountry != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localGeoIPCountry));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("geoIPCountry cannot be null!!");
                                        }
                                    } if (localGeoIPCountryCodeTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "geoIPCountryCode"));
                                 
                                        if (localGeoIPCountryCode != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localGeoIPCountryCode));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("geoIPCountryCode cannot be null!!");
                                        }
                                    } if (localPrimaryBrowserTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "primaryBrowser"));
                                 
                                        if (localPrimaryBrowser != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPrimaryBrowser));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("primaryBrowser cannot be null!!");
                                        }
                                    } if (localMobileBrowserTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "mobileBrowser"));
                                 
                                        if (localMobileBrowser != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMobileBrowser));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("mobileBrowser cannot be null!!");
                                        }
                                    } if (localPrimaryEmailClientTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "primaryEmailClient"));
                                 
                                        if (localPrimaryEmailClient != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPrimaryEmailClient));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("primaryEmailClient cannot be null!!");
                                        }
                                    } if (localMobileEmailClientTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "mobileEmailClient"));
                                 
                                        if (localMobileEmailClient != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMobileEmailClient));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("mobileEmailClient cannot be null!!");
                                        }
                                    } if (localOperatingSystemTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "operatingSystem"));
                                 
                                        if (localOperatingSystem != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localOperatingSystem));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("operatingSystem cannot be null!!");
                                        }
                                    } if (localFirstOrderDateTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "firstOrderDate"));
                                 
                                        if (localFirstOrderDate != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFirstOrderDate));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("firstOrderDate cannot be null!!");
                                        }
                                    } if (localLastOrderDateTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "lastOrderDate"));
                                 
                                        if (localLastOrderDate != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLastOrderDate));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("lastOrderDate cannot be null!!");
                                        }
                                    } if (localLastOrderTotalTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "lastOrderTotal"));
                                 
                                        if (localLastOrderTotal != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localLastOrderTotal));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("lastOrderTotal cannot be null!!");
                                        }
                                    } if (localTotalOrdersTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "totalOrders"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTotalOrders));
                            } if (localTotalRevenueTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "totalRevenue"));
                                 
                                        if (localTotalRevenue != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localTotalRevenue));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("totalRevenue cannot be null!!");
                                        }
                                    } if (localAverageOrderValueTracker){
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "averageOrderValue"));
                                 
                                        if (localAverageOrderValue != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAverageOrderValue));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("averageOrderValue cannot be null!!");
                                        }
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
        public static ReadOnlyContactData parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            ReadOnlyContactData object =
                new ReadOnlyContactData();

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
                    
                            if (!"readOnlyContactData".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (ReadOnlyContactData)com.bronto.api.v4.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","geoIPCity").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"geoIPCity" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setGeoIPCity(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","geoIPStateRegion").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"geoIPStateRegion" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setGeoIPStateRegion(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","geoIPZip").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"geoIPZip" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setGeoIPZip(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","geoIPCountry").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"geoIPCountry" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setGeoIPCountry(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","geoIPCountryCode").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"geoIPCountryCode" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setGeoIPCountryCode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","primaryBrowser").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"primaryBrowser" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPrimaryBrowser(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","mobileBrowser").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"mobileBrowser" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMobileBrowser(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","primaryEmailClient").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"primaryEmailClient" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPrimaryEmailClient(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","mobileEmailClient").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"mobileEmailClient" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMobileEmailClient(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","operatingSystem").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"operatingSystem" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setOperatingSystem(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","firstOrderDate").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"firstOrderDate" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setFirstOrderDate(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","lastOrderDate").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"lastOrderDate" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setLastOrderDate(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","lastOrderTotal").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"lastOrderTotal" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setLastOrderTotal(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDecimal(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","totalOrders").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"totalOrders" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setTotalOrders(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToLong(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setTotalOrders(java.lang.Long.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","totalRevenue").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"totalRevenue" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setTotalRevenue(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDecimal(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","averageOrderValue").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"averageOrderValue" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAverageOrderValue(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDecimal(content));
                                              
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
           
    