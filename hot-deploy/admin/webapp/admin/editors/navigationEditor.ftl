<link href="<@ofbizContentUrl>/html/css/top.css?ts=24</@ofbizContentUrl>" rel="stylesheet" />
<link href="<@ofbizContentUrl>/html/css/global.css</@ofbizContentUrl>?ts=24" rel="stylesheet" />
<link href="/html/css/addons/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet" />

<style>
    .editMenuPopup {
        background-color: rgb(255, 255, 255);
        position: absolute;
        left: -99999px;
        padding: 10px;
        border: 1px solid #e3e3e3;
        color: #000000;
        font-size: 14px;
        z-index: 9999999;
    }
        .editMenuPopup h3 {
            font-size: 18px;
            line-height: 18px;
            font-weight: bold;
            text-decoration: underline;
        }
        .editMenuPopup .closeMenu {
            position: absolute;
            top: 10px;
            right: 10px;
            cursor: pointer;
        }
        .editMenuPopup div {
            margin-top: 10px;
        }
        .editMenuPopup .updateContent,
        .undoButton {
            border: 1px solid #e3e3e3;
            background-color: #00bb00;
            width: 100%;
            padding: 10px;
            font-size: 16px;
            font-weight: bold;
            color: #ffffff;
            text-align: center;
            cursor: pointer;
        }
</style>

<style>
    .megaMenuFirstColumns h2 {
        margin-top: 10px;
    }
    .megaMenuFirstColumns > h2 {
        margin-top: 0px !important;
    }
    .megaMenuContainer {
        display: inline-block;
    }
    .megaMenuContainer > * {
        display: inline-block;
        padding: 5px 40px;
        font-weight: bold;
        cursor: pointer;
        border: 2px solid transparent;
        color: #000000;
        font-size: 18px;
    }
    .megaMenuDropdownContent {
        padding: 10px;
        color: #313131;
    }
    .megaMenuDropdownContent a:hover {
        text-decoration: underline;
    }
    .megaMenuDropdownContent .megaMenuFirstColumns {
        padding: 0px 5px !important;
    }
    .megaMenuDropdownContent .megaMenuFirstColumns:first-child {
        padding: 0px 5px 0px 0px !important;
    }
    .megaMenuDropdownContent .megaMenuFirstColumns:last-child {
        padding: 0px 0px 0px 5px !important;
    }
    .megaMenuDropdownContent h2 {
        border-bottom: 2px solid #313131;
        padding-bottom: 5px;
        margin-bottom: 10px;
        font-size: 16px;
        font-weight: 600;
    }
    .megaMenuDropdownContent h2 a {
        font-size: 16px;
        font-weight: 600;
    }
    .megaMenuDropdownContent h3,
    .megaMenuDropdownContent h3 a {
        font-size: 14px;
        font-weight: 600;
    }
    .megaMenuDropdownContent a {
        color: inherit;
        font-size: 11px;
        line-height: 16px;
    }
    .megaMenuDropdownContent .seeAll {
        margin-top: 5px;
        padding-bottom: 5px;
    }
    .megaMenuDropdownContent .seeAll a {
        font-size: 12px;
    }
    #dropdown-clearance {
        width: 210px;
    }
</style>

<div id="main-header" class="row text-center" style="background-color: #f1f1f1;">
    <div id="megaMenuContainer" class="megaMenuContainer">
        <#assign currentColumn = 0 />
        <#assign bodyOutput = "" />
        <#assign columnSizeCount = 0 />
        <#if megaMenuData?has_content>
            <#list megaMenuData as nav>
                <#assign attributes = 'bns-editable data-nav-id="' + nav.id?if_exists + '" data-nav-type="' + nav.type?if_exists + '" data-nav-description="' + nav.description?if_exists + '" data-nav-link="' + nav.link?default('') + '" data-nav-columnsequencenum="' + nav.columnSequenceNum?if_exists + '"' />
                <#assign orderedList = orderedList?if_exists + "'" + nav.id + "'," />

                <#if (nav.type)?string == "1">
                    <#if bodyOutput != "">
                        <#if columnSizeCount gt 0>
                        ${bodyOutput?replace("REPLACECOLUMNSIZECOUNT1REPLACE", columnSizeCount?string)?replace("REPLACECOLUMNSIZECOUNT2REPLACE", (12 / (columnSizeCount / 2))?string)}</div></div></div></div></div></div>
                        </#if>
                        <#assign currentColumn = 0 />
                        <#assign bodyOutput = "" />
                        <#assign columnSizeCount = 0 />
                    </#if>
                <div class="dropdown-hover-navigation" data-dropdown-target="dropdown-${nav.description?replace(" ", "_")?replace("&amp;", "")}" data-dropdown-alternate-parent="megaMenuContainer" data-dropdown-options="click shadowed(main-header) ignore-reverse-dropdown" ${attributes}>${nav.description}</div>
                <div id="dropdown-${nav.description?replace(" ", "_")?replace("&amp;", "")}" class="drop-down">
                <div class="megaMenuDropdownContent">
                <div class="row">
                <#else>
                    <#assign newColumn = false />
                    <#if currentColumn?string != (nav.columnSequenceNum)?string && (nav.type)?string == "2">
                        <#if currentColumn != 0 && columnSizeCount gt 0>
                        ${bodyOutput?replace("REPLACECOLUMNSIZECOUNT1REPLACE", columnSizeCount?string)?replace("REPLACECOLUMNSIZECOUNT2REPLACE", (12 / (columnSizeCount / 2))?string)}</div></div></div>
                        </#if>

                        <#assign columnSizeCount = 0 />
                        <#assign bodyOutput = '<div class="columns large-REPLACECOLUMNSIZECOUNT1REPLACE no-padding megaMenuFirstColumns">' />
                    </#if>

                    <#if currentColumn != nav.columnSequenceNum>
                        <#assign columnSizeCount = columnSizeCount + (2 * (nav.columnSequenceNum - currentColumn)) />
                        <#assign currentColumn = nav.columnSequenceNum />
                        <#assign newColumn = true />
                    </#if>

                    <#if (nav.type)?string == "2">
                        <#if (nav.link)?exists>
                            <#assign bodyOutput = bodyOutput + '<h2 ' + attributes + '><a href="' + (nav.link)?if_exists + '">' + nav.description + '</a></h2>' />
                        <#else>
                            <#assign bodyOutput = bodyOutput + '<h2 ' + attributes + '>' + nav.description + '</h2>' />
                        </#if>
                    </#if>

                    <#if (nav.type)?string == "2" && newColumn == true>
                        <#assign bodyOutput = bodyOutput + '<div class="row">' />
                        <#assign bodyOutput = bodyOutput + '<div class="columns large-REPLACECOLUMNSIZECOUNT2REPLACE">' />
                    <#elseif newColumn == true>
                        <#assign bodyOutput = bodyOutput + '</div>' />
                        <#assign bodyOutput = bodyOutput + '<div class="columns large-REPLACECOLUMNSIZECOUNT2REPLACE">' />
                    </#if>

                    <#if (nav.type)?string == "3">
                        <#if (nav.link)?exists>
                            <#assign bodyOutput = bodyOutput + '<h3 ' + attributes + '><a href="' + (nav.link)?if_exists + '">' + nav.description + '</a></h3>' />
                        <#else>
                            <#assign bodyOutput = bodyOutput + '<h3 ' + attributes + '>' + nav.description + '</h3>' />
                        </#if>
                    <#elseif (nav.type)?string == "4">
                        <#assign bodyOutput = bodyOutput + '<div ' + attributes + '><a href="' + (nav.link)?if_exists + '">' + nav.description + '</a></div>' />
                    <#elseif (nav.type)?string == "5">
                        <#assign bodyOutput = bodyOutput + '<div class="seeAll" ' + attributes + '><a href="' + (nav.link)?if_exists + '">' + nav.description + ' <i class="fa fa-caret-right"></i></a></div>' />
                    </#if>
                </#if>
            </#list>
            <#if columnSizeCount gt 0>${bodyOutput?replace("REPLACECOLUMNSIZECOUNT1REPLACE", columnSizeCount?string)?replace("REPLACECOLUMNSIZECOUNT2REPLACE", (12 / (columnSizeCount / 2))?string)}</#if></div></div></div></div></div></div>
        </#if>
    </div>
</div>

<div bns-undolast class="undoButton" style="margin-top: 20px;">Undo</div>
<div bns-redolast class="undoButton" style="margin-top: 20px;">Redo</div>
<div bns-savemegamenu class="undoButton" style="margin-top: 20px;">Save</div>

<script>
    var navigationMenuOrder = [${orderedList?replace(',$', '', 'r')}];
    var navigationMenuItemsToDelete = navigationMenuOrder;
    var navigationMenuHistory = [];
    var navigationMenuHistoryCurrentIndex = null;
    var navWebSiteId = 'envelopes'; // This could change if we do something like this for folders in the future... but for now let's be lazy 8^)

    function updateContent(elementToUpdate) {
        var newDescription = $('input[name="navDescription"]').val();
        var newLink = $('input[name="navLink"]').val();

        elementToUpdate.attr({
            'data-nav-description': newDescription,
            'data-nav-link': newLink
        }).html(newDescription);

        buildHistory();
    }

    function buildHistory() {
        var navigationMenuContent = [];

        for (var i = 0; i < navigationMenuOrder.length; i++) {
            element = $('[data-nav-id="' + navigationMenuOrder[i] + '"]');

            navigationMenuContent.push({
                'webSiteId': navWebSiteId,
                'type': element.attr('data-nav-type'),
                'description': (element.attr('data-nav-description') != '' ? element.attr('data-nav-description') : null),
                'link': (element.attr('data-nav-link') != '' ? element.attr('data-nav-link') : null),
                'sequenceNum': (i + 1) + '',
                'columnSequenceNum': element.attr('data-nav-columnsequencenum'),
                'id': element.attr('data-nav-id')
            });
        }

        if (navigationMenuHistory.length == 0 || (JSON.stringify(navigationMenuHistory[navigationMenuHistoryCurrentIndex]) != JSON.stringify(navigationMenuContent))) {
            navigationMenuHistory = navigationMenuHistory.splice(0, navigationMenuHistoryCurrentIndex);
            navigationMenuHistory.push(navigationMenuContent);
            navigationMenuHistoryCurrentIndex = navigationMenuHistoryCurrentIndex != null ? navigationMenuHistoryCurrentIndex + 1 : navigationMenuHistory.length;
        }
    }

    function loadHistory(index) {
        if (typeof index != 'undefined' && typeof navigationMenuHistory[index] != 'undefined') {
            loadContent(navigationMenuHistory[index]);
            navigationMenuHistoryCurrentIndex = index + 1;
        }
    }

    function loadContent(contentToLoad) {
        for (var i = 0; i < contentToLoad.length; i++) {
            var elementToUpdate = $('[data-nav-id="' + contentToLoad[i].id + '"]');
            $.each(contentToLoad[i], function(key, value) {
                elementToUpdate.attr('data-nav-' + key, value);
            });

            elementToUpdate.html(elementToUpdate.attr('data-nav-description'));
        }
    }

    $('[bns-editable]').off('mouseenter.contextMenu').on('mouseenter.contextMenu', function() {
        $('body').attr('oncontextmenu', 'return false;');
    }).off('mouseleave.contextMenu').on('mouseleave.contextMenu', function() {
        $('body').removeAttr('oncontextmenu');
    }).contextmenu(function(e) {
        if (e.button == 2) {
            var self = $(this);
            var type = typeof self.attr('data-nav-type') != 'undefined' ? self.attr('data-nav-type') : '';
            var description = typeof self.attr('data-nav-description') != 'undefined' ? self.attr('data-nav-description') : '';
            var link = typeof self.attr('data-nav-link') != 'undefined' ? self.attr('data-nav-link') : '';

            // Clear All Menus
            $('[bns-editmenu]').remove();

            var editMenu = $('<div />').addClass('editMenuPopup').attr({
                'bns-editmenu': '',
                'bns-ignoredropdownactions': ''
            }).append(
                $('<h3 />').html('Menu Editor').append(
                    $('<i />').addClass('fa fa-times closeMenu').off('click.closeMenu').on('click.closeMenu', function() {
                        $('[bns-editmenu]').remove();
                    })
                )
            );

            // Description
            editMenu.append(
                $('<div />').append(
                    $('<p />').html('Description')
                ).append(
                    $('<input />').attr({
                        'type': 'text',
                        'name': 'navDescription',
                        'placeholder': 'Enter Description Here'
                    }).val(description)
                )
            )

            // Link
            if (type != 1) {
                editMenu.append(
                    $('<div />').append(
                        $('<p />').html('Link')
                    ).append(
                        $('<input />').attr({
                            'type': 'text',
                            'name': 'navLink',
                            'placeholder': 'Enter Link Here'
                        }).val(link)
                    )
                );
            }

            editMenu.append(
                $('<div />').addClass('updateContent').html('Update').on('click.updateContent', function() {
                    updateContent(self);

                    $('[bns-editmenu]').remove();
                })
            );

            $('body').append(editMenu);

            $('[bns-editmenu]').css({
                'left': (parseInt(self.offset().left) - getFullWidth($('[bns-editmenu]'))) + 'px',
                'top': parseInt(self.offset().top) + 'px'
            });
        }
    });

    $('[bns-undolast]').off('click.undo').on('click.undo', function() {
        loadHistory(navigationMenuHistoryCurrentIndex - 2);
    });

    $('[bns-redolast]').off('click.redo').on('click.redo', function() {
        loadHistory(navigationMenuHistoryCurrentIndex);
    });

    // Wait for final event didn't work, so we need to use this to prevent bad data!!!
    var lockSave = false;
    $('[bns-savemegamenu]').off('click.save').on('click.save', function() {
        if (!lockSave) {
            lockSave = true;
            $.ajax({
                type: 'POST',
                url: '/' + websiteId + '/control/updateMegaMenu',
                data: {
                    itemsToDelete: JSON.stringify(navigationMenuItemsToDelete),
                    itemsToSave: JSON.stringify(navigationMenuHistory[navigationMenuHistoryCurrentIndex - 1])
                },
                dataType: 'json',
                cache: false
            }).done(function(data) {
                location.reload();
            });
        }
    });

    buildHistory();
</script>