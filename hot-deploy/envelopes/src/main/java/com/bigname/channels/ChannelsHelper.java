package com.bigname.channels;

import com.google.gson.Gson;
import org.apache.ofbiz.base.util.Debug;
import org.apache.ofbiz.entity.Delegator;
import org.apache.ofbiz.entity.GenericValue;
import org.apache.ofbiz.entity.util.EntityQuery;

import java.util.*;

public class ChannelsHelper {
    public static final String module = ChannelsHelper.class.getName();

    public static Map<String, Long> formatChannelsData(String channelsDataString) {
        Map<String, Long> newChannelsDataMap = new HashMap<>();

        try {
            Map<String, Object> channelsDataMap = new Gson().fromJson(channelsDataString, HashMap.class);
            Iterator channelsData = channelsDataMap.keySet().iterator();

            while(channelsData.hasNext()) {
                String channelName = (String) channelsData.next();
                newChannelsDataMap.put(channelName, Long.valueOf(String.valueOf(channelsDataMap.get(channelName))));
            }
        } catch (Exception e) {
            Debug.log("There was an issue trying to format channels data: " + e.getMessage(), module);
        }

        return newChannelsDataMap;
    }

    public static List<GenericValue> getChannelsQuantityOverrideList(Delegator delegator) {
        List<GenericValue> channelsQuantityOverrideList = new ArrayList<>();

        try {
            channelsQuantityOverrideList = EntityQuery.use(delegator).from("ChannelsQuantityOverride").queryList();
        } catch (Exception e) {
            Debug.log("There was an issue trying to get the Channels Quantity Override List Data: " + e.getMessage(), module);
        }

        return channelsQuantityOverrideList;
    }

    public static void updateChannelsQuantityOverride(Delegator delegator, String productId, String channelName, Long quantity) throws Exception {
        GenericValue channelsQuantity = EntityQuery.use(delegator).from("ChannelsQuantityOverride").where("productId", productId).queryOne();
        channelsQuantity.put("productId", productId);
        channelsQuantity.put(channelName, quantity);
        delegator.store(channelsQuantity);
    }

    public static void addChannelsQuantityOverride(Delegator delegator, String productId, Map<String, Long> channelsDataMap) throws Exception {
        GenericValue channelsQuantity = delegator.makeValue("ChannelsQuantityOverride");
        channelsQuantity.put("productId", productId);

        Iterator channelsData = channelsDataMap.keySet().iterator();

        while(channelsData.hasNext()) {
            String channelName = (String) channelsData.next();
            channelsQuantity.put(channelName, channelsDataMap.get(channelName));
        }
        delegator.createOrStore(channelsQuantity);
    }
}
