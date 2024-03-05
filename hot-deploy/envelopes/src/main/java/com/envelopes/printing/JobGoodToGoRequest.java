package com.envelopes.printing;

import com.envelopes.http.FileHelper;
import com.envelopes.util.EnvUtil;
import com.google.gson.Gson;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.ofbiz.base.util.UtilValidate;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Manu on 9/9/2016.
 */
public class JobGoodToGoRequest extends JobIdentifier {

//    private JobIdentifier identifier;
    private String fileColors;
    private String color1, color2, color3, color4;
    private List<String> colors = new ArrayList<>();
    private int numberOfColors;
    private double artHeight;
    private String previewFile;
    private static final String KEYLINE_COLOR = "KEYLINE";
    private boolean standaloneMode = false;

    private enum Key {fileColors, artHeight, previewFile}

    protected JobGoodToGoRequest(Map jsonMap) throws InvalidRequestException {
        super((String)jsonMap.get(JobIdentifier.Key.jobId));
        process(jsonMap);

    }

    public JobGoodToGoRequest(HttpServletRequest request) throws InvalidRequestException {
        super(request);
        /*if(UtilValidate.isEmpty(previewFile = savePreviewFile(request, getJobId()))) {
            throw new InvalidRequestException("Unable to save the preview file");
        }*/
        process(EnvUtil.getParameterMap(request));

    }

    protected void process(Map context) throws InvalidRequestException {

        if(UtilValidate.isEmpty(fileColors = (String)context.get(Key.fileColors.name()))) {
            throw new InvalidRequestException("File Colors is empty");
        }

        if((artHeight = NumberUtils.toDouble((String)context.get(Key.artHeight.name()))) <= 0) {
            throw new InvalidRequestException("Art Height is invalid");
        }

        if(context.containsKey("standaloneMode") && ((String) context.get("standaloneMode")).equals("true")) {
            standaloneMode = true;
        }

        /*if(previewFile == null) {
            if(UtilValidate.isEmpty(previewFile = (String) context.get(Key.previewFile.name()))) {
                throw new InvalidRequestException("Empty preview file");
            }
        }*/

        StringTokenizer tokenizer = new StringTokenizer(fileColors, ",");
        colors = new ArrayList<>();
        while(tokenizer.hasMoreElements()) {
            String color = tokenizer.nextToken().trim();
            if(!color.equals(KEYLINE_COLOR)) {
                colors.add(color);
                numberOfColors ++;
            }
        }

        if(numberOfColors == 0) {
            throw new InvalidRequestException("File color is empty");
        }

        switch(numberOfColors) {    //Don't use break in any of the case blocks, we are using fall through to assign individual colors
            case 4:
                color4 = colors.get(3);
            case 3:
                color3 = colors.get(2);
            case 2:
                color2 = colors.get(1);
            case 1:
                color1 = colors.get(0);
        }
    }

    private String savePreviewFile(HttpServletRequest request, String jobId) throws InvalidRequestException {
        try {
            Map fileData = FileHelper.uploadFile(request, PressManHelper.ARTWORK_PREVIEW_FILE_LOCATION + jobId, false, false);
            if(UtilValidate.isNotEmpty(fileData) && UtilValidate.isNotEmpty(fileData.get("files"))) {
                return (String)((Map)((List) fileData.get("files")).get(0)).get("name");
            }
        } catch(Exception e) {
            throw new InvalidRequestException("Error occurred while saving the artwork preview file due to :" + e.getMessage());
        }

        return "";
    }

    public String getFileColors() {
        return fileColors;
    }

    public String getColor1() {
        return color1;
    }

    public String getColor2() {
        return color2;
    }

    public String getColor3() {
        return color3;
    }

    public String getColor4() {
        return color4;
    }

    public List<String> getColors() {
        return colors;
    }

    public boolean isStandaloneMode() {
        return standaloneMode;
    }

    public int getNumberOfColors() {
        return numberOfColors;
    }

    public double getArtHeight() {
        return artHeight;
    }

    public String getPreviewFile() {
        return previewFile;
    }

    public String getJSON() {
        Map<String, Object> jsonMap = new LinkedHashMap<>();
        jsonMap.put(JobIdentifier.Key.jobId.name(), getJobId());
        jsonMap.put(Key.artHeight.name(), getArtHeight());
        jsonMap.put(Key.fileColors.name(), color1 + (color2.isEmpty() ? "" : ',' + color2) + (color3.isEmpty() ? "" : ',' + color3) + (color4.isEmpty() ? "" : ',' + color4));
        jsonMap.put(Key.previewFile.name(), getPreviewFile());
        return new Gson().toJson(jsonMap, Map.class);
    }

    public JobGoodToGoRequest loadFromJSON(String jsonString) throws InvalidRequestException {
        Map jsonMap = new Gson().fromJson(jsonString, Map.class);
        return new JobGoodToGoRequest(jsonMap);
    }
}
