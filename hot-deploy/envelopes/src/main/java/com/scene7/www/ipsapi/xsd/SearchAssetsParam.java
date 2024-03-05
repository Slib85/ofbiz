
/**
 * SearchAssetsParam.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:34:40 IST)
 */

            
                package com.scene7.www.ipsapi.xsd;
            

            /**
            *  SearchAssetsParam bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class SearchAssetsParam
        implements org.apache.axis2.databinding.ADBBean{
        
                public static final javax.xml.namespace.QName MY_QNAME = new javax.xml.namespace.QName(
                "http://www.scene7.com/IpsApi/xsd",
                "searchAssetsParam",
                "ns1");

            

                        /**
                        * field for CompanyHandle
                        */

                        
                                    protected java.lang.String localCompanyHandle ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getCompanyHandle(){
                               return localCompanyHandle;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CompanyHandle
                               */
                               public void setCompanyHandle(java.lang.String param){
                            
                                            this.localCompanyHandle=param;
                                    

                               }
                            

                        /**
                        * field for AccessUserHandle
                        */

                        
                                    protected java.lang.String localAccessUserHandle ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAccessUserHandleTracker = false ;

                           public boolean isAccessUserHandleSpecified(){
                               return localAccessUserHandleTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getAccessUserHandle(){
                               return localAccessUserHandle;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AccessUserHandle
                               */
                               public void setAccessUserHandle(java.lang.String param){
                            localAccessUserHandleTracker = param != null;
                                   
                                            this.localAccessUserHandle=param;
                                    

                               }
                            

                        /**
                        * field for AccessGroupHandle
                        */

                        
                                    protected java.lang.String localAccessGroupHandle ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAccessGroupHandleTracker = false ;

                           public boolean isAccessGroupHandleSpecified(){
                               return localAccessGroupHandleTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getAccessGroupHandle(){
                               return localAccessGroupHandle;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AccessGroupHandle
                               */
                               public void setAccessGroupHandle(java.lang.String param){
                            localAccessGroupHandleTracker = param != null;
                                   
                                            this.localAccessGroupHandle=param;
                                    

                               }
                            

                        /**
                        * field for Folder
                        */

                        
                                    protected java.lang.String localFolder ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localFolderTracker = false ;

                           public boolean isFolderSpecified(){
                               return localFolderTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getFolder(){
                               return localFolder;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Folder
                               */
                               public void setFolder(java.lang.String param){
                            localFolderTracker = param != null;
                                   
                                            this.localFolder=param;
                                    

                               }
                            

                        /**
                        * field for IncludeSubfolders
                        */

                        
                                    protected boolean localIncludeSubfolders ;
                                

                           /**
                           * Auto generated getter method
                           * @return boolean
                           */
                           public  boolean getIncludeSubfolders(){
                               return localIncludeSubfolders;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param IncludeSubfolders
                               */
                               public void setIncludeSubfolders(boolean param){
                            
                                            this.localIncludeSubfolders=param;
                                    

                               }
                            

                        /**
                        * field for PublishState
                        */

                        
                                    protected java.lang.String localPublishState ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localPublishStateTracker = false ;

                           public boolean isPublishStateSpecified(){
                               return localPublishStateTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getPublishState(){
                               return localPublishState;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param PublishState
                               */
                               public void setPublishState(java.lang.String param){
                            localPublishStateTracker = param != null;
                                   
                                            this.localPublishState=param;
                                    

                               }
                            

                        /**
                        * field for ConditionMatchMode
                        */

                        
                                    protected java.lang.String localConditionMatchMode ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localConditionMatchModeTracker = false ;

                           public boolean isConditionMatchModeSpecified(){
                               return localConditionMatchModeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getConditionMatchMode(){
                               return localConditionMatchMode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ConditionMatchMode
                               */
                               public void setConditionMatchMode(java.lang.String param){
                            localConditionMatchModeTracker = param != null;
                                   
                                            this.localConditionMatchMode=param;
                                    

                               }
                            

                        /**
                        * field for KeywordArray
                        */

                        
                                    protected com.scene7.www.ipsapi.xsd.StringArray localKeywordArray ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localKeywordArrayTracker = false ;

                           public boolean isKeywordArraySpecified(){
                               return localKeywordArrayTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.scene7.www.ipsapi.xsd.StringArray
                           */
                           public  com.scene7.www.ipsapi.xsd.StringArray getKeywordArray(){
                               return localKeywordArray;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param KeywordArray
                               */
                               public void setKeywordArray(com.scene7.www.ipsapi.xsd.StringArray param){
                            localKeywordArrayTracker = param != null;
                                   
                                            this.localKeywordArray=param;
                                    

                               }
                            

                        /**
                        * field for SystemFieldMatchMode
                        */

                        
                                    protected java.lang.String localSystemFieldMatchMode ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSystemFieldMatchModeTracker = false ;

                           public boolean isSystemFieldMatchModeSpecified(){
                               return localSystemFieldMatchModeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getSystemFieldMatchMode(){
                               return localSystemFieldMatchMode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param SystemFieldMatchMode
                               */
                               public void setSystemFieldMatchMode(java.lang.String param){
                            localSystemFieldMatchModeTracker = param != null;
                                   
                                            this.localSystemFieldMatchMode=param;
                                    

                               }
                            

                        /**
                        * field for SystemFieldConditionArray
                        */

                        
                                    protected com.scene7.www.ipsapi.xsd.SystemFieldConditionArray localSystemFieldConditionArray ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSystemFieldConditionArrayTracker = false ;

                           public boolean isSystemFieldConditionArraySpecified(){
                               return localSystemFieldConditionArrayTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.scene7.www.ipsapi.xsd.SystemFieldConditionArray
                           */
                           public  com.scene7.www.ipsapi.xsd.SystemFieldConditionArray getSystemFieldConditionArray(){
                               return localSystemFieldConditionArray;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param SystemFieldConditionArray
                               */
                               public void setSystemFieldConditionArray(com.scene7.www.ipsapi.xsd.SystemFieldConditionArray param){
                            localSystemFieldConditionArrayTracker = param != null;
                                   
                                            this.localSystemFieldConditionArray=param;
                                    

                               }
                            

                        /**
                        * field for MetadataMatchMode
                        */

                        
                                    protected java.lang.String localMetadataMatchMode ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMetadataMatchModeTracker = false ;

                           public boolean isMetadataMatchModeSpecified(){
                               return localMetadataMatchModeTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getMetadataMatchMode(){
                               return localMetadataMatchMode;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MetadataMatchMode
                               */
                               public void setMetadataMatchMode(java.lang.String param){
                            localMetadataMatchModeTracker = param != null;
                                   
                                            this.localMetadataMatchMode=param;
                                    

                               }
                            

                        /**
                        * field for MetadataConditionArray
                        */

                        
                                    protected com.scene7.www.ipsapi.xsd.MetadataConditionArray localMetadataConditionArray ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localMetadataConditionArrayTracker = false ;

                           public boolean isMetadataConditionArraySpecified(){
                               return localMetadataConditionArrayTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.scene7.www.ipsapi.xsd.MetadataConditionArray
                           */
                           public  com.scene7.www.ipsapi.xsd.MetadataConditionArray getMetadataConditionArray(){
                               return localMetadataConditionArray;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param MetadataConditionArray
                               */
                               public void setMetadataConditionArray(com.scene7.www.ipsapi.xsd.MetadataConditionArray param){
                            localMetadataConditionArrayTracker = param != null;
                                   
                                            this.localMetadataConditionArray=param;
                                    

                               }
                            

                        /**
                        * field for AssetTypeArray
                        */

                        
                                    protected com.scene7.www.ipsapi.xsd.StringArray localAssetTypeArray ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localAssetTypeArrayTracker = false ;

                           public boolean isAssetTypeArraySpecified(){
                               return localAssetTypeArrayTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.scene7.www.ipsapi.xsd.StringArray
                           */
                           public  com.scene7.www.ipsapi.xsd.StringArray getAssetTypeArray(){
                               return localAssetTypeArray;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AssetTypeArray
                               */
                               public void setAssetTypeArray(com.scene7.www.ipsapi.xsd.StringArray param){
                            localAssetTypeArrayTracker = param != null;
                                   
                                            this.localAssetTypeArray=param;
                                    

                               }
                            

                        /**
                        * field for ProjectHandle
                        */

                        
                                    protected java.lang.String localProjectHandle ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localProjectHandleTracker = false ;

                           public boolean isProjectHandleSpecified(){
                               return localProjectHandleTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getProjectHandle(){
                               return localProjectHandle;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ProjectHandle
                               */
                               public void setProjectHandle(java.lang.String param){
                            localProjectHandleTracker = param != null;
                                   
                                            this.localProjectHandle=param;
                                    

                               }
                            

                        /**
                        * field for RecordsPerPage
                        */

                        
                                    protected int localRecordsPerPage ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localRecordsPerPageTracker = false ;

                           public boolean isRecordsPerPageSpecified(){
                               return localRecordsPerPageTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return int
                           */
                           public  int getRecordsPerPage(){
                               return localRecordsPerPage;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param RecordsPerPage
                               */
                               public void setRecordsPerPage(int param){
                            
                                       // setting primitive attribute tracker to true
                                       localRecordsPerPageTracker =
                                       param != java.lang.Integer.MIN_VALUE;
                                   
                                            this.localRecordsPerPage=param;
                                    

                               }
                            

                        /**
                        * field for ResultsPage
                        */

                        
                                    protected int localResultsPage ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localResultsPageTracker = false ;

                           public boolean isResultsPageSpecified(){
                               return localResultsPageTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return int
                           */
                           public  int getResultsPage(){
                               return localResultsPage;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ResultsPage
                               */
                               public void setResultsPage(int param){
                            
                                       // setting primitive attribute tracker to true
                                       localResultsPageTracker =
                                       param != java.lang.Integer.MIN_VALUE;
                                   
                                            this.localResultsPage=param;
                                    

                               }
                            

                        /**
                        * field for SortBy
                        */

                        
                                    protected java.lang.String localSortBy ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSortByTracker = false ;

                           public boolean isSortBySpecified(){
                               return localSortByTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getSortBy(){
                               return localSortBy;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param SortBy
                               */
                               public void setSortBy(java.lang.String param){
                            localSortByTracker = param != null;
                                   
                                            this.localSortBy=param;
                                    

                               }
                            

                        /**
                        * field for SortDirection
                        */

                        
                                    protected java.lang.String localSortDirection ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localSortDirectionTracker = false ;

                           public boolean isSortDirectionSpecified(){
                               return localSortDirectionTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getSortDirection(){
                               return localSortDirection;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param SortDirection
                               */
                               public void setSortDirection(java.lang.String param){
                            localSortDirectionTracker = param != null;
                                   
                                            this.localSortDirection=param;
                                    

                               }
                            

                        /**
                        * field for ResponseFieldArray
                        */

                        
                                    protected com.scene7.www.ipsapi.xsd.StringArray localResponseFieldArray ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localResponseFieldArrayTracker = false ;

                           public boolean isResponseFieldArraySpecified(){
                               return localResponseFieldArrayTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.scene7.www.ipsapi.xsd.StringArray
                           */
                           public  com.scene7.www.ipsapi.xsd.StringArray getResponseFieldArray(){
                               return localResponseFieldArray;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ResponseFieldArray
                               */
                               public void setResponseFieldArray(com.scene7.www.ipsapi.xsd.StringArray param){
                            localResponseFieldArrayTracker = param != null;
                                   
                                            this.localResponseFieldArray=param;
                                    

                               }
                            

                        /**
                        * field for ExcludeFieldArray
                        */

                        
                                    protected com.scene7.www.ipsapi.xsd.StringArray localExcludeFieldArray ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localExcludeFieldArrayTracker = false ;

                           public boolean isExcludeFieldArraySpecified(){
                               return localExcludeFieldArrayTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return com.scene7.www.ipsapi.xsd.StringArray
                           */
                           public  com.scene7.www.ipsapi.xsd.StringArray getExcludeFieldArray(){
                               return localExcludeFieldArray;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ExcludeFieldArray
                               */
                               public void setExcludeFieldArray(com.scene7.www.ipsapi.xsd.StringArray param){
                            localExcludeFieldArrayTracker = param != null;
                                   
                                            this.localExcludeFieldArray=param;
                                    

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
            
                


                java.lang.String prefix = null;
                java.lang.String namespace = null;
                

                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();
                    writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
                
                  if (serializeType){
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://www.scene7.com/IpsApi/xsd");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":searchAssetsParam",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "searchAssetsParam",
                           xmlWriter);
                   }

               
                   }
               
                                    namespace = "http://www.scene7.com/IpsApi/xsd";
                                    writeStartElement(null, namespace, "companyHandle", xmlWriter);
                             

                                          if (localCompanyHandle==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("companyHandle cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localCompanyHandle);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                              if (localAccessUserHandleTracker){
                                    namespace = "http://www.scene7.com/IpsApi/xsd";
                                    writeStartElement(null, namespace, "accessUserHandle", xmlWriter);
                             

                                          if (localAccessUserHandle==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("accessUserHandle cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localAccessUserHandle);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localAccessGroupHandleTracker){
                                    namespace = "http://www.scene7.com/IpsApi/xsd";
                                    writeStartElement(null, namespace, "accessGroupHandle", xmlWriter);
                             

                                          if (localAccessGroupHandle==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("accessGroupHandle cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localAccessGroupHandle);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localFolderTracker){
                                    namespace = "http://www.scene7.com/IpsApi/xsd";
                                    writeStartElement(null, namespace, "folder", xmlWriter);
                             

                                          if (localFolder==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("folder cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localFolder);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             }
                                    namespace = "http://www.scene7.com/IpsApi/xsd";
                                    writeStartElement(null, namespace, "includeSubfolders", xmlWriter);
                             
                                               if (false) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("includeSubfolders cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIncludeSubfolders));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                              if (localPublishStateTracker){
                                    namespace = "http://www.scene7.com/IpsApi/xsd";
                                    writeStartElement(null, namespace, "publishState", xmlWriter);
                             

                                          if (localPublishState==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("publishState cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localPublishState);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localConditionMatchModeTracker){
                                    namespace = "http://www.scene7.com/IpsApi/xsd";
                                    writeStartElement(null, namespace, "conditionMatchMode", xmlWriter);
                             

                                          if (localConditionMatchMode==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("conditionMatchMode cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localConditionMatchMode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localKeywordArrayTracker){
                                            if (localKeywordArray==null){
                                                 throw new org.apache.axis2.databinding.ADBException("keywordArray cannot be null!!");
                                            }
                                           localKeywordArray.serialize(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","keywordArray"),
                                               xmlWriter);
                                        } if (localSystemFieldMatchModeTracker){
                                    namespace = "http://www.scene7.com/IpsApi/xsd";
                                    writeStartElement(null, namespace, "systemFieldMatchMode", xmlWriter);
                             

                                          if (localSystemFieldMatchMode==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("systemFieldMatchMode cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localSystemFieldMatchMode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localSystemFieldConditionArrayTracker){
                                            if (localSystemFieldConditionArray==null){
                                                 throw new org.apache.axis2.databinding.ADBException("systemFieldConditionArray cannot be null!!");
                                            }
                                           localSystemFieldConditionArray.serialize(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","systemFieldConditionArray"),
                                               xmlWriter);
                                        } if (localMetadataMatchModeTracker){
                                    namespace = "http://www.scene7.com/IpsApi/xsd";
                                    writeStartElement(null, namespace, "metadataMatchMode", xmlWriter);
                             

                                          if (localMetadataMatchMode==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("metadataMatchMode cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localMetadataMatchMode);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localMetadataConditionArrayTracker){
                                            if (localMetadataConditionArray==null){
                                                 throw new org.apache.axis2.databinding.ADBException("metadataConditionArray cannot be null!!");
                                            }
                                           localMetadataConditionArray.serialize(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","metadataConditionArray"),
                                               xmlWriter);
                                        } if (localAssetTypeArrayTracker){
                                            if (localAssetTypeArray==null){
                                                 throw new org.apache.axis2.databinding.ADBException("assetTypeArray cannot be null!!");
                                            }
                                           localAssetTypeArray.serialize(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","assetTypeArray"),
                                               xmlWriter);
                                        } if (localProjectHandleTracker){
                                    namespace = "http://www.scene7.com/IpsApi/xsd";
                                    writeStartElement(null, namespace, "projectHandle", xmlWriter);
                             

                                          if (localProjectHandle==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("projectHandle cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localProjectHandle);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localRecordsPerPageTracker){
                                    namespace = "http://www.scene7.com/IpsApi/xsd";
                                    writeStartElement(null, namespace, "recordsPerPage", xmlWriter);
                             
                                               if (localRecordsPerPage==java.lang.Integer.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("recordsPerPage cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRecordsPerPage));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localResultsPageTracker){
                                    namespace = "http://www.scene7.com/IpsApi/xsd";
                                    writeStartElement(null, namespace, "resultsPage", xmlWriter);
                             
                                               if (localResultsPage==java.lang.Integer.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("resultsPage cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localResultsPage));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localSortByTracker){
                                    namespace = "http://www.scene7.com/IpsApi/xsd";
                                    writeStartElement(null, namespace, "sortBy", xmlWriter);
                             

                                          if (localSortBy==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("sortBy cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localSortBy);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localSortDirectionTracker){
                                    namespace = "http://www.scene7.com/IpsApi/xsd";
                                    writeStartElement(null, namespace, "sortDirection", xmlWriter);
                             

                                          if (localSortDirection==null){
                                              // write the nil attribute
                                              
                                                     throw new org.apache.axis2.databinding.ADBException("sortDirection cannot be null!!");
                                                  
                                          }else{

                                        
                                                   xmlWriter.writeCharacters(localSortDirection);
                                            
                                          }
                                    
                                   xmlWriter.writeEndElement();
                             } if (localResponseFieldArrayTracker){
                                            if (localResponseFieldArray==null){
                                                 throw new org.apache.axis2.databinding.ADBException("responseFieldArray cannot be null!!");
                                            }
                                           localResponseFieldArray.serialize(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","responseFieldArray"),
                                               xmlWriter);
                                        } if (localExcludeFieldArrayTracker){
                                            if (localExcludeFieldArray==null){
                                                 throw new org.apache.axis2.databinding.ADBException("excludeFieldArray cannot be null!!");
                                            }
                                           localExcludeFieldArray.serialize(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","excludeFieldArray"),
                                               xmlWriter);
                                        }
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://www.scene7.com/IpsApi/xsd")){
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

                
                                      elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "companyHandle"));
                                 
                                        if (localCompanyHandle != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCompanyHandle));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("companyHandle cannot be null!!");
                                        }
                                     if (localAccessUserHandleTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "accessUserHandle"));
                                 
                                        if (localAccessUserHandle != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAccessUserHandle));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("accessUserHandle cannot be null!!");
                                        }
                                    } if (localAccessGroupHandleTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "accessGroupHandle"));
                                 
                                        if (localAccessGroupHandle != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAccessGroupHandle));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("accessGroupHandle cannot be null!!");
                                        }
                                    } if (localFolderTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "folder"));
                                 
                                        if (localFolder != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localFolder));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("folder cannot be null!!");
                                        }
                                    }
                                      elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "includeSubfolders"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localIncludeSubfolders));
                             if (localPublishStateTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "publishState"));
                                 
                                        if (localPublishState != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localPublishState));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("publishState cannot be null!!");
                                        }
                                    } if (localConditionMatchModeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "conditionMatchMode"));
                                 
                                        if (localConditionMatchMode != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localConditionMatchMode));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("conditionMatchMode cannot be null!!");
                                        }
                                    } if (localKeywordArrayTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "keywordArray"));
                            
                            
                                    if (localKeywordArray==null){
                                         throw new org.apache.axis2.databinding.ADBException("keywordArray cannot be null!!");
                                    }
                                    elementList.add(localKeywordArray);
                                } if (localSystemFieldMatchModeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "systemFieldMatchMode"));
                                 
                                        if (localSystemFieldMatchMode != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSystemFieldMatchMode));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("systemFieldMatchMode cannot be null!!");
                                        }
                                    } if (localSystemFieldConditionArrayTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "systemFieldConditionArray"));
                            
                            
                                    if (localSystemFieldConditionArray==null){
                                         throw new org.apache.axis2.databinding.ADBException("systemFieldConditionArray cannot be null!!");
                                    }
                                    elementList.add(localSystemFieldConditionArray);
                                } if (localMetadataMatchModeTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "metadataMatchMode"));
                                 
                                        if (localMetadataMatchMode != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localMetadataMatchMode));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("metadataMatchMode cannot be null!!");
                                        }
                                    } if (localMetadataConditionArrayTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "metadataConditionArray"));
                            
                            
                                    if (localMetadataConditionArray==null){
                                         throw new org.apache.axis2.databinding.ADBException("metadataConditionArray cannot be null!!");
                                    }
                                    elementList.add(localMetadataConditionArray);
                                } if (localAssetTypeArrayTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "assetTypeArray"));
                            
                            
                                    if (localAssetTypeArray==null){
                                         throw new org.apache.axis2.databinding.ADBException("assetTypeArray cannot be null!!");
                                    }
                                    elementList.add(localAssetTypeArray);
                                } if (localProjectHandleTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "projectHandle"));
                                 
                                        if (localProjectHandle != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localProjectHandle));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("projectHandle cannot be null!!");
                                        }
                                    } if (localRecordsPerPageTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "recordsPerPage"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localRecordsPerPage));
                            } if (localResultsPageTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "resultsPage"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localResultsPage));
                            } if (localSortByTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "sortBy"));
                                 
                                        if (localSortBy != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSortBy));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("sortBy cannot be null!!");
                                        }
                                    } if (localSortDirectionTracker){
                                      elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "sortDirection"));
                                 
                                        if (localSortDirection != null){
                                            elementList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localSortDirection));
                                        } else {
                                           throw new org.apache.axis2.databinding.ADBException("sortDirection cannot be null!!");
                                        }
                                    } if (localResponseFieldArrayTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "responseFieldArray"));
                            
                            
                                    if (localResponseFieldArray==null){
                                         throw new org.apache.axis2.databinding.ADBException("responseFieldArray cannot be null!!");
                                    }
                                    elementList.add(localResponseFieldArray);
                                } if (localExcludeFieldArrayTracker){
                            elementList.add(new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd",
                                                                      "excludeFieldArray"));
                            
                            
                                    if (localExcludeFieldArray==null){
                                         throw new org.apache.axis2.databinding.ADBException("excludeFieldArray cannot be null!!");
                                    }
                                    elementList.add(localExcludeFieldArray);
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
        public static SearchAssetsParam parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            SearchAssetsParam object =
                new SearchAssetsParam();

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
                    
                            if (!"searchAssetsParam".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (SearchAssetsParam)com.scene7.www.ipsapi.xsd.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","companyHandle").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"companyHandle" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setCompanyHandle(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","accessUserHandle").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"accessUserHandle" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAccessUserHandle(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","accessGroupHandle").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"accessGroupHandle" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAccessGroupHandle(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","folder").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"folder" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setFolder(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","includeSubfolders").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"includeSubfolders" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setIncludeSubfolders(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToBoolean(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","publishState").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"publishState" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setPublishState(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","conditionMatchMode").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"conditionMatchMode" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setConditionMatchMode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","keywordArray").equals(reader.getName())){
                                
                                                object.setKeywordArray(com.scene7.www.ipsapi.xsd.StringArray.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","systemFieldMatchMode").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"systemFieldMatchMode" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setSystemFieldMatchMode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","systemFieldConditionArray").equals(reader.getName())){
                                
                                                object.setSystemFieldConditionArray(com.scene7.www.ipsapi.xsd.SystemFieldConditionArray.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","metadataMatchMode").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"metadataMatchMode" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setMetadataMatchMode(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","metadataConditionArray").equals(reader.getName())){
                                
                                                object.setMetadataConditionArray(com.scene7.www.ipsapi.xsd.MetadataConditionArray.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","assetTypeArray").equals(reader.getName())){
                                
                                                object.setAssetTypeArray(com.scene7.www.ipsapi.xsd.StringArray.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","projectHandle").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"projectHandle" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setProjectHandle(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","recordsPerPage").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"recordsPerPage" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setRecordsPerPage(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setRecordsPerPage(java.lang.Integer.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","resultsPage").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"resultsPage" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setResultsPage(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                               object.setResultsPage(java.lang.Integer.MIN_VALUE);
                                           
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","sortBy").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"sortBy" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setSortBy(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","sortDirection").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"sortDirection" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setSortDirection(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","responseFieldArray").equals(reader.getName())){
                                
                                                object.setResponseFieldArray(com.scene7.www.ipsapi.xsd.StringArray.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("http://www.scene7.com/IpsApi/xsd","excludeFieldArray").equals(reader.getName())){
                                
                                                object.setExcludeFieldArray(com.scene7.www.ipsapi.xsd.StringArray.Factory.parse(reader));
                                              
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
           
    