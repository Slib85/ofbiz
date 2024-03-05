$(function() {
	//global config for ajax
	$.ajaxSetup({
		dataType : 'json',
		contentType: "application/x-www-form-urlencoded; charset=utf-8"
	});

	$('button.addcondition').on('click', function(e) {
		var container = null;
		$(this).parent().siblings('.Condition').each(function(i) {
			if($.trim($(this).html()) == '') {
				return true;
			} else {
				container = $(this);
				return false;
			}
		});
		$(this).parent().before(container.clone(true));
		reIndexNumbers('Condition', $(this).parent());
	});
	$('button.addaction').on('click', function(e) {
		var container = null;
		$(this).parent().siblings('.Action').each(function(i) {
			if($.trim($(this).html()) == '') {
				return true;
			} else {
				container = $(this);
				return false;
			}
		});
		$(this).parent().before(container.clone(true));
		reIndexNumbers('Action', $(this).parent());
	});
	$('button.addrule').on('click', function(e) {
		var container = null;
		$(this).parent().parent().siblings('.Rule').each(function(i) {
			if($.trim($(this).html()) == '') {
				return true;
			} else {
				container = $(this);
				return false;
			}
		});
		$(this).parent().parent().before(container.clone(true));
		reIndexNumbers('Rule', $(this).parent().parent());
	});
	$('button.addcoupon').on('click', function(e) {
		var container = $(this).parent().siblings('.coupon:eq(0)');
		$(this).parent().before(container.clone(true));
	});

	//on form submit
	function submitPromo() {
        //console.log($('#tab-1').find('input,select,textarea').serializeObject());
        var productPromoId = null;

        var promoObject = {};
        promoObject.productPromo = $('#tab-1').find('input,select,textarea').serializeObject(); //ProductPromo
        promoObject.productStorePromoAppl = $('#tab-3').find('input,select,textarea').serializeObject(); //ProductStorePromoAppl
        //promoObject.productPromoCode = $('#tab-4').find('input,select,textarea').serializeObject(); //ProductPromoCode

        var rules = [];
        $.each($('#tab-2').find('.row.Rule'), function (i, el) { //loop through rules
            var rulesObj = $(el).find('.row').first().find('input,select,textarea').serializeObject();
            rulesObj.productPromoRuleId = i + 1;

            var conds = [];
            $.each($(el).find('.col-md-12.Condition'), function (i2, el2) { //loop through conditions
                var condObj = $(el2).find('input,select,textarea').serializeObject();
                condObj.productPromoCondSeqId = i2 + 1;
                condObj.productPromoRuleId = i + 1;
                conds.push(condObj);
            });
            rulesObj.conds = conds;

            var actions = [];
            $.each($(el).find('.col-md-12.Action'), function (i2, el2) { //loop through actions
                var actionObj = $(el2).find('input,select,textarea').serializeObject();
                actionObj.productPromoActionSeqId = i2 + 1;
                actionObj.orderAdjustmentTypeId = 'PROMOTION_ADJUSTMENT';
                actionObj.productPromoRuleId = i + 1;
                actions.push(actionObj);
            });
            rulesObj.actions = actions;

            rules.push(rulesObj);
        });

        promoObject.productPromoRule = rules;

        var promoCodes = [];
        $.each($('#tab-4').find('.row.promoCode'), function (i, el) { //loop through rules
            $.each($(el).find('.row.coupon'), function (i2, el2) { //loop through actions
                var pCObj = $(el2).find('input,select,textarea').serializeObject();
                promoCodes.push(pCObj);
            });
        });

        promoObject.productPromoCodes = promoCodes;

        $.ajax({
            type: "POST",
            url: '/admin/control/createOrUpdateProductPromo',
            data: {data: JSON.stringify(promoObject)},
            cache: false,
            async: false
        }).done(function (data) {
            if (data.success) {
                alert("Promo created successfully.");
                window.location = '/admin/control/promoList';
            } else {
                alert("Error trying to create promo.");
            }
        });
    }

    var defaults = Plugin.getDefaults('wizard');
    var options = $.extend(true, {}, defaults, {
        buttonsAppendTo: '#main-panel',
        onFinish: function (event, currentIndex) {
            submitPromo();
        }
    });
    $('#orderWizard').wizard(options).data('wizard');
});

function reIndexNumbers(type, parent) {
	parent.siblings().each(function(i) {
		$(this).find('..panel-title' + '.' + type).html(type + ' #' + (i+1));
	});
}