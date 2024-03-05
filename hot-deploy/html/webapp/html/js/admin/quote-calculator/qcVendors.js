(function($) {
    $.extend({
        vendorHelper: function() {
            var helper = {};
            helper.entityName = 'Vendor';
            helper.entityNamePlural = 'Vendors';
            helper.endpoint = '/admin/control/qcVendor';
            helper.render = function(data, editMode) {
                $.vendorDetailsRenderer(data, editMode);
            };
            helper.build = function(entity) {
                return $.vendorRowBuilder(entity);
            };
            return helper;
        },
        vendorDetailsRenderer: function(data, editMode) {
            if(data.success === false) {
                $.showMessage('An error occurred while getting the Vendor details', true);
            } else {
                var vendor = data.output;
                var vendorDialog;
                if(editMode === true) {
                    vendorDialog = $('#jqs-edit-item');
                    $(vendorDialog).find('input[name="vendorId"]').val(vendor.vendorId);
                    $(vendorDialog).find('input[name="name"]').val(vendor.vendorName);
                    $(vendorDialog).find('select[name="activeFlag"]').val(vendor.activeFlag === null ? 'N' : vendor.activeFlag);
                } else {
                    vendorDialog = $('#jqs-view-item');
                    $(vendorDialog).find('.jqs-vendor-name').html(vendor.vendorName);
                    $(vendorDialog).find('.jqs-vendor-id').html(vendor.vendorId);
                    $(vendorDialog).find('.jqs-style-group-active-flag').html(vendor.activeFlag === null ? 'N' : vendor.activeFlag);
                    $(vendorDialog).find('.jqs-vendor-created').html(vendor.createdStamp);
                    $(vendorDialog).find('.jqs-vendor-updated').html(vendor.lastUpdatedStamp);
                    $(vendorDialog).find('.jqs-edit').on('click', function() {
                        $(vendorDialog).modal('hide');
                        $.vendorDetailsRenderer(data, true);
                    });
                }

                $(vendorDialog).modal('show');
            }
        },
        vendorRowBuilder: function(entity) {
            var entityRow = $(
                '<tr id="' + entity.id + '">' +
                '<td>' + (entity.id) + '</td>' +
                '<td>' + (entity.vendorName) + '</td>' +
                '<td align="center">' + (typeof entity.activeFlag !== "undefined" || entity.activeFlag !== null ? entity.activeFlag : "N") + '</td>' +
                '</tr>');
            return entityRow;
        },
        bindShowVendorPricing: function() {
            $('.jqs-vendor-pricing').on('click', function() {
                window.location.href='/admin/control/qcVendors?vendorId=ADMORE';
            });
        },
        bindAddStyleGroup: function() {
            $('#add-style-group-btn').on('click', function(){
                $.ajax({
                    type: 'GET',
                    url: '/admin/control/qcAvailableVendorStyleGroups',
                    data: {'vendorId' : $('input[name="vendorId"]').val()},
                    dataType: 'json',
                    cache: false,
                    async: false
                }).done(function(data){
                    if(data.success) {
                        $.updateAvailableAndAsignedStyleGroups(data.output);
                    } else {
                        $.showMessage('An error occurred while getting Vendor StyleGroups', true);
                    }
                }).fail(function() {
                    $.showMessage('An error occurred while getting Vendor StyleGroups', true);
                });
                $('#jqs-add-style-group-popup').modal('show');
            });
        },
        updateAvailableAndAsignedStyleGroups: function(data) {
            var availableGroups = data.available;
            var assignedGroups = data.assigned;
            var availableEl = $('select[name="available"]');
            var assignedEl = $('select[name="assigned"]');
            availableEl.find('option').remove();
            assignedEl.find('option').remove();
            for(var name in availableGroups) {
                availableEl.prepend('<option value="' + name + '">' + availableGroups[name] +'</option>');
            }
            for(var name in assignedGroups) {
                assignedEl.prepend('<option value="' + name + '">' + assignedGroups[name] +'</option>');
            }

        },
        bindAddVendorStyleGroup: function() {
            $('#addVendorStyleGroup').on('click', function() {
                var selectedOption = $('select[name="available"] option:selected');
                var styleGroupId = selectedOption.val();
                if(typeof styleGroupId !== 'undefined' && styleGroupId !== '') {
                    $.ajax({
                        type: 'GET',
                        url: '/admin/control/qcAddOrRemoveVendorStyleGroups',
                        data: {'vendorId' : $('input[name="vendorId"]').val(), 'styleGroupId' : styleGroupId, 'removeFlag' : 'false'},
                        dataType: 'json',
                        cache: false,
                        async: false
                    }).done(function(data){
                        if(data.success) {
                            $('select[name="available"] option:selected').remove();
                            $('select[name="assigned"]').prepend(selectedOption);
                            var entityRow = $(
                                '<tr id="' + styleGroupId + '">' +
                                '<td>' + (styleGroupId) + '</td>' +
                                '<td>' + (selectedOption.text()) + '</td>' +
                                '<td align="center">Y</td>' +
                                '</tr>');
                            $('#jqs-vendor-style-groups-panel #items-grid').prepend(entityRow);
                        } else {
                            $.showMessage('An error occurred while adding Vendor StyleGroup', true);
                        }
                    }).fail(function() {
                        $.showMessage('An error occurred while adding Vendor StyleGroup', true);
                    });
                } else {
                    $.showMessage('Please select one of the available StyleGroup to Add', true);
                }
            });
        },
        bindRemoveVendorStyleGroup: function() {
            $('#removeVendorStyleGroup').on('click', function() {
                var selectedOption = $('select[name="assigned"] option:selected');
                var styleGroupId = selectedOption.val();
                if(typeof styleGroupId !== 'undefined' && styleGroupId !== '') {
                    $.ajax({
                        type: 'GET',
                        url: '/admin/control/qcAddOrRemoveVendorStyleGroups',
                        data: {'vendorId' : $('input[name="vendorId"]').val(), 'styleGroupId' : styleGroupId, 'removeFlag' : 'true'},
                        dataType: 'json',
                        cache: false,
                        async: false
                    }).done(function(data){
                        if(data.success) {
                            $('select[name="assigned"] option:selected').remove();
                            $('select[name="available"]').prepend(selectedOption);
                            $('#jqs-vendor-style-groups-panel #items-grid tr#' + styleGroupId).remove();
                        } else {
                            $.showMessage('An error occurred while removing Vendor StyleGroup', true);
                        }
                    }).fail(function() {
                        $.showMessage('An error occurred while removing Vendor StyleGroup', true);
                    });
                } else {
                    $.showMessage('Please select one of the available StyleGroup to Add', true);
                }
            });
        }
    });
})(jQuery);

$(function() {
    $.initEntity($.vendorHelper);
    $.bindAddStyleGroup();
    $.bindAddVendorStyleGroup();
    $.bindRemoveVendorStyleGroup();
    $.bindShowVendorPricing();
});