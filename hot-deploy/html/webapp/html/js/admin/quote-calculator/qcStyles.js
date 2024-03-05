(function($) {
    $.extend({
        styleHelper: function() {
            var helper = {};
            helper.entityName = 'Style';
            helper.entityNamePlural = 'Styles';
            helper.endpoint = '/admin/control/qcStyle';
            helper.render = function(data, editMode) {
                $.styleDetailsRenderer(data, editMode);
            };
            helper.build = function(entity, rawDataOnly) {
                return $.styleRowBuilder(entity, rawDataOnly);
            };
            helper.valid = function(form) {
                return $.styleValidator(form);
            };
            return helper;
        },
        styleValidator: function(form) {
            var valid = true;
            var formName = $(form).attr('name');
            var styleIdEl = $(form).find('input[name="styleId"]');
            var styleNameEl = $(form).find('input[name="name"]');
            var styleGroupIdEl = $(form).find('select[name="styleGroupId"]');
            if(formName === 'addForm' ) {
                if(styleIdEl.val() === '') {
                    valid = false;
                    $.invalidate(styleIdEl);
                } else if(/^[A-Za-z0-9-]+$/.test(styleIdEl.val()) === false) {
                    valid = false;
                    $.invalidate(styleIdEl, "Use letters, numbers and hyphens only for Style Id ");
                } else {
                    $.ajax({
                        type: 'POST',
                        url: 'qcIsValidId',
                        data: {'id' : styleIdEl.val(), 'type' : 'Style'},
                        dataType: 'json',
                        cache: false,
                        async: false
                    }).done(function (data) {
                        if(data.success !== true || data.valid !== true) {
                            valid = false;
                            $.invalidate(styleIdEl, "The Style Id already exists ");
                        }
                    });
                }
            }
            if(styleNameEl.val() === '') {
                valid = false;
                $.invalidate(styleNameEl);
            }
            if(styleGroupIdEl.val() === '') {
                valid = false;
                $.invalidate(styleGroupIdEl);
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
        styleDetailsRenderer: function(data, editMode) {
            if(data.success === false) {
                $.showMessage('An error occurred while getting the Style details', true);
            } else {
                var style = data.output;
                var styleDialog;
                if(editMode === true) {
                    styleDialog = $('#jqs-edit-item');
                    $(styleDialog).find('input[name="styleId"]').val(style.styleId);
                    $(styleDialog).find('input[name="name"]').val(style.styleName);
                    $(styleDialog).find('input[name="shortName"]').val(style.styleShortName);
                    $(styleDialog).find('select[name="styleGroupId"]').val(style.styleGroupId);
                    if(style.hasUpcharge === true) {
                        $(styleDialog).find('select[name="styleGroupId"]').attr('disabled', 'disabled');
                    } else {
                        $(styleDialog).find('select[name="styleGroupId"]').removeAttr('disabled');
                    }
                    $(styleDialog).find('textarea[name="description"]').val(style.styleDescription);
                    $(styleDialog).find('select[name="activeFlag"]').val(style.activeFlag === null ? 'N' : style.activeFlag);

                } else {
                    styleDialog = $('#jqs-view-item');
                    $(styleDialog).find('.jqs-style-name').html(style.styleName);
                    $(styleDialog).find('.jqs-style-short-name').html(style.styleShortName);
                    $(styleDialog).find('.jqs-style-id').html(style.styleId);
                    $(styleDialog).find('.jqs-style-group').html(style.styleGroupId);
                    $(styleDialog).find('.jqs-style-description').html(style.styleDescription);
                    $(styleDialog).find('.jqs-style-active-flag').html(style.activeFlag === null ? 'N' : style.activeFlag);
                    $(styleDialog).find('.jqs-style-created').html(style.createdStamp);
                    $(styleDialog).find('.jqs-style-updated').html(style.lastUpdatedStamp);
                    $(styleDialog).find('.jqs-edit').on('click', function() {
                        $(styleDialog).modal('hide');
                        $.styleDetailsRenderer(data, true);
                    });
                }

                $(styleDialog).modal('show');
            }
        },
        styleRowBuilder: function(entity, rawDataOnly) {
            if(rawDataOnly === true) {
                var rowValues = [];
                rowValues[0] = entity.id;
                rowValues[1] = entity.styleName;
                rowValues[2] = entity.styleGroupId;
                rowValues[3] = typeof entity.activeFlag !== "undefined" || entity.activeFlag !== null ? entity.activeFlag : "N";
                return rowValues;
            } else {
                var entityRow = $(
                    '<tr id="' + entity.id + '">' +
                    '<td><a href="javascript:void(0)">' + (entity.id) + '</a></td>' +
                    '<td>' + (entity.styleName) + '</td>' +
                    '<td>' + (entity.styleGroupId) + '</td>' +
                    '<td style="text-align: center">' + (typeof entity.activeFlag !== "undefined" || entity.activeFlag !== null ? entity.activeFlag : "N") + '</td>' +
                    '</tr>');
                return entityRow;
            }
        }
    });
})(jQuery);

$(function() {
    $.initEntity($.styleHelper);
});