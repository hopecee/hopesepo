define(['durandal/composition'], function(composition){
	
	'use strict';

	function DataTable(){
		this.sortColumn = ko.observable(0);
		this.sortDirection = ko.observable(-1);
	}
	
	DataTable.prototype.activate = function(settings){
		this.columns = ko.observableArray();
		this.columnsKeys = ko.observableArray();
		this.rows = ko.observableArray();

		var me = this;
		this.settings = this._normalizeSettings(settings);

		if(ko.isObservable(this.settings.rows)){
			this.settings.rows.subscribe(function(newValue){
				me.rows(newValue);
				me.onRowsChange();
			});
			this.rows(this.settings.rows());
		}else{
			this.rows(this.settings.rows);
		}
		this.onRowsChange();
	};

	DataTable.prototype._normalizeSettings = function(settings){
		var defaults = {
			rows: [],
			lang: {},
			itemPerPage: 0,
			search: false,
			columnsBlackList: []
		};
		return $.extend({}, defaults, settings);
	};

	DataTable.prototype.onRowsChange = function(){
		var item = this.settings.rows()[0],
			key
		;
		this.columns([]);
		this.columnsKeys([]);
		for(key in item){
			if(item.hasOwnProperty(key)){
				if(this.settings.columnsBlackList.indexOf(key) === -1){
					this.columns.push(this.settings.lang[key]);
					this.columnsKeys.push(key);
				}
			}
		}
		
		this.sort();
		
		console.log('columns', this.columns());
		console.log('columnsKeys', this.columnsKeys());
	};
	
	DataTable.prototype.toggleSort = function(keyIx){
		this.sortColumn(keyIx);

		if(this.sortDirection() <= 0){
			this.sortDirection(1);
		}else{
			this.sortDirection(-1);
		}

		this.sort();
	};
	
	DataTable.prototype.sort = function(){
		var key = this.columnsKeys()[this.sortColumn()],
			me = this;
		this.rows.sort(function(a, b){
			if(a[key] > b[key]){
				return -1 * me.sortDirection();
			}else if(a[key] < b[key]){
				return 1 * me.sortDirection();
			}else{
				return 0;
			}
		});
	};
	
	DataTable.prototype._ = function(key, value){
		key = 'v_' + key;
		console.log('LANGING', key, value);
		if(this.settings.lang.hasOwnProperty(key) && this.settings.lang[key].hasOwnProperty(value)){
			return this.settings.lang[key][value];
		}else{
			return value;
		}
	};
		
	return DataTable;
});