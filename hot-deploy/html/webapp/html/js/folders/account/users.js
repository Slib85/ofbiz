(function($){
    $.extend({
        addUser: function() {
            $.ajax({
                type: 'POST',
                url: '/' + websiteId + '/control/addUser',
                data: $('form[name=add-user]').serialize(),
                dataType: 'json',
                cache: false
            }).done(function(data) {
                if(data.success) {
                    var userRow = '<tr id="' + data.userLoginId + '"><td>' + data.userLoginId + '</td><td><a href="#" data-reveal-id="edit-user" onclick="$(\'#edit-user input[name=email]\').val(\'' + data.userLoginId + '\');">Reset Password</a></td><td><input type="checkbox" checked name="active" data-email="' + data.userLoginId + '" onclick="$.toggleActive(this)" /></td></tr>';
                    $('#userList').append(userRow);
                    $.showMessage(data.message, 'success');
                } else {
                    $.showMessage(data.errorMessage, 'error');
                }
                $.closeUserLayer('#add-user');
            });
        },
        updateUser: function(data) {
            $.ajax({
                type: 'POST',
                url: '/' + websiteId + '/control/updateUser',
                data: data !== undefined ? data : $('form[name=edit-user]').serialize(),
                dataType: 'json',
                cache: false
            }).done(function(data) {
                if(data.success) {
                    var checked = $('[id="' + data.userLoginId + '"]').find('input[type=checkbox]').prop('checked') == true;
                    var userRow = '<tr id="' + data.userLoginId + '"><td>' + data.userLoginId + '</td><td><a href="#" data-reveal-id="edit-user" onclick="$(\'#edit-user input[name=email]\').val(\'' + data.userLoginId + '\');">Reset Password</a></td><td><input type="checkbox" ' + (checked == true ? 'checked ' : '') + ' name="active" data-email="' + data.userLoginId + '" onclick="$.toggleActive(this)"/></td></tr>';
                    $('[id="' + data.userLoginId + '"]').replaceWith($(userRow));
                    $.showMessage(data.message, 'success');
                } else {
                    $.showMessage(data.errorMessage, 'error');
                }
                $.closeUserLayer('#edit-user');
            });
        },
        toggleActive: function(checkboxElement) {
            var data = '';
            data += 'email=' + $(checkboxElement).data('email');
            data += '&active=' + $(checkboxElement).prop('checked');
            data += '&updateFlag=true';
            $.updateUser(data);
        },
        showMessage: function(message, type) {
            $.hideMessage();
            $(type == 'success' ? '#user-message' : '#user-error').removeClass('hidden').find('span').html(message);
        },
        hideMessage: function() {
            $('.alert-box').addClass('hidden');
        },
        closeUserLayer: function(layerElement) {
            $('.bnRevealShadowedBackground').trigger('click');
            // $(layerElement).foundation('reveal', 'close');
            $.resetUserLayer(layerElement);
        },
        resetUserLayer: function(layerElement) {
            $(layerElement).find('.jqs-clearable').val('');
        },
        bindCustomAbideValidationEvent: function() {
            $('.jqs-abide').each(function() {
                var validationMessageElement = $(this).next();
                var defaultValidationMessage = validationMessageElement.html();
                var invalidMessage = 'Valid ' + defaultValidationMessage;
                $(this).on('blur', function(e) {
                    if($(this).val() != '' && $(this).data('abide-validator') === undefined) {
                        validationMessageElement.html(invalidMessage);
                    } else if($(this).val() == '') {
                        validationMessageElement.html(defaultValidationMessage);
                    }
                }).on('focus', function(e) {
                    $(this).parent().removeClass('error');
                });

            });
        }

    });
})(jQuery);

function addUserSubmit() {
	$.addUser();
}

function resetPasswordSubmit() {
	$.updateUser();
}

$(document).ready(function(){
    $('#userList').dataTable( {
		"aoColumnDefs": [
			{ 'bSortable': false, 'aTargets': [ 1, 2 ] }
		],
		"fnDrawCallback": function() {
			var oTable = $("#userList").dataTable();

			if (oTable.fnGetData().length <= 10) {
				$('#userList_length')[0].style.display = "none";
			}
			else {
				$('#userList_length')[0].style.display = "block";
			}

			if ($('#userList_paginate span span.paginate_button').size()) {
				$('#userList_paginate')[0].style.display = "block";
			}
			else {
				$('#userList_paginate')[0].style.display = "none";
			}
		}
	});
    $.bindCustomAbideValidationEvent();
});
