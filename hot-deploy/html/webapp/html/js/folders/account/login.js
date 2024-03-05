/**
 * Created by Manu on 10/3/2014.
 */
(function($) {
    $.extend({
        bindLoginClickEvent: function() {
            var secureIframe = $('#secure-layer iframe');
            $('.jqs-login-button,.jqs-hidden-login-button').off('click.loginLayer').on('click.loginLayer', function(e, callback) {
                // $('#jqs-login-layer').removeClass('invalid');
                $('#secure-layer').removeClass('invalid');
                var iframeSrc = secureIframe.attr('src');
                $(secureIframe).attr('src', '/' + websiteId + '/control/secureWrapper?dest=' + $(this).data('dest'));
                $.removeCookie('__ES_ll', {path: '/'});
                $.updateSessionStorageLogin(null);
                $.startLoginListener(callback);
            });
        },

        showResetPasswordLayer: function() {
            $('#jqs-reset-password-layer').removeClass('invalid');
            var secureIframe = $('#secure-layer iframe');
            var iframeSrc = secureIframe.attr('src');
            $(secureIframe).attr('src', '/' + websiteId + '/control/secureWrapper?dest=reset-password-layer');
            $('#secure-layer').foundation('reveal', 'open');
        },

        bindLogoutClickEvent: function() {
            $('.jqs-logout-button').off().on('click', function() {
                $.removeCookie('__ES_ll', {path: '/'});
                $.removeCookie('__SC_Data', {path: '/'});
                $.updateSessionStorageLogin(null);
                window.location.href = '/' + websiteId + '/control/logout';
            });
        },

        startLoginListener: function(callback) {
            if($('#secure-layer').parent().hasClass('footer')) {
                setTimeout(function() {$.startLoginListener(callback);}, 500);
            } else {
                $.loginListener(callback);
            }
        },
        loginListener: function(callback) {
            if(!$('#secure-layer').parent().hasClass('footer')) {
                $.checkStatus(callback);
            }
        },

        checkAuthenticated: function() {
            $.ajax({
                type: 'GET',
                url: '/' + websiteId + '/control/isUserLoggedIn',
                dataType: 'json',
                cache: false
            }).done(function(data) {
                if (data.success) {
                    dataLayer.push({
                        'userId': data.userLogin,
                        'userPartyId': data.partyId,
                        'event': 'authentication'
                    });
                }
            });
        },
        checkStatus: function(callback) {
            var llCookie = $.cookie('__ES_ll');
            var success = llCookie && llCookie == 'true';
            var closeLayer = llCookie && llCookie == 'close';
            if(success) {
                $.ajax({
                    type: 'GET',
                    url: '/' + websiteId + '/control/isUserLoggedIn',
                    dataType: 'json',
                    cache: false
                }).done(function(data) {
                    if (data.success) {
                        $.updateHeader(true, data.firstName, data.lastName);

                        if(localStorageEnabled) {
                            localStorage.partyId = data.partyId;
                        }

                        dataLayer.push({
                            'userId': data.userLogin,
                            'userPartyId': data.partyId,
                            'event': 'authentication'
                        });
                        ga('set', '&uid', data.userLogin); // Set the user ID using signed-in user_id.
                        GoogleAnalytics.trackEvent('Log In', 'Logged In', 'Success');

                        var original_login_height = $('.login-layer').css('height');

                        $('.login-layer').css('height', '131px');
                        $('.login-layer > .env-login').removeClass('hidden').addClass('hidden');
                        $('.login_success').removeClass('hidden');

                        setTimeout(function() {
                            $.closeLoginLayer();
                            if(typeof callback !== 'undefined') {
                                callback({'partyId': data.partyId, 'userId': data.userLogin});
                            }

                            setTimeout(function() {
                                $('.login-layer').css('height', original_login_height);
                                $('.login_success').removeClass('hidden').addClass('hidden');
                                $('.login-layer > .env-login').removeClass('hidden');
                            }, 2000);
                        }, 3000);

                        //window.location.reload(false);
                    }
                });
            } else {
                if(closeLayer) {
                    $.closeLoginLayer();
                } else {
                    if(llCookie !== undefined) {
                        // $('#jqs-login-layer').addClass('invalid');
                        $('#secure-layer').addClass('invalid');
                    }
                    setTimeout(function() {$.loginListener(callback)}, 10);
                }
            }
        },
        closeLoginLayer: function() {
            $('#secure-layer').foundation('reveal', 'close');
            $('.bnRevealShadowedBackground').trigger('click');
        },
        updateHeader: function(loginFlag, firstName, lastName) {
            if(loginFlag) {
                $('#jqs-login-container .logged-in-text').html('').append($('<span/>').text('').text('Welcome ' + firstName + '! ')).append('<span class="tablet-desktop-only-inline-block">Not ' + firstName + '?</span>');
                $('.jqs-login-button').removeClass('jqs-login-button').addClass('jqs-logout-button').attr('data-dest', 'logout').removeAttr('data-reveal-id').text('Log Out');
                $.bindLogoutClickEvent();
                $.cookie('__ES_ll', firstName, { path: '/'});
                $.updateSessionStorageLogin(firstName);
            } else {
                $('#jqs-login-container .logged-in-text').append($('<span/>').text(''));
                $('.jqs-logout-button').removeClass('jqs-logout-button').addClass('jqs-login-button').attr('data-reveal-id', 'secure-layer').attr('data-dest', 'login').text('Log In');
                $.bindLoginClickEvent();
                $.updateSessionStorageLogin(null);
            }
        },
        updateSessionStorageLogin: function(firstName) {
            if(typeof(Storage) !== 'undefined') {
                if(firstName != null) {
                    localStorage.setItem('firstName', firstName);
                } else {
                    localStorage.removeItem('firstName');
                    localStorage.removeItem('reOrderHistory');
                }
            }
        },
        validateAndOpen: function(destinationURL) {
            $.ajax({
                type: 'GET',
                url: '/' + websiteId + '/control/isUserLoggedIn',
                dataType: 'json',
                cache: false
            }).done(function(data) {
                if (data.success) {
                    if(typeof(destinationURL) == "function") {
                        destinationURL();
                    } else {
                        window.location = destinationURL;
                    }
                } else {
                    $.promptLogin();
                }
            });
        },

        promptLogin: function() {
            $('.jqs-hidden-login-button').trigger('click');

        }
    });
})(jQuery);

$(function() {
    $.bindLoginClickEvent();
    $.bindLogoutClickEvent();
});

function loginCallback(callback) {
    $('.jqs-hidden-login-button').trigger('click', callback);
}