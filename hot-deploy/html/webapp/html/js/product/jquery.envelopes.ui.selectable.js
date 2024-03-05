/**
 * Created by Manu on 8/19/2014.
 *
 * //usage
 *
 * <pre>
 *     $('[data-name*="printSettings0"]').ContainerUI('value', 'front-ink-color', 'full-color')
 *
 *     $('[data-name*="chooseColor0"]').ContainerUI('value', 'envelope-color')

 $('[data-name*="chooseColor0"]').ContainerUI('value', 'envelope-color', 'mandarin')

 $('[data-name*="printSettings"]').ContainerUI('data')
 */

/** @namespace $.envelopes */

$.widget("envelopes.ContainerUI", {
    options: {
        onUIReady: function(event, data) {/*console.log(data.containerId + ' is READY');*/},
        onUILoad: function(){},
        SelectableUI: {}
    },
    _ready: false,
    _data: {},

    _create: function() {
        var containerAttr = this.element.attr('data-container-ui');
        if(containerAttr !== typeof undefined && containerAttr !== false) {
            this._on({
                dataChanged: function(e, data) {
                    this._setData(data);
                }
            });
            this._data[this.element.data('name')] = {};
            $('[data-envelopes-ui]', this.element).EnvelopesUI(this.options);
            this._ready = true;
            this._trigger(this._trigger('onUIReady', null, {containerId : this.element.data('name')}));
        }
    },
    init: function() {
        this._ready = false;
        this._create();
    },

    _setData: function(data) {
        var containerData = this._data[data.container.data('name')];
        containerData[data.name] = data.value;
        this._data[data.container.data('name')] = containerData;
    },

    _uiType: function(name) {
        return $('[data-name$=' + name + ']', this.element).data('type');
    },

    onLoad: function() {
        this._trigger('onUILoad', null, this.data());
    },

    data: function() {
        return this._data;
    },

    value: function (name, val) {
        if (this._ready) {
            if (val === undefined) {
                return $('[data-name$=' + name + ']', this.element).EnvelopesUI('value', val);
            } else {
                if(this._uiType(name) == 'selectable-ui') {
                    $('[data-name$=' + name + '][data-value$=' + val + ']', this.element).EnvelopesUI('value', val);
                } else {
                    $('[data-name$=' + name + ']', this.element).EnvelopesUI('value', val);
                }
            }
        } else {
            return 'undefined';
        }
    }

});

$.widget("envelopes.BaseUI", {
    _containerUI: undefined,
    _inValidationMode: false,
    _name: undefined,
    _eventType:'implicit',
    uiType: 'text',
    _valid: false,
    _create: function() {
        this._internalValidate();
        if (this._valid) {
            this._preCreate();
            this._on({click: function (e) {
                this._onClickEvent(e);
            }});

            // There is a bug in jquery, that's triggering the change event twice when a checkbox is clicked
            this._on({change: function (e) {
                this._onChangeEvent(e);
            }});

            this._on({load: function (e) {
                this._onLoadEvent(e);
            }});
            this._postCreate();
        }
    },
    _internalValidate: function() {
        this._containerUI = this.element.closest('[data-container-ui]');
        this._name = this.element.data('name');

        if(this.element.data('type') !== undefined && $.trim(this.element.data('type')) != '') {
            this._uiType = this.element.data('type');
        } else if(this.element.attr('type') !== undefined && $.trim(this.element.attr('type')) != ''){
            this._uiType = this.element.attr('type');
        }

        this._valid = this._containerUI !== undefined && this._name !== undefined;

    },

    _preCreate: function() {
        //Override this method to do any pre-create operations
    },

    _postCreate: function() {
        //Override this method to do any post-create operations
        this.element.trigger('load');
    },

    _onChangeEvent: function(e) {
        var data = {container: this._containerUI, name: this._name, value: this.value(), eventType: this._eventType};
        this._eventType = 'implicit';
        this._containerUI.trigger('dataChanged', data);
        this._trigger('onchange', e, data);
    },
    _onClickEvent: function(e) {
        this._eventType = 'explicit';
    },
    _onLoadEvent: function() {

    },

    _isValid: function(currentValue) {
        var isValid = true;
        //Validation should be disabled, if we try to set the value from within the validate function.
        // Otherwise this may result in recursive invocation of the validate function.
        if(this._inValidationMode != true) {
            this._inValidationMode = true;
            isValid = this._trigger('validate', null, {container: this._containerUI, name: this._name, value: currentValue});
            this._inValidationMode = false;
        }
        return isValid;
    },
    value: function() {
        // This function needs to be implemented by all concrete Envelopes UI elements
    },
    destroy: function() {
        $.Widget.prototype.destroy.call( this );
    }
});

$.widget("envelopes.EnvelopesUI", {
    options: {
        onchange: function() {},
        validate: function() {return true;}
    },
    _containerUI: undefined,
    _name: undefined,
    _uiType: 'text',
    _valid: false,
    _create: function() {
        this._internalValidate();
        if(this._valid) {
            switch(this._uiType) {
                case 'checkbox':
                    $('[data-name$=' + this._name + ']', this._containerUI).HtmlCheckboxUI(this.options.HtmlCheckboxUI);
                    break;
                case 'text':

                    break;
                case 'selectable-ui':
                    $('[data-name$=' + this._name + ']', this._containerUI).SelectableUI(this.options.SelectableUI);
                    break;
            }
        }
    },
    _internalValidate: function() {
        this._containerUI = this.element.closest('[data-container-ui]');
        this._name = this.element.data('name');

        if(this.element.data('type') !== undefined && $.trim(this.element.data('type')) != '') {
            this._uiType = this.element.data('type');
        } else if(this.element.attr('type') !== undefined && $.trim(this.element.attr('type')) != ''){
            this._uiType = this.element.attr('type');
        }

        this._valid = this._containerUI !== undefined && this._name !== undefined;

    },
    /*destroy: function() {
        this._destroyAll();
        this._super('destroy');
    },*/
    value: function(val) {
        switch(this._uiType) {
            case 'checkbox':
                return $('[data-name$=' + this._name + ']', this._containerUI).HtmlCheckboxUI('value', val);
                break;
            case 'text':

                break;
            case 'selectable-ui':
                if(val === undefined) {
                    return $('[data-name$=' + this._name + ']', this._containerUI).SelectableUI('value', val);
                } else {
                    return $('[data-name$=' + this._name + '][data-value$=' + val + ']', this._containerUI).SelectableUI('value', val);
                }
                break;
        }
    }/*,
    _destroyAll: function() {
        $('[data-name$=' + this._name + ']', this._containerUI).HtmlCheckboxUI('destroy');
        $('[data-name$=' + this._name + ']', this._containerUI).SelectableUI('destroy');
    }*/
});

$.widget("envelopes.SelectableUI", $.envelopes.BaseUI, {
    options: {
        dependentSelectableUIs: [],
        selectedClass : 'selected',
        onchange: function() {},
        validate: function() {return true;}

    },
    _dependentUIs: [],

    _preCreate: function() {
        this._parseDependentUIs();
    },

    _create: function() {
        this._super('_create');
    },

    _onClickEvent: function(e) {
        if(!this._isAlreadySelected()) {
            this._super('_onClickEvent');
            this._selectThis(false);
        }
    },

    _onLoadEvent: function() {
        if(this.element.data('checked') == true) {
            this._selectThis(true);
        }
    },

    _parseDependentUIs: function() {
        var name = this._name;
        var dependentUIs = [name];
        $.each(this.options.dependentSelectableUIs, function(i, val){
            if(val.indexOf(name) > -1) {
                var vals = val.split(',');
                for(var j = 0; j < vals.length; j ++) {
                    if($.trim(vals[j]) != name) {
                        dependentUIs[dependentUIs.length] = vals[j];
                    }
                }
            }
        });
        this._dependentUIs = dependentUIs;
    },

    _unselectAll: function(name) {
        if(name === undefined) {
            name = this._name;
        }
        for(var i = 0; i < this._dependentUIs.length; i ++) {
            var allSelectedUIs = this._containerUI.find('[data-name$=' + this._dependentUIs[i] + '][data-checked$=true]');

            allSelectedUIs.removeAttr('data-checked').removeClass(this.options.selectedClass);
            allSelectedUIs.find("i[class*=check]").removeClass("hidden").addClass("hidden");
            if(i > 0) {
                $(allSelectedUIs).trigger('change');
            }
        }

    },

    _selectThis: function(internalEvent) {
        if(internalEvent == true || this._isValid())  {
            this._unselectAll();
            this.element.attr('data-checked', true).addClass(this.options.selectedClass);
            this.element.find("i[class*=check]").removeClass("hidden");
            this.element.trigger('change');
        }
    },

    _isValid: function() {
        return this._super(this.element.data('value'));
    },

    _isAlreadySelected: function() {
        return this.element.attr('data-checked') == 'true';
    },

    _setValue: function(val) {
        if(!this._isAlreadySelected()) {
            this._selectThis(true);
        }
    },


    value: function(val) {
        if(val !== undefined) {
            this._setValue(val);
        } else {
            var value = this._containerUI.find('[data-name$=' + this._name + '][data-checked$=' + true + ']').data('value');

            return typeof value == 'string' || typeof value == 'boolean' ? value : 'undefined';
        }

    }

});

$.widget("envelopes.HtmlCheckboxUI", $.envelopes.BaseUI, {
    options: {
        onchange: function() {},
        validate: function() {return true;}
    },
    _create: function() {
        this._super('_create');

    },

    _onClickEvent: function(e) {
        this._super('_onClickEvent');
        this._toggleThis();
    },

    _onLoadEvent: function() {
        if(this.element.is(':checked') == true) {
            this.element.trigger('change');
        }
    },

    _toggleThis: function() {
        if(!this._isValid())  {
            this.element.prop('checked', !this.element.is(':checked'));
        } else {
            this.element.trigger('change');
        }
    },

    _isValid: function() {
        return this._super(this.element.is(':checked') ? 'undefined' : this.element.attr('value'));
    },

    _setValue: function(val) {
        var isAlreadyChecked = this.element.is(':checked');
        if((!isAlreadyChecked && (val == 'yes' || val == true)) || (isAlreadyChecked && (val == 'no' || val == false))) {
//            this.element.prop('checked', !isAlreadyChecked);
//            this.element.trigger('change');
            this.element.trigger('click');
        }
    },

    value: function(val) {
        if(val !== undefined) {
            this._setValue(val);
        } else {
            var value = this._containerUI.find('[data-name$=' + this._name + ']:checked').val();
            return typeof value == 'string' || typeof value == 'boolean' ? value : 'undefined';
        }
    }

});

$.widget("envelopes.ToolbarUI", {
    options: {typeIndex: {invitation: 0, envelope: 1}, productType:'envelope', onclick: function(event, data) {}},
    _activeButton: undefined,
    _showDesignerFlag: false,
    _showOptionsFlag: false,
    _designerOpen: false,
    _eventType: 'explicit',
    _create: function() {
        var toolbarAttr = this.element.attr('data-toolbar-ui');
        if(toolbarAttr !== typeof undefined && toolbarAttr !== false) {
            $('[data-toolbar-button-ui]', this.element).ToolbarButtonUI(this.options);
        }
    },
    action: function(newActiveButton, _type) {
        var name = newActiveButton.data('name');
        var type = _type === undefined ? newActiveButton.data('type') : _type;
        var groupType = newActiveButton.data('group-type');
        if(this._activeButton === undefined || (groupType == 'envelope' && (this._showDesignerFlag || this._showOptionsFlag)) || this._activeButton.data('name') != name) {
            type == 'designer' || this._showDesignerFlag == true ? this._showDesigner(newActiveButton) : type == 'options' ? this._showOptions(newActiveButton) : this._designerOpen ? this._hideDesigner() : this._hideOptions(newActiveButton);
            this._updateActiveState(newActiveButton);
            this._trigger('onclick', null, {name: name, type: type, groupType: groupType, productType: this.options.productType, index: this.options.typeIndex[groupType], eventType: this._eventType});
            this._eventType = 'explicit';
        }
    },
    productType: function() {
        return this.options.productType;
    },
    _updateActiveState: function(newActiveButton) {
        $('i', $('[data-toolbar-button-ui]').removeClass('selected')).addClass('hidden');
        $('i', $('.' + newActiveButton.data('name')).addClass('selected')).removeClass('hidden');
        this._activeButton = newActiveButton;
    },

    _showDesigner: function(button) {
        var index = this.options.typeIndex[button.data('group-type')];
        if(this._activeButton !== undefined && this._activeButton.data('group-type')  != button.data('group-type')) {
            this._hideDesigner(this.options.typeIndex[this._activeButton.data('group-type')]);
        }
        productEditEnd();
        var productContentEl = $('#product-content');
        productContentEl.hide();
        this._createDesignerDiv(productContentEl, button);
        var designerEl = $('#designer_' + index);
        designerEl.show();
        $('body').data('product').callOnlineDesigner(index, designerEl);
        this._designerOpen = true;
        this._showDesignerFlag = false;
    },
    _createDesignerDiv: function(element, button) {
        var index = this.options.typeIndex[button.data('group-type')];
        if ($('#designer_' + index).length == 0) {
            element.after($('<div/>').attr('id', 'designer_' + index).attr('data-id', index).addClass('full-edit-content row no-padding margin-bottom-xxs margin-top-xxs').css({'height': '530px', 'max-height': '530px'}).hide());
        }
    },
    destroyDesignerDiv: function() {
        var index = this._activeButton !== undefined ? this.options.typeIndex[this._activeButton.data('group-type')] : 0;
        if ($('#designer_' + index).length != 0) {
            $('#designer_' + index).remove();
        }
    },
    _hideDesigner: function(index) {
        if(index === undefined) {
            index = this.options.typeIndex[this._activeButton.data('group-type')];
        }
        $('#edit-container').OrbitWidget('changeSlideSet', index);
        $('#designer_' + index).hide();
        $('#product-content').show();
        this._designerOpen = false;
    },

    _showOptions: function(button) {
        var index = this.options.typeIndex[button.data('group-type')];

        if (parseInt($("#edit-container").css("height")) == 0) {
			$("#edit-container").css("height", "492px");
		}

        if(this._activeButton !== undefined && (this.options.productType == 'envelope' || this._activeButton.data('type') == 'designer')) {
            this._hideDesigner(this.options.typeIndex[this._activeButton.data('group-type')]);
        }
        if(this.options.productType == 'invitation') {
            $('#edit-container').OrbitWidget('changeSlideSet', index);
        }
        if(this._activeButton === undefined || this.options.productType == 'envelope' || this._activeButton.data('type') != 'options') {
            productEditStart();
        }
        this._showOptionsFlag = false;
    },
    _hideOptions: function(button) {
        if(this._activeButton !== undefined && button.data('type') != 'options') {
            productEditEnd();
        }
    },
    showDesigner: function() {
        this._eventType = 'implicit';
        this._showDesignerFlag = true;
        this._showOptionsFlag = false;
        var index = this.options.typeIndex[this._activeButton.data('group-type')];
        $('[data-toolbar-button-ui][data-type$=' + (this.options.productType == 'envelope' ? 'options' : 'designer') + ']:' + (index == 0 ? 'first' : 'last')).ToolbarButtonUI().trigger('click');
    },

    showOptions: function(ignoreFlag) {
        if(!ignoreFlag && this._activeButton !== undefined) {
            this._eventType = 'implicit';
            this._showOptionsFlag = true;
            this._showDesignerFlag = false;
            var index = this.options.typeIndex[this._activeButton.data('group-type')];
            this.action($('[data-toolbar-button-ui][data-type$="options"]:' + (index == 0 ? 'first' : 'last')));
        }
    },

    isDesignerOpen: function() {
        return this._designerOpen;
    },

    isNoPrintSelected: function() {
        return $('[data-type$="none"].selected').length == 1;
    },

    hideDesigner: function() {
        this._hideDesigner();
    },

    activateButton: function(index) {
        this._eventType = 'implicit';
        $('[data-toolbar-button-ui][data-type$="options"]:' + (index == 0 ? 'first' : 'last')).ToolbarButtonUI().trigger('click');
    },

    disableButton: function(disableFlag, index, type) {
        $('[data-toolbar-button-ui][data-type$=' + type + ']:' + (index == 0 ? 'first' : 'last')).ToolbarButtonUI('disable', disableFlag);
    }

});

$.widget("envelopes.ToolbarButtonUI", {
    options: {},
    _create: function() {
        if(this.element.data('disable') == true) {
            this.disable(true);
        }
        this._on({click: function (e) {
            if(animation_active == false && this.element.data('disable') != true) {
                $('body').ToolbarUI('action', this.element);
            }
        }});
    },
    disable:function(disableFlag) {
        if(disableFlag == true) {
            this.element.addClass('element-inactive');
            this.element.data('disable', true);
        } else {
            this.element.removeClass('element-inactive');
            this.element.data('disable', false);
        }
    }
});
