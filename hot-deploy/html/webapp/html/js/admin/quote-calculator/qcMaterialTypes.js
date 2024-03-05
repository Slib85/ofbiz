(function($) {
    $.extend({
        materialTypeHelper: function() {
            var helper = {};
            helper.entityName = 'MaterialType';
            helper.entityNamePlural = 'MaterialTypes';
            helper.endpoint = '/admin/control/qcMaterialType';
            helper.render = function(data, editMode) {
                $.materialTypeDetailsRenderer(data, editMode);
            };
            helper.build = function(entity, rawDataOnly) {
                return $.materialTypeRowBuilder(entity, rawDataOnly);
            };
            helper.valid = function(form) {
                return $.materialTypeValidator(form);
            };
            return helper;
        },
        materialTypeValidator: function(form) {
            var valid = true;
            var formName = $(form).attr('name');
            var materialTypeIdEl = $(form).find('input[name="materialTypeId"]');
            var materialTypeNameEl = $(form).find('input[name="name"]');
            if(formName === 'addForm' ) {
                if(materialTypeIdEl.val() === '') {
                    valid = false;
                    $.invalidate(materialTypeIdEl);
                } else if(/^[A-Za-z0-9_]+$/.test(materialTypeIdEl.val()) === false) {
                    valid = false;
                    $.invalidate(materialTypeIdEl, "Use letters, numbers and underscores only for Material Type Id ");
                } else {
                    $.ajax({
                        type: 'POST',
                        url: 'qcIsValidId',
                        data: {'id' : materialTypeIdEl.val(), 'type' : 'MaterialType'},
                        dataType: 'json',
                        cache: false,
                        async: false
                    }).done(function (data) {
                        if(data.success !== true || data.valid !== true) {
                            valid = false;
                            $.invalidate(materialTypeIdEl, "The Material Type Id already exists ");
                        }
                    });
                }
            }
            if(materialTypeNameEl.val() === '') {
                valid = false;
                $.invalidate(materialTypeNameEl);
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
        materialTypeDetailsRenderer: function(data, editMode) {
            if(data.success === false) {
                $.showMessage('An error occurred while getting the MaterialType details', true);
            } else {
                var materialType = data.output;
                var materialTypeDialog;
                if(editMode === true) {
                    materialTypeDialog = $('#jqs-edit-item');
                    $(materialTypeDialog).find('input[name="materialTypeId"]').val(materialType.materialTypeId);
                    $(materialTypeDialog).find('input[name="name"]').val(materialType.materialTypeName);
                    $(materialTypeDialog).find('textarea[name="description"]').val(materialType.materialTypeDescription);
                    $(materialTypeDialog).find('select[name="activeFlag"]').val(materialType.activeFlag === null ? 'N' : materialType.activeFlag);

                } else {
                    materialTypeDialog = $('#jqs-view-item');
                    $(materialTypeDialog).find('.jqs-material-type-name').html(materialType.materialTypeName);
                    $(materialTypeDialog).find('.jqs-material-type-id').html(materialType.materialTypeId);
                    $(materialTypeDialog).find('.jqs-material-type-description').html(materialType.materialTypeDescription);
                    $(materialTypeDialog).find('.jqs-material-type-active-flag').html(materialType.activeFlag === null ? 'N' : materialType.activeFlag);
                    $(materialTypeDialog).find('.jqs-material-type-created').html(materialType.createdStamp);
                    $(materialTypeDialog).find('.jqs-material-type-updated').html(materialType.lastUpdatedStamp);
                    $(materialTypeDialog).find('.jqs-edit').on('click', function() {
                        $(materialTypeDialog).modal('hide');
                        $.materialTypeDetailsRenderer(data, true);
                    });
                }

                $(materialTypeDialog).modal('show');
            }
        },
        materialTypeRowBuilder: function(entity, rawDataOnly) {
            if(rawDataOnly === true) {
                var rowValues = [];
                rowValues[0] = entity.id;
                rowValues[1] = entity.materialTypeName;
                rowValues[2] = typeof entity.activeFlag !== "undefined" || entity.activeFlag !== null ? entity.activeFlag : "N";
                return rowValues;
            } else {
                var entityRow = $(
                    '<tr id="' + entity.id + '">' +
                    '<td><a href="javascript:void(0)">' + (entity.id) + '</a></td>' +
                    '<td>' + (entity.materialTypeName) + '</td>' +
                    '<td style="text-align: center">' + (typeof entity.activeFlag !== "undefined" || entity.activeFlag !== null ? entity.activeFlag : "N") + '</td>' +
                    '</tr>');
                return entityRow;
            }
        }
    });
})(jQuery);

$(function() {
    $.initEntity($.materialTypeHelper);
});