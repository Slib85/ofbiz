

/**
 * IpsApiService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.scene7.www.ipsapi;

    /*
     *  IpsApiService java interface
     */

    public interface IpsApiService {
          

        /**
          * Auto generated method signature
          * 
                    * @param executeJobParam
                
                    * @param authHeader
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.ExecuteJobReturn executeJob(

                        com.scene7.www.ipsapi.xsd.ExecuteJobParam executeJobParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param removeFolderPermissionsParam
                
                    * @param authHeader0
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.RemoveFolderPermissionsReturn removeFolderPermissions(

                        com.scene7.www.ipsapi.xsd.RemoveFolderPermissionsParam removeFolderPermissionsParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader0)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param getFoldersParam
                
                    * @param authHeader1
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.GetFoldersReturn getFolders(

                        com.scene7.www.ipsapi.xsd.GetFoldersParam getFoldersParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader1)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param generatePasswordParam
                
                    * @param authHeader2
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.GeneratePasswordReturn generatePassword(

                        com.scene7.www.ipsapi.xsd.GeneratePasswordParam generatePasswordParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader2)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param getGroupsParam
                
                    * @param authHeader3
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.GetGroupsReturn getGroups(

                        com.scene7.www.ipsapi.xsd.GetGroupsParam getGroupsParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader3)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param saveZoomTargetParam
                
                    * @param authHeader4
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.SaveZoomTargetReturn saveZoomTarget(

                        com.scene7.www.ipsapi.xsd.SaveZoomTargetParam saveZoomTargetParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader4)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param getMetadataFieldsParam
                
                    * @param authHeader5
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.GetMetadataFieldsReturn getMetadataFields(

                        com.scene7.www.ipsapi.xsd.GetMetadataFieldsParam getMetadataFieldsParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader5)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param getUniqueMetadataValuesParam
                
                    * @param authHeader6
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.GetUniqueMetadataValuesReturn getUniqueMetadataValues(

                        com.scene7.www.ipsapi.xsd.GetUniqueMetadataValuesParam getUniqueMetadataValuesParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader6)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param getCompanyMembershipParam
                
                    * @param authHeader7
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.GetCompanyMembershipReturn getCompanyMembership(

                        com.scene7.www.ipsapi.xsd.GetCompanyMembershipParam getCompanyMembershipParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader7)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param addUserParam
                
                    * @param authHeader8
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.AddUserReturn addUser(

                        com.scene7.www.ipsapi.xsd.AddUserParam addUserParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader8)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param saveImageFormatParam
                
                    * @param authHeader9
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.SaveImageFormatReturn saveImageFormat(

                        com.scene7.www.ipsapi.xsd.SaveImageFormatParam saveImageFormatParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader9)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param setImageSetMembersParam
                
                    * @param authHeader10
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.SetImageSetMembersReturn setImageSetMembers(

                        com.scene7.www.ipsapi.xsd.SetImageSetMembersParam setImageSetMembersParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader10)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param saveImageMapParam
                
                    * @param authHeader11
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.SaveImageMapReturn saveImageMap(

                        com.scene7.www.ipsapi.xsd.SaveImageMapParam saveImageMapParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader11)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param getScheduledJobsParam
                
                    * @param authHeader12
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.GetScheduledJobsReturn getScheduledJobs(

                        com.scene7.www.ipsapi.xsd.GetScheduledJobsParam getScheduledJobsParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader12)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param addGroupMembersParam
                
                    * @param authHeader13
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.AddGroupMembersReturn addGroupMembers(

                        com.scene7.www.ipsapi.xsd.AddGroupMembersParam addGroupMembersParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader13)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param setFolderPermissionsParam
                
                    * @param authHeader14
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.SetFolderPermissionsReturn setFolderPermissions(

                        com.scene7.www.ipsapi.xsd.SetFolderPermissionsParam setFolderPermissionsParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader14)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param getProjectsParam
                
                    * @param authHeader15
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.GetProjectsReturn getProjects(

                        com.scene7.www.ipsapi.xsd.GetProjectsParam getProjectsParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader15)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param deleteGroupParam
                
                    * @param authHeader16
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.DeleteGroupReturn deleteGroup(

                        com.scene7.www.ipsapi.xsd.DeleteGroupParam deleteGroupParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader16)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param setUserInfoParam
                
                    * @param authHeader17
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.SetUserInfoReturn setUserInfo(

                        com.scene7.www.ipsapi.xsd.SetUserInfoParam setUserInfoParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader17)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param getActiveJobsParam
                
                    * @param authHeader18
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.GetActiveJobsReturn getActiveJobs(

                        com.scene7.www.ipsapi.xsd.GetActiveJobsParam getActiveJobsParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader18)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param removeGroupMembershipParam
                
                    * @param authHeader19
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.RemoveGroupMembershipReturn removeGroupMembership(

                        com.scene7.www.ipsapi.xsd.RemoveGroupMembershipParam removeGroupMembershipParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader19)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param deleteAssetParam
                
                    * @param authHeader20
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.DeleteAssetReturn deleteAsset(

                        com.scene7.www.ipsapi.xsd.DeleteAssetParam deleteAssetParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader20)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param getCompanyMembersParam
                
                    * @param authHeader21
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.GetCompanyMembersReturn getCompanyMembers(

                        com.scene7.www.ipsapi.xsd.GetCompanyMembersParam getCompanyMembersParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader21)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param getJobLogsParam
                
                    * @param authHeader22
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.GetJobLogsReturn getJobLogs(

                        com.scene7.www.ipsapi.xsd.GetJobLogsParam getJobLogsParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader22)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param renameFolderParam
                
                    * @param authHeader23
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.RenameFolderReturn renameFolder(

                        com.scene7.www.ipsapi.xsd.RenameFolderParam renameFolderParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader23)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param removeGroupMembersParam
                
                    * @param authHeader24
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.RemoveGroupMembersReturn removeGroupMembers(

                        com.scene7.www.ipsapi.xsd.RemoveGroupMembersParam removeGroupMembersParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader24)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param getUserCharsParam
                
                    * @param authHeader25
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.GetUserCharsReturn getUserChars(

                        com.scene7.www.ipsapi.xsd.GetUserCharsParam getUserCharsParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader25)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param pauseJobParam
                
                    * @param authHeader26
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.PauseJobReturn pauseJob(

                        com.scene7.www.ipsapi.xsd.PauseJobParam pauseJobParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader26)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param getImageSetMembersParam
                
                    * @param authHeader27
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.GetImageSetMembersReturn getImageSetMembers(

                        com.scene7.www.ipsapi.xsd.GetImageSetMembersParam getImageSetMembersParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader27)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param deleteZoomTargetParam
                
                    * @param authHeader28
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.DeleteZoomTargetReturn deleteZoomTarget(

                        com.scene7.www.ipsapi.xsd.DeleteZoomTargetParam deleteZoomTargetParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader28)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param stopJobParam
                
                    * @param authHeader29
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.StopJobReturn stopJob(

                        com.scene7.www.ipsapi.xsd.StopJobParam stopJobParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader29)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param setAssetPublishStateParam
                
                    * @param authHeader30
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.SetAssetPublishStateReturn setAssetPublishState(

                        com.scene7.www.ipsapi.xsd.SetAssetPublishStateParam setAssetPublishStateParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader30)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param setPasswordParam
                
                    * @param authHeader31
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.SetPasswordReturn setPassword(

                        com.scene7.www.ipsapi.xsd.SetPasswordParam setPasswordParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader31)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param renameAssetParam
                
                    * @param authHeader32
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.RenameAssetReturn renameAsset(

                        com.scene7.www.ipsapi.xsd.RenameAssetParam renameAssetParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader32)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param addCompanyMembershipParam
                
                    * @param authHeader33
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.AddCompanyMembershipReturn addCompanyMembership(

                        com.scene7.www.ipsapi.xsd.AddCompanyMembershipParam addCompanyMembershipParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader33)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param moveAssetParam
                
                    * @param authHeader34
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.MoveAssetReturn moveAsset(

                        com.scene7.www.ipsapi.xsd.MoveAssetParam moveAssetParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader34)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param searchAssetsParam
                
                    * @param authHeader35
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.SearchAssetsReturn searchAssets(

                        com.scene7.www.ipsapi.xsd.SearchAssetsParam searchAssetsParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader35)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param addCompanyParam
                
                    * @param authHeader36
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.AddCompanyReturn addCompany(

                        com.scene7.www.ipsapi.xsd.AddCompanyParam addCompanyParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader36)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param checkLoginParam
                
                    * @param authHeader37
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.CheckLoginReturn checkLogin(

                        com.scene7.www.ipsapi.xsd.CheckLoginParam checkLoginParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader37)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param deleteJobParam
                
                    * @param authHeader38
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.DeleteJobReturn deleteJob(

                        com.scene7.www.ipsapi.xsd.DeleteJobParam deleteJobParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader38)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param removeCompanyMembershipParam
                
                    * @param authHeader39
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.RemoveCompanyMembershipReturn removeCompanyMembership(

                        com.scene7.www.ipsapi.xsd.RemoveCompanyMembershipParam removeCompanyMembershipParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader39)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param updateAssetPermissionsParam
                
                    * @param authHeader40
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.UpdateAssetPermissionsReturn updateAssetPermissions(

                        com.scene7.www.ipsapi.xsd.UpdateAssetPermissionsParam updateAssetPermissionsParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader40)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param getGroupMembershipParam
                
                    * @param authHeader41
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.GetGroupMembershipReturn getGroupMembership(

                        com.scene7.www.ipsapi.xsd.GetGroupMembershipParam getGroupMembershipParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader41)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param setGroupMembershipParam
                
                    * @param authHeader42
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.SetGroupMembershipReturn setGroupMembership(

                        com.scene7.www.ipsapi.xsd.SetGroupMembershipParam setGroupMembershipParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader42)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param createImageSetParam
                
                    * @param authHeader43
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.CreateImageSetReturn createImageSet(

                        com.scene7.www.ipsapi.xsd.CreateImageSetParam createImageSetParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader43)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param deleteFolderParam
                
                    * @param authHeader44
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.DeleteFolderReturn deleteFolder(

                        com.scene7.www.ipsapi.xsd.DeleteFolderParam deleteFolderParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader44)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param getCompanyInfoParam
                
                    * @param authHeader45
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.GetCompanyInfoReturn getCompanyInfo(

                        com.scene7.www.ipsapi.xsd.GetCompanyInfoParam getCompanyInfoParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader45)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param getDiskUsageParam
                
                    * @param authHeader46
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.GetDiskUsageReturn getDiskUsage(

                        com.scene7.www.ipsapi.xsd.GetDiskUsageParam getDiskUsageParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader46)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param deleteImageMapParam
                
                    * @param authHeader47
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.DeleteImageMapReturn deleteImageMap(

                        com.scene7.www.ipsapi.xsd.DeleteImageMapParam deleteImageMapParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader47)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param getAllCompaniesParam
                
                    * @param authHeader48
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.GetAllCompaniesReturn getAllCompanies(

                        com.scene7.www.ipsapi.xsd.GetAllCompaniesParam getAllCompaniesParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader48)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param resumeJobParam
                
                    * @param authHeader49
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.ResumeJobReturn resumeJob(

                        com.scene7.www.ipsapi.xsd.ResumeJobParam resumeJobParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader49)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param updateFolderPermissionsParam
                
                    * @param authHeader50
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.UpdateFolderPermissionsReturn updateFolderPermissions(

                        com.scene7.www.ipsapi.xsd.UpdateFolderPermissionsParam updateFolderPermissionsParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader50)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param setCompanyMembershipParam
                
                    * @param authHeader51
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.SetCompanyMembershipReturn setCompanyMembership(

                        com.scene7.www.ipsapi.xsd.SetCompanyMembershipParam setCompanyMembershipParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader51)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param removeAssetPermissionsParam
                
                    * @param authHeader52
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.RemoveAssetPermissionsReturn removeAssetPermissions(

                        com.scene7.www.ipsapi.xsd.RemoveAssetPermissionsParam removeAssetPermissionsParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader52)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param getAssetsParam
                
                    * @param authHeader53
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.GetAssetsReturn getAssets(

                        com.scene7.www.ipsapi.xsd.GetAssetsParam getAssetsParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader53)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param saveMetadataFieldParam
                
                    * @param authHeader54
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.SaveMetadataFieldReturn saveMetadataField(

                        com.scene7.www.ipsapi.xsd.SaveMetadataFieldParam saveMetadataFieldParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader54)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param setAssetMetadataParam
                
                    * @param authHeader55
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.SetAssetMetadataReturn setAssetMetadata(

                        com.scene7.www.ipsapi.xsd.SetAssetMetadataParam setAssetMetadataParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader55)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param getAllUsersParam
                
                    * @param authHeader56
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.GetAllUsersReturn getAllUsers(

                        com.scene7.www.ipsapi.xsd.GetAllUsersParam getAllUsersParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader56)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param getPropertyParam
                
                    * @param authHeader57
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.GetPropertyReturn getProperty(

                        com.scene7.www.ipsapi.xsd.GetPropertyParam getPropertyParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader57)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param saveGroupParam
                
                    * @param authHeader58
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.SaveGroupReturn saveGroup(

                        com.scene7.www.ipsapi.xsd.SaveGroupParam saveGroupParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader58)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param setAssetPermissionsParam
                
                    * @param authHeader59
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.SetAssetPermissionsReturn setAssetPermissions(

                        com.scene7.www.ipsapi.xsd.SetAssetPermissionsParam setAssetPermissionsParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader59)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param getUserInfoParam
                
                    * @param authHeader60
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.GetUserInfoReturn getUserInfo(

                        com.scene7.www.ipsapi.xsd.GetUserInfoParam getUserInfoParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader60)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param addGroupMembershipParam
                
                    * @param authHeader61
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.AddGroupMembershipReturn addGroupMembership(

                        com.scene7.www.ipsapi.xsd.AddGroupMembershipParam addGroupMembershipParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader61)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param getOriginalFilePathsParam
                
                    * @param authHeader62
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.GetOriginalFilePathsReturn getOriginalFilePaths(

                        com.scene7.www.ipsapi.xsd.GetOriginalFilePathsParam getOriginalFilePathsParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader62)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param createFolderParam
                
                    * @param authHeader63
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.CreateFolderReturn createFolder(

                        com.scene7.www.ipsapi.xsd.CreateFolderParam createFolderParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader63)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param submitJobParam
                
                    * @param authHeader64
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.SubmitJobReturn submitJob(

                        com.scene7.www.ipsapi.xsd.SubmitJobParam submitJobParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader64)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param getGroupMembersParam
                
                    * @param authHeader65
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.GetGroupMembersReturn getGroupMembers(

                        com.scene7.www.ipsapi.xsd.GetGroupMembersParam getGroupMembersParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader65)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param deleteImageFormatParam
                
                    * @param authHeader66
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.DeleteImageFormatReturn deleteImageFormat(

                        com.scene7.www.ipsapi.xsd.DeleteImageFormatParam deleteImageFormatParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader66)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param getImageFormatsParam
                
                    * @param authHeader67
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.GetImageFormatsReturn getImageFormats(

                        com.scene7.www.ipsapi.xsd.GetImageFormatsParam getImageFormatsParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader67)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param setGroupMembersParam
                
                    * @param authHeader68
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.SetGroupMembersReturn setGroupMembers(

                        com.scene7.www.ipsapi.xsd.SetGroupMembersParam setGroupMembersParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader68)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param getJobLogDetailsParam
                
                    * @param authHeader69
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.GetJobLogDetailsReturn getJobLogDetails(

                        com.scene7.www.ipsapi.xsd.GetJobLogDetailsParam getJobLogDetailsParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader69)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param getFolderTreeParam
                
                    * @param authHeader70
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.GetFolderTreeReturn getFolderTree(

                        com.scene7.www.ipsapi.xsd.GetFolderTreeParam getFolderTreeParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader70)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        /**
          * Auto generated method signature
          * 
                    * @param getUsersParam
                
                    * @param authHeader71
                
             * @throws com.scene7.www.ipsapi.AuthorizationFaultException : 
             * @throws com.scene7.www.ipsapi.AuthenticationFaultException : 
             * @throws com.scene7.www.ipsapi.IpsApiFaultException : 
         */

         
                     public com.scene7.www.ipsapi.xsd.GetUsersReturn getUsers(

                        com.scene7.www.ipsapi.xsd.GetUsersParam getUsersParam,com.scene7.www.ipsapi.xsd.AuthHeader authHeader71)
                        throws java.rmi.RemoteException
             
          ,com.scene7.www.ipsapi.AuthorizationFaultException
          ,com.scene7.www.ipsapi.AuthenticationFaultException
          ,com.scene7.www.ipsapi.IpsApiFaultException;

        

        
       //
       }
    