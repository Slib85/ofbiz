
(function($) {
    var itemTable;
    var _entityHelper;
    $.extend({
        initEntity : function(entityHelper) {
            $.pluginDialogContent();
            $.bindShowDetails(entityHelper);
            $.bindSaveItem(entityHelper);
            _entityHelper = entityHelper;
        },
        closeModal: function(buttonEl) {
            $(buttonEl).closest('.modal').modal('hide');
        },
        closeAllModal: function() {
            $('.modal').each(function(){
                $(this).modal('hide');
            });
        },
        showMessage: function (message, isError, hideAll) {
            $.closeAllModal();
            var dialog;
            if(isError === true) {
                dialog = $('#messageDialog').removeClass('modal-success').addClass('modal-danger');
            } else {
                dialog = $('#messageDialog').removeClass('modal-danger').addClass('modal-success');
            }
            $(dialog).find('.jqs-dialog-body').html(message);
            $(dialog).modal('show');
        },
        pluginDialogContent: function() {
            $('.jqs-dialogs-body .jqs-add-dialog').removeClass('jqs-add-dialog').appendTo($('#jqs-add-item .modal-dialog'));
            $('.jqs-dialogs-body .jqs-edit-dialog').removeClass('jqs-add-dialog').appendTo($('#jqs-edit-item .modal-dialog'));
            $('.jqs-dialogs-body .jqs-view-dialog').removeClass('jqs-add-dialog').appendTo($('#jqs-view-item .modal-dialog'));
        },
        bindShowDetails: function(entityHelper) {
            $('#jqs-items-panel').find('table#items-grid tbody > tr').each(function(){
                var itemId = $(this).attr('id');
                $(this).find('a').off().on('click', function(){
                    $.showItemDetails(itemId, entityHelper);
                });
            });
        },
        bindSaveItem: function(entityHelper) {
            $('.jqs-save-item').each(function() {
                $(this).on('click', function() {
                    $.saveItem($(this), $(this).hasClass('jqs-edit'), entityHelper);
                });
            });
        },
        getItemData: function(endpoint, itemId, entityHelper, editMode) {
            $.ajax({
                type: 'GET',
                url: entityHelper().endpoint,
                data: {'id' : itemId},
                dataType: 'json',
                cache: false
            }).done(function(data){
                $.renderItemDetails(data, editMode, entityHelper);
            }).fail(function() {
                $.renderItemDetails({'success' : false}, editMode, entityHelper);
            });
        },
        showItemDetails: function(itemId, entityHelper) {
            $.getItemData('', itemId, entityHelper, false);
        },
        renderItemDetails: function(data, editMode, entityHelper) {
            entityHelper().render(data, editMode);
        },
        updateItemsGrid: function(buttonEl, data, updateMode, entityHelper) {
            if(data.success === true) {
                var entity = data.output;

                var entityRow = entityHelper().build(entity, false);
                var entityData = entityHelper().build(entity, true);
                var rowId = entityData[0];
                var idColumn = '<a href="javascript:void(0)">' + rowId + '</a>';
                entityData[0] = idColumn;

                if(itemTable.row('#' + rowId).length === 0) {
                    itemTable.row.add(entityData).draw();

                } else {
                    itemTable.row('#' + rowId).data(entityData).draw(false);
                }

                $('#' + rowId).on('click', function() {
                    $.showItemDetails(rowId, entityHelper);
                });

                $.closeModal(buttonEl);
                $.showMessage($.getItemSaveMessage(updateMode, false, entityHelper), false);

            } else {
                $.showMessage($.getItemSaveMessage(updateMode, true, entityHelper), true);
            }
        },
        getItemSaveMessage: function(updateMode, isError, entityHelper) {
            var message;
            if(isError === true) {
                message = 'An error occurred while ' + (updateMode === true ? 'updating' : 'creating') + ' the ' + entityHelper().entityName;
            } else {
                message = entityHelper().entityName + (updateMode === true ? ' updated' : ' created') + ' successfully';
            }
            return message;
        },
        saveItem: function(buttonEl, updateMode, entityHelper) {
            $.submitForm(buttonEl, entityHelper, function(data) {
                $.updateItemsGrid(buttonEl, data, updateMode, entityHelper);
            });
        },
        submitForm: function(buttonEl, entityHelper, callback) {
            var formEl = $(buttonEl).closest('form');
            if(entityHelper().valid(formEl) === true) {
                $.ajax({
                    type: 'POST',
                    url: $(formEl).attr("action"),
                    data: $(formEl).serialize(),
                    dataType: 'json',
                    cache: false
                }).done(function (data) {
                    callback(data);
                }).fail(function () {
                    callback({'success': false});
                });
            }
        },

        initDataTable: function() {
            var offsetTop = 0;
            itemTable = $('#items-grid').DataTable({
                iDisplayLength: 25,
                responsive: true,
                fixedHeader: {
                    header: true,
                    headerOffset: offsetTop
                },
                bPaginate: true,
                aaSorting: [],
                initComplete: function() {
                    $('#items-grid .replace-inputs > th').each(function(index) {
                        var title = $(this).text();
                        $(this).html('<input class="form-control w-full" type="text" placeholder="' + title + '" />');
                    });

                    $("#items-grid .replace-inputs input").on('keyup change', function () {
                        itemTable
                            .column($(this).parent().index() + ':visible' )
                            .search(this.value)
                            .draw();
                    });
                }
            });
            $('#items-grid').find('tbody').show();
            itemTable.on('draw.dt', function(){$.bindShowDetails(_entityHelper);});
        }
    });

})(jQuery);

$(function() {
    $.initDataTable();
});


