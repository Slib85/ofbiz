package com.bigname.pricingengine.mock;

import com.bigname.pricingengine.PricingEngine;
import com.bigname.pricingengine.common.Config;
import com.bigname.pricingengine.common.impl.ConfigSupport;
import com.bigname.pricingengine.request.PricingRequest;
import com.bigname.pricingengine.request.impl.DefaultPricingRequest;
import com.bigname.pricingengine.response.PricingResponse;
import com.google.gson.Gson;

import java.util.Map;

public class RequestData {

    static String JSON = "{" +
            "            \"VENDOR_SKU\" : \"SF-101\"," +
            "            \"VENDOR_ID\" : \"ADMORE\"," +
            "            \"PREFERRED_VENDOR_ID\" : \"ADMORE\"," +
            "            \"COLOR_GROUP\" : \"White\"," +
            "            \"COLOR_NAME\" : \"Smooth White\"," +
            "            \"PAPER_TEXTURE\" : \"\"," +
            "            \"PAPER_WEIGHT\" : \"100lb.\"," +
            "            \"CUSTOM_QUANTITY\" : 450," +
            "            \"QUANTITIES\" : [100, 250, 500, 1000]," +
            "            \"RESPONSE_TYPE\" : \"DETAILED\"," +
            "            \"CUSTOM_OPTIONS\" : [" +
            "               {" +
            "                   \"CUSTOM_OPTION_NAME\" : \"OFFSET\"," +
            "                   \"SIDES\" : [" +
            "                       {" +
            "                           \"PRINT_METHOD\" : \"PMS\"," +
            "                           \"NUMBER_OF_INKS\" : 12," +
            "                           \"HEAVY_COVERAGE\" : \"Y\"," +
            "                           \"COLOR_WASHES\" : 0," +
            "                           \"PLATE_CHANGES\" : 0" +
            "                       }," +
            "                       {" +
            "                           \"PRINT_METHOD\" : \"FOUR_COLOR\"," +
            "                           \"NUMBER_OF_INKS\" : 3," +
            "                           \"HEAVY_COVERAGE\" : \"Y\"," +
            "                           \"COLOR_WASHES\" : 0," +
            "                           \"PLATE_CHANGES\" : 0" +
            "                       }" +
            "                   ]" +
            "               }," +
            "               {" +
            "                   \"CUSTOM_OPTION_NAME\" : \"FOIL_STAMPING\"," +
            "                   \"RUNS\" : [" +
            "                       {" +
            "                           \"IMAGES\" : [\"15\", \"36\", \"40\", \"40+\", \"15\", \"15\", \"15\", \"15\", \"15\", \"15\"]" +
            "                       }," +
            "                       {" +
            "                           \"IMAGES\" : [\"15\", \"36\", \"40\", \"40+\", \"15\", \"15\", \"15\", \"15\", \"15\", \"15\"]" +
            "                       }," +
            "                       {" +
            "                           \"IMAGES\" : [\"15\", \"36\", \"40\", \"40+\", \"15\", \"15\", \"15\", \"15\", \"15\", \"15\"]" +
            "                       }," +
            "                       {" +
            "                           \"IMAGES\" : [\"15\", \"36\", \"40\", \"40+\", \"15\", \"15\", \"15\", \"15\", \"15\", \"15\"]" +
            "                       }," +
            "                       {" +
            "                           \"IMAGES\" : [\"15\", \"36\", \"40\", \"40+\", \"15\", \"15\", \"15\", \"15\", \"15\", \"15\"]" +
            "                       }" +
            "                   ]" +
            "               }," +
            "               {" +
            "                   \"CUSTOM_OPTION_NAME\" : \"EMBOSSING\"," +
            "                   \"SIDES\" : [" +
            "                       {" +
            "                           \"RUNS\" : [" +
            "                           {" +
            "                               \"IMAGES\" : [\"15\", \"36\", \"40\", \"40+\", \"15\", \"15\", \"15\", \"15\", \"15\", \"15\"]" +
            "                           }," +
            "                           {" +
            "                               \"IMAGES\" : [\"15\", \"36\", \"40\", \"40+\", \"15\", \"15\", \"15\", \"15\", \"15\", \"15\"]" +
            "                           }" +
            "                                   ]" +
            "                       }," +
            "                       {" +
            "                           \"RUNS\" : [" +
            "                           {" +
            "                               \"IMAGES\" : [\"15\", \"36\", \"40\", \"40+\", \"15\", \"15\", \"15\", \"15\", \"15\", \"15\"]" +
            "                           }," +
            "                           {" +
            "                               \"IMAGES\" : [\"15\", \"36\", \"40\", \"40+\", \"15\", \"15\", \"15\", \"15\", \"15\", \"15\"]" +
            "                           }" +
            "                                   ]" +
            "                       }" +
            "                   ]" +
            "               }" +
            "           ]," +
            "           \"ADDONS_OPTION\" : [" +
            "               {" +
            "                    \"ADDON_TYPE\" : \"COATINGS\"," +
            "                    \"SIDES\" : [" +
            "                       {" +
            "                           \"ADDONS\" : [\"AQUEOUS_MATT\"]" +
            "                       }," +
            "                       {" +
            "                           \"ADDONS\" : [\"AQUEOUS_MATT\"]" +
            "                       }" +
            "                    ]" +
            "               }," +
            "               {" +
            "                    \"ADDON_TYPE\" : \"ATTACHMENTS\"," +
            "                    \"SIDES\" : [" +
            "                       {" +
            "                           \"ADDONS\" : [\"VELCRO\"]" +
            "                       }" +
            "                    ]" +
            "               }" +
            "           ]" +
            "       }";


    public static Map<String, Object> getMockRequest() {
        return new Gson().fromJson(JSON, Map.class);
    }

}
