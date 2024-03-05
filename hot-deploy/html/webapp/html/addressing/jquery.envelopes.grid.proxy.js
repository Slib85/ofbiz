/**
 * Created by Manu on 10/1/2015.
 */

$.widget("envelopes.DataGridProxy", $.envelopes.DataGrid, {
    _separator: function(logLevel) {
        this._log(logLevel, '_____________________________________________________________________________________________________________________________________');
        this._log(logLevel, '');
    },
    _create: function() {
        this._separator(this.INFO);
        this._log(this.INFO, 'Creating the DataGrid Widget ...', 1);
//        this._log(this.options, 1, 'options');
        this._super();
        this._log(this.INFO, 'Creating the DataGrid Widget is complete !', -1);
        this._separator(this.INFO);
    },
    _renderGridOverlay: function() {
        this._separator(this.INFO);
        this._log(this.INFO, 'Rendering the Grid Overlay ...', 1);
        this._super();
        this._log(this.INFO, 'Rendering the Grid Overlay is complete !', -1);
        this._separator(this.INFO);
    },
    _buildToolbarElement: function() {
        this._separator(this.INFO);
        this._log(this.INFO, 'Building the Toolbar ...', 1);
        var returnValue = this._super();
        this._log(this.INFO, 'Building the Toolbar is complete !', -1);
        this._separator(this.INFO);
        return returnValue;
    },
    _generateColumnHeaderRowHTML: function() {
        this._separator(this.INFO);
        this._log(this.INFO, 'Generating the ColumnHeader row ...', 1);
        var returnValue = this._super();
        this._log(this.INFO, 'Generating the ColumnHeader row is complete !', -1);
        this._separator(this.INFO);
        return returnValue;
    },
    _generateRowNumberColumnHTML: function() {
        this._separator(this.INFO);
        this._log(this.INFO, 'Generating the RowNumber Column ...', 1);
        var returnValue = this._super();
        this._log(this.INFO, 'Generating the RowNumber Column is complete !', -1);
        this._separator(this.INFO);
        return returnValue;
    },
    _buildScrollBarElement: function() {
        this._separator(this.INFO);
        this._log(this.INFO, 'Building the Scrollbar ...', 1);
        var returnValue = this._super();
        this._log(this.INFO, 'Building the Scrollbar is complete !', -1);
        this._separator(this.INFO);
        return returnValue;
    },
    _addDefaultColumnHeader: function() {
        this._separator(this.INFO);
        this._log(this.INFO, 'Adding default ColumnHeader cells to the ColumnHeader row ...', 1);
        this._super();
        this._log(this.INFO, 'Adding default ColumnHeader cells to the ColumnHeader row is complete !', -1);
        this._separator(this.INFO);
    },
    _addColumnHeaderCell: function(columnIndex, columnTitle) {
        this._separator(this.DEBUG);
        this._log(this.DEBUG, 'Adding ColumnHeader cell to the ColumnHeader row ...', 1);
        this._log(this.DEBUG, "*arg[columnIndex]: " + columnIndex, 1);
        this._log(this.DEBUG, "*arg[columnTitle]: " + columnTitle, -1);
        this._super(columnIndex, columnTitle);
        this._log(this.DEBUG, 'Adding ColumnHeader cell to the ColumnHeader row is complete !', -1);
        this._separator(this.DEBUG);
    },
    _getDefaultColumnTitle: function(columnIndex) {
        this._separator(this.DEBUG);
        this._log(this.DEBUG, 'Getting DefaultColumnTitle ...', 1);
        this._log(this.DEBUG, "*arg[columnIndex]: " + columnIndex, 0);
        var returnValue = this._super(columnIndex);
        this._log(this.DEBUG, 'Got DefaultColumnTitle :' + returnValue + ' !', -1);
        this._separator(this.DEBUG);
        return returnValue;
    },
    _addRowsToGridOverlay: function(numberOfRows) {
        this._separator(this.INFO);
        this._log(this.INFO, 'Adding rows to GridOverlay ...', 1);
        this._log(this.INFO, "*arg[numberOfRows]: " + numberOfRows, 0);
        var returnValue = this._super(numberOfRows);
        if(typeof returnValue === 'undefined') {
            this._log(this.INFO, 'Added ' + numberOfRows + ' rows to the GridOverlay !', -1);
        } else {
            this._log(this.INFO, 'The GridOverlay only supports ' + this._getGridOverlayMaxRows() + 'rows. Only ' + returnValue + 'rows added !', -1);
        }
        this._separator(this.INFO);
    },
    _addRowToGridOverlay: function() {
        this._separator(this.DEBUG);
        this._log(this.DEBUG, 'Adding a new row to the GridOverlay ...', 1);
        this._super();
        this._log(this.DEBUG, 'Added a new row to the GridOverlay !', -1);
        this._separator(this.DEBUG);
    },
    _addRowNumberCellToGridOverlay: function() {
        this._separator(this.DEBUG);
        this._log(this.DEBUG, 'Adding a new RowNumber cell to the GridOverlay ...', 1);
        this._super();
        this._log(this.DEBUG, 'Added a new RowNumber cell to the GridOverlay !', -1);
        this._separator(this.DEBUG);
    },
    _buildOverlayRowCells: function(numberOfCells) {
        this._separator(this.DEBUG);
        this._log(this.DEBUG, 'Adding cells to the GridOverlay row ...', 1);
        this._log(this.DEBUG, "*arg[numberOfCells]: " + numberOfCells, 0);
        var returnValue = this._super(numberOfCells);
        this._log(this.DEBUG, 'Added ' + numberOfCells + ' cells to the GridOverlay row !', -1);
        this._separator(this.DEBUG);
        return returnValue;
    },
    //TODO
    _initializeGridData: function() {
        this._separator(this.INFO);
        this._log(this.INFO, 'Initializing the GridData ...', 1);
        this._super();
        this._log(this.INFO, 'Initializing the GridData complete !', -1);
        this._separator(this.INFO);
    },
    _addColumnToGridOverlay: function(columnIndex, columnTitle) {
        this._separator(this.INFO);
        this._log(this.INFO, 'Adding a new column to the GridOverlay at index ' + columnIndex + ' ...', 1);
        this._log(this.INFO, "*arg[columnIndex]: " + columnIndex, 0);
        this._log(this.INFO, "*arg[columnTitle]: " + columnTitle, 0);
        this._super(columnIndex, columnTitle);
        this._log(this.INFO, 'Added a new column to the GridOverlay at index ' + columnIndex + ' !', -1);
        this._separator(this.INFO);
    },
    _addColumnToGridData: function(columnIndex) {
        this._separator(this.INFO);
        this._log(this.INFO, 'Adding a new column to the GridData at index ' + columnIndex + ' ...', 1);
        this._log(this.INFO, "*arg[columnIndex]: " + columnIndex, 0);
        this._super(columnIndex);
        this._log(this.INFO, 'Added a new column to the GridData at index ' + columnIndex + ' !', -1);
        this._log(this.DEBUG, this.getGridData());
        this._separator(this.INFO);
    },
    _addRowsToGridData: function() {
        this._separator(this.INFO);
        this._log(this.INFO, 'Adding ' + this._getDataAddRowSize() + ' new rows to GridData ...', 1);
        this._super();
        this._log(this.INFO, 'Added ' + this._getDataAddRowSize() + ' new rows to GridData ...', -1);
        this._log(this.DEBUG, this.getGridData());
        this._separator(this.INFO);
    },
    _setVerticalScrollHeight: function() {
        this._separator(this.INFO);
        this._log(this.INFO, 'Setting the vertical scroll height ...', 1);
        this._super();
        this._log(this.INFO, 'Set the vertical scroll height to ' + (this._currentDataRowCount() * parseInt(this._getCellHeight())) + 'px !', -1);
        this._separator(this.INFO);
    },
    _renderData: function() {
        this._separator(this.DEBUG);
        this._log(this.DEBUG, 'Rendering the GridData ...', 1);
        var returnValue = this._super();
        this._log(this.DEBUG, 'Rendered the GridData based on RowOffSet  ' + returnValue + '!', -1);
        this._separator(this.DEBUG);
        return returnValue;
    },
    scrollTo: function(rowOffSet) {
        this._separator(this.DEBUG);
        this._log(this.DEBUG, 'Scrolling to rowOffSet ' + rowOffSet + ' ...', 1);
        this._log(this.INFO, "*arg[rowOffSet]: " + rowOffSet, 0);
        var returnValue = this._super(rowOffSet);
        this._log(this.DEBUG, 'Scrolled to rowOffSet ' + returnValue + ' !', -1);
        this._separator(this.DEBUG);
        return returnValue;
    },
    _currentDataRowCount: function() {
        return this._super();
    }


});
