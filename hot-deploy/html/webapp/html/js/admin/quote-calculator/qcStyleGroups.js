(function($) {
    $.extend({
        styleGroupHelper: function() {
            var helper = {};
            helper.entityName = 'Style Group';
            helper.entityNamePlural = 'Style Groups';
            helper.endpoint = '/admin/control/qcStyleGroup';
            helper.render = function(data, editMode) {
                $.styleGroupDetailsRenderer(data, editMode);
            };
            helper.build = function(entity, rawDataOnly) {
                return $.styleGroupRowBuilder(entity, rawDataOnly);
            };
            helper.valid = function(form) {
                return $.styleGroupValidator(form);
            };
            return helper;
        },
        styleGroupValidator: function(form) {
            var valid = true;
            var formName = $(form).attr('name');
            var styleGroupIdEl = $(form).find('input[name="styleGroupId"]');
            var styleGroupNameEl = $(form).find('input[name="name"]');
            if(formName === 'addForm' ) {
                if(styleGroupIdEl.val() === '') {
                    valid = false;
                    $.invalidate(styleGroupIdEl);
                } else if(/^[A-Za-z0-9_]+$/.test(styleGroupIdEl.val()) === false) {
                    valid = false;
                    $.invalidate(styleGroupIdEl, "Use letters, numbers and underscores only for Style Group Id ");
                } else {
                    $.ajax({
                        type: 'POST',
                        url: 'qcIsValidId',
                        data: {'id' : styleGroupIdEl.val(), 'type' : 'StyleGroup'},
                        dataType: 'json',
                        cache: false,
                        async: false
                    }).done(function (data) {
                        if(data.success !== true || data.valid !== true) {
                            valid = false;
                            $.invalidate(styleGroupIdEl, "The Style Group Id already exists ");
                        }
                    });
                }
            }
            if(styleGroupNameEl.val() === '') {
                valid = false;
                $.invalidate(styleGroupNameEl);
            }
            return valid;
        },
        invalidate: function(inputEl, errorMessage) {
            $(inputEl).on('focus', function() {$(this).closest('.form-group').removeClass('has-danger')});
            $(inputEl).closest('.form-group').addClass('has-danger');
            if(typeof errorMessage !== 'undefined') {
                $(inputEl).siblings('.text-error').text(errorMessage);
            } else {
                $(inputEl).siblings('.text-error').text($(inputEl).siblings('.text-error').data('default-message'));
            }
        },
        styleGroupDetailsRenderer: function(data, editMode) {
            if(data.success === false) {
                $.showMessage('An error occurred while getting the Style Group details', true);
            } else {
                var styleGroup = data.output;
                var styleGroupDialog;
                if(editMode === true) {
                    styleGroupDialog = $('#jqs-edit-item');
                    $(styleGroupDialog).find('input[name="styleGroupId"]').val(styleGroup.styleGroupId);
                    $(styleGroupDialog).find('input[name="name"]').val(styleGroup.styleGroupName);
                    $(styleGroupDialog).find('select[name="activeFlag"]').val(styleGroup.activeFlag === null ? 'N' : styleGroup.activeFlag);
                } else {
                    styleGroupDialog = $('#jqs-view-item');
                    $(styleGroupDialog).find('.jqs-style-group-name').html(styleGroup.styleGroupName);
                    $(styleGroupDialog).find('.jqs-style-group-id').html(styleGroup.styleGroupId);
                    $(styleGroupDialog).find('.jqs-style-group-active-flag').html(styleGroup.activeFlag === null ? 'N' : styleGroup.activeFlag);
                    $(styleGroupDialog).find('.jqs-style-group-created').html(styleGroup.createdStamp);
                    $(styleGroupDialog).find('.jqs-style-group-updated').html(styleGroup.lastUpdatedStamp);
                    $(styleGroupDialog).find('.jqs-pricing-attributes').data('groupid', styleGroup.styleGroupId);
                    $(styleGroupDialog).find('.jqs-edit').on('click', function() {
                        $(styleGroupDialog).modal('hide');
                        $.styleGroupDetailsRenderer(data, true);
                    });
                }

                $(styleGroupDialog).modal('show');
            }
        },
        styleGroupRowBuilder: function(entity, rawDataOnly) {
            if(rawDataOnly === true) {
                var rowValues = [];
                rowValues[0] = entity.id;
                rowValues[1] = entity.styleGroupName;
                rowValues[2] = typeof entity.activeFlag !== "undefined" || entity.activeFlag !== null ? entity.activeFlag : "N";
                return rowValues;
            } else {
                var entityRow = $(
                    '<tr id="' + entity.id + '">' +
                    '<td>' + (entity.id) + '</td>' +
                    '<td>' + (entity.styleGroupName) + '</td>' +
                    '<td align="center">' + (typeof entity.activeFlag !== "undefined" || entity.activeFlag !== null ? entity.activeFlag : "N") + '</td>' +
                    '</tr>');
                return entityRow;
            }
        },
        bindShowPricingAttributes: function() {
            $('.jqs-pricing-attributes').on('click', function() {
                window.location.href='/admin/control/qcStyleGroups?styleGroupId=' + $(this).data('groupid');
            });
        },
        bindAddOrRemovePricingAttribute: function() {
            $('#add-pricing-attribute-btn').on('click', function(){
                $.ajax({
                    type: 'GET',
                    url: '/admin/control/qcAvailableStyleGroupPricingAttributes',
                    data: {'styleGroupId' : $('input[name="styleGroupId"]').val()},
                    dataType: 'json',
                    cache: false,
                    async: false
                }).done(function(data){
                    if(data.success) {
                        $.updateAvailableAndAsignedPricingAttributes(data.output);
                    } else {
                        $.showMessage('An error occurred while getting StyleGroup Pricing Attributes', true);
                    }
                }).fail(function() {
                    $.showMessage('An error occurred while getting StyleGroup Pricing Attributes', true);
                });
                $('#jqs-add-pricing-attribute-popup').modal('show');
            });
        },
        updateAvailableAndAsignedPricingAttributes: function(data) {
            var availableAttributes = data.available;
            var assignedAttributes = data.assigned;
            var availableEl = $('select[name="available"]');
            var assignedEl = $('select[name="assigned"]');
            availableEl.find('option').remove();
            assignedEl.find('option').remove();
            for(var name in availableAttributes) {
                availableEl.prepend('<option value="' + name + '">' + availableAttributes[name] +'</option>');
            }
            for(var name in assignedAttributes) {
                assignedEl.prepend('<option value="' + name + '">' + assignedAttributes[name] +'</option>');
            }

        },
        bindAddPricingAttribute: function() {
            $('#addPricingAttribute').on('click', function() {
                var selectedOption = $('select[name="available"] option:selected');
                var attributeId = selectedOption.val();
                if(typeof attributeId !== 'undefined' && attributeId !== '') {
                    $.ajax({
                        type: 'GET',
                        url: '/admin/control/qcAddOrRemoveStyleGroupPricingAttributes',
                        data: {'styleGroupId' : $('input[name="styleGroupId"]').val(), 'attributeId' : attributeId, 'removeFlag' : 'false'},
                        dataType: 'json',
                        cache: false,
                        async: false
                    }).done(function(data){
                        if(data.success) {
                            $('select[name="available"] option:selected').remove();
                            $('select[name="assigned"]').prepend(selectedOption);
                            var entityRow = $(
                                '<tr id="' + attributeId + '">' +
                                '<td>' + (attributeId) + '</td>' +
                                '<td>' + (selectedOption.text()) + '</td>' +
                                '</tr>');
                            $('#jqs-style-group-pricing-attributes-panel #items-grid').prepend(entityRow);
                        } else {
                            $.showMessage('An error occurred while adding StyleGroup Pricing Attribute', true);
                        }
                    }).fail(function() {
                        $.showMessage('An error occurred while adding StyleGroup Pricing Attribute', true);
                    });
                } else {
                    $.showMessage('Please select one of the available Pricing Attribute to Add', true);
                }
            });
        },
        bindRemovePricingAttribute: function() {
            $('#removePricingAttribute').on('click', function() {
                var selectedOption = $('select[name="assigned"] option:selected');
                var attributeId = selectedOption.val();
                if(typeof attributeId !== 'undefined' && attributeId !== '') {
                    $.ajax({
                        type: 'GET',
                        url: '/admin/control/qcAddOrRemoveStyleGroupPricingAttributes',
                        data: {'styleGroupId' : $('input[name="styleGroupId"]').val(), 'attributeId' : attributeId, 'removeFlag' : 'true'},
                        dataType: 'json',
                        cache: false,
                        async: false
                    }).done(function(data){
                        if(data.success) {
                            $('select[name="assigned"] option:selected').remove();
                            $('select[name="available"]').prepend(selectedOption);
                            $('#jqs-style-group-pricing-attributes-panel #items-grid tr#' + styleGroupId).remove();
                        } else {
                            $.showMessage('An error occurred while removing StyleGroup Pricing Attribute', true);
                        }
                    }).fail(function() {
                        $.showMessage('An error occurred while removing StyleGroup Pricing Attribute', true);
                    });
                } else {
                    $.showMessage('Please select one of the available Pricing Attribute to Add', true);
                }
            });
        }
    });
})(jQuery);

$(function() {
    $.initEntity($.styleGroupHelper);
    $.bindShowPricingAttributes();
    $.bindAddOrRemovePricingAttribute();
    $.bindAddPricingAttribute();
    $.bindRemovePricingAttribute();
});