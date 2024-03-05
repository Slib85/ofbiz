/**
 * Created by Manu on 7/30/2015.
 */
if(!window.CoreLibrary) {
    alert('The envelopes-library.js must be included in order to use this library');
}

eval(LibraryUtil.importRelative("envelopes-object-util.js"));


if(!window.CollectionLibrary) {
    window.CollectionLibrary = {};

    List = function(initialArray) {
        BaseObject.call(this);

        this.elements = (((typeof initialArray == "undefined") || (initialArray == null)) ? new Array() : initialArray);

        if(List.functionsDefined) {
            return;
        }

        List.functionsDefined = true;

        List.prototype.size = function() {
            return this.elements.length;
        };

        List.prototype.add = function(obj) {
            this.elements.push(obj);
            return this.elements.length;
        };

        List.prototype.addAtIndex = function(index, obj) {

            if(index >= 0) {
                ObjectUtil.insertArrayElement(this.elements, index, obj);
            }
            return this.elements.length;
        };

        List.prototype.get = function(index) {
            if(index >= this.size() || index < 0) {
                return null;
            } else {
                return this.elements[index];
            }
        };

        List.prototype.set = function(index, obj) {
            if(index >= this.size() || index < 0) {
                return null;
            }

            var previousObject = this.get(index);
            this.elements[index] = obj;

            return previousObject;
        };

        List.prototype.remove = function(obj) {
            if(ObjectUtil.isNull(obj)) {
                return false;
            }

            var indexToRemove = -1;
            if (Array.prototype.indexOf) {
                indexToRemove = this.elements.indexOf(obj);
            }

            if(indexToRemove >= 0) {
                ObjectUtil.removeArrayElement(this.elements, indexToRemove);
                return true;
            }

            return false;
        };

        List.prototype.removeAll = function(list) {
            this.elements = new Array();
        };

        List.prototype.removeAtIndex = function(index) {
            var obj = this.get(index);

            if(!ObjectUtil.isNull(obj)) {
                this.remove(obj);
            }

            return obj;
        };

        List.prototype.iterator = function() {
            return new Iterator(this);
        };

        List.prototype.getClassName = function() {
            return "List";
        };
    };

    List._extends(BaseObject);

    new List();

    //TODO - not complete
    Grid = function(dimension) {
        BaseObject.call(this);


        this.rowCount = dimension[0];
        this.columnCount = dimension[1];

        this.gridElements = new Array(this.rowCount);

        for(var i = 0; i < this.rowCount; i ++) {
            this.gridElements[i] = new Array(this.columnCount);
        }

        if(Grid.functionsDefined) {
            return;
        }

        Grid.functionsDefined = true;

        this.checkXDimension = function(count) {
            if(this.rowCount < count) {

            }
        };

        this.checkYDimension = function(count) {
            if(this.columnCount < count) {

            }
        };

        this.expandAllColumns = function(count) {
            for(var i = 0; i < this.rowCount; i ++) {
                for(var j = 0; j < count; j ++) {

                }
            }
        };

        Grid.prototype.rows = function() {
            return this.rowCount;
        };

        Grid.prototype.columns = function() {
            return this.columnCount;
        };

        Grid.prototype.getColumn = function(rowIndex, columnIndex) {
            return this.gridElements[rowIndex][columnIndex];
        };

        Grid.prototype.setColumn = function(rowIndex, columnIndex, value) {
            this.gridElements[rowIndex][columnIndex] = value;
        };

        Grid.prototype.setColumns = function(rowIndex, values, columnIndexOffSet) {
            columnIndexOffSet = ObjectUtil.isDefined(columnIndexOffSet) ? columnIndexOffSet : 0;
            for(var i = 0; i < values.length; i ++) {
                this.setColumn(rowIndex, i + columnIndexOffSet, values[i]);
            }
        };

    };

    Grid._extends(BaseObject);

    new Grid();

    Iterator = function(list) {
        BaseObject.call(this);

        this.list = list || new List();

        this.index = 0;

        if(Iterator.functionsDefined) {
            return;
        }

        Iterator.functionsDefined = true;

        Iterator.prototype.hasNext = function() {
            return (this.index < this.list.size());
        };

        Iterator.prototype.next = function() {
            if(this.index < this.list.size()) {
                this.index++;
                return this.list.get(this.index - 1);
            } else {
                return false;
            }
        };

        Iterator.prototype.getClassName = function() {
            return "Iterator";
        };
    };

    Iterator._extends(BaseObject);


}