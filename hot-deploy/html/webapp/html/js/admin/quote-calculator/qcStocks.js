(function($) {
    $.extend({
        stockHelper: function() {
            var helper = {};
            helper.entityName = 'Stock';
            helper.entityNamePlural = 'Stocks';
            helper.endpoint = '/admin/control/qcStock';
            helper.render = function(data, editMode) {
                $.stockDetailsRenderer(data, editMode);
            };
            helper.build = function(entity, rawDataOnly) {
                return $.stockRowBuilder(entity, rawDataOnly);
            };
            helper.valid = function(form) {
                return $.stockValidator(form);
            };
            return helper;
        },
        stockValidator: function(form) {
            var valid = true;
            var formName = $(form).attr('name');
            var stockIdEl = $(form).find('input[name="stockId"]');
            var stockNameEl = $(form).find('input[name="name"]');
            var stockTypeNameEl = $(form).find('select[name="stockTypeId"]');
            if(formName === 'addForm' ) {
                if(stockIdEl.val() === '') {
                    valid = false;
                    $.invalidate(stockIdEl);
                } else if(/^[A-Za-z0-9]+$/.test(stockIdEl.val()) === false) {
                    valid = false;
                    $.invalidate(stockIdEl, "Use letters and numbers only for Stock Id ");
                } else {
                    $.ajax({
                        type: 'POST',
                        url: 'qcIsValidId',
                        data: {'id' : stockIdEl.val(), 'type' : 'Stock'},
                        dataType: 'json',
                        cache: false,
                        async: false
                    }).done(function (data) {
                        if(data.success !== true || data.valid !== true) {
                            valid = false;
                            $.invalidate(stockIdEl, "The Stock Id already exists ");
                        }
                    });
                }
            }
            if(stockNameEl.val() === '') {
                valid = false;
                $.invalidate(stockNameEl);
            }
            if(stockTypeNameEl.val() === '') {
                valid = false;
                $.invalidate(stockTypeNameEl);
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
        stockDetailsRenderer: function(data, editMode) {
            if(data.success === false) {
                $.showMessage('An error occurred while getting the Stock details', true);
            } else {
                var stock = data.output;
                var stockDialog;
                if(editMode === true) {
                    stockDialog = $('#jqs-edit-item');
                    $(stockDialog).find('input[name="stockId"]').val(stock.stockId);
                    $(stockDialog).find('input[name="name"]').val(stock.stockName);
                    $(stockDialog).find('select[name="stockTypeId"]').val(stock.stockTypeId);
                    $(stockDialog).find('textarea[name="description"]').val(stock.stockDescription);
                    $(stockDialog).find('select[name="activeFlag"]').val(stock.activeFlag === null ? 'N' : stock.activeFlag);

                } else {
                    stockDialog = $('#jqs-view-item');
                    $(stockDialog).find('.jqs-stock-name').html(stock.stockName);
                    $(stockDialog).find('.jqs-stock-id').html(stock.stockId);
                    $(stockDialog).find('.jqs-stock-type').html(stock.stockTypeId);
                    $(stockDialog).find('.jqs-stock-description').html(stock.stockDescription);
                    $(stockDialog).find('.jqs-stock-active-flag').html(stock.activeFlag === null ? 'N' : stock.activeFlag);
                    $(stockDialog).find('.jqs-stock-created').html(stock.createdStamp);
                    $(stockDialog).find('.jqs-stock-updated').html(stock.lastUpdatedStamp);
                    $(stockDialog).find('.jqs-edit').on('click', function() {
                        $(stockDialog).modal('hide');
                        $.stockDetailsRenderer(data, true);
                    });
                }

                $(stockDialog).modal('show');
            }
        },
        stockRowBuilder: function(entity, rawDataOnly) {
            if(rawDataOnly === true) {
                var rowValues = [];
                rowValues[0] = entity.id;
                rowValues[1] = entity.stockName;
                rowValues[2] = entity.stockTypeId;
                rowValues[3] = typeof entity.activeFlag !== "undefined" || entity.activeFlag !== null ? entity.activeFlag : "N";
                return rowValues;
            } else {
                var entityRow = $(
                    '<tr id="' + entity.id + '">' +
                    '<td><a href="javascript:void(0)">' + (entity.id) + '</a></td>' +
                    '<td>' + (entity.stockName) + '</td>' +
                    '<td>' + (entity.stockTypeId) + '</td>' +
                    '<td style="text-align: center">' + (typeof entity.activeFlag !== "undefined" || entity.activeFlag !== null ? entity.activeFlag : "N") + '</td>' +
                    '</tr>');
                return entityRow;
            }
        }
    });
})(jQuery);

$(function() {
    $.initEntity($.stockHelper);
});