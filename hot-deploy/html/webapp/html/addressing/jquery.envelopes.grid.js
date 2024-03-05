/**
 * Created by Manu on 7/15/2015.
 *
 * <pre>
 *     Known Issues:
 * </pre>
 */

//@deprecated
function createShadowedBackground(parent) {
    parent = typeof parent !== 'undefined' ? parent : $('body');

    if ($('.gridShadowedBackground').length < 1) {
        $(parent).append(
            $('<div />').addClass('gridShadowedBackground')
        )
    } else {
        $('.gridShadowedBackground').removeClass('grid-hide');
    }
}
//@deprecated
function createModalPopup(parent) {

    parent = typeof parent !== 'undefined' ? parent : $('body');
    createShadowedBackground(parent);

    $(parent).append(
        $('<div />').addClass('gridPopupContent dialog').append(
        	$('<div />').addClass('gridPopupInner').append(
        		$('<h5 />').addClass('gridPopupHeader').html('Upload an Address Book')
        	).append(
        		$('<div />').addClass('gridPopupBody').html('<h5>Upload your address list:</h5><div style="20px 20px 10px 20px"><input type="text" style="width:75%;display:inline-block"/><span class="jqs-choose-file grid-btn">Choose File</span></div><div style="padding-left:40px"><input type="checkbox"/>Check this if the uploaded file has Header Row</div><div style="text-align: right;padding-right: 40px"><span class="jqs-upload-file grid-btn">Upload</span></div>')
        	)
        )
    );
}

//@deprecated
function createProgressBar(parent, className, startingLength) {
    $(parent).append(
        $('<div />').addClass('meter').append(
            $('<span />').addClass(typeof className !== 'undefined' ? className : 'jqs-progress').css({'width': (typeof startingLength !== 'undefined' ? startingLength : 0) + '%'})
        )
    );
}
//@deprecated
function destroyProgressBar(parent, className) {
    $('.gridShadowedBackground').remove();
    $(parent).find('.gridPopupContent').remove();
}

//@deprecated
function updateProgressBar(element, width, animate) {
    $(element).stop();
    if (typeof animate !== 'undefined' && !animate) {
        $(element).css('width', width + '%');
    }
    else {
        $(element).animate({'width': width + '%'});
    }
}

function getTextNodesIn(node) {
    var textNodes = [];
    if (node.nodeType == 3) {
        textNodes.push(node);
    } else {
        var children = node.childNodes;
        for (var i = 0, len = children.length; i < len; ++i) {
            textNodes.push.apply(textNodes, getTextNodesIn(children[i]));
        }
    }
    return textNodes;
}

function setSelectionRange(el, start, end) {
    if (document.createRange && window.getSelection) {
        var range = document.createRange();
        range.selectNodeContents(el);
        var textNodes = getTextNodesIn(el);
        var foundStart = false;
        var charCount = 0, endCharCount;

        for (var i = 0, textNode; textNode = textNodes[i++]; ) {
            endCharCount = charCount + textNode.length;
            if (!foundStart && start >= charCount && (start < endCharCount || (start == endCharCount && i <= textNodes.length))) {
                range.setStart(textNode, start - charCount);
                foundStart = true;
            }
            if (foundStart && end <= endCharCount) {
                range.setEnd(textNode, end - charCount);
                break;
            }
            charCount = endCharCount;
        }

        var sel = window.getSelection();
        sel.removeAllRanges();
        sel.addRange(range);
    } else if (document.selection && document.body.createTextRange) {
        var textRange = document.body.createTextRange();
        textRange.moveToElementText(el);
        textRange.collapse(true);
        textRange.moveEnd("character", end);
        textRange.moveStart("character", start);
        textRange.select();
    }
}

function makeEditableAndHighlight(colour) {
    var range;
    var sel = window.getSelection();
    if (sel.rangeCount && sel.getRangeAt) {
        range = sel.getRangeAt(0);
    }
    document.designMode = "on";
    if (range) {
        sel.removeAllRanges();
        sel.addRange(range);
    }
    // Use HiliteColor since some browsers apply BackColor to the whole block
    if (!document.execCommand("HiliteColor", false, colour)) {
        document.execCommand("BackColor", false, colour);
    }
    document.designMode = "off";
}

function highlight(colour) {
    var range, sel;
    if (window.getSelection) {
        // IE9 and non-IE
        try {
            if (!document.execCommand("BackColor", false, colour)) {
                makeEditableAndHighlight(colour);
            }
        } catch (ex) {
            makeEditableAndHighlight(colour);
        }
    } else if (document.selection && document.selection.createRange) {
        // IE <= 8 case
        range = document.selection.createRange();
        range.execCommand("BackColor", false, colour);
    }
}

function selectAndHighlightRange(element, start, end) {
    setSelectionRange(element, start, end);
    highlight("");
}

$(window).on('resize', function() {

    try {
        $('#env-variabledata-grid').VariableDataGrid('resizeGridOverlay');
    } catch (ignore) { }

});

function scrollEvent(e) {
    if($('.jqs-grid-shadowed-background.hide').length == 0 && $('.jqs-grid-shadowed-background').length > 0) {
        if ($('.jqs-data-group-drop-down.texelGridList:hover').length == 0 && $('#env-variabledata-grid:hover').length != 0) {
            var evt = window.event || e; //equalize event object
            var delta = evt.detail ? evt.detail * (-120) : evt.wheelDelta; //check for detail first so Opera uses that instead of wheelDelta
            if ((typeof evt.deltaY !== 'undefined' && (evt.deltaY > 0 || evt.deltaY < 0)) || (typeof evt.axis !== 'undefined' && evt.axis == 2) || (typeof evt.deltaY === 'undefined' && typeof evt.axis === 'undefined')) {
                if (delta < 0) {
                    $('.env-grid_vertical_scrollbar').scrollTo("+=30");
                } else {
                    $('.env-grid_vertical_scrollbar').scrollTo("-=30");
                }
            }
        }
    }
}
$(function(){
    var mouseWheelEvent=(/Firefox/i.test(navigator.userAgent))? "DOMMouseScroll" : "mousewheel"; //FF doesn't recognize mousewheel as of FF3.x
    if (document.attachEvent) {//if IE (and Opera depending on user setting)
        document.attachEvent("on" + mouseWheelEvent, scrollEvent);
    } else if (document.addEventListener) { //WC3 browsers
        document.removeEventListener(mouseWheelEvent, scrollEvent);
        document.addEventListener(mouseWheelEvent, scrollEvent, false);
    }
});

var waitForFinalEvent = (function () {
    var timers = {};
    return function (callback, ms, uniqueId) {
        if(!uniqueId) {
            uniqueId = "Don't call this twice without a uniqueId.";
        }
        if(timers[uniqueId]) {
            clearTimeout (timers[uniqueId]);
        }
        timers[uniqueId] = setTimeout(callback, ms);
    };
})();

Grid = function(dimension) {
    this.rows = 0;
    this.columns = 0;
    this.data = [];



    if(Grid.functionsDefined) {
        return;
    }

    Grid.functionsDefined = true;

    Grid.prototype.init = function(dimension) {
        this.rows = dimension[0];
        this.columns = dimension[1];
        this.data = new Array(this.rows);
        for(var i = 0; i < this.rowCount; i ++) {
            this.data[i] = new Array(this.columnCount);
        }
    };

    Grid.prototype.load = function(data) {
        this.init(data.length, data[0].length);
        for(var i =0; i < this.rows; i ++) {
            this.data[i] = new Array(this.rows);
            for(var j = 0; j < this.columns; j ++) {
                this.data[i][j] = data;
            }
        }
    };
};

var _getColumnIndex = function(elem) {
    return $(elem).parent().index();
};

$.widget("envelopes.DataGrid", $.envelopes.BaseWidget, {
    options: {
        debug: false,
        usesDesign: true,
        responsive: false,
        overlayRows: 100,
        overlayColumns: 26,
        overlayMaxRows: 200,
        overlayMaxColumns: 100,
        dataMinRows: 100,
        dataMinColumns: 1,
        dataAddRowSize: 100,
        cellHeight: 20,
        dataGroupId:0,//30185,// 30181,
        clientId: 'envelopes',
        partyId: '',
        sessionId: '',
        dataGroupMode: 'manual', // manual, upload, existing
        dataGroupAttributeSet: 'dynamic',
        dataGroupAttributes: ['A'],
        persistenceBatchSize: 100,
        fileUploadEndpoint: '/' + websiteId + '/control/uploadVariableDataFile',
        getVariableDataGroupNamesEndPoint: '/' + websiteId + '/control/getVariableDataGroupNames',
        findVariableDataGroupEndPoint: '/' + websiteId + '/control/findVariableDataGroup',
        syncToServerEndpoint: '/' + websiteId + '/control/saveVariableData',
        deleteVariableDataEndpoint: '/' + websiteId + '/control/deleteVariableData',
        saveVariableDataForLaterEndpoint: '/' + websiteId + '/control/saveVariableDataForLater',
        loginCallback: function() {console.log('login callback');},
        contextMenu: '<div class="env-grid_contextmenu">' +
            '  <div>a</div>' +
            '  <div>b</div>' +
            '  <div>c</div>' +
            '  <div>d</div>' +
            '  <div>e</div>' +
            '  <div>f</div>' +
            '</div>'

    },

    /**
     * Getter method for options.responsive
     *
     * options.responsive - Boolean variable to specify if the height of the grid needs to be responsive depending on the container height.
     *
     * @returns {boolean}
     * @private
     */
    _isResponsive: function() {
        return this.options.responsive;
    },

    /**
     * Getter method for options.overlayRows.
     *
     * options.overlayRows - Number of rows in the overlay grid.
     *
     * @returns {number}
     * @private
     */
    _getGridOverlayRows: function () {
        return this.options.overlayRows;
    },

    /**
     * Setter method for options.overlayRows
     *
     * @param rows - The number of overlayRows that need to be set to options.overlayRows option settings
     *
     * If the input row parameter is empty and _isResponsive() == true, then the number of overlayRows will be calculated by the widget from the height
     * of the container. Otherwise, the passed in 'row' parameter value will be set as the options.overlayRows.
     *
     * @private
     */
    _setGridOverlayRows: function(rows) {
        if(typeof rows === 'undefined') {
            if(this._isResponsive() ) {
                var containerHeight = this.element.parent().height();
                this.options.overlayRows = parseInt((containerHeight - 80) / this._getCellHeight()) - 5;
            }
        } else if(this._getGridOverlayRows() != rows) {
            this.options.overlayRows = rows;
            this._resetOverlayColumns();
            this._resetOverlayRows();
            this._renderGridOverlay();
            this._setVerticalScrollHeight();
            this._loadDropdowns();
            var dataGroup = this._getDataGroup();
            if(typeof dataGroup.name !== 'undefined') {
                this._setDataGroupName(dataGroup.name);
            }
            if(this._lastRowOffSet > 1) {
                $('.env-grid_vertical_scrollbar').scrollTo(parseInt((this._lastRowOffSet - 1) * this._getScrollFactor()));
            }
        }
    },

    _scrollToTop: function() {
        $('.env-grid_vertical_scrollbar').scrollTo(0);
    },

    /**
     * Getter method for options.overlayColumns
     *
     * options.overlayColumns - number of columns in the grid overlay
     *
     * If has custom columnTitles, then the number of column titles will be considered as the number of columns instead of options.overlayColumns
     *
     * @returns {*}
     * @private
     */
    _getGridOverlayColumns: function () {
        return this._getColumnTitles().length > 0 ? this._getColumnTitles().length : this.options.overlayColumns;
    },

    /**
     * Getter method for options.overlayMaxRows
     *
     * options.overlayMaxRows - Maximum number of rows that the gridOverlay can have.
     *
     * @returns {number}
     * @private
     */
    _getGridOverlayMaxRows: function () {
        return this.options.overlayMaxRows;
    },

    /**
     * Getter method for this.options.overlayMaxColumns
     *
     * this.options.overlayMaxColumns - Maximum number of columns that the gridOverlay can have.
     *
     * @returns {number}
     * @private
     */
    _getGridOverlayMaxColumns: function () {
        return this.options.overlayMaxColumns;
    },

    /**
     * Getter method for options.dataMinRows
     *
     * options.dataMinRows - Minimum number of rows that the initial gridData should have
     *
     * @returns {number}
     * @private
     */
    _getDataMinRows: function() {
        return this.options.dataMinRows;
    },

    /**
     * Getter method for options.dataMinColumns
     *
     * options.dataMinColumns - Minimum number of columns that the initial gridData should have
     *
     * @returns {number}
     * @private
     */
    _getDataMinColumns: function() {
        return this.options.dataMinColumns;
    },

    /**
     * Getter method for options.dataAddRowSize
     *
     * options.dataAddRowSize - Number of empty rows that need to be added when the user clicks addRows toolbar button
     *
     * @returns {number}
     * @private
     */
    _getDataAddRowSize: function() {
        return this.options.dataAddRowSize;
    },

    /**
     * Getter method for options.cellHeight
     *
     * options.cellHeight - The grid cell height in pixels
     *
     * @returns {number}
     * @private
     */
    _getCellHeight: function() {
        return this.options.cellHeight;
    },

    /**
     * Getter method for options.dataGroupId
     *
     * options.dataGroupId - The id of this dataGroup in the database
     *
     * @returns {number}
     * @private
     */
    _getDataGroupId$: function() {
        return this.options.dataGroupId;
    },

    /**
     * Getter method for options.partyId
     *
     * options.partyId - The unique id for the user login
     *
     * @returns {string}
     * @private
     */
    _getPartyId$: function() {
        return this.options.partyId;
    },

    _setPartyId$: function(partyId) {
        this.options.partyId = partyId;
    },

    /**
     * Getter method for options.sessionId
     *
     * options.sessionId - The unique sessionId
     *
     * @returns {string}
     * @private
     */
    _getSessionId$: function() {
        return this.options.sessionId;
    },

    _getClientId$: function() {
        return this.options.clientId;
    },

    /**
     * Getter method for options.dataGroupAttributeSet
     *
     * options.dataGroupAttributeSet - The name of the dataAttributeSet.
     *
     * @returns {string}
     * @private
     */
    _getDataGroupAttributeSet$: function() {
        return this.options.dataGroupAttributeSet;
    },

    /**
     * Getter method for options.dataGroupAttributes
     *
     * options.dataGroupAttributes - The dataGroupAttributes for the given dataGroupAttributeSet
     *
     * @returns {*}
     * @private
     */
    _getDataGroupAttributes$: function() {
        return this.options.dataGroupAttributes;
    },


    /**
     * The name of this widget, overriding the name in envelopes.BaseWidget
     * @returns {string}
     * @private
     */
    //@Override
    _getWidgetName: function () {
        return 'envelopes.DataGrid';
    },

    /**
     * The indent offset for this widget
     * @returns {string}
     * @private
     */
    //@Override
    _getIndentBaseOffset: function () {
        return '\t\t\t\t\t';
    },

    /**
     * Method used to get the default array dimension for the grid data. The DataGrid widget requires all the rows should contain the same number
     * of columns. So the rows and the columns in each row should be verified for same number of columns and if not the same, then need to be made
     * equal by the _fixArrayDimension() method. The gridData is passed in as a 2D array and  the number of max columns in the gridData is found
     * from the max length of the inner array. If the gridData is not passed as a 2D array, then in order to get the number of rows and columns,
     * a default value for rows and columns are required and this method is used to return that default values as an array of 2 elements. The
     * first elements is the default number of rows and the second value is the default number of columns
     *
     * @returns {*[]}
     * @private
     */
    _getDefaultGridDataArrayDimension: function() {
        return [this._getDataMinRows(), this._getDataMinColumns()];
    },


    /**
     * The method used to return the column titles. The base DataGrid use dynamically generated alphabetical column titles like (A, B, C,...., AA, AB, ...)
     * So this method will return an empty array. This method needs to be overridden in child widgets, in order to use widget specific column titles.
     *
     * @returns {Array}
     * @private
     */
    _getColumnTitles: function() {
        return [];
    },

    _emptyRowsAtBottom: 2,

    _uploadedFileName: '',

    /**
     * _dataGroupId - The id of this dataGroup in the database
     */
    _dataGroupId: 0,

    /**
     * Getter method for _dataGroupId
     *
     * @returns {number}
     * @private
     */
    _getDataGroupId: function() {
        return this._dataGroupId;
    },

    /**
     * Method used to set the dataGroupId(grid instance id) and rowDataId (row id) of all rowData associated with the dataGroup
     *
     * This method will in turn call the _updateRowDataIds() method to set the rowDataId of all associated rowData
     *
     * @param data
     * @private
     */
    _setDataGroupId: function(data) {
        this._dataGroupId = data.dataGroupId;
        this._setRowDataIds(data.rowDataIds);
    },

    /**
     * Method used to set the rowDataId of all created/modified rowData. The rowDataIds are coming as a map, where the
     * rowDataId as the key and its index in the gridData array as the value.
     *
     * @param rowDataIds
     * @private
     */
    _setRowDataIds: function(rowDataIds) {
        for(prop in rowDataIds) {
            var row = this._gridData[rowDataIds[prop]];
            row[0] = this._setIdCellValue(row[0], prop)
        }
    },

    /**
     * The name of the dataAttributeSet. The DataGrid widget can be for any type of tabular data and the attributes for each of these types
     * are defined on the server side based on this dataGroupAttributeSet. The default value for this variable is 'sheet'
     */
    _dataGroupAttributeSet: 'dynamic',

    /**
     * Getter method for _dataGroupAttributeSet
     *
     * @returns {string}
     * @private
     */
    _getDataGroupAttributeSet: function() {
        return this._dataGroupAttributeSet;
    },

    /**
     * The setter method for _dataGroupAttributeSet
     *
     * @param attributeSet
     * @private
     */
    _setDataGroupAttributeSet: function(attributeSet) {
        this._dataGroupAttributeSet = attributeSet;
    },

    /**
     * The dataGroupAttributes for the given dataGroupAttributeSet. These attributes are used as the columns in the DataGrid widget
     */
    _dataGroupAttributes: [],

    /**
     * Getter method for _dataGroupAttributes.
     *
     * If _dataGroupAttributes is empty, then return the options.dataGroupAttributes
     *
     * @returns {*}
     * @private
     */
    _getDataGroupAttributes: function() {
        return this._dataGroupAttributes.length > 0 ? this._dataGroupAttributes : this._getDataGroupAttributes$();
    },

    /**
     * Setter method for _dataGroupAttributes
     *
     * @param dataGroupAttributes
     * @private
     */
    _setDataGroupAttributes: function(dataGroupAttributes) {
        this._dataGroupAttributes = dataGroupAttributes;
    },

    /**
     * Map used to store all the dataGroups associated with this widget instance.
     *
     * NOTE - All the dataGroups should be of the same dataAttributeSet
     *
     */
    _dataGroups: {},

    /**
     * The current dataGroup associated with this widget instance.
     */
    _dataGroup: {},

    /**
     * Getter method for _dataGroup
     *
     * @returns {*}
     * @private
     */
    _getDataGroup: function() {
        return this._dataGroup;
    },

    _setDataGroup: function(dataGroup) {
        this._dataGroup = dataGroup;
        this._dataGroupId = dataGroup.id;
    },

    _loadDataGroup: function(groupId) {
        if(this._getDataGroupId() != groupId) {
            var dataGroup = this._getDataGroupFromClient(groupId);
            if(typeof dataGroup.id !== 'undefined') {
                this._setDataGroup(dataGroup);
                this._setDataGroupAttributeSet(dataGroup.attributeSet);
                this._setDataGroupAttributes(dataGroup.attributes);
                this._initializeGridData();
                this._initGridData();
                this._setDataGroupName(dataGroup.name);
                this._resetSelectedCells();
                this._makeCellActive();
                this._markSelection(true);
                this._scrollToTop();
                this._showServiceCharge();
            }
        }
    },

    /**
     * Method used to get the dataGroup from the client side cache corresponding to the given groupId.
     *
     * If dataGroup corresponding to the given groupId is not found on the client side, this method will try to get it from  the server side using
     * _getDataGroupFromServer() method.
     *
     * @param groupId
     *
     * @returns {*}
     * @private
     */
    _getDataGroupFromClient: function(groupId) {
        if(typeof groupId === 'undefined') {
            groupId = 0;
        }

        /*
         var dataGroup = this._dataGroups[groupId];
         if(typeof dataGroup === 'undefined') {
         dataGroup = this._getDataGroupFromServer(groupId);
         if(typeof dataGroup !== 'undefined' && typeof dataGroup.id !== 'undefined') {
         this._dataGroups[dataGroup.id] = dataGroup;
         }
         }

         return dataGroup;
         */
        return this._getDataGroupFromServer(groupId);

    },

    /**
     * Method used to get the dataGroup from the server side corresponding to the given groupId.
     *
     * If groupId is not passed in or is less than 1, then an empty dataGroup with default groupName
     * will be generated from the server side.
     *
     * @param groupId
     *
     * @returns {*}
     * @private
     */
    _getDataGroupFromServer: function(groupId) {
        var self = this;
        var params = 'attributeSet=' + this._getDataGroupAttributeSet$();
        if(typeof groupId !== 'undefined' && groupId > 0) {
            params += '&groupId=' + groupId;
        }
        var dataGroup = null;
        $.ajax({
            url: this.options.findVariableDataGroupEndPoint,
            data: params,
            method: 'GET',
            dataType: 'json',
            async: false
        }).done(function (data) {
            if (data.success) {
                dataGroup = data.dataGroup;
            }
        });
        return dataGroup;
    },

    /**
     * Method used to fetch and bind the dataGroup corresponding to the given dataGroupId from the server. If the dataGroupId is less than 1,
     * then an empty dataGroup with default name will be used
     * @private
     */
    _bindDataGroup: function() {
        var dataGroup = this._getDataGroupFromClient(this._getDataGroupId$());
        if(typeof dataGroup.id !== 'undefined') {
            this._setDataGroup(dataGroup);
            this._setDataGroupAttributeSet(dataGroup.attributeSet);
            this._setDataGroupAttributes(dataGroup.attributes);
        }
    },

    /**
     * Method used to initialize the empty dataGrid with the required/minimum dimension
     *
     * @private
     */
    _initializeGridData: function() {
        var initialDataRows = this._getOverlayRows() < this._getDataMinRows() ? this._getDataMinRows() : this._getOverlayRows();
        var initialDataColumns = this._getOverlayColumns() < this._getDataMinColumns() ? this._getDataMinColumns() : this._getOverlayColumns();
        this._gridData = new Array(initialDataRows);

        for (var i = 0; i < initialDataRows; i++) {
            this._gridData[i] = new Array(initialDataColumns);
        }

        this._setVerticalScrollHeight();
    },

    /**
     * Method used to initialize the empty dataGrid with the dataGrid object bound by the _bindDataGroup() method
     * @private
     */
    _initGridData: function() {
        var dataGroup = this._getDataGroup();
        if(typeof dataGroup.name !== 'undefined') {
            this._setDataGroupName(dataGroup.name);
            this._loadGridData(dataGroup.rowData);
        }
    },

    /**
     * Method used to load data to the GridData. This method will add additional columns to GridOverlay, if necessary.
     * Before loading the passed in data, this method will fix the dimension of the 2D array, by making all columns in
     * each row the same by adding missing columns, if any
     * @param data - The 2D array data that needs to be loaded to the GridData.
     * @private
     */
    _loadGridData: function(data) {
        this._resetActiveCell();
        data = this._fixArrayDimension(data, this._getDefaultGridDataArrayDimension());
        var rows = this._currentDataRowCount();
        var columns = this._currentDataColumnCount();
        if(data.length > this._currentDataRowCount()) {
            rows = data.length;
        }
        if(data[0].length > this._currentDataColumnCount()) {
            /*for(var i =0; i < data[0].length - columns; i ++) {
             this._addColumnToGridOverlay();
             }*/
            this.addColumns(-1, data[0].length - columns);
            columns = data[0].length;
        }
        if(rows % 100 > 0) {
            rows += (100 - (rows % 100));
        }
        this._gridData = new Array(rows);
        for(var i = 0; i < rows; i ++) {
            this._gridData[i] = new Array(columns);
            if(i < data.length) {
                for(var j = 0; j < columns; j ++) {
                    if(j < data[i].length) {
                        this._gridData[i][j] = data[i][j];
                    }
                }
            }
        }
        this._setVerticalScrollHeight();
    },

    /**
     * Method used to get the name of the current dataGroup
     *
     * @returns {*|jQuery}
     * @private
     */
    _getDataGroupName: function() {
        return $('.jqs-grid-name').val();
    },

    /**
     * Method used to set the name of the current dataGroup
     *
     * @param groupName
     * @private
     */
    _setDataGroupName: function(groupName) {
        $('.jqs-grid-name').val(groupName);
    },

    /**
     * Buffer used to keep track of unsaved row data and their corresponding indices in gridData
     */
    _unsavedRowDataBuffer: {'indices':[], 'rowData': []},

    /**
     * Method used to clear the unsaved row data buffer
     * @param self
     * @private
     */
    _clearUnsavedRowDataBuffer: function(self) {
        self._unsavedRowDataBuffer = {'indices':[], 'rowData': []};
    },


    /**
     * Method used to add row data to the unsaved buffer
     *
     * @param rowIdx
     * @private
     */
    _addRowDataToUnsavedRowDataBuffer: function(rowIdx, skipSaveFlag) {

            var firstIndexInArray = this._firstIndexInArray(this._unsavedRowDataBuffer.indices, rowIdx);
            var idx = firstIndexInArray > -1 ? firstIndexInArray : this._unsavedRowDataBuffer.indices.length;
            if(!this._isArrayEmpty(this._gridData[rowIdx])) {
                var variableDataId = this._getRowIdValue(this._gridData[rowIdx][0]);
                if(variableDataId != '' && this._isArrayEmpty(this._getRowDataWithoutIdValue(this._gridData[rowIdx]))) {
                    if(firstIndexInArray > -1) {
                        this._unsavedRowDataBuffer.indices.splice(firstIndexInArray, 1);
                        this._unsavedRowDataBuffer.rowData.splice(firstIndexInArray, 1);
                    }
                    this._removeRowDataFromServer(variableDataId, rowIdx);
                } else {
                    this._unsavedRowDataBuffer.indices[idx] = rowIdx;
                    this._unsavedRowDataBuffer.rowData[idx] = this._removeNullsFrom1DArray(this._gridData[rowIdx]);
                }
            }
        if((typeof skipSaveFlag === 'undefined' || skipSaveFlag == false) && this._unsavedRowDataBuffer.rowData.length > 0) {
            this._saveDataGroup({}, this._clearUnsavedRowDataBuffer);
        }
        this._updateServiceCharge();
    },

    _savedDataGroupNames: {},

    _getSavedDataGroupNames: function(dialogFlag) {
        if(dialogFlag == true || Object.keys(this._savedDataGroupNames).length == 0) {
            var self = this;
            var params = 'clientId=' + this._getClientId$() + '&partyId=' + this._getPartyId$() + '&sessionId=' + this._getSessionId$();
            $.ajax({
                url: self.options.getVariableDataGroupNamesEndPoint,
                data: params,
                method: 'GET',
                dataType: 'json',
                async: false
            }).done(function (data) {
                if (data.success) {
                    self._savedDataGroupNames = data.dataGroups;
                }
            });
            if(!dialogFlag) {
                self._savedDataGroupNames['0'] = 'New AddressBook';
            }
        }

        return this._savedDataGroupNames;
    },

    _updateSavedDataGroupNames: function(groupId, groupName) {
        if(typeof groupName !== 'undefined' && groupName.trim() != '' && (typeof this._savedDataGroupNames[groupId] === 'undefined' || this._savedDataGroupNames[groupId] != groupName)) {
            this._savedDataGroupNames[groupId] = groupName;
            this._getDataGroup().name = groupName;
            this._renderSavedDataGroupNamesDropDown();
        }
    },

    _renderSavedDataGroupNamesDropDown: function() {
        var self = this;
        var dataGroupNamesDropDown = $('.jqs-data-group-drop-down');
        $(dataGroupNamesDropDown).html('');
        var savedDataGroupNames = this._getSavedDataGroupNames();
        for (prop in savedDataGroupNames) {
            dataGroupNamesDropDown.append($('<li />').attr('data-id', prop).html(savedDataGroupNames[prop]).on('click', function() {
                    // do stuff here
                    self._loadDataGroup($(this).attr('data-id'));
                })
            );
        }
    },

    /**
     * Client side gridData that stores the entire dataObject on the client side
     */
    _gridData: [[]],

    /**
     * Number of rows in the gridOverlay
     */
    _overlayRows: 0,

    /**
     * Getter method for _overlayRows
     *
     * @returns {number}
     * @private
     */
    _getOverlayRows: function() {
        return this._overlayRows;
    },

    /**
     * Setter method for _overlayRows
     *
     * This method can be used to set a specific value to _overlayRows or increment a specific value to the current value of _overlayRows
     *
     * @param _overlayRows
     * @param incrementerFlag
     * @private
     */
    _setOverlayRows: function(_overlayRows, incrementerFlag) {
        if(typeof incrementerFlag === 'boolean') {
            if(incrementerFlag == true) {
                this._overlayRows += _overlayRows;
            } else {
                this._overlayRows -= _overlayRows;
            }
        } else {
            this._overlayRows = _overlayRows;
        }
    },

    /**
     * Method used to reset _overlayRows
     *
     * @private
     */
    _resetOverlayRows: function() {
        this._overlayRows = 0;
    },

    /**
     * Number of columns in the gridOverlay
     */
    _overlayColumns: 0,

    /**
     * Getter method for _overlayColumns
     *
     * @returns {number}
     * @private
     */
    _getOverlayColumns: function() {
        return this._overlayColumns;
    },

    /**
     * Setter method for _overlayColumns
     *
     * This method can be used to set a specific value to _overlayColumns or increment a specific value to the current value of _overlayColumns
     *
     * @param _overlayColumns
     * @param incrementerFlag
     * @private
     */
    _setOverlayColumns: function(_overlayColumns, incrementerFlag) {
        if(typeof incrementerFlag === 'boolean') {
            if(incrementerFlag == true) {
                this._overlayColumns += _overlayColumns;
            } else {
                this._overlayColumns -= _overlayColumns;
            }
        } else {
            this._overlayColumns = _overlayColumns;
        }
    },

    /**
     * Method used to reset _overlayColumns
     * @private
     */
    _resetOverlayColumns: function() {
        this._overlayColumns = 0;
    },

    /**
     * The current rowOffSet of the DataGrid.
     *
     * It is based on this rowOffSet, the rowData from the underlying gridData[[]] 2D array corresponding to the scroll position is rendered
     * on the gridOverlay. So if we scroll down and the 50th row is the first row in the visible gridOverlay, the value of rowOffSet will be 50
     */
    _rowOffSet:1,
    _lastRowOffSet: 1,

    /**
     * Getter method for _rowOffSet
     *
     * @returns {number}
     * @private
     */
    _getRowOffSet: function() {
        return this._rowOffSet;
    },

    /**
     * Setter method for _rowOffSet
     *
     * This method assures that the passed in rowOffSet is not less than 1 and not greater than the total number of rows
     *
     * @param rowOffSet
     * @private
     */
    _setRowOffSet: function(rowOffSet) {
        //If the passed in rowOffSet is less than 1, then set it to 1
        if(rowOffSet < 1) {
            rowOffSet = 1;
        }
        //If the passed in rowOffSet is greater than the total number of rows in the GridData, set it as the last row in the GridData
        else if(rowOffSet >= this._currentDataRowCount()) {
            rowOffSet = this._currentDataRowCount();
        }

        this._rowOffSet = rowOffSet;
        this._lastRowOffSet = rowOffSet;
    },

    /**
     * The grid view(layout) type.
     *
     * Default 'list', which shows all attributes for a give rowData in a single row. The other option is 'tile'
     *
     * NOTE - Part of next version
     */
    _view:'list',

    /**
     * Getter method for _view
     *
     * @returns {string}
     * @private
     */
    _getView: function() {
        return this._view;
    },

    /**
     * Setter method for _view
     *
     * @param _view
     * @private
     */
    _setView: function(_view) {
        this._view = _view;
    },

    /**
     * The caret position of the activeCell text. This is required to set the caret position of the activeCell text after scrolling
     */
    _caretPosition: 'end',

    /**
     * Getter method for _caretPosition
     *
     * After scrolling
     *
     * @returns {string}
     * @private
     */
    _getCaretPosition: function() {
        return this._caretPosition;
    },

    /**
     * Setter method for _caretPosition
     *
     * @param caretPosition
     * @private
     */
    _setCaretPosition: function(caretPosition) {
        this._caretPosition = caretPosition;
    },

    /**
     * Reset _caretPosition
     * @private
     */
    _resetCaretPosition: function() {
        this._caretPosition = 'end';
    },

    /**
     * A 2D array based data structure to store indices of selected rows, columns or cells. The first array stores the row indices and the second
     * array stores the column indices of the selected cells. If one or more rows are selected, then the second array will be empty. If
     * one or more columns are selected, then the first array will be empty. If one or more adjacent cells are selected, then both arrays
     * will have the index values for the selected cells rows and column indices.
     */
    _selectedCells: [[],[]],

    /**
     * Getter method for _selectedCells
     *
     * @returns {*}
     * @private
     */
    _getSelectedCells: function() {
        return this._selectedCells;
    },

    /**
     * Method used to add row and column indices to _selectedCells. If _selectedCells already has row and cell indices, then the new index
     * values will be concatenated to the existing one to support continuous selection by click and dragging.
     *
     * @param rowIndices
     * @param columnIndices
     * @private
     */
    _setSelectedCells: function(rowIndices, columnIndices) {
        if(rowIndices !== null && columnIndices !== null) {
            this._selectedCells = [this._selectedCells[0].concat(rowIndices), this._selectedCells[1].concat(columnIndices)];
        } else if(rowIndices !== null) {
            this._selectedCells = [this._selectedCells[0].concat(rowIndices), this._selectedCells[1]];
        } else if(columnIndices !== null) {
            this._selectedCells = [this._selectedCells[0], this._selectedCells[1].concat(columnIndices)];
        }
    },

    /**
     * Method used to reset _selectedCells
     * @private
     */
    _resetSelectedCells: function() {
        this._selectedCells = [[],[]];
        this._resetCopiedCells();
        this._resetCaretPosition();
    },

    /**
     * A 2D array based data structure for an internal clipboard to store indices of copied rows, columns or cells.
     */
    _copiedCells: [[],[]],

    /**
     * Getter method for _copiedCells
     *
     * @returns {*}
     * @private
     */
    _getCopiedCells: function() {
        return this._copiedCells;
    },

    /**
     * Setter method for _copiedCells
     *
     * @param selectedCells
     * @private
     */
    _setCopiedCells: function(selectedCells) {
        this._copiedCells = selectedCells;
    },

    /**
     * Method used to reset _copiedCells
     * @private
     */
    _resetCopiedCells: function() {
        this._copiedCells = [[], []];
        $('.jqs-copied').removeClass('jqs-copied');
    },

    /**
     * Method used to find if there are copied cells in the internal clipboard
     * @returns {boolean}
     * @private
     */
    _hasCopiedCells: function() {
        return this._copiedCells[0].length > 0 || this._copiedCells[1].length > 0;
    },

    /**
     * The row and column index of the active cell
     */
    _activeCell: [0, 0],

    /**
     * Getter method for _activeCell
     *
     * @returns {*}
     * @private
     */
    _getActiveCell: function() {
        return this._activeCell;
    },

    /**
     * Setter method for _activeCell
     *
     * @param _activeCell
     * @private
     */
    _setActiveCell: function(_activeCell) {
        if(this._activeCell.length == 4) {
            this._activeCell = [_activeCell[0], _activeCell[1], this._activeCell[2], this._activeCell[3]];
        } else {
            this._activeCell = _activeCell;
        }
    },

    /**
     * Method used to reset _activeCell
     * @private
     */
    _resetActiveCell: function() {
        if(this._activeCell.length == 4) {
            this._activeCell = [0, 0, this._activeCell[2], this._activeCell[3]];
        } else {
            this._activeCell = [0, 0];
        }
        this._makeCellActive();
    },

    /**
     * Boolean flag used to track if we are in column mapping mode or not
     */
    _mappingMode: false,

    /**
     * Getter method for _mappingMode
     *
     * @returns {boolean}
     * @private
     */
    _isMappingMode: function() {
        return this._mappingMode;
    },

    /**
     * Setter method for _mappingMode
     *
     * @private
     */
    _setMappingMode: function() {
        this._mappingMode = true;
        $('.jqs-grid-name').attr('readonly', 'readonly');
        this._setToolbarButtonsState();
        this._setMapColumnsToolbarButtonState();
        this._hideServiceCharge();
    },

    /**
     * Method used to reset _mappingMode
     *
     * @private
     */
    _resetMappingMode: function() {
        this._mappingMode = false;
        $('.jqs-grid-name').removeAttr('readonly');
        this._setToolbarButtonsState();
        this._setMapColumnsToolbarButtonState();
        this._showServiceCharge();

    },

    /**
     * Boolean flag to bypass the scrollTo event
     */
    _ignoreScroll: false,

    /**
     * Getter method for _ignoreScroll
     *
     * @returns {boolean}
     * @private
     */
    _isIgnoreScroll: function() {
        return this._ignoreScroll;
    },

    /**
     * Setter method for _ignoreScroll
     *
     * @private
     */
    _setIgnoreScroll: function() {
        this._ignoreScroll = true;
    },

    /**
     * Method for resetting _ignoreScroll
     *
     * @private
     */
    _resetIgnoreScroll: function() {
        this._ignoreScroll = false;
    },

    /**
     * The method equivalent to the widget constructor
     * @private
     */
    _create: function () {
        if($('.texelFileUploadForm').length == 0) {
            this.element.parent().append('<div style="position:absolute; top:-100px;"><form class="texelFileUploadForm" id="texelFileUploadForm" action="' + this.options.fileUploadEndpoint + '" method="post" enctype="multipart/form-data"><input type="file" name="addressFile" id="addressFileButton"><input type="hidden" name="hasHeaderColumn" id="hasHeaderColumn"</form></div>');
        }
        $(window).scrollTop(0);
        this._bindDataGroup();
        this._setGridOverlayRows();
        this._renderGridOverlay();
        this._initializeGridData();
        this._initGridData();
        this.resizeGridOverlay();

    },

    /**
     * Method used to render the grid overlay. The grid overlay is a fixed size grid UI with a specific number of rows and columns.
     * This method will render the grid overlay, toolbar, bind all the events for the toolbar buttons, bind the context menu and
     * all default events to the grid overlay for interacting with the actual GridData.
     * @private
     */
    _renderGridOverlay: function() {
        var self = this;
        this.element.empty()
            .append(this._buildToolbarElement())
            .append($('<div/>').addClass('env-grid_container')
                .append($(this.options.contextMenu))
                .append('<div class="env-grid_topLeft"></div>')

                .append(this._generateColumnHeaderRowHTML())
                .append(this._generateRowNumberColumnHTML()))
            .append(this._buildScrollBarElement())
            .append($('<div/>').addClass('footer-container')
                .append($('<div/>').addClass('left-side-container')
                    .append($('<div/>').addClass('jqs-upload-file grid-btn-cta margin-left-xs uploadAddressList').html('<i class="fa fa-upload"></i>  Upload Address List  <i class="fa fa-caret-right"></i>')
                    )
                )
                //.append('<div class="middle-container"><table class="addressTable"><tbody><tr><td>Number of Addresses</td><td>1-99</td><td>100-199</td><td>200-299</td><td>300-499</td><td>500-999</td><td>1000+</td></tr><tr><td>Rate per Address</td><td>$1.15</td><td>$0.87</td><td>$0.75</td><td>$0.55</td><td>$0.48</td><td>$0.38</td></tr></tbody></table></div>')
                .append($('<div/>').addClass('right-side-container')
                    .append($('<span/>').addClass('jqs-cancel grid-btn grid-btn-regular').text('Cancel')
                        .on('click',
                            function() {
                                if(self._isMappingMode()) {
                                    self._cancelColumnMapping();
//                                    self.hideGrid();//TODO 
                                } else {
                                    self.closeEvent();
                                    self.hideGrid();
                                }

                            }
                        )
                    )
                    .append($('<span/>').addClass('jqs-save-for-later grid-btn grid-btn-cta2' + (self._getPartyId$() != '' ? ' grid-hide' : '')).text('Save for Later')
                        .on('click',
                            function() {
                                //Get the value from the active cell overlay
                                var cellValue = $($('.env-grid_active_cell')[0]).find('.jqs-editable').text();

                                //Save the already active cell value.
                                self._saveCellData1(self._getActiveCell()[0], self._getActiveCell()[1], cellValue);
                                self._addRowDataToUnsavedRowDataBuffer(self._getActiveCell()[0], true);
                                self._saveDataGroup({},
                                    function () {
                                        self.saveVariableDataGroupsToAccount();
                                    }
                                );
                            }
                        )
                    )
                    .append($('<span/>').addClass('jqs-apply-to-design grid-btn grid-btn-cta').text((this.options.usesDesign ? 'Apply to Design' : 'Apply'))
                        .on('click',
                            function() {
                                if($($('.env-grid_active_cell')[0]).find('.jqs-editable').length > 0) {
                                    //Get the value from the active cell overlay
                                    var cellValue = $($('.env-grid_active_cell')[0]).find('.jqs-editable').text();

                                    //Save the already active cell value.
                                    self._saveCellData1(self._getActiveCell()[0], self._getActiveCell()[1], cellValue);
                                    self._addRowDataToUnsavedRowDataBuffer(self._getActiveCell()[0], true);
                                }
                                var gridData = self.getFilteredGridData(true);
                                if(gridData.length == 0) {
                                    alert('Please enter addressing data before applying to the design');
                                    //this._showDialog('emptyData', 'Warning', 'Please enter addressing data before applying to the design');
                                } else {
                                    if (typeof triggerAddressing == 'function') { triggerAddressing(); }
                                    $('#texAddAddressing').addClass('jqs-hasdata').removeAttr('data-reveal-id');

                                    self._saveDataGroup({},
                                        function () {
                                            self.applyEvent();
                                            self.hideGrid();
                                        }
                                    );
                                }
                            }
                        )
                    )
                    .append($('<span style="display:none"/>').addClass('jqs-finish-mapping grid-btn grid-btn-cta').text('Finish Mapping')
                        .on('click',
                            function() {
                                if($('.jqs-co1umn-mapper option[value!=""]:selected').length > 0) {
                                    self.hideColumnMapper();
                                } else {
                                    alert("Please map the fields that matches your data ");
                                    //self._showDialog('mapColumns', 'Warning', 'Please map the fields that matches your data ');
                                }

                            }
                        )
                    )
                    .append('<div class="jqs-addressing-service-charge" style="font-size: 20px;margin: 10px 5px 0px 0px;">Addressing Service Charge: <span class="jqs-addressing-fee" style="color: #FC7E22;">$0.00</span></div>')
                )
            )
            .append($('<div />').addClass('jqs-grid-shadowed-background gridShadowedBackground grid-hide'));

        $('.texelFileUploadForm').find('#addressFileButton').on('change', function(e) {
            var fileName = e.target.files[0].name;
            if(typeof fileName === 'undefined' || typeof fileName == '' || fileName == null) {
                fileName = $(this).val().split('\\').pop();
            }
            self.uploadedFileChangeEvent(fileName);
            self._uploadedFileName = fileName;
        });
        $('#texelFileUploadForm').on('submit', function(e) {
            e.preventDefault();
            if($('#jqs-has-column-header:checked').val() == 'true') {
                $('#hasHeaderColumn').val('true');
            }
            var form = e.target;
            var formData = new FormData(form);

            $.ajax({
                url: form.action,
                method: form.method,
                processData: false,
                contentType: false,
                data: formData,
                dataType: 'json'
            }).done(function(data) {

                if(data.success) {
                    $('.texelFileUploadForm').find('#addressFileButton').val('');
                    self.options.dataGroupMode = '';
                    self._hideFileUpload();
                    self.uploadGridData(data.variableData, true);
                    self._showHintBalloon($('.env-grid_container .hint-start-mapping'), 1000);
                } else {
                    var message = data.message;
                    if(message == 'EMPTY_SHEET') {
                        alert('The uploaded file has no address data in it.')
                    } else if(message == 'TOO_MANY_COLUMNS') {
                        alert('You can upload files with a maximum of 100 columns and found more than 100 columns.')
                    }
                }
            });
        });


        this._addDefaultColumnHeader();
        this._addRowsToGridOverlay(this._getGridOverlayRows());
        // Row, Column and Cell sections - Start
        /*$( ".env-grid_left" ).selectable({
         filter: '.jqs-row-number',
         selecting: function() {
         var overlayRowIndex = $( ".ui-selecting:first", this).index();
         var dataRowIndex = self._getDataRowIndex(overlayRowIndex);
         var rowsSelected = $( ".ui-selecting", this).length;
         if(dataRowIndex >= 0) {
         //                    self.selectRow(dataRowIndex, rowsSelected);
         }
         },
         unselecting: function() {
         var overlayRowIndex = $( ".ui-selecting:first", this).index();
         var dataRowIndex = self._getDataRowIndex(overlayRowIndex);
         var rowsSelected = $( ".ui-selecting", this).length;
         //                self.selectRow(dataRowIndex, rowsSelected);
         }

         });*/

        /*$( ".env-grid_top" ).selectable({
         filter: '.jqs-header-co1umn',
         selecting: function() {
         var overlayColumnIndex = $( ".ui-selecting:first", this).index();
         var dataColumnIndex = self._getDataColumnIndex(overlayColumnIndex);
         var columnsSelected = $( ".ui-selecting", this).length;
         if(dataColumnIndex >= 0) {
         //                    self.selectColumn(dataColumnIndex, columnsSelected);
         }
         },
         unselecting: function() {
         var overlayColumnIndex = $( ".ui-selecting:first", this).index();
         var dataColumnIndex = self._getDataColumnIndex(overlayColumnIndex);
         var columnsSelected = $( ".ui-selecting", this).length;
         if(dataColumnIndex >= 0) {
         //                    self.selectColumn(dataColumnIndex, columnsSelected);
         }
         }

         });*/
        self._makeGridCellsSelectable();
        self._makeColumnsResizable();
        // Row, Column and Cell sections - End


    },

    siteLoginCallback: function(obj) {
        $('#env-variabledata-grid').VariableDataGrid('saveVariableDataGroupsToAccount', obj.partyId);
    },

    siteLoginCallbackToLoadVariableDataGroups: function(obj) {
        $('#env-variabledata-grid').VariableDataGrid('loadAllVariableDataGroups', obj.partyId);
    },

    _showExistingDataGroups: function() {
        var self = this;
        var isLoggedIn = self._getPartyId$() != '';
        this._hideExistingDataGroups();
        var popup = this._createPopup('existingGroups', (isLoggedIn != '' ? 'loggedin ' : '') + 'dialog');
        $(popup).append(
            $('<div />').addClass('gridPopupInner').append(
                $('<h5 />').addClass('gridPopupHeader').html('Existing Address Books')
            ).append(self._buildExistingDataGroupsDialogBody(isLoggedIn))
            .append($('<div />').addClass('gridCloseButton').on('click', function(){self.options.dataGroupMode = '';self._hideExistingDataGroups();}))
        );
        if(isLoggedIn == true) {
            $('.jqs-save-for-later').addClass('grid-hide');
        }
        $('#jqs-grid-popup-existingGroups').css('height', $('#jqs-grid-popup-existingGroups').height() + 10 + 'px');
    },
    _buildExistingDataGroupsDialogBody: function(isLoggedIn) {
        var self = this;
        var savedDataGroupNames = this._buildSavedDataGroupNamesDropDown(true, isLoggedIn);
        var elem = $('<div style="text-align: center; width:400px"/>').addClass('gridPopupBody');
        var hasSavedDataGroups = false;
        if(savedDataGroupNames != '') {
            $(elem).append($('<div style="text-align: left"/>').append('<h5>Choose an Address Book:</h5>').append(savedDataGroupNames));
            if(isLoggedIn == false) {
                $(elem).append($('<div style="margin-top:30px"/>'));
            }
            hasSavedDataGroups = true;
        }

        if(isLoggedIn == false) {
            $(elem).append('<h6>Login to your account to see saved Address Books:</h6>')
                   .append($('<div>').css('text-align', 'center').append($('<span/>').css('width', '200px').addClass('jqs-grid-login grid-btn grid-btn-cta').text('Log In').on('click', function(){self.loadAllVariableDataGroups()})));

        }

        if(hasSavedDataGroups == false) {
            if(isLoggedIn == false) {
                $(elem).append($('<div style="margin-top:30px"/>'));
            }
            $(elem).append(isLoggedIn == false ? '<h6>Or click below to create a New Address Book:</h6>' : '<h6>No existing Address Books found, click below to create a New Address Book:</h6>')
                .append($('<div>').css('text-align', 'center').append($('<span/>').css('width', '200px').addClass('jqs-grid-login grid-btn grid-btn-cta2').text('New Address Book').on('click', function(){self._hideExistingDataGroups()})));
        }
        return elem;
    },

    loadAllVariableDataGroups: function(partyId) {
        if(typeof partyId !== 'undefined') {
            this._setPartyId$(partyId);
        }
        if(this._getPartyId$() != '') {
            this._showExistingDataGroups();
        } else {
            this._hideExistingDataGroups();
            loginCallback(this.siteLoginCallbackToLoadVariableDataGroups);
        }
    },

    _hideExistingDataGroups: function() {
        this.element.find('#jqs-grid-popup-existingGroups').remove();
        this.element.find('.jqs-grid-shadowed-background').addClass('grid-hide');
        $('.jqs-editable').focus();
        this._setCaret('end');
    },

    _showFileUpload: function() {
        var self = this;
        this._hideFileUpload();
        var popup = this._createPopup('fileUpload', 'dialog upload');
        $(popup).append(
            $('<div />').addClass('gridPopupInner').append(
                $('<h5 />').addClass('gridPopupHeader').html('Upload Address Data')
            ).append(
                $('<div />').addClass('gridPopupBody').html('<h5>Upload the Address Data File:</h5>').append(this._buildFileUploadUI())
            ).append(
            	$('<div />').addClass('gridCloseButton').on('click', function(){self._hideFileUpload();})
            )
        )
    },

    _showDialog: function(id, title, message, actions) {
        var self = this;
        var popup = this._createPopup(id, 'dialog');
        $(popup).append(
            $('<div />').addClass('gridPopupInner').append($('<h5 />').addClass('gridPopupHeader').html(title))
                .append($('<div />').addClass('gridPopupBody').html('<h6>' + message + '</h6>'))
                .append($('<div/>').css('text-align', 'center').css('padding', '10px 40px 10px')
                    .append($('<span/>').addClass('grid-btn grid-btn-cta').html('OK').on('click', function() {self._closeDialog(id);}))
                )
                .append($('<div />').addClass('gridCloseButton').on('click', function(){self._closeDialog(id);}))
        );
        $('#jqs-grid-popup-' + id).css('height', $('#jqs-grid-popup-' + id).height() + 10 + 'px');
    },

    _closeDialog: function(id) {
        this.element.find('#jqs-grid-popup-' + id).remove();
        this.element.find('.jqs-grid-shadowed-background').addClass('grid-hide');
        $('.jqs-editable').focus();
        this._setCaret('end');
    },

    _buildFileUploadUI: function() {
        var fileUploadUI = $('<div style="width:400px"/>')
                                .append($('<div/>')
                                    .append('<input id="jqs-address-data-file" type="text" style="width:70%;display:inline-block"/>')
                                    .append($('<span/>').addClass('jqs-choose-file-btn grid-btn grid-btn-cta').html('Choose File').off('click').on('click', function() {
                                                                                                                                                $('.texelFileUploadForm').find('#addressFileButton').trigger('click');
                                                                                                                                            }))
                                )
                                .append($('<div style="position: relative;top: -8px;left:55px; disp"/>')
                                    .append('<input id="jqs-has-column-header" value="true" readonly="readonly" type="checkbox" checked ="checked"/><span style="padding-left:10px">First row contains column names</span>')
                                )
                                .append($('<div/>').css('text-align', 'right')
                                    .append($('<span/>').addClass('jqs-upload-btn grid-btn').html('Upload').off('click').on('click', function() {$('#texelFileUploadForm').submit();}))
                                );
        return fileUploadUI;
    },

    _hideFileUpload: function() {
        this.element.find('#jqs-grid-popup-fileUpload').remove();
        $('.texelFileUploadForm').find('#addressFileButton').val('');
        this.element.find('.jqs-grid-shadowed-background').addClass('grid-hide');
        $('.jqs-editable').focus();
        this._setCaret('end');
    },


    _showProgressBar: function(progressBarId, percentage) {
        this._hideProgressBar();
        var popup = this._createPopup(progressBarId);
        $(popup).append($('<div />').attr('id', 'jqs-grid-progressbar-' + progressBarId).addClass('meter').append($('<span />').addClass('jqs-progress').css({'width': percentage + '%'})));

    },

    _updateProgressBar: function(progressBarId, percentage, hideOnComplete) {
        this.element.find('#jqs-grid-progressbar-' + progressBarId + ' .jqs-progress').css({'width':  percentage + '%'});
        if(hideOnComplete == true && percentage == 100) {
            this._hideProgressBar(progressBarId);
        }
    },

    _hideProgressBar: function(progressBarId) {
        this.element.find('#jqs-grid-popup-' + progressBarId).remove();
        this.element.find('.jqs-grid-shadowed-background').addClass('grid-hide');
    },

    _createPopup: function(popupId, classes) {
        this.element.find('.jqs-grid-shadowed-background').parent().append($('<div />').attr('id', 'jqs-grid-popup-' + popupId).addClass('jqs-grid-popup-content gridPopupContent' + (typeof classes !== 'undefined' ? ' ' + classes : '')));
        this.element.find('.jqs-grid-shadowed-background').removeClass('grid-hide');
        return this.element.find('#jqs-grid-popup-' + popupId);
    },

    /**
     * Method used to make the grid overlay cells selectable. This
     * @private
     */
    _makeGridCellsSelectable: function() {
        var self = this;
        $( ".env-grid_container" ).selectable({
            filter: '.env-grid_co1umn',
            appendTo: '.gridOverlayBg',
            selecting: function() {
                $('.jqs-addr-drop-down').hide();
                var firstOverlayCell = $( ".ui-selecting:first", this);
                var firstDataCellIndex = [self._getDataRowIndex($(firstOverlayCell).parent().index('.env-grid_row')), self._getDataColumnIndex($(firstOverlayCell).index())];
                var lastOverlayCell = $( ".ui-selecting:last", this);
                var lastDataCellIndex = [self._getDataRowIndex($(lastOverlayCell).parent().index('.env-grid_row')), self._getDataColumnIndex($(lastOverlayCell).index())];
//                waitForFinalEvent(function() {self.selectCell(firstDataCellIndex, lastDataCellIndex);}, 50, 'addressing-grid-selection');
//                self.selectCell(firstDataCellIndex, firstDataCellIndex);
            },
            unselecting: function() {
                var firstOverlayCell = $( ".ui-selecting:first", this);
                var firstDataCellIndex = [self._getDataRowIndex($(firstOverlayCell).parent().index('.env-grid_row')), self._getDataColumnIndex($(firstOverlayCell).index())];
                var lastOverlayCell = $( ".ui-selecting:last", this);
                var lastDataCellIndex = [self._getDataRowIndex($(lastOverlayCell).parent().index('.env-grid_row')), self._getDataColumnIndex($(lastOverlayCell).index())];
//                waitForFinalEvent(function() {self.selectCell(firstDataCellIndex, lastDataCellIndex);}, 100, 'addressing-grid-selection');
            },
            selected: function() {
                var firstOverlayCell = $( ".ui-selected:first", this);
                var firstDataCellIndex = [self._getDataRowIndex($(firstOverlayCell).parent().index('.env-grid_row')), self._getDataColumnIndex($(firstOverlayCell).index())];
                self.selectCell(firstDataCellIndex, firstDataCellIndex);

            }
        });
    },

    _makeColumnsResizable: function() {
        var self = this;
        $('.jqs-header-co1umn').resizable({
            handles: 'e',
            resize: function( event, ui ) {
                var width = $(ui.element).width();//ui.size.width;
                var columnIndex = ui.element.index();
                if($('.jqs-editable').parent().parent().index() == columnIndex) {
                    $('.jqs-editable').css('width', width - 4 + 'px');
                    $('.jqs-editable').css('max-width', width - 4 + 'px');
                }
                $('.env-grid_row .env-grid_co1umn:nth-child(' + (columnIndex + 1) + ')').css('width', width + 10 + 'px');
                self._activeCell = [self._activeCell[0], self._activeCell[1], width - 4, columnIndex];
            }
        });
    },

    _checkReadOnlyMode: function(e) {
        var isMappingMode = this._isMappingMode();
        if(isMappingMode) {
            alert('The grid is read only and you need to finish Column Mapping in order to make the grid editable');
            //this._showDialog('readOnly', 'Warning', 'The grid is read only and you need to finish Column Mapping in order to make the grid editable');
            e.preventDefault();
            return false;
        }
    },

    /**
     * Builds Drop Down after the tool bar element is built
     */
    _loadDropdowns: function() {
        var self = this;
        $(document).off('click.addr-drop-down').on('click.addr-drop-down', function(e) {
            if ($(e.target).attr('data-dropdown-active') != 'true') {
//                if(self.options.dataGroupMode != 'existing') {
                    $('.jqs-addr-drop-down').hide();
                    $('[data-dropdown-active="true"]').each(function() {
                        $(this).attr('data-dropdown-active', 'false');
                    });
//                }
            }
        });

        $('.jqs-addr-drop-down').each(function() {
            var dropdown = $(this);
            if (dropdown.attr('data-dropdown-live') != 'true') {
                var parent = $('#' + dropdown.attr('data-dropdown-parent'));
                dropdown.attr('data-dropdown-live', 'true');

                dropdown.css({
                    top: parent.offset().top - dropdown.offset().top + parseFloat(parent.outerHeight()),
                    left: parent.offset().left - dropdown.offset().left,
                    width: parseFloat(parent.outerWidth()),
                    height: 'auto',
                    visibility: 'visible',
                    display: 'none'
                });
                /*if(self.options.dataGroupMode == 'existing') {
                    dropdown.show();
                    dropdown.attr('data-dropdown-active', 'true');
                    parent.attr('data-dropdown-active', 'true');
                }*/
                parent.on('click.addr-drop-down', function() {
//                    if(self.options.dataGroupMode != 'existing') {
                        dropdown.toggle();
                        dropdown.attr('data-dropdown-active', 'true');
                        parent.attr('data-dropdown-active', 'true');
//                    }
                });
            }
        });
    },

    _hideDropdown: function() {

    },

    _buildSavedDataGroupNamesDropDown: function(dialogFlag, refreshDropdownFlag, savedDataGroupNames) {
        var self = this;
        if(typeof savedDataGroupNames === 'undefined') {
            savedDataGroupNames = this._getSavedDataGroupNames(dialogFlag);
        }
        if(typeof dialogFlag !== 'undefined' && dialogFlag == true) {
            var dataGroupNamesDropDown = $('<ul />').addClass('selectListContainer');
            var hasSavedDataGroups = false;
            for (prop in savedDataGroupNames) {
                dataGroupNamesDropDown.append($('<li />').attr('data-id', prop).addClass('selectList').html(savedDataGroupNames[prop]).on('click', function() {
                        self._loadDataGroup($(this).attr('data-id'));
                        self.options.dataGroupMode = '';
                        self._hideExistingDataGroups();
                    })
                );
                hasSavedDataGroups = true;
            }
            if(typeof refreshDropdownFlag !== 'undefined' && refreshDropdownFlag == true) {
                $('.jqs-addr-drop-down').html('').append(self._buildSavedDataGroupNamesDropDown(false, false, savedDataGroupNames));
            }
            return hasSavedDataGroups ? dataGroupNamesDropDown : '';
        } else {
            var dataGroupNamesDropDown = $('<ul />').addClass('jqs-data-group-drop-down texelGridList');
            for (prop in savedDataGroupNames) {
                dataGroupNamesDropDown.append($('<li />').attr('data-id', prop).html(savedDataGroupNames[prop]).on('click', function() {
                        self._loadDataGroup($(this).attr('data-id'));
                        self.options.dataGroupMode = '';
                    })
                );
            }
            return dataGroupNamesDropDown;
        }

    },

    /**
     * Method used to build the top toolbar DOM element and its buttons. This method will bind the events for all the buttons on the toolbar
     * @returns {*|jQuery} Return the complete toolbar DOM element with all the buttons bound with their corresponding events.
     * @private
     */
    _buildToolbarElement: function() {
        var self = this;
        var isMappingMode = this._isMappingMode();
        return $('<div/>').addClass('env-grid_toolbar')
            .append(
            $('<div/>').addClass('tbr-btn-group').append(
                $('<div/>').addClass('tbr-button')
            ).append(
                $('<input/>').attr('id', 'env-grid-name-input').addClass('tbr-name-textBox jqs-grid-name').attr("spellcheck",false).on('keydown', function(){$('.jqs-addr-drop-down').hide();}).on('click', function(e) {self._checkReadOnlyMode();}).on('blur', function(element) {self.saveDataGroupName($(this).val());})
            ).append(
                $('<div />').addClass('jqs-addr-drop-down data-groups').attr('data-dropdown-parent', 'env-grid-name-input').append(self._buildSavedDataGroupNamesDropDown())
            )
        )
            .append($('<div/>').addClass('tbr-btn-group')
                .append($('<div/>').addClass('jqs-upload-file grid-btn-cta uploadAddressListMini').html('<i class="fa fa-upload"></i>'))
                //.append($('<div style="display: inline-block;position: relative; top: -7px; display: none"/>').append($('<input style="width: 300px"/>').attr('readonly', 'readonly')).append($('<input type="checkbox" style="position: relative; top: 3px"/>')).append($('<label/>').text('Check this if the uploading file has column headers')).append($('<button/>').text('Upload')).append($('<button/>').text('Cancel')))
        )
            .append($('<div/>').addClass('tbr-btn-group')
                /*.append($('<div/>').addClass('jqs-add-column tbr-button').append($('<img src="/html/addressing/img/addColumn.png"/>').attr('title', 'Add Column at End')))*/
                /*.append($('<div/>').addClass('jqs-add-rows tbr-button disabled').append($('<img src="/html/addressing/img/addRow.png"/>').attr('title', 'Add Rows at Bottom')))*/
                /*.append($('<div/>').addClass('jqs-map-column tbr-button grid-hide disabled').append($('<img src="/html/addressing/img/mapColumns.png"/>').attr('title', 'Map Columns'))
            )*/
        );
    },

    _setMapColumnsToolbarButtonState: function() {
        /*var self = this;
        if(!this.isGridEmpty() && !this._isMappingMode()) {
            $('.tbr-button.jqs-map-column').removeClass('disabled').off('click').on('click', function() {self.showColumnMapper();});
        } else {
            $('.tbr-button.jqs-map-column').addClass('disabled').off('click');
        }*/
    },

    _setToolbarButtonsState: function() {
        var self = this;
        if(this._isMappingMode()) {
            $('.tbr-button').addClass('disabled').off('click');
        } else {
            if(this.getFilteredGridData(true).length == 0) {
                $('.jqs-upload-file').removeClass('disabled').off('click').on('click', function() {self._showFileUpload()});
            } else {
                $('.jqs-upload-file').addClass('disabled').off('click');
            }
//            $('.tbr-button.jqs-add-column').removeClass('disabled').off('click').on('click', function() {self.addColumns();});
        }
    },

    /**
     * Method used to build the top column header row HTML. This method only generate the container div, not the individual header cells for the columns.
     * The _addDefaultColumnHeader() method will create the individual header cells for each rows
     *
     * @returns {string} Returns the column header HTML string
     *
     * @private
     */
    _generateColumnHeaderRowHTML: function() {
        return '<div class="env-grid_top"></div>';
    },

    /**
     * Method used to build the left most row number column HTML. This method only generate the container div, not the individual row number cells for the rows.
     * The _addRow() method will create the row number cell using the _addRowNumberCell() method
     *
     * @returns {string} Returns the row number column HTML string
     *
     * @private
     */
    _generateRowNumberColumnHTML: function() {
        return '<div class="env-grid_left"></div>';
    },

    /**
     * Method used to build the custom vertical scrollbar
     *
     * @returns {*|jQuery} - Returns the Scrollbar element with customized scroll event
     * @private
     */
    _buildScrollBarElement: function() {
        var self = this;
        var vScrollbarHeight = (this._getGridOverlayRows() * this._getCellHeight()) + 21;
        var vScrollbarBottom = vScrollbarHeight + 11;
        return $('<div class="env-grid_vertical_scrollbar"/>').css('height', vScrollbarHeight + 'px').css('bottom', vScrollbarBottom + 'px')
            .on('scroll', function() {
                self.scrollEvent();
            })
            .append($('<div/>').addClass('jqs-vertical-scroll-height'));
    },

    /**
     * Method used to find the scroll factor based on the scroll height.
     * @returns {Number} - The scroll factor
     * @private
     */
    _getScrollFactor: function() {
        return /*this._gridData.length < 700 ? 19.95 : */20.00;
    },

    /**
     * Method used to build the initial header column cells. This method will only be called when the grid is rendered initially.
     *
     * @private
     */
    _addDefaultColumnHeader: function() {
        var columnTitles = this._getStringArray(this._getColumnTitles());
        for(var i = 0; i < this._getGridOverlayColumns() && i < this._getGridOverlayMaxColumns(); i ++) {
            this._addColumnHeaderCell(i, columnTitles.length > i ? columnTitles[i] : '');
        }
    },

    /**
     * Method used to build the column header cell at the given index. When a new column is added, this method will always be called and so this method
     * will call the _overlayColumnCreated() event to increment the column count.
     *
     * @param columnIndex - The index where the header cell needs to be created
     * @param columnTitle - [OPTIONAL] The title that should be used for this column header cell. If not present, the alphabet corresponding to
     *                                the column index will be used (A, B, C ...)
     * @private
     */
    _addColumnHeaderCell: function(columnIndex, columnTitle) {
        var self = this;
        var html = '';
        if (typeof columnTitle !== 'undefined' && columnTitle != '') {
            html += '<div class="jqs-header-co1umn">' + columnTitle + '</div>';
        } else {
            html += '<div class="jqs-header-co1umn">' + this._getDefaultColumnTitle(columnIndex) + '</div>';
        }

        if(columnIndex == 0) {
            if(this._getOverlayColumns() == 0) {
                $('.env-grid_top').append($(html)/*.on('click', function() {self.selectColumn($(this).index());})*/);
            } else {
                $('.env-grid_top div.jqs-header-co1umn:eq(' + columnIndex + ')').before($(html)/*.on('click', function() {self.selectColumn($(this).index());})*/);
            }
        } else {
            $('.env-grid_top div.jqs-header-co1umn:eq(' + (columnIndex - 1) + ')').after($(html)/*.on('click', function() {self.selectColumn($(this).index());})*/);
        }

        this._overlayColumnCreated();

    },

    /**
     * Method used to remove the header column cell for the given column index. This method will trigger the overlay column removed event as well, which will decrement
     * the number of columns (_overlayColumns).
     *
     * @param columnIndex
     * @private
     */
    _removeColumnHeaderCell: function(columnIndex) {
        $('.env-grid_top .jqs-header-co1umn:eq(' + columnIndex + ')').remove();

        this._overlayColumnRemoved();
    },

    /**
     * Method used to generate the default column title based on the columnIndex. (A, B, C......, Z, AA, AB, etc)
     *
     * @param columnIndex The index of the column
     * @returns {string} - Returns the column title corresponding to the given index
     *
     * @private
     */
    _getDefaultColumnTitle: function(columnIndex) {
        var title = String.fromCharCode(65 + columnIndex % 26);
        if(columnIndex > 25) {
            title = String.fromCharCode(64 + parseInt(columnIndex / 26)) + title;
        }
        return title;
    },

    /**
     * Method used to add empty rows to the grid overlay. This method will add 'this._getGridOverlayRows()' number of rows up to the max ('this._getGridOverlayMaxRows()')
     * allowed for the grid overlay. This method will only be called while rendering the grid overlay by _renderGridOverlay() method. Once this method is finished it is
     * not possible to add additional rows to the grid overlay.
     *
     * @param numberOfRows - The number of rows that needs to be generated
     * @private
     */
    _addRowsToGridOverlay: function(numberOfRows) {
        var self = this;
        for(var i = 0; i < numberOfRows; i ++) {
            if(this._getOverlayRows() + i >= this._getGridOverlayMaxRows()) {
                return i;
            }
            this._addRowToGridOverlay();
        }
        $('.env-grid_container').append($('<div/>').addClass('jqs-grid-bottom grid-hide')
                .append($('<div/>').append($('<span style="background: #808080"/>').addClass('jqs-add grid-btn grid-btn-regular').text('Add')
                .on('click',
                function() {
                    self.addRows();
                }
            )
        )
            .append($('<span style="position:relative; top: 2px; left: 7px;"/>').text('100 more rows at bottom'))));
    },

    /**
     * Method used to add a single empty row to the gridOverlay. This method will add 'this._getOverlayColumns()' cells to the row as well
     *
     * @private
     */
    _addRowToGridOverlay: function() {
        this._addRowNumberCellToGridOverlay();
        var rowElement = $('<div/>').addClass('env-grid_row');
        $.each(this._buildOverlayRowCells(this._getOverlayColumns()), function(index, cellElement) {
            $(rowElement).append(cellElement);
        });
        $('.env-grid_container').append($(rowElement));

        this._overlayRowCreated();
    },

    /**
     * Method used to add the row number cell
     * @private
     */
    _addRowNumberCellToGridOverlay: function() {
        var self = this;
        var html = '<div class="jqs-row-number">' + (this._getOverlayRows() + 1) + '</div>';
        var rowNum = '';
        var rowIndex = this._getOverlayRows();
        $('.env-grid_left')
            .append($(html)
                .hover(
                    function(){
                        rowNum = $(this).html();
                        $(this).empty()
                            .append($('<img class="delete-icon" src="/html/addressing/img/delete.png"/>'))
                            .addClass('pointer-cursor')
                            .off('click').on('click', function(){
                                var remove = confirm('This will remove the entire row, do you want to proceed?');
                                if(remove == true) {
                                    self.removeRows(rowIndex);
                                }
                            })
                    },
                    function(){
                        $(this).html(rowNum).removeClass('cursor-pointer')
                    }
                )
            );
    },
    removeRows:function(rowIndex, numberOfRows) {
        var self = this;
        var dataRowIndex = this._getDataRowIndex(rowIndex);
        var variableDataId = this._getRowIdValue(this._gridData[dataRowIndex][0]);
        if(parseInt(variableDataId) > 0) {
            $.ajax({
                url: self.options.deleteVariableDataEndpoint,
                method: 'POST',
                data: 'variableDataId=' + variableDataId,
                dataType: 'json',
                async: false
            }).done(function(data) {
                if(data.success) {
                    self._gridData.splice(dataRowIndex, 1);
                    self._renderData();
                }
            });
        } else {
            self._gridData.splice(dataRowIndex, 1);
            this._renderData();
        }
    },

    _removeRowDataFromServer: function(variableDataId, dataRowIndex) {
        var self = this;
        if(parseInt(variableDataId) > 0) {
            $.ajax({
                url: self.options.deleteVariableDataEndpoint,
                method: 'POST',
                data: 'variableDataId=' + variableDataId,
                dataType: 'json',
                async: false
            }).done(function(data) {
                if(data.success) {
                    self._gridData[dataRowIndex][0] = '';
                }
            });
        }
    },

    /**
     * Method used to build empty overlay row cell elements for a single row. This method will build 'numberOfCells' cells up to the max allowed for
     * the Grid 'this._getGridOverlayMaxColumns()'
     *
     * @param numberOfCells - The number of cells that need to be built.
     * @returns {Array} - An array of all the overlay cell elements built by this method
     *
     * @private
     */
    _buildOverlayRowCells: function(numberOfCells) {
        var rowCellElements = [];
        for(var i = 0; i < numberOfCells; i ++) {
            if(this._getOverlayColumns() + i >= this._getGridOverlayMaxColumns()) {
                break;
            }
            rowCellElements[i] = this._buildOverlayCell();
        }
        return rowCellElements;
    },

    /**
     * Method used to build a single empty gridOverlay cell element.
     * @returns {*|jQuery} - The empty cell element
     * @private
     */
    _buildOverlayCell: function() {
        var self = this;
        return $('<div/>').addClass('env-grid_co1umn').attr("spellcheck",false).on('click', function(e) {self._checkReadOnlyMode(e);});
    },

    /**
     * Method used to find if the adjacent cell can be activated or not, when the arrow keys are pressed
     *
     * @param keyCode
     * @param text
     * @returns {boolean}
     * @private
     */
    _canSwitchCell: function(keyCode, text) {
        if(keyCode == 38 || keyCode == 40) {
            return true;
        }
        var caretPosition = window.getSelection().getRangeAt(0).startOffset;
        if(keyCode == 37 && caretPosition == 0) {
            return true;
        }

        if(keyCode == 39 && caretPosition == text.trim().length) {
            return true;
        }

        return false;
    },

    /**
     * Method used to build the Active Cell DOM element with all the required events bound to it
     * @returns {*|jQuery} - The active cell element
     * @private
     */
    _buildActiveCellElement: function() {
        var self = this;
        var inlineStyle = 'style="min-height:14px;';
        if(this._activeCell.length == 4 && this._activeCell[1] == this._activeCell[3]) {
            inlineStyle = inlineStyle + 'width:' + this._activeCell[2] + 'px;max-width:' + this._activeCell[2] + 'px;" ';
        } else {
            inlineStyle += '"';
        }
        var activeCellElement =  $('<div/>').addClass('env-grid_active_cell')
            .append($('<div class="jqs-editable" ' + inlineStyle + ' contenteditable="true"/>')
                .on('mousedown', function() {
                    try {
                        $(".env-grid_container").selectable('destroy');
                        $(window).on('mouseup.jqs-editable-mouseup', function() {self._makeGridCellsSelectable(); self._setCaretPosition(window.getSelection().getRangeAt(0).startOffset);$(this).off('mouseup.jqs-editable-mouseup')});
                    } catch (ignore) {}
                })/*.on('mouseup', function() {
             //self._makeGridCellsSelectable();
             //self._setCaretPosition(window.getSelection().getRangeAt(0).startOffset);
             })*/.on('paste', function(e) {
                    e.preventDefault();
                    var clipboardData = e.originalEvent.clipboardData.getData('text');
                    clipboardData = clipboardData.split(/\n/);
                    var dataArray = [];
                    for(var i = 0; i < clipboardData.length; i ++) {
                        dataArray[i] = clipboardData[i].split(/\t/);
                    }
                    if(dataArray.length > 1 || dataArray[0].length > 1) {
                        alert('Pasting tabular data is not supported');
                        //self._showDialog('pastingTabularData', 'Warning', 'Pasting tabular data is not supported');
                    }
                    $(this).text(dataArray[0][0]);
                    self._setCaretPosition('end');
                    self._setCaret('end');

                })
            /**
             * key down events
             *      - Resize the active cell based on its content
             *      - Special treatment for Enter key and the Arrow keys
             */
                .on('keydown', function(e) {
                    $('.jqs-addr-drop-down').hide();
                    // Resizing the active cell when the content overflows
                    if($(this).width() > $(this).parent().width() - 5) {
                        $(this).width($(this).width() + $('.jqs-editable').parent().parent().next().width());
//                    $(this).next().hide();
                    }
                    // Enter Key - cancel the enter key event and make the cell immediately below the current cell active. If there is no cell below the current cell, then the current cell will remain active
                    if (e.keyCode == '13') {
                        e.preventDefault();
                        self._activateNextCell(3);
                    }
                    // Left Arrow Key - Make the cell immediately left to the current cell active. If there is no cell left to the current cell, then the current cell will remain active
                    else if(e.keyCode == '37' && self._canSwitchCell(e.keyCode, $(this).text())) {
                        e.preventDefault();
                        self._activateNextCell(4);
                    }
                    // Up Arrow Key - Make the cell immediately above the current cell active. If there is no cell above the current cell, then the current cell will remain active
                    else if(e.keyCode == '38' && self._canSwitchCell(e.keyCode, $(this).text())) {
                        e.preventDefault();
                        self._activateNextCell(1);
                    }
                    // Right Arrow Key - Make the cell immediately right to the current cell active. If there is no cell right to the current cell, then the current cell will remain active
                    else if(e.keyCode == '39' && self._canSwitchCell(e.keyCode, $(this).text())) {
                        e.preventDefault();
                        self._activateNextCell(2);
                    }
                    // Down Arrow Key - Make the cell immediately below the current cell active. If there is no cell below the current cell, then the current cell will remain active
                    else if(e.keyCode == '40' && self._canSwitchCell(e.keyCode, $(this).text())) {
                        e.preventDefault();
                        self._activateNextCell(3);
                    }/*
                     else if(e.keyCode == '9') {
                     if(e.keyCode == e.shiftKey) {
                     e.preventDefault();
                     self._activateNextCell(4);
                     } else {
                     e.preventDefault();
                     self._activateNextCell(2);
                     }
                     }*/
                }).on('keyup', function() {
                    self._setCaretPosition(window.getSelection().getRangeAt(0).startOffset);
                })
                .on('blur', function() {
//                    var rowIndex = $(this).parent().parent().parent().index('.env-grid_row');
//                    var columnIndex = $(this).parent().parent().index();
//                    var cellValue = $(this).text();
//                    self.setCellValue(rowIndex, columnIndex, cellValue);
                }))
            .append($('<div/>'));

        this._shiftTab(activeCellElement, function() {
            self._activateNextCell(4);
        }, function() {
            self._activateNextCell(2);
        });

        //Code block for Copying Cell Selection - Start
        /*this._ctrl(activeCellElement, 'C', function() {
         self._copySelection();
         console.log(self._getCopiedCells());
         });*/

        /*this._ctrl(activeCellElement, 'V', function() {
         self._copySelection();
         console.log(self._getCopiedCells());
         });*/
        //Code block for Copying Cell Selection - End
        return activeCellElement;
    },

    /**
     * Method used to activate the adjacent cell based on the direction
     *
     * @param direction
     * @returns {boolean}
     * @private
     */
    _activateNextCell: function(direction) { // 1- UP, 2- RIGHT, 3- DOWN, 4- LEFT
        var rowIdx = this._getActiveCell()[0], columnIdx = this._getActiveCell()[1];
        switch(direction) {
            case 1:
                rowIdx -= 1;
                break;
            case 2:
                columnIdx += 1;
                break;
            case 3:
                rowIdx += 1;
                break;
            case 4:
                columnIdx -= 1;
                break;
        }
        if(rowIdx < 0 || columnIdx < 0 || columnIdx >= this._currentDataColumnCount() || rowIdx >= this._currentDataRowCount()) {
            return false;
        } /*else if(rowIdx >= this._currentDataRowCount()) { //Uncomment this block, if we need to add more rows when hitting the down arrow after the last row
         this.addRows();
         this.scrollTo(this._getRowOffSet() + 1);
         this._activateNextCell(direction);
         this._setIgnoreScroll();
         $('.env-grid_vertical_scrollbar').scrollTo('+=30');
         } */else {
            if (this._getOverlayRowIndex(rowIdx) < 0) {
                this.scrollTo(this._getRowOffSet() + (direction == 1 ? -1 : 1));
                this._activateNextCell(direction);
                this._setIgnoreScroll();
                $('.env-grid_vertical_scrollbar').scrollTo((direction == 1 ? '-=30' : '+=30'));
            } else {
                this.makeCellActive($('.env-grid_row:eq(' + this._getOverlayRowIndex(rowIdx) + ') .env-grid_co1umn:eq(' + this._getOverlayColumnIndex(columnIdx) + ')'));
            }
        }

    },

    /**
     * Method used to make a cell active.
     *
     * @private
     */
    _makeCellActive: function() {
        if(!this._isMappingMode()) {
            var activeCellOverlayElement = this._buildActiveCellElement();

            var dataRowIndex = this._getActiveCell()[0];
            var dataColumnIndex = this._getActiveCell()[1];

            var cellValue = this.getGridData()[dataRowIndex][dataColumnIndex];

            var overlayRowIndex = this._getOverlayRowIndex(dataRowIndex);
            var overlayColumnIndex = this._getOverlayColumnIndex(dataColumnIndex);
            if (overlayRowIndex >= 0 && overlayColumnIndex >= 0) {
                this._renderCellValue(overlayRowIndex, overlayColumnIndex, '');

                $(activeCellOverlayElement).find('.jqs-editable').empty().text(this._getIdCellValue(cellValue));

                $('.env-grid_container .env-grid_row:eq(' + overlayRowIndex + ') .env-grid_co1umn:eq(' + overlayColumnIndex + ')').append(activeCellOverlayElement);

                //Set the focus to the active cell overlay
                $('.jqs-editable').focus();
            }

            //Select the current active row number and column title
            this._showActiveRowAndColumn();
            this._setMapColumnsToolbarButtonState();
            this._setToolbarButtonsState();
        }
    },

    /**
     * Method used to highlight the selected cell(s) row numbers and column titles
     *
     * @private
     */
    _showActiveRowAndColumn: function() {
        $('.jqs-active').removeClass('jqs-active');
        var overlayRowIndex = this._getOverlayRowIndex(this._getActiveCell()[0]);
        var overlayColumnIndex = this._getOverlayColumnIndex(this._getActiveCell()[1]);
        var selectionType = this._getSelectionType();
        var selectedRowCount = this._getSelectedCells()[0].length;
        var selectedColumnCount = this._getSelectedCells()[1].length;
        /*var selectedRowCount = selectionType == 'column' ? this.getGridData().length: this._getSelectedCells()[0].length;
         var selectedColumnCount = selectionType == 'row' ? this._getOverlayColumns(): this._getSelectedCells()[1].length;

         for(var i = 1; overlayRowIndex < 0 && i < selectedRowCount; i ++) {
         overlayRowIndex = this._getOverlayRowIndex(this._getActiveCell()[0] + i);
         selectedRowCount --;
         }

         for(var i = 1; overlayColumnIndex < 0 && i < selectedColumnCount; i ++) {
         overlayColumnIndex = this._getOverlayColumnIndex(this._getActiveCell()[1] + i);
         selectedColumnCount --;
         }*/

        if(overlayRowIndex >= 0 || selectionType == 'column' || selectionType == 'row' ) {
            if(selectionType == 'row' || selectionType == 'cell') {
                for(var i = 0; i < selectedRowCount; i ++) {
                    if(overlayRowIndex + i < 0) {
                        continue;
                    }
                    $('.env-grid_container .env-grid_left > div:eq(' + (overlayRowIndex + i) + ')').addClass('jqs-active');
                }
            } else if(selectionType == 'column') {
                $('.env-grid_container .env-grid_left > div').addClass('jqs-active');
            } else {
                $('.env-grid_container .env-grid_left > div:eq(' + overlayRowIndex + ')').addClass('jqs-active');
            }
        }

        if(overlayColumnIndex >= 0 || selectionType == 'row' || selectionType == 'column') {
            if(selectionType == 'column' || selectionType == 'cell') {
                for(var i = 0; i < selectedColumnCount; i ++) {
                    if(overlayColumnIndex + i < 0) {
                        continue;
                    }
                    $('.env-grid_container .env-grid_top > div:eq(' + (overlayColumnIndex + i) + ')').addClass('jqs-active');
                }
            } else if(selectionType == 'row') {
                $('.env-grid_container .env-grid_top > div').addClass('jqs-active');
            } else {
                $('.env-grid_container .env-grid_top > div:eq(' + overlayColumnIndex + ')').addClass('jqs-active');
            }

        }
    },

    /**
     * Method used to add a single empty column at the given index in the GridOverlay. This method will add one cell each to all the existing rows at the given index.
     * After adding the column to the GridOverlay, this method add a new column at the given index to the GridData as well
     * @param columnIndex - [OPTIONAL] The index where this column needs to be created. If not preset, the new column will be added as the last column
     * @param columnTitle - [OPTIONAL] The title that should be used for this column header cell. If not present, the alphabet corresponding to
     *                                the column index will be used (A, B, C ...)
     *
     * @private
     */
    _addColumnToGridOverlay: function(columnIndex, columnTitle) {
        // If no columnIndex is specified, add the new column as the last column
        if(typeof columnIndex === 'undefined') {
            columnIndex = this._getOverlayColumns();
        }

        //If we already have the max number of columns, don't add anymore columns
        if(columnIndex >= this._getGridOverlayMaxColumns()) {
            return;
        }

        //Add the HeaderCell for the new column
        this._addColumnHeaderCell(columnIndex, columnTitle);

        //Build an OverlayCell element and reuse it for all rows by cloning
        var overlayCellElement = this._buildOverlayCell();
        var self = this;
        //Select all the rows in the GridOverlay and iterate over each row and add the cloned OverlayCell element at the given columnIndex.
        $('.env-grid_row').each(function(i) {

            //Deep clone the overlayCell element
            overlayCellElement = $(overlayCellElement).clone(true);

            //If the new column is adding at the first column in the GridOverlay (columnIndex '0')
            if(columnIndex == 0) {
                //If the GridOverlay has no columns, then the new overlayCell should be added at index '0' by appending to the current row.
                if(self._getOverlayColumns() == 0) {
                    $(this).append($(overlayCellElement));
                    //Select the first cell in the row and add the new cell before the selected cell
                } else {
                    $(this).find('.env-grid_co1umn:eq(' + columnIndex + ')').before($(overlayCellElement));
                }
                //If the new column is not adding at the beginning (columnIndex greater than '0')
            } else {
                //Select the cell before the given index in the row and add the new cell after the selected cell
                $(this).find('.env-grid_co1umn:eq(' + (columnIndex - 1) + ')').after($(overlayCellElement));
            }
        });

        //The new column has been added to the GridOverlay. Now add a new column to the GridData at the same columnIndex
        this._addColumnToGridData(columnIndex);
    },

    /**
     * Method used to remove the column at the given index in the GridOverlay.
     * @param columnIndex - The index of the column that needs to be removed
     * @private
     */
    _removeColumnFromGridOverlay: function(columnIndex) {
        if(typeof columnIndex === 'undefined' || columnIndex >= this._getOverlayColumns()) {
            throw 'An error occurred while removing the column at index ' + columnIndex + ' due to: Invalid column index';
        }
        this._removeColumnHeaderCell(columnIndex);

        $('.env-grid_row').each(function() {
            $(this).find('.env-grid_co1umn:eq(' + columnIndex + ')').remove();
        });

        this._removeColumnFromGridData(columnIndex);
    },

    /**
     * Method used to add a single empty column at the given index in the GridData. This method will add one cell each to all the existing rows at the given index.
     *
     * @param columnIndex - The index where this column needs to be created.
     *
     * @private
     */
    _addColumnToGridData: function(columnIndex) {
        //Invert the 2D GridData, so that the rows become the columns and columns become the rows. After that we can add a column to the actual 2D GridData by adding a row to the inverted 2D array
        var invertedArray = this._inverseArray(this._gridData, this._getDefaultGridDataArrayDimension());

        //If the new column is adding as the last column, add a new row at the end of the inverted 2D array.
        if(columnIndex == invertedArray.length) {
            invertedArray[invertedArray.length] = new Array(invertedArray[0].length);
        }

        //If the new column is adding as the first column, insert a new row at the beginning of the inverted 2D array
        else if(columnIndex == 0) {
            invertedArray.splice(0, 0, new Array(invertedArray[0].length));

        }
        //If the new column is adding is not the first column not a new column at the end, insert a new row at the given index of the inverted 2D array
        else {
            invertedArray.splice(columnIndex, 0, new Array(invertedArray[0].length));
        }

        //Invert the inverted array back to the original dimension
        this._gridData = this._inverseArray(invertedArray, this._getDefaultGridDataArrayDimension());
    },

    /**
     * Method used to remove the column at the given index in the GridData.
     * @param columnIndex - The index of the column that needs to be removed
     * @private
     */
    _removeColumnFromGridData: function(columnIndex) {
        var gridData = this.getGridData();
        gridData = this._inverseArray(gridData, this._getDefaultGridDataArrayDimension());
        if(gridData.length > columnIndex) {
            gridData.splice(columnIndex, 1);
            /*if(this._headerRow.length > columnIndex) {
             this._headerRow.splice(columnIndex, 1);
             }*/
        }
        this._gridData = this._inverseArray(gridData, this._getDefaultGridDataArrayDimension());

        this._gridDataChanged();
    },

    /**
     * Method used to add 'n' empty rows to the bottom of the dataGrid, where 'n' is equal to _getDataAddRowSize(). While adding new rows,
     * there is no need to add rows to the GridOverlay, we only need to add the new rows to the GridData, since the number of rows in the
     * GridOverlay is fixed
     *
     * @private
     */
    _addRowsToGridData: function() {
        //Rows are added in groups defined by '_getDataAddRowSize()'
        for(var i = this._currentDataRowCount(), j = 0; j < this._getDataAddRowSize(); i ++, j ++) {
            this._gridData[i] = new Array(this._currentDataColumnCount());
        }

        //Set the new ScrollHeight
        this._setVerticalScrollHeight();
    },

    /**
     * Method used to set the scroll height of the custom vertical scroll bar based on the number of rows in the grid data
     * @private
     */
    _setVerticalScrollHeight: function() {
        $('.jqs-vertical-scroll-height').css('height', ((this._currentDataRowCount() + this._emptyRowsAtBottom) * parseInt(this._getCellHeight())) + 'px');
        this._renderData();
    },

    /**
     * Method used to capture the gridData changed event. THis method will ten render the changed data to the gridOverlay
     * @private
     */
    _gridDataChanged: function() {
        this._renderData();
    },

    /**
     * Method used to render the GridData to the GridOverlay based on the current rowOffSet. The required
     * row offset needs to be set prior to calling this by calling the _setRowOffset() method. Usually the
     * public scrollTo() method will set the currentRowOffSet and will then call this private method.
     * @private
     */
    _renderData: function() {
        var self = this;
        var reachedBottom = false;
        var rowOffSet = (this._getRowOffSet() + this._getOverlayRows()) < this._currentDataRowCount() + this._emptyRowsAtBottom ? (this._getRowOffSet() - 1) : this._currentDataRowCount() - this._getOverlayRows() + this._emptyRowsAtBottom;
        if(rowOffSet == this._getRowOffSet()) {
            this._setRowOffSet(rowOffSet + 1);
        }
        for(var i = rowOffSet, j = 0; j < this._getOverlayRows(); i ++, j++) {
            $('.env-grid_left div:eq(' + j + ')').text(i >= 0 ? i + 1 : 1);
            if(i + 1 > this._currentDataRowCount()) {
                reachedBottom = true;
            }
            if(reachedBottom) {
                $('.env-grid_container .env-grid_row:eq(' + j + ')').css('visibility', 'hidden');
                $('.env-grid_left div:eq(' + j + ')').css('visibility', 'hidden');
                $('.env-grid_container .jqs-grid-bottom').removeClass('grid-hide');
            } else {
                $('.env-grid_container .env-grid_row:eq(' + j + ')').css('visibility', '');
                $('.env-grid_left div:eq(' + j + ')').css('visibility', '');
                $('.env-grid_container .jqs-grid-bottom').addClass('grid-hide');
            }
            var rowsInGridData = this._currentDataRowCount();
            if(i < rowsInGridData) {
                var rowData = this._gridData[i];
                $('.env-grid_container .env-grid_row:eq(' + j + ') .env-grid_co1umn').each(function(k) {
                    if(k < rowData.length) {
                        $(this).empty().text(typeof rowData[k] === 'undefined' ? '' : k == 0 ? self._getIdCellValue(rowData[k]) : rowData[k]);
                    } else {
                        $(this).empty().text('');
                    }
                });
            } else {
                $('.env-grid_container .env-grid_row:eq(' + j + ') div').empty().text('');
            }
        }
        if(reachedBottom && !this._isMappingMode()) {
            $('.jqs-add-rows-section').show();
            $('.tbr-button.jqs-add-rows').removeClass('disabled').off('click').on('click', function() {self.addRows();});
        } else {
            $('.jqs-add-rows-section').hide();
            $('.tbr-button.jqs-add-rows').addClass('disabled').off('click');
        }
        self._setMapColumnsToolbarButtonState();
        self._setToolbarButtonsState();
        self._updateServiceCharge();
        return rowOffSet;
    },

    _showServiceCharge: function() {
        this._updateServiceCharge();
        $('.jqs-addressing-service-charge').removeClass('grid-hide');
    },

    _hideServiceCharge: function() {
        this._updateServiceCharge();
        $('.jqs-addressing-service-charge').addClass('grid-hide');
    },

    _updateServiceCharge: function() {
        var numberOfAddresses = this.getFilteredGridData(true).length;
        var fee = 0.0;
        if(numberOfAddresses > 0 && numberOfAddresses < 100) {
            fee = 1.15 * numberOfAddresses;
        } else if(numberOfAddresses >= 100 && numberOfAddresses < 200) {
            fee = 0.87 * numberOfAddresses;
        } else if(numberOfAddresses >= 200 && numberOfAddresses < 300) {
            fee = 0.75 * numberOfAddresses;
        } else if(numberOfAddresses >= 300 && numberOfAddresses < 500) {
            fee = 0.55 * numberOfAddresses;
        } else if(numberOfAddresses >= 500 && numberOfAddresses < 1000) {
            fee = 0.48 * numberOfAddresses;
        } else if(numberOfAddresses >= 100) {
            fee = 0.38 * numberOfAddresses;
        }
        $('.jqs-addressing-fee').text(this._formatCurrency(fee));

    },

    _formatCurrency: function(n) {
        return '$'+n.toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,');
    },

    /**
     * Method used to capture the overlayColumn created event. This method will increment the gridOverlay's column count
     * @private
     */
    _overlayColumnCreated: function() {
        this._setOverlayColumns(1, true);
        this._log(this.DEBUG, 'Column added to GridOverlay !', 0);
    },

    /**
     * Method used to capture the overlayColumn removed event. This method will decrement the gridOverlay's column count
     * @private
     */
    _overlayColumnRemoved: function() {
        this._setOverlayColumns(1, false);
        this._log(this.DEBUG, 'Column removed from GridOverlay !', 0);
    },
    /**
     * Method used to capture the overlayRow created event. This method will increment the gridOverlay's row count
     * @private
     */
    _overlayRowCreated: function() {
        this._setOverlayRows(1, true);
        this._log(this.DEBUG, 'Row added to GridOverlay !', 0);
    },
    /**
     * Method used to capture the overlayRow removed event. This method will decrement the gridOverlay's row count
     * @private
     */
    _overlayRowRemoved: function() {
        this._setOverlayRows(1, false);
        this._log(this.DEBUG, 'Row removed from GridOverlay !', 0);
    },

    /**
     * Method used to get the number of rows in the GridData
     * @returns {number} - The number of rows in the GridData
     * @private
     */
    _currentDataRowCount: function() {
        return this.getGridData().length;
    },

    /**
     * Method used to get the number of columns in the GridData
     * @returns {number} - The number of columns in the GridData
     * @private
     */
    _currentDataColumnCount: function() {
        return this.getGridData()[0].length;
    },

    /**
     * Method used to find the GridData RowIndex from GridOverlay RowIndex
     * @param overlayRowIndex - The GridOverlay RowIndex
     * @returns {number} - The GridData RowIndex
     * @private
     */
    _getDataRowIndex: function(overlayRowIndex) {
        var idx = this._getRowOffSet() + overlayRowIndex - 1;
        if(idx >= this._currentDataRowCount()) {
            idx = -1;
        }
        return idx;
    },

    /**
     * Method used to find the GridData ColumnIndex from GridOverlay ColumnIndex
     * @param overlayColumnIndex - The GridOverlay ColumnIndex
     * @returns {*} - The GridData ColumnIndex
     * @private
     */
    _getDataColumnIndex: function(overlayColumnIndex) {
        return overlayColumnIndex;
    },

    /**
     * Method used to find the GridOverlay RowIndex from GridData RowIndex
     * @param dataRowIndex - The GridData RowIndex
     * @returns {number} - The GridOverlay RowIndex
     * @private
     */
    _getOverlayRowIndex: function(dataRowIndex) {
        var idx = dataRowIndex - this._getRowOffSet() + 1;
        if(idx >= this._getOverlayRows()) {
            idx = -1;
        }
        return idx;
    },

    /**
     * Method used to find the GridOverlay ColumnIndex from GridData ColumnIndex
     * @param dataColumnIndex - The GridData ColumnIndex
     * @returns {number} - The GridOverlay ColumnIndex
     * @private
     */
    _getOverlayColumnIndex: function(dataColumnIndex) {
        return dataColumnIndex;
    },

    /**
     * Method used to save the data change in a particular gridOverlay cell. This method will update the serverSide gridData
     * and will then update the gridData on the clientSide. If the serverSide save fails, then the cell data won't be saved
     * at the client side and will also revert the cell data in the GridOverlay as well.
     *
     * NOTE - cell based data changes are not saved on blur of active cell in this version. Data is saved on blur of the row
     *
     * @param overlayRowIdx - The rowIndex of the cell in the GridOverlay
     * @param overlayColumnIdx - The columnIndex of the cell in the GridOverlay
     * @param newValue - The newValue of the cell in the GridOverlay
     * @private
     */
    _saveCellData: function(overlayRowIdx, overlayColumnIdx, newValue) {
        //Get the GridData rowIndex for the cell from the GridOverlay rowIndex
        var dataRowIdx = this._getDataRowIndex(overlayRowIdx);

        //Get the GridData columnIndex for the cell from the GridOverlay columnIndex
        var dataColumnIdx = this._getDataColumnIndex(overlayColumnIdx);

        //This normally won't happen, but for safety we are validating the GridData rowIndex and columnIndex
        if(dataRowIdx < 0 || dataColumnIdx < 0 || dataRowIdx >= this._currentDataRowCount || dataRowIdx >= this._currentDataColumnCount) {
            throw 'An error occurred while saving the cell data at Row:' + (overlayRowIdx + 1) + ', Column:' + (overlayColumnIdx + 1) + ' due to: Invalid Data Index';
        }

        //The existing value for the given cell in GridData
        var oldValue = this._gridData[dataRowIdx][dataColumnIdx];
        newValue = this._mergeIdCellValues(oldValue, newValue);
        //We only need to save the cell data, if the value of the cell is changed
        if(oldValue != newValue) {
            //TODO ajax call to nodeJS endpoint to save the data.

            //on success, set the cell value in the GridData on the client side with the new value
            this._setCellValue(dataRowIdx, dataColumnIdx, newValue);
//            this._renderCellValue(overlayRowIdx, overlayColumnIdx, newValue);

            //on failure, reset the cell value in the GridOverlay with the old value and throw an exception.
//            this._renderCellValue(overlayRowIdx, overlayColumnIdx, oldValue);
//            throw 'An error occurred while saving the cell data at Row:' + (overlayRowIdx + 1) + ', Column:' + (overlayColumnIdx + 1) + ' due to: Server-side save failed';
        }
    },

    /**
     * Overloaded version of the above _saveCellData() method to take dataRowIndex and dataColumnIndex instead of overlayRowIndex and overlayColumnIndex
     *
     * NOTE - cell based data changes are not saved on blur of active cell in this version. Data is saved on blur of the row
     *
     * @param dataRowIdx
     * @param dataColumnIdx
     * @param newValue
     * @returns {*}
     * @private
     */
    _saveCellData1: function(dataRowIdx, dataColumnIdx, newValue) {
        //The existing value for the given cell in GridData
        var oldValue = this._gridData[dataRowIdx][dataColumnIdx];
        newValue = this._mergeIdCellValues(oldValue, newValue);
        //We only need to save the cell data, if the value of the cell is changed
        if(oldValue != newValue) {
            //TODO ajax call to nodeJS endpoint to save the data.
            //on success, set the cell value in the GridData on the client side with the new value
            this._setCellValue(dataRowIdx, dataColumnIdx, newValue);
            return this._getIdCellValue(newValue);

            //on failure, reset the cell value in the GridOverlay with the old value and throw an exception.
//            return oldValue;
        }
        return this._getIdCellValue(oldValue);
    },

    /**
     * Method used to set the cell value in the GridData on the client side
     * @param dataRowIdx - The row index for the cell in the GridData
     * @param dataColumn - The column index for the cell in the GridData
     * @param value - The value that needs to be set for the given cell
     * @private
     */
    _setCellValue: function(dataRowIdx, dataColumn, value) {
        this._gridData[dataRowIdx][dataColumn] = value;
    },

    /**
     * Method used to render the value of a cell in the GridOverlay
     * @param overlayRowIdx - The rowIndex of the cell in the GridOverlay
     * @param overlayColumnIdx - The columnIndex of the cell in the GridOverlay
     * @param value - The value that needs to be rendered for the given cell in the GridOverlay
     * @private
     */
    _renderCellValue: function(overlayRowIdx, overlayColumnIdx, value) {
        $('.env-grid_container .env-grid_row:eq(' + overlayRowIdx + ') .env-grid_co1umn:eq(' + overlayColumnIdx + ')').empty().text(value);
    },

    /**
     * Method used to replace the Column Header Titles with the Column Mapper UI element.
     * @private
     */
    _showColumnMapper: function(columnMapperData) {
        this._renderMapperUIColumnHeader(columnMapperData);
        this._setMappingMode();
        $('.footer-container').find('.jqs-apply-to-design').hide();
        $('.footer-container').find('.jqs-save-for-later').addClass('grid-hide');
        $('.footer-container').find('.jqs-finish-mapping').show();
        this._scrollToTop();
    },

    /**
     * Method used to select the column mapper with the corresponding column names
     * @private
     */
    _selectColumnMappers: function() {
        var gridElement = this.element;
        $.each(this._getColumnTitles(), function(index, value){
            gridElement.find('.env-grid_top .jqs-co1umn-mapper:eq(' + index + ')').val(index).trigger('change');
        });
    },

    /**
     * Method used to finalize the column mapping of uploaded file. This method will remove all the unmapped columns from the uploaded data.
     * This method will save the mapped gridData to the back end and will make the grid editable.
     * @private
     */
    _finalizeColumnMapping: function() {
        var titles = this._getColumnTitles();
        this._orderMappedColumns(titles.length);
        var self = this;
        $('.jqs-header-co1umn select').each(function(index) {
            if(index >= self._getOverlayColumns()) {
                return false;
            }
            if($(this).val() == '') {
                self.removeColumns(index);
                self._addColumnToGridOverlay(index, titles[index]);
            } else {
                $(this).parent().html(titles[index]);
            }
            if(index + 1 < self._getOverlayColumns() && index == titles.length - 1) {
                self.removeColumns(index + 1, 'all');
                return false;
            }
        });
        $('.jqs-header-co1umn').each(function(index) {
            $(this).html(titles[index]);
        });
        this._makeColumnsResizable();
        $('.footer-container').find('.jqs-apply-to-design').show();
        $('.footer-container').find('.jqs-save-for-later').removeClass('grid-hide');
        $('.footer-container').find('.jqs-finish-mapping').hide();

        this._saveDataGroup(null, function() {
            self._resetMappingMode();
            self._resetActiveCell();
            self._resetSelectedCells();
            self._makeCellActive();
            self._markSelection(true);
            self._scrollToTop();
        });
    },

    _cancelColumnMapping: function() {
        var self = this;
        self._hideFileUpload();
        $('.gridOverlay .tooltip').remove();
        self._resetOverlayColumns();
        self._resetOverlayRows();
        self._resetMappingMode();
        self._resetActiveCell();
        self._resetSelectedCells();
        self._create();
        self.showGrid();
    },

    _orderMappedColumns: function(columnsCount) {
        var self = this;
        for(var i = 0; i < columnsCount - $('.jqs-header-co1umn select').length; i ++) {
            self.addColumns();
        }
        $('.jqs-header-co1umn select').each(function(index) {
            var value = $(this).attr('data-value');
            if(value != '') {
                self.swapColumns(_getColumnIndex($(this)), parseInt(value));
            }
        });
    },

    /**
     * Method used to render the Mapper UI column header. This column header is only display
     * after uploading an external data file and until the column mapping is finalized by the
     * user.
     * @private
     */
    _renderMapperUIColumnHeader: function(columnMapperData) {
        this.element.find('.env-grid_top').html('');
        this._log(this.DEBUG, 'started building ' + this._getOverlayColumns() + ' header columns', 1);
        for(var i = 0; i < this._getOverlayColumns(); i ++) {
            this.element.find('.env-grid_top').append($('<div/>').addClass('jqs-header-co1umn').append(this._buildMapperDropdown(i, columnMapperData)).append(this._buildRemoveColumnButton(i)))  ;
            var width = $('.env-grid_row .env-grid_co1umn:nth-child(' + (i + 1) + ')').width();
            if(width < $('.jqs-header-co1umn:eq('+ i + ')').width()) {
                width = $('.jqs-header-co1umn:eq('+ i + ')').width();
            }
            $('.jqs-header-co1umn:eq('+ i + ')').css('width', (width + 10) + 'px');
            $('.env-grid_row .env-grid_co1umn:nth-child(' + (i + 1) + ')').css('width', width + 10 + 'px');
//            $('.env-grid_row .env-grid_co1umn:nth-child(' + (i + 1) + ')').css('width', $('.jqs-header-co1umn:eq('+ i + ')').width() + 10 + 'px');
        }
        this._log(this.DEBUG, 'finished building ' + this._getOverlayColumns() + ' header columns', -1);
    },

    /**
     * Method use build the column mapper dropdown UI with the ability to disable mapped column options from other column mapping UIs
     *
     * @returns {*|jQuery}
     * @private
     */
    _buildMapperDropdown: function(idx, columnMapperData) {
        var self = this;
        var mapperUI = $('<select/>').addClass('jqs-co1umn-mapper').attr('data-previous-value', '-1').append($('<option/>').attr('value', '').html('Select One'));

        if(idx == 0) {
            mapperUI.attr('data-hint', 'Select the fields that matches your data').addClass('hint-start-mapping');
        }
        $(mapperUI).on('click', function(){self._hideHintBalloon($('.env-grid_container .hint-start-mapping'))});
        $.each(this._getColumnTitles(), function(index, value){
            mapperUI.append($('<option/>').attr('value', index).html(value))
        });

        $(mapperUI).on('change', function() {
            if($(this).val() == '-1') {
                $(this).val('');
                $(this).trigger('change');
            } else {
                var mapperIdx = $(this).parent().index();
                var value = parseInt($(this).val());
                var previousValue = parseInt($(this).attr('data-previous-value'));
                if(previousValue >= 0) {
                    $('.jqs-co1umn-mapper').find('option:eq(' + (previousValue + 1) + ')').removeAttr('disabled');
                    $(this).parent().find('.jqs-co1umn-remove').show();
                }
                $(this).attr('data-value', $(this).val());
                if($(this).val() != '') {
                    //self.swapColumns(_getColumnIndex($(this)), value);
                    $(this).find('option[value="-1"]').remove();
                    $(this).append($('<option/>').attr('value', '-1').html('Un-map this Column'));
                    $(this).attr('data-previous-value', value);
                    $('.jqs-co1umn-mapper:lt(' + mapperIdx + '),.jqs-co1umn-mapper:gt(' + mapperIdx + ')').find('option:eq(' + (value + 1) + ')').attr('disabled', 'disabled');
                    $(this).parent().find('.jqs-co1umn-remove').hide();
                    } else {
                        $(this).find('option[value="-1"]').remove();
                }
            }

        });
        if(columnMapperData.length >= idx) {
            $(mapperUI).val(columnMapperData[idx]);
            $(mapperUI).trigger('change');
        }
        return mapperUI;
    },

    /**
     * Method used to build the Remove column button for each columns during the columnMapping mode
     *
     * @returns {*|jQuery}
     * @private
     */
    _buildRemoveColumnButton: function() {
        var self = this;
        return $('<span/>').addClass('jqs-co1umn-remove').append($('<img src="/html/addressing/img/delete.png"/>'))
            .on('click', function() {
                if($('.env-grid_top div.jqs-header-co1umn').length > 1) {
                    var remove = confirm('This will remove the entire column, do you want to proceed?');
                    if(remove == true) {
                        self.removeColumns(_getColumnIndex($(this)));
                    }
                } else {
                    alert('The grid requires at least one column, so this last column can\'t be deleted');
                    //self._showDialog('deleteAllColumns', 'Warning', 'The grid requires at least one column, so this last column can\'t be deleted');
                }

            });
    },

    /**
     * Method used to swap two ColumnHeader cells
     * @param fromIdx - The index of the first columnHeader that needs to be swapped
     * @param toIdx - The index of the second columnHeader that needs to be swapped
     * @private
     */
    _swapColumnHeader: function(fromIdx, toIdx) {
        if(fromIdx > toIdx) {
            $('.env-grid_top .jqs-header-co1umn:eq(' + toIdx + ')').before($('.env-grid_top .jqs-header-co1umn:eq(' + fromIdx + ')'));
            if(Math.abs(fromIdx - toIdx) > 1) {
                $('.env-grid_top .jqs-header-co1umn:eq(' + fromIdx + ')').after($('.env-grid_top .jqs-header-co1umn:eq(' + (toIdx + 1) + ')'));
            }
        }
    },

    /*###################################################################################################################################################################*/

    //TODO PUBLIC METHODS

    resizeGridOverlay: function() {
        if(this._isResponsive()) {
            var containerHeight = this.element.parent().height();
            var numberOfOverlayRows = parseInt((containerHeight - 80) / this._getCellHeight()) - 5;
            var vScrollbarHeight = (numberOfOverlayRows * this._getCellHeight()) + 21;
            var vScrollbarBottom = vScrollbarHeight + 11;
            this._setGridOverlayRows(numberOfOverlayRows);
            $('.env-grid_vertical_scrollbar').css('height', vScrollbarHeight + 'px').css('bottom', vScrollbarBottom + 'px');
            this._markSelection();
            if(this._isMappingMode()) {
                this._showColumnMapper();
            }
        }
        if(this.options.dataGroupMode == 'upload') {
            this._showFileUpload();
            if(this._uploadedFileName != '') {
                this.uploadedFileChangeEvent(this._uploadedFileName);
            }
//            $('.texelFileUploadForm').find('#addressFileButton').trigger('click');
        } else if(this.options.dataGroupMode == 'existing') {
            this._showExistingDataGroups();
        }
        this._setToolbarButtonsState();
    },

    /**
     * The actual scroll event, that's responsible for scrolling the grid data underneath the grid overlay.
     */
    scrollEvent: function() {
        if(this._isIgnoreScroll()) {
            this._resetIgnoreScroll();
            return false;
        }
        var scrollTo = Math.ceil($('.env-grid_vertical_scrollbar').scrollTop() / this._getScrollFactor());

        this.scrollTo(parseInt(scrollTo) + 1);
        this._setCaret(this._getCaretPosition());
    },

    /**
     * Public method used to save the data change in a particular gridOverlay cell.
     * @param rowIdx - The rowIndex of the cell in the GridOverlay
     * @param columnIdx - The columnIndex of the cell in the GridOverlay
     * @param value - The value of the cell in the GridOverlay that needs to be saved
     */
    setCellValue: function(rowIdx, columnIdx, value) {
        this._saveCellData(rowIdx, columnIdx, value);
        console.log(this.getGridData());
    },

    /**
     * Public method used to get the GridData from the client side
     * @returns {Array} - The client side GridData
     */
    getGridData: function() {
        return this._gridData;
    },

    /**
     * Public method used to get the GridData from the client side after filtering empty rows
     * @returns {Array} - The client side GridData containing non empty rows
     */
    getFilteredGridData: function(removeNulls) {
        return this._removeEmptyRows(this._clone(this._gridData), removeNulls, true);
    },

    _applyGridData: function() {
        return this._removeEmptyRows(this._gridData, true);
    },

    isGridEmpty: function() {
        var notEmpty = false;
        for(var i = 0; i < this._gridData.length; i ++) {
            var rowData = this._gridData[i];
            if(notEmpty) {
                break;
            }
            for(var j = 0; j < rowData.length; j ++) {
                var cellData = rowData[j];
                if(typeof  cellData !== 'undefined' && cellData != null && cellData.trim() != '') {
                    notEmpty = true;
                    break;
                }
            }
        }
        return !notEmpty;
    },

    /**
     * Public method to add new rows to the existing grid.
     *
     */
    addRows: function() {
        this._addRowsToGridData();
        this._makeCellActive();
    },

    /**
     * Public method to add new columns to the GridOverlay.
     * @param columnIndex -  [OPTIONAL] The index where the new columns need to be added. If the index is not passed in or is invalid,
     *                       then the new columns will be added to the end
     * @param columnCount -  [OPTIONAL] The number of columns that needs to be added. If not present or invalid, one new column will be added
     * @param columnTitles - [OPTIONAL] The titles for the columnHeader cell.
     */
    addColumns: function(columnIndex, columnCount, columnTitles) {
        if(typeof columnCount === 'undefined' || columnCount < 1) {
            columnCount = 1;
        }

        if(typeof columnIndex === 'undefined' || columnIndex < 0 || columnIndex > this._getOverlayColumns()) {
            columnIndex = this._getOverlayColumns();
        }

        for(var i = 0; i < columnCount; i ++) {
            this._addColumnToGridOverlay(columnIndex + i, columnTitles);
        }
    },

    /**
     * Public method to remove one or more columns at or from a given index. If the numberOfColumns is 'all', then this method will
     * remove all remaining columns from the given index.
     * @param columnIndex - The index of the column that needs to be removed
     * @param numberOfColumns - [OPTIONAL] The number of columns need to be removed. If not present, then only the column at the given
     *                                     index will be removed. If the values is 'all', then all the columns from the given index will
     *                                     be removed.
     */

    removeColumns: function(columnIndex, numberOfColumns) {
        if(typeof columnIndex === 'undefined' || columnIndex >= this._getOverlayColumns()) {
            throw 'An error occurred while removing the column at index ' + columnIndex + ' due to: Invalid column index';
        }
        if(typeof numberOfColumns === 'string' && numberOfColumns == 'all') {
            for(var i = this._getOverlayColumns() - 1; i >= columnIndex; i --) {
                this._removeColumnFromGridOverlay(i);
            }
        } else if(typeof numberOfColumns === 'number') {
//            for(var i = 0; i < numberOfColumns && i + columnIndex < this._getOverlayColumns(); i ++) { //TODO - fix
            for(var i = columnIndex + numberOfColumns - 1; i >= columnIndex; i --) {
                this._removeColumnFromGridOverlay(i);
            }
        } else {
            this._removeColumnFromGridOverlay(columnIndex);
        }
    },
    /**
     * JS Doc - TODO
     */
    switchToTileView: function() {
        if(this._getView() != 'tile') {
            $('.env-grid_container').removeClass('list-view').addClass('tile-view');
            $('.tbr-button-list-view').removeClass('tbr-on');
            $('.tbr-button-tile-view').addClass('tbr-on');
            this._setView('tile');
        }
    },

    /**
     * JS Doc - TODO
     */
    switchToListView: function() {
        if(this._getView() != 'list') {
            $('.env-grid_container').removeClass('tile-view').addClass('list-view');
            $('.tbr-button-tile-view').removeClass('tbr-on');
            $('.tbr-button-list-view').addClass('tbr-on');
            this._setView('list');
        }
    },

    /**
     * Method used to make a cell active. This method will save the data in the current active cell before activating the clicked div. Also before activating the clicked cell,
     * this method will get the value from the active cell overlay and render it to he already activated cell. If you want to make a cell active without saving the data of the
     * already active cell (during initial load to make the first cell active), use the private method _makeCellActive().
     * @param activatingCellElement
     * @param e
     */
    makeCellActive: function(activatingCellElement, keepSelection, implicitFlag) {

        if(this._isMappingMode() && typeof implicitFlag === 'undefined') {
//            alert('The grid is read only and you need to finish Column Mapping in order to make the grid editable');
        } else {
            this._clearSelection(keepSelection);

            /*Saving the data in the active cell overlay(if any), before making the clicked cell active */

            if ($($('.env-grid_active_cell')[0]).find('.jqs-editable').length > 0) {
                //Get the value from the active cell overlay
                var cellValue = $($('.env-grid_active_cell')[0]).find('.jqs-editable').text();

                //Save the already active cell value. If an error happens while saving the data, the _saveCellData() method will return the old cell value, otherwise will send the new cel value
                cellValue = this._saveCellData1(this._getActiveCell()[0], this._getActiveCell()[1], cellValue);

                //Render the saved cell value to the already active cell
                this._renderCellValue(this._getOverlayRowIndex(this._getActiveCell()[0]), this._getOverlayColumnIndex(this._getActiveCell()[1]), this._getIdCellValue(cellValue));

                //Clear the current active row number and column title
//                this._showActiveRowAndColumn(false);
            }
            /*Saving the data COMPLETE*/

            //Get the overlay row index of the clicked cell
            var overlayRowIndex = $(activatingCellElement).parent().index('.env-grid_row');

            //Get the overlay column index of the clicked cell
            var overlayColumnIndex = $(activatingCellElement).index();

            //Get the data row index of the clicked cell
            var dataRowIndex = this._getDataRowIndex(overlayRowIndex);

            //Get the data column index of the clicked cell
            var dataColumnIndex = this._getDataColumnIndex(overlayColumnIndex);

            /*if (this._getActiveCell()[0] == dataRowIndex && this._getActiveCell()[1] == dataColumnIndex) {
             this._setBypassCursorManager();
             }*/
            if(!implicitFlag) {
                if(dataRowIndex != this._getActiveCell()[0]) {
//                    console.log('RowChanged::' + this._getActiveCell()[0]);
                    this._addRowDataToUnsavedRowDataBuffer(this._getActiveCell()[0]);
                }
                //Update the activeCell row and column indices
                this._setActiveCell([dataRowIndex, dataColumnIndex]);
            }


            //Now make the clicked cell active
            this._makeCellActive();

            this._setCaret(this._getCaretPosition());
        }
    },

    /**
     * Method used to scroll to the the specific rowOffSet. This method will set the rowOffSet and will then call the
     * _renderData() method to render the data based on the passed in row offset.
     * @param rowOffSet - The rowOffSet that the GridData needs to be scrolled to
     */
    scrollTo: function(rowOffSet) {

        var cellValue = $($('.env-grid_active_cell')[0]).find('.jqs-editable').text();

        var dataRowIndex = this._getActiveCell()[0];
        var dataColumnIndex = this._getActiveCell()[1];

        var overlayRowIndex = this._getOverlayRowIndex(dataRowIndex);
        var overlayColumnIndex = this._getOverlayColumnIndex(dataColumnIndex);

        if($($('.env-grid_active_cell')[0]).find('.jqs-editable').length > 0) {
            this._saveCellData(overlayRowIndex, overlayColumnIndex, cellValue);
            this._renderCellValue(overlayRowIndex, overlayColumnIndex, '');
        }

        this._setRowOffSet(rowOffSet);
        var scrolledToRowOffSet = this._renderData();
        this._makeCellActive();
        this._markSelection(true);
        return scrolledToRowOffSet;
    },

    selectRow:function(rowIndex, rowsSelected) {
        if(typeof rowsSelected === 'undefined') {
            rowsSelected = 1;
        }
        this._clearSelection();
        for(var i = rowIndex, j =0; j < rowsSelected; i ++, j ++) {
            this._setSelectedCells(i, null);
        }
        this._markSelection();
    },

    selectColumn:function(columnIndex, columnsSelected) {
        if(typeof columnsSelected === 'undefined') {
            columnsSelected = 1;
        }
        this._clearSelection();
        for(var i = columnIndex, j =0; j < columnsSelected; i ++, j ++) {
            this._setSelectedCells(null, i);
        }
        this._markSelection();

    },

    selectCell: function(firstCell, lastCell) {
        this._clearSelection();
        var rowIndices = [];
        for(var i = firstCell[0], j =0; i <= lastCell[0]; i ++, j ++) {
            rowIndices[j] = i;
        }
        var columnIndices = [];
        for(var i = firstCell[1], j =0; i <= lastCell[1]; i ++, j ++) {
            columnIndices[j] = i;
        }
        this._selectedCells = [rowIndices, columnIndices];
        this._markSelection();
    },

    applyEvent: function() {
        var self = this;
        var dataGroupId = this._getDataGroupId();
        this._trigger('onGridApply', null, {dataGroupId: dataGroupId, data: self.getFilteredGridData(true)});
        this._applyGridData();
    },

    closeEvent: function() {
        this._trigger('onGridClose', null, {});
    },

    saveVariableDataGroupsToAccount: function(partyId) {
        if(typeof partyId !== 'undefined') {
            this._setPartyId$(partyId);
        }
        if(this._getPartyId$() != '') {
            this._saveVariableDataGroupsToAccount();
        } else {
            loginCallback(this.siteLoginCallback);
        }

    },

    showAddVariableDataGroups: function() {

    },

    _saveVariableDataGroupsToAccount: function() {
        var self = this;
        $.ajax({
            url: self.options.saveVariableDataForLaterEndpoint,
            method: 'POST',
            data: 'clientId=' + self._getClientId$(),
            dataType: 'json'
        }).done(function(data) {
            if (data.success) {
                $('.jqs-save-for-later').addClass('grid-hide');
            }
        });
    },

    uploadedFileChangeEvent: function(fileName) {
        var uploadedFile = fileName;
        $('#jqs-address-data-file').val(fileName);
        $('.jqs-choose-file-btn').removeClass('grid-btn-cta').addClass('grid-btn-regular');
        $('.jqs-upload-btn').addClass('grid-btn-cta');
    },

    _getSelectionType: function() {
        var selectionType = '';
        var selectedCells = this._getSelectedCells();

        if(selectedCells[0].length > 0 && selectedCells[1].length == 0) {
            selectionType = 'row';
        } else if(selectedCells[0].length == 0 && selectedCells[1].length > 0) {
            selectionType = 'column';
        } else if(selectedCells[0].length > 0 && selectedCells[1].length > 0){
            selectionType = 'cell';
        }

        return selectionType;
    },

    _copySelection: function() {
        if(this._getSelectedCells()[0].length > 0 || this._getSelectedCells()[1].length > 0) {
            $('.env-grid_container').addClass('jqs-copied');
            this._setCopiedCells(this._getSelectedCells());
        }
    },

    _markSelection: function(implicitFlag) {
        /*if(this._isMappingMode()) {
         alert('The grid is read only and you need to finish Column Mapping in order to make the grid editable');
         return false;
         }*/
        var selectionType = this._getSelectionType();
        var selectedCells = this._getSelectedCells();
        this._clearSelection(true);
        var self = this;
        switch(selectionType) {
            case 'row':
                var openTop = self._getOverlayRowIndex(selectedCells[0][0]) < 0, openBottom = self._getOverlayRowIndex(selectedCells[0][selectedCells[0].length - 1]) < 0;
                $.each(selectedCells[0], function(index, value) {
                    var overlayRowIndex = self._getOverlayRowIndex(value);
                    if(overlayRowIndex >= 0) {
                        if(index == 0) {
                            self.makeCellActive($('.env-grid_container .env-grid_row:eq(' + overlayRowIndex + ') > div.env-grid_co1umn:first'), true, implicitFlag);
                            //                      self.makeCellActive($('.env-grid_container .jqs-row-selected:first .jqs-selected-cell:first'));
                        }

                        $('.env-grid_container .env-grid_row:eq(' + overlayRowIndex + ')').addClass('jqs-row-selected');
                        $('.env-grid_container .env-grid_row:eq(' + overlayRowIndex + ') > div.env-grid_co1umn').addClass('jqs-selected-cell');
                    }
                });
                if(!openTop) {
                    $('.jqs-row-selected:first .jqs-selected-cell').addClass('selected-border-top');
                }
                $('.jqs-row-selected').each(function (i) {
                    $(this).find('.jqs-selected-cell:last').addClass('selected-border-right');
                    $(this).find('.jqs-selected-cell:first').addClass('selected-border-left');
                });
                if(!openBottom) {
                    $('.jqs-row-selected:last .jqs-selected-cell').addClass('selected-border-bottom');
                }
                break;

            case 'column':
                $.each(selectedCells[1], function(index, value) {
                    var overlayColumnIndex = self._getOverlayColumnIndex(value);
                    if(overlayColumnIndex >= 0) {
                        if (index == 0) {
                            self.makeCellActive($('.env-grid_container .env-grid_row:eq(0) > div.env-grid_co1umn:eq(' + value + ')'), true, implicitFlag);
                            //                      self.makeCellActive($('.env-grid_container .jqs-column-selected:first .jqs-selected-cell:first'));
                        }
                        $('.env-grid_row').each(function (i) {
                            $(this).addClass('jqs-co1umn-selected');
                            $(this).find('.env-grid_co1umn:eq(' + value + ')').addClass('jqs-selected-cell');
                        });
                    }
                });
                $('.jqs-co1umn-selected').each(function (i) {
                    $(this).find('.jqs-selected-cell:last').addClass('selected-border-right');
                    $(this).find('.jqs-selected-cell:first').addClass('selected-border-left');
                });
                break;

            case 'cell':
                var openTop = self._getOverlayRowIndex(selectedCells[0][0]) < 0, openBottom = self._getOverlayRowIndex(selectedCells[0][selectedCells[0].length - 1]) < 0;

                for(var x = 0; x < selectedCells[0].length; x ++) {
                    for(var y = 0; y < selectedCells[1].length; y ++) {
                        var overlayRowIndex = self._getOverlayRowIndex(selectedCells[0][x]);
                        /*if(x == 0 && y == 0) {
                         openTop = overlayRowIndex < 0;
                         }*/
                        var overlayColumnIndex = self._getOverlayColumnIndex(selectedCells[1][y]);
                        if(overlayRowIndex >= 0 && overlayColumnIndex >= 0) {
                            if (x == 0 && y == 0) {
                                if($('.env-grid_container .env-grid_row:eq(' + overlayRowIndex + ') > div.env-grid_co1umn:eq(' + overlayColumnIndex + ')').find('.jqs-editable').length == 0) {
                                    self.makeCellActive($('.env-grid_container .env-grid_row:eq(' + overlayRowIndex + ') > div.env-grid_co1umn:eq(' + overlayColumnIndex + ')'), true, implicitFlag);
                                    //                              self.makeCellActive($('.env-grid_container .jqs-cell-selected:first .jqs-selected-cell:first'));
                                }
                            }
                            $('.env-grid_row:eq(' + overlayRowIndex + ')').each(function (i) {
                                if(i > 0 || typeof implicitFlag !== 'undefined') {
                                    $(this).addClass('jqs-cell-selected');
                                    $(this).find('.env-grid_co1umn:eq(' + overlayColumnIndex + ')').addClass('jqs-selected-cell');
                                }
                            });
                        }
                    }
                }
                if(!openTop) {
                    $('.jqs-cell-selected:first .jqs-selected-cell').addClass('selected-border-top');
                }
                $('.jqs-cell-selected').each(function (i) {
                    $(this).find('.jqs-selected-cell:last').addClass('selected-border-right');
                    $(this).find('.jqs-selected-cell:first').addClass('selected-border-left');
                });
                if(!openBottom) {
                    $('.jqs-cell-selected:last .jqs-selected-cell').addClass('selected-border-bottom');
                }
                break;
            default:
                self._makeCellActive();
        }
    },

    _clearSelection: function(keepSelection) {
        if(typeof keepSelection === 'undefined' || !keepSelection) {
            this._resetSelectedCells();
        }
        $('.jqs-row-selected').removeClass('jqs-row-selected');
        $('.jqs-co1umn-selected').removeClass('jqs-co1umn-selected');
        $('.jqs-cell-selected').removeClass('jqs-cell-selected');
        $('.jqs-selected-cell').removeClass('jqs-selected-cell');
        $('.selected-border-top').removeClass('selected-border-top');
        $('.selected-border-right').removeClass('selected-border-right');
        $('.selected-border-bottom').removeClass('selected-border-bottom');
        $('.selected-border-left').removeClass('selected-border-left');
    },

    /*
     element = typeof element === 'undefined' ? $('.jqs-editable')[0] : element;
     caretPosition = typeof caretPosition === 'undefined' ? 0 : caretPosition;
     if(caretPosition == 'end') {
     caretPosition = $(element).text().length;
     }
     var range = document.createRange();
     var sel = window.getSelection();
     range.setStart(element.childNodes[0], caretPosition);
     range.collapse(true);
     sel.removeAllRanges();
     sel.addRange(range);
     element.focus();
     */
    _setCaret: function(caretPosition, element) {
        element = typeof element === 'undefined' ? $('.jqs-editable')[0] : element;
        caretPosition = typeof caretPosition === 'undefined' ? 0 : caretPosition;
        if(caretPosition == 'end') {
            caretPosition = $(element).text().length;
        }
        if(element) {
            if($(element).text() != '') {
                var range = document.createRange();
                var sel = window.getSelection();
                range.setStart(element.childNodes[0], caretPosition);
                range.collapse(true);
                sel.removeAllRanges();
                sel.addRange(range);
            }
            element.focus();
        }
    },

    showColumnMapper: function() {
        this._showColumnMapper();
        this._selectColumnMappers();
    },

    hideColumnMapper: function() {
        this._finalizeColumnMapping();
    },

    /**
     * JS Doc - TODO
     * @returns {*}
     */
    uploadGridData: function(data, showColumnMapper) {
        this._extractColumnMapperData(data);
        this._loadGridData(data);
        if(typeof showColumnMapper !== 'undefined' && showColumnMapper == true) {
            this._showColumnMapper(this._columnMapperData);
            if(this._hasDefaultTemplate()) {
                this._finalizeColumnMapping();
                $('.gridOverlay .tooltip').remove();
                this._resetMappingMode();
            }
        }
    },

    _columnMapperData: [],

    _extractColumnMapperData: function(data) {
        this._columnMapperData = data[0];
        data.splice(0, 1);
        if(!this._hasDefaultTemplate()) {
            this._columnMapperData = [];
        }
    },

    _hasDefaultTemplate: function() {
        for(var i = 0; i < this._columnMapperData.length; i ++) {
            if(isNaN(parseInt(this._columnMapperData[i])) || parseInt(this._columnMapperData[i]) == -1) {
                return false;
            }
        }
        return this._columnMapperData.length > 0;
    },

    /**
     * Public method used to swap two columns. This method will swap the column headers as well
     * @param fromIdx - The index of the first column that needs to be swapped
     * @param toIdx - The index of the first column that needs to be swapped
     */
    swapColumns: function(fromIdx, toIdx) {
        var idx1 = fromIdx > toIdx ? fromIdx : toIdx;
        var idx2 = fromIdx < toIdx ? fromIdx : toIdx;
        this._swapColumnHeader(idx1, idx2);
        var gridData = this.getGridData();
        var data1 = this._inverseArray(gridData, this._getDefaultGridDataArrayDimension());
        var temp = data1[fromIdx];
        data1[fromIdx] = data1[toIdx];
        data1[toIdx] = temp;
        this._gridData = this._inverseArray(data1, this._getDefaultGridDataArrayDimension());
        this._gridDataChanged();

    },

    /**
     * JS Doc - TODO
     * @returns {*}
     */
    showGrid: function(dataGroupMode, rowIndex) {
        var self = this;
        $(window).scrollTop(0);
        this.element.parent().show();

        // Not sure if this is the best place to initiate dropdowns... the reason we have this here
        // is because we need the offsets of the parent and child elements.  Can't do that when the container is display none.
        this._loadDropdowns();

        $('.gridOverlayBg').removeClass('grid-hide');
        $('.jqs-editable').trigger('click');
        this._setCaret(this._getCaretPosition());
        $('body').removeClass('texelGridScrollRemoval').addClass('texelGridScrollRemoval');
        if(typeof rowIndex !== 'undefined' && parseInt(rowIndex) > 0) {

            $('.env-grid_vertical_scrollbar').scrollTo(parseInt((rowIndex - 1) * this._getScrollFactor()));
            setTimeout(function(){
                self._activeCell = [rowIndex - 1, 0];
                self._makeCellActive();}, 500);

        } else {
            if(dataGroupMode == 'existing') {
                this._showExistingDataGroups();
            } else if(dataGroupMode == 'upload') {
                this._showFileUpload();
            }
        }
    },

    /**
     * Method to reload the grid with a different groupId
     * @param groupId
     */
    reloadGrid: function(groupId) {
        if(typeof groupId == 'undefined') {
            groupId = -1;
        }
        this._loadDataGroup(groupId);
    },

    /**
     * JS Doc - TODO
     * @returns {*}
     */
    hideGrid: function() {
        this.element.parent().hide();
        $('.gridOverlayBg').addClass('grid-hide');
        $('body').removeClass('texelGridScrollRemoval');
    },

    saveDataGroupName: function(name) {
        if(this._getDataGroup().name != name) {
            var dataGroup = {'name': name};
            this._saveDataGroup(dataGroup);
        }
    },

    /**
     * The method used to save the variableDataGroup. (1)If this method is called without the variableDataGroup parameter,
     * this method assume that we are trying to save the entire variableDataGroup data on the grid.(2) If this method is
     * called with the variableDataGroup parameter and the current variableDataGroupId is 0, we will still save the entire
     * variableDataGroup data on the grid to the server.(3)If this method is called with the variableDataGroup parameter
     * and the variableDataGroupId is greater than 0, then it can be either saving the name of the variableDataGroup
     * and/or saving the data of one entire row.
     *
     * @param variableDataGroup
     * @private
     */
    _saveDataGroup: function(dataGroup, callback) {
        var self = this;
        // Saving the entire variableDataGroup data on the grid after finalizeMapping followed by data upload
        if(dataGroup == null) { // Finalize Mapping
            dataGroup = {};
            dataGroup.name = this._getDataGroupName();
            this._setRowDataToDataGroup(dataGroup, true, false);
            // Save the variableDataGroup on the server only if the grid has data. (don't create empty grid on the server)
            if(dataGroup.batchedRowData.length > 0 &&  dataGroup.batchedRowData[0].length > 0) {
                this._saveDataGroupOnServer(dataGroup, callback);
            }
        } else if (Object.keys(dataGroup).length == 0){ //Apply or onRowDataChange
            this._setRowDataToDataGroup(dataGroup, true, true);
            if(this._getDataGroupId() == 0) {
                dataGroup.name = this._getDataGroupName();
            }
            // Save the variableDataGroup on the server only if the grid has data. (don't create empty grid on the server)
            if(dataGroup.batchedRowData.length > 0 &&  dataGroup.batchedRowData[0].length > 0) {
                this._saveDataGroupOnServer(dataGroup, callback);
            } else {
                if(typeof callback !== 'undefined') {
                    callback(self);
                }
            }
        } else if(Object.keys(dataGroup).length == 1 && Object.keys(dataGroup)[0] == 'name') { // Name change for new data group or existing data group
            this._setRowDataToDataGroup(dataGroup, true, true);
            // Save the variableDataGroup on the server only if this is an existing grid or a new grid with data. (don't create empty grid on the server)
            if(this._getDataGroupId() > 0 || (dataGroup.batchedRowData.length > 0 &&  dataGroup.batchedRowData[0].length > 0)) {
                this._saveDataGroupOnServer(dataGroup, callback);
            }
        }
        self._showServiceCharge();
    },

    _setRowDataToDataGroup: function(dataGroup, batchedFlag, useBufferFlag) {
        var batchSize = this.options.persistenceBatchSize;
        if(typeof batchedFlag === 'undefined' || batchedFlag == false) {
            if(useBufferFlag == false) {
                dataGroup.batchedRowData = [this.getFilteredGridData(true)];
                dataGroup.batchedRowDataIndices = [];
                for(var i = 0; i < dataGroup.batchedRowData.length; i ++) {
                    dataGroup.batchedRowDataIndices[i] = i;
                }
            } else if(useBufferFlag == true) {
                dataGroup.batchedRowData = [this._unsavedRowDataBuffer.rowData];
                if(this._unsavedRowDataBuffer.indices.length > 0) {
                    dataGroup.batchedRowDataIndices = [this._unsavedRowDataBuffer.indices];
                } else {
                    var rowDataIndices = [];
                    for(var i = 0; i < dataGroup.batchedRowData.length; i ++) {
                        rowDataIndices[i] = i;
                    }
                    dataGroup.batchedRowDataIndices = [rowDataIndices];
                }
            }
        } else if(batchedFlag == true) {
            if(useBufferFlag == false) {
                var rowData = this.getFilteredGridData(true);
                var batchedRowData = [];
                var batchedRowDataIndices = [];
                for(var i = 0, j = 0, k = 0; i < rowData.length; i ++) {
                    if(i == 0) {
                        batchedRowData[0] = [];
                        batchedRowDataIndices[0] = [];
                    }
                    batchedRowData[j][k] = rowData[i];
                    batchedRowDataIndices[j][k++] = i;
                    if(k == batchSize && i < rowData.length - 1) {
                        j++;
                        batchedRowData[j] = [];
                        batchedRowDataIndices[j] = [];
                        k = 0;
                    }
                }
                dataGroup.batchedRowData = batchedRowData;
                dataGroup.batchedRowDataIndices = batchedRowDataIndices;
            } else if(useBufferFlag == true) {
                var rowData = this._unsavedRowDataBuffer.rowData;
                var rowDataIndices = this._unsavedRowDataBuffer.indices;
                var batchedRowData = [];
                var batchedRowDataIndices = [];
                for(var i = 0, j = 0, k = 0; i < rowData.length; i ++) {
                    if(i == 0) {
                        batchedRowData[0] = [];
                        batchedRowDataIndices[0] = [];
                    }
                    batchedRowData[j][k] = rowData[i];
                    if(rowDataIndices.length > 0) {
                        batchedRowDataIndices[j][k++] = rowDataIndices[i];
                    } else {
                        batchedRowDataIndices[j][k++] = i;
                    }

                    if(k == batchSize && i < rowData.length - 1) {
                        j++;
                        batchedRowData[j] = [];
                        batchedRowDataIndices[j] = [];
                        k = 0;
                    }
                }
                dataGroup.batchedRowData = batchedRowData;
                dataGroup.batchedRowDataIndices = batchedRowDataIndices;
            }
        }
    },

    _saveDataGroupOnServerInBatch: function(self, dataGroup, batchedRowData, batchedRowDataIndices, numberOfBatches, batchCount, callback) {
        if(dataGroup.id == 0) {
            dataGroup.id = this._getDataGroupId();
        }

        dataGroup.rowData = JSON.stringify(batchedRowData.length > 0 ? batchedRowData[0] : batchedRowData);
        dataGroup.rowDataIndices = JSON.stringify(batchedRowDataIndices.length > 0 ? batchedRowDataIndices[0] : batchedRowDataIndices);
        $.ajax({
            url: self.options.syncToServerEndpoint,
            method: 'POST',
            data: dataGroup,
            dataType: 'json'
        }).done(function(data) {
            if(data.success) {
                self._setDataGroupId(data);
                if(numberOfBatches > 1) {
                    self._updateProgressBar('saveData', self._calculateProgressPercentage(numberOfBatches, batchCount + 1), true);
                }
                if(batchCount == 0) {
                    self._updateSavedDataGroupNames(data.dataGroupId, dataGroup.name);
                }

                if(batchedRowData.length > 1) {
                    batchedRowData.shift();
                    batchedRowDataIndices.shift();
                    self._saveDataGroupOnServerInBatch(self, dataGroup, batchedRowData, batchedRowDataIndices, numberOfBatches, ++batchCount, callback);
                } else if(typeof callback !== 'undefined') {
                    callback(self);
                }
            }
        });
    },

    _saveDataGroupOnServer: function(dataGroup, callback) {
        var self = this;
        dataGroup.id = this._getDataGroupId();
        dataGroup.attributeSet = this._getDataGroupAttributeSet();
        dataGroup.attributes = JSON.stringify(this._getDataGroupAttributes());
        dataGroup.sessionId = this._getSessionId$();
        dataGroup.partyId = this._getPartyId$();
        dataGroup.clientId = this._getClientId$();

        var batchedRowData = dataGroup.batchedRowData;
        delete dataGroup.batchedRowData;
        var batchedRowDataIndices = dataGroup.batchedRowDataIndices;
        delete dataGroup.batchedRowDataIndices;

        if(batchedRowData.length > 1) {
            self._showProgressBar('saveData', 0);
        }

        self._saveDataGroupOnServerInBatch(self, dataGroup, batchedRowData, batchedRowDataIndices, batchedRowData.length, 0, callback);
    },

    _calculateProgressPercentage: function(total, completed) {
        return parseInt((completed * 100) / total);
    },

    _showHintBalloon: function(el, delayInMilliSeconds) {
        $(el).tooltips();
        setTimeout(function(){$(el).trigger('show');}, delayInMilliSeconds);

    },

    _hideHintBalloon: function(el) {
        $(el).tooltips();
        $(el).trigger('close');
    }
    /*###################################################################################################################################################################*/

    /*###################################################################################################################################################################*/

});


(function( $ ) {

    // Create plugin
    $.fn.tooltips = function(el) {

        var $tooltip,
            $body = $('body'),
            $el;

        // Ensure chaining works
        return this.each(function(i, el) {

            $el = $(el).attr("data-tooltip", i);

            $('.gridOverlay .tooltip[data-tooltip="' + i + '"]').remove();
            // Make DIV and append to page
            var $tooltip = $('<div class="tooltip" data-tooltip="' + i + '">' + $el.attr('data-hint') + '<div class="arrow"></div></div>').appendTo($('.gridOverlay'));

            // Position right away, so first appearance is smooth
            var linkPosition = $el.position();

            $tooltip.css({
                top: linkPosition.top - $tooltip.outerHeight() + 20,
                left: linkPosition.left - ($tooltip.width()/2) + 90
            });

            $el
                // Get rid of yellow box popup
                //.removeAttr("title")

                // Mouseenter
                .on('show', function() {
                    $el = $(this);

                    $tooltip = $('div[data-tooltip=' + $el.data('tooltip') + ']');

                    // Reposition tooltip, in case of page movement e.g. screen resize
                    var linkPosition = $el.position();

                    $tooltip.css({
                        top: linkPosition.top - $tooltip.outerHeight() + 20,
                        left: linkPosition.left - ($tooltip.width()/2) + 90
                    });

                    // Adding class handles animation through CSS
                    $tooltip.addClass("active");

                    // Mouseleave
                })
                .on('close', function(){

                    $el = $(this);

                    // Temporary class for same-direction fadeout
                    $tooltip = $('div[data-tooltip=' + $el.data('tooltip') + ']').addClass("out");

                    // Remove all classes
                    setTimeout(function() {
                        $tooltip.removeClass("active").removeClass("out");
                    }, 300);
                })
                ;

        });

    }

})(jQuery);
