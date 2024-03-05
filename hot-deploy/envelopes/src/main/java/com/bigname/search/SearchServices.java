package com.bigname.search;

import com.envelopes.http.FileHelper;
import com.envelopes.util.EnvConstantsUtil;
import com.envelopes.util.EnvUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.base.util.UtilDateTime;
import org.apache.ofbiz.base.util.UtilMisc;
import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;
import org.apache.ofbiz.service.DispatchContext;
import org.apache.ofbiz.service.LocalDispatcher;
import org.apache.ofbiz.service.ServiceUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by shoabkhan on 6/17/17.
 */
public class SearchServices {
    public static final String module = SearchServices.class.getName();

    private static final String SYN_FOLDER = "/etc/synonyms/";
    private static final String SYN_FOLDER_BKUP = "/etc/synonyms/backup/";

    /**
     * Generate the synonym file for each website
     * @param dctx
     * @param context
     * @return
     */
    public static Map<String, Object> generateSynonymFile(DispatchContext dctx, Map<String, ? extends Object> context) {
        LocalDispatcher dispatcher = dctx.getDispatcher();
        Delegator delegator = dctx.getDelegator();

        Map<String, Object> result = ServiceUtil.returnSuccess();

        try {
            if(moveFiles()) {
                List<GenericValue> synonyms = EntityQuery.use(delegator).from("SearchSynonym").orderBy(UtilMisc.toList("webSiteId ASC", "synonymTypeId DESC")).queryList();
                String webSiteId = null;

                if (UtilValidate.isNotEmpty(synonyms)) {
                    StringBuilder data = new StringBuilder();
                    for (GenericValue synonym : synonyms) {
                        /*
                         * If webSiteId is not null and the current webSiteId is not the same
                         * New group is about to start, save the old file
                         */
                        if (webSiteId != null && !webSiteId.equalsIgnoreCase(synonym.getString("webSiteId"))) {
                            saveFile(webSiteId, data.toString());

                            //reset variables
                            data = new StringBuilder();
                            webSiteId = synonym.getString("webSiteId");
                        }

                        //set the default
                        if (webSiteId == null) {
                            webSiteId = synonym.getString("webSiteId");
                        }

                        //add data
                        if ("TWO_WAY".equalsIgnoreCase(synonym.getString("synonymTypeId"))) {
                            data.append(synonym.getString("fromSynonyms")).append(System.lineSeparator());
                        } else {
                            data.append(synonym.getString("fromSynonyms")).append("=>").append(synonym.getString("toSynonyms")).append(System.lineSeparator());
                        }
                    }

                    //save the last iteration
                    saveFile(webSiteId, data.toString());
                }
            }
        } catch(Exception e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error trying to create file.", module);
            return ServiceUtil.returnError(e.getMessage());
        }

        return result;
    }

    /**
     * Move the synonym files to a backup location before updating
     * @return
     */
    public static boolean moveFiles() {
        boolean success = false;

        File folder = new File(FileHelper.cleanFolderPath(EnvConstantsUtil.OFBIZ_HOME + SYN_FOLDER));
        File[] listOfFiles = folder.listFiles();

        try {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    File newFile = new File(FileHelper.cleanFolderPath(EnvConstantsUtil.OFBIZ_HOME + SYN_FOLDER_BKUP), FilenameUtils.removeExtension(file.getName()) + "_" + UtilDateTime.nowDateString() + ".txt");
                    FileUtils.moveFile(file, newFile);
                }
            }
            success = true;
        } catch (IOException e) {
            EnvUtil.reportError(e);
            Debug.logError(e, "Error trying to copy synonym file.", module);
        }

        return success;
    }

    /**
     * Save the synonym file
     * @param webSiteId
     * @param data
     * @throws Exception
     */
    public static void saveFile(String webSiteId, String data) throws Exception {
        String filePath = FileHelper.cleanFolderPath(EnvConstantsUtil.OFBIZ_HOME + SYN_FOLDER);
        File file = new File(filePath, webSiteId + ".txt");

        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(data);
        bw.close();
    }
}
