(function($) {
    $.extend({
        stockTypeHelper: function() {
            var helper = {};
            helper.entityName = 'StockType';
            helper.entityNamePlural = 'StockTypes';
            helper.endpoint = '/admin/control/qcStockType';
            helper.render = function(data, editMode) {
                $.stockTypeDetailsRenderer(data, editMode);
            };
            helper.build = function(entity, rawDataOnly) {
                return $.stockTypeRowBuilder(entity, rawDataOnly);
            };
            helper.valid = function(form) {
                return $.stockTypeValidator(form);
            };
            return helper;
        },
        stockTypeValidator: function(form) {
            var valid = true;
            var formName = $(form).attr('name');
            var stockTypeIdEl = $(form).find('input[name="stockTypeId"]');
            var stockTypeNameEl = $(form).find('input[name="name"]');
            var materialTypeIdEl = $(form).find('select[name="materialTypeId"]');
            if(formName === 'addForm' ) {
                if(stockTypeIdEl.val() === '') {
                    valid = false;
                    $.invalidate(stockTypeIdEl);
                } else if(/^[A-Za-z0-9_]+$/.test(stockTypeIdEl.val()) === false) {
                    valid = false;
                    $.invalidate(stockTypeIdEl, "Use letters, numbers and underscores only for Stock Type Id ");
                } else {
                    $.ajax({
                        type: 'POST',
                        url: 'qcIsValidId',
                        data: {'id' : stockTypeIdEl.val(), 'type' : 'StockType'},
                        dataType: 'json',
                        cache: false,
                        async: false
                    }).done(function (data) {
                        if(data.success !== true || data.valid !== true) {
                            valid = false;
                            $.invalidate(stockTypeIdEl, "The Stock Type Id already exists ");
                        }
                    });
                }
            }
            if(stockTypeNameEl.val() === '') {
                valid = false;
                $.invalidate(stockTypeNameEl);
            }
            if(materialTypeIdEl.val() === '') {
                valid = false;
                $.invalidate(materialTypeIdEl);
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
        stockTypeDetailsRenderer: function(data, editMode) {
            if(data.success === false) {
                $.showMessage('An error occurred while getting the StockType details', true);
            } else {
                var stockType = data.output;
                var stockTypeDialog;
                if(editMode === true) {
                    stockTypeDialog = $('#jqs-edit-item');
                    $(stockTypeDialog).find('input[name="stockTypeId"]').val(stockType.stockTypeId);
                    $(stockTypeDialog).find('input[name="name"]').val(stockType.stockTypeName);
                    $(stockTypeDialog).find('select[name="materialTypeId"]').val(stockType.materialTypeId);
                    $(stockTypeDialog).find('textarea[name="description"]').val(stockType.stockTypeDescription);
                    $(stockTypeDialog).find('select[name="activeFlag"]').val(stockType.activeFlag === null ? 'N' : stockType.activeFlag);

                } else {
                    stockTypeDialog = $('#jqs-view-item');
                    $(stockTypeDialog).find('.jqs-stock-type-name').html(stockType.stockTypeName);
                    $(stockTypeDialog).find('.jqs-stock-type-id').html(stockType.stockTypeId);
                    $(stockTypeDialog).find('.jqs-material-type').html(stockType.materialTypeId);
                    $(stockTypeDialog).find('.jqs-stock-type-description').html(stockType.stockTypeDescription);
                    $(stockTypeDialog).find('.jqs-stock-type-active-flag').html(stockType.activeFlag === null ? 'N' : stockType.activeFlag);
                    $(stockTypeDialog).find('.jqs-stock-type-created').html(stockType.createdStamp);
                    $(stockTypeDialog).find('.jqs-stock-type-updated').html(stockType.lastUpdatedStamp);
                    $(stockTypeDialog).find('.jqs-edit').on('click', function() {
                        $(stockTypeDialog).modal('hide');
                        $.stockTypeDetailsRenderer(data, true);
                    });
                }

                $(stockTypeDialog).modal('show');
            }
        },
        stockTypeRowBuilder: function(entity, rawDataOnly) {
            if(rawDataOnly === true) {
                var rowValues = [];
                rowValues[0] = entity.id;
                rowValues[1] = entity.stockTypeName;
                rowValues[2] = entity.materialTypeId;
                rowValues[3] = typeof entity.activeFlag !== "undefined" || entity.activeFlag !== null ? entity.activeFlag : "N";
                return rowValues;
            } else {
                var entityRow = $(
                    '<tr id="' + entity.id + '">' +
                    '<td><a href="javascript:void(0)">' + (entity.id) + '</a></td>' +
                    '<td>' + (entity.stockTypeName) + '</td>' +
                    '<td>' + (entity.materialTypeId) + '</td>' +
                    '<td style="text-align: center">' + (typeof entity.activeFlag !== "undefined" || entity.activeFlag !== null ? entity.activeFlag : "N") + '</td>' +
                    '</tr>');
                return entityRow;
            }
        }
    });
})(jQuery);

$(function() {
    $.initEntity($.stockTypeHelper);
});