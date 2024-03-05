<style>
    .channel_quantity_header {
        border-bottom: 1px solid #c3c3c3;
        background-color: #000000;
        color: #ffffff;
        font-weight: bold;
    }
    .channel_quantity_container {
        border: 1px solid #c3c3c3;
        overflow-y: scroll;
        height: 510px;
        width: 519px;
    }
        .channel_quantity,
        .channel_quantity_header {
            display: table;
            width: 500px;
        }
        .channel_quantity:nth-child(even) {
            background-color: #f1f1f1;
        }
            .channel_quantity > *,
            .channel_quantity_header > * {
                display: table-cell;
                width: 100px; 
                padding: 5px;
                border-left: 1px solid #c3c3c3;
            }
            .channel_quantity > *:first-child,
            .channel_quantity_header > *:first-child {
                width: auto;
                border-left: 0px;
            }
            .channel_quantity .input_div {
                cursor: text;
            }
                .channel_quantity .input_div input {
                    width: 90px;
                    height: 24px;
                }
    .channel_quantity_response {
        text-align: center;
        width: 500px;
        background-color: green;
        color: #ffffff;
        font-weight: bold;
        padding: 5px;
        margin-top: 10px;
    }
</style>

<div>
    <input type="text" name="newSku" placeholder="Product Id" />
    <input type="text" bns-column_name="amazon" name="newAmazonQuantity" placeholder="Amazon Quantity" />
    <input type="text" bns-column_name="officeDepot" name="newOfficeDepotQuantity" placeholder="Office Depot Quantity" />
    <input type="text" bns-column_name="staples" name="newStaplesQuantity" placeholder="Staples Quantity"/>
    <div bns-submit_new_sku style="border-radius: 5px; background-color: #009900; font-weight: bold; text-transform: uppercase; letter-spacing: 1px; color: #FFFFFF; padding: 10px; cursor: pointer; display: inline-block;">Submit</div>
</div>

<div class="channel_quantity_header">
    <div>Product ID</div>
    <div>Amazon</div>
    <div>Office Depot</div>
    <div>Staples</div>
</div>
<div class="channel_quantity_container">
    <#list channelsQuantityOverrideList as channelsQuantityOverride>
        <div bns-channel_quantity="${channelsQuantityOverride["productId"]}" class="channel_quantity">
            <div>${channelsQuantityOverride["productId"]}</div>
            <div bns-input_div class="input_div" bns-column_name="amazon" bns-input_value="${channelsQuantityOverride["amazon"]?default("")}">${channelsQuantityOverride["amazon"]?default("")}</div>
            <div bns-input_div class="input_div" bns-column_name="officeDepot" bns-input_value="${channelsQuantityOverride["officeDepot"]?default("")}">${channelsQuantityOverride["officeDepot"]?default("")}</div>
            <div bns-input_div class="input_div" bns-column_name="staples" bns-input_value="${channelsQuantityOverride["staples"]?default("")}">${channelsQuantityOverride["staples"]?default("")}</div>
        </div>
    </#list>
</div>
<div bns-channel_quantity_response class="channel_quantity_response" style="display: none;"></div>

<script>
    function filterQuantity(value) {
        if (typeof value !== "undefined" && value != "") {
            value = value.replace(/[^0-9\-]/g, "").match(/(^.)/)[1] + value.replace(/^./, "").replace(/[^0-9]/g, "");
            return (value == "" ? 0 : value) + "";
        } else {
            return "0";
        }

    }

    $("[bns-submit_new_sku]").on("click", function() {
        var channelData = {};

        $(this).parent().find($("[bns-column_name]")).each(function() {
            channelData[$(this).attr("bns-column_name")] = filterQuantity($(this).val());
        });

        $.ajax({
            type: "POST",
            url: "/admin/control/addChannelsQuantityOverride",
            dataType: "json",
            data: {
                "productId": $("[name=\"newSku\"]").val(),
                "channelData": JSON.stringify(channelData),
            }
        }).done(function(response) {
            if(response.success) {
                $("[bns-channel_quantity_response]").show();
                $("[bns-channel_quantity_response]").html(productId + " Saved!");

                waitForFinalEvent(function() {
                    $("[bns-channel_quantity_response]").fadeOut("slow");
                }, 3000, "itemSaved");
            } else if (typeof response.error !== "undefined" ) {
                alert(response.error);
            }
        })
    });

    $("[bns-input_div]").on("click", function() {
        if ($(this).children().length == 0) {
            $(this).html("");

            $(this).append(
                $("<input />").attr({
                    "name": $(this).attr("bns-column_name"),
                    "value": $(this).attr("bns-input_value")
                }).on("input.update_quantity", function(e) {
                    var element = $(this);
                    var productId = element.parents("[bns-channel_quantity]").attr("bns-channel_quantity");
                    var channelName = element.attr("name");
                    
                    if (element.val() != "") {
                        var quantity = filterQuantity(element.val());

                        element.val(quantity);
                        element.parent().attr("bns-input_value", quantity);
                        element[0].setSelectionRange(e.target.selectionStart, e.target.selectionStart);
                        
                        $("[bns-channel_quantity_response]").hide();

                        waitForFinalEvent(function() {
                            if (quantity != "") {
                                $.ajax({
                                    type: "POST",
                                    url: "/admin/control/updateChannelsQuantityOverride",
                                    dataType: "json",
                                    data: {
                                        "productId": productId,
                                        "channelName": channelName,
                                        "quantity": quantity
                                    }
                                }).done(function(response) {
                                    if(response.success) {
                                        $("[bns-channel_quantity_response]").show();
                                        $("[bns-channel_quantity_response]").html(productId + " Saved!");

                                        waitForFinalEvent(function() {
                                            $("[bns-channel_quantity_response]").fadeOut("slow");
                                        }, 3000, "itemSaved");
                                    } else if (typeof response.error !== "undefined" ) {
                                        alert(response.error);
                                    }
                                })
                            }
                        }, 1000, "update_quantity");
                    }
                }).on("blur.update_quantity", function() {
                    $(this).parent().html($(this).val());
                })
            );

            var inputElement = $(this).children()[0];

            $(inputElement).on("focus", function() {
                this.setSelectionRange(0, $(this).val().length);
            });

            inputElement.focus();
        }
    });
</script>