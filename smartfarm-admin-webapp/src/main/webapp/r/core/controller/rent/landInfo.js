
var app = angular.module("myApp", ["ui.bootstrap"]);
var submitNow = false;
app.controller("myCtrl", function($scope,$http) {
			
	$scope.common = {
		showAdd:false,
		showEdit:false,
		showAcreage:false,
		showShip:false
		
	};	
	
	$scope.statusList = [
	             		{id:1,name:'上架'},
	             		{id:2,name:'下架'}
	             	];
	
	$scope.formData = {
		total:0,
		pageSize:10,
		pageNumber:1,
		name:'',
		searchStatus:'',
		status:'',
		landInfoId:''		
	}	
	
	//存储可种植种子信息
	$scope.seedInfos=[];
	//存储地块信息
	$scope.acreageInfoList = [];	
	//存储配送周期信息
	$scope.shipList= [];	
	
	$scope.showAddMsg=function(){
		$scope.getLandTypesAndSeedInfos();
		$scope.common.showAdd=true;
	}
	
	$scope.closeAddMsg=function(){
		$scope.common.showAdd=false;
	}
	
	$scope.showEditMsg=function(landInfoId){		
		$scope.formData.landInfoId=landInfoId;
		$http({
	        method  : 'POST',
	       	url     : '/landInfo/getLandInfoEdit.htm',
	        params:{
	            'id':landInfoId
	        },
        	headers : {'Content-Type' : 'application/x-www-form-urlencoded'}  //angularjs设置文件上传的content-type修改方式		
	    }).then(function(response) {
	    	if(!response.data.success) {
				Notify("操作失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				return;
			}
	    	$scope.landInfoEdit = response.data.landInfo;
	    	$scope.landTypes = response.data.landTypes;
	    	$scope.seedInfos = response.data.seedInfos;
	    });		
		$scope.common.showEdit=true;
	}
	
	$scope.closeEditMsg=function(){
		$scope.common.showEdit=false;
	}	
	
	//显示土块管理弹窗
	$scope.showAcreageMsg=function(landId,status){
		$scope.formData.landInfoId=landId;
		$scope.formData.status=status;
		$http({
	        method  : 'POST',
	       	url     : '/landInfo/getAcreageInfoList.htm',
	        params:{
	            'landId':landId
	        },
        	headers : {'Content-Type' : 'application/x-www-form-urlencoded'}  //angularjs设置文件上传的content-type修改方式		
	    }).then(function(response) {
	    	if(!response.data.success) {
				Notify("操作失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				return;
			}
	    	$scope.acreageInfoList = response.data.acreageInfoList;
	    	$scope.common.showAcreage=true;
	    });
	}
	
	$scope.closeAcreageMsg=function(){
		$scope.common.showAcreage=false;
	}	
	//显示配送周期弹窗
	$scope.showShipMsg=function(landId,status){
		$scope.formData.landInfoId=landId;	
		$scope.formData.status=status;
		$http({
	        method  : 'POST',
	       	url     : '/landInfo/getshipList.htm',
	        params:{
	            'landId':landId
	        },
        	headers : {'Content-Type' : 'application/x-www-form-urlencoded'}  //angularjs设置文件上传的content-type修改方式		
	    }).then(function(response) {
	    	if(!response.data.success) {
				Notify("操作失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				return;
			}
	    	$scope.shipList = response.data.shipList;
	    	$scope.common.showShip=true;
	    });
	}
	
	$scope.closeShipMsg=function(){
		$scope.common.showShip=false;
	}
	
	$scope.pageChanged = function() {
		$http({
	        method  : 'POST',
	       	url     : '/landInfo/queryLandInfoPageList.htm',
	        data    : $.param($scope.formData),
	        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
	    }).then(function(response) {
	    	if(!response.data.success) {
				Notify("操作失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				return;
			}
	    	$scope.list = response.data.landInfoList;
	    	$scope.formData.total = response.data.total;
	    });
	}	
	$scope.pageChanged();
	
	//获得土地的分类和种子信息
	$scope.getLandTypesAndSeedInfos = function() {
		$http({
	        method  : 'POST',
	        url     : '/landInfo/queryLandTypesAndSeedInfos.htm',
	        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
	    }).then(function(response) {
	    	$scope.landTypes = response.data.landTypes;
	    	$scope.seedInfos = response.data.seedInfos;
	    });
	}
		
	//改变状态
	$scope.changeStatus = function(id){
		$http({
		 	method  : 'GET',
	        url     : '/landInfo/changeStatus.htm',
	        params:{
	            'landId':id
	        },
        	headers : {'Content-Type' : 'application/x-www-form-urlencoded'}  //angularjs设置文件上传的content-type修改方式
		}).then(function(response){
			if(!response.data.success) {
				Notify("请先添加土块和配送周期！", 'top-right', '4000', 'danger', 'fa-bolt', true);			
				return;
			}		
			$scope.pageChanged();
		});
	}
	
	
	//提交新增
	$scope.submitAdd = function() {
		if(submitNow) {
			return;
		}
	
		if($scope.landInfoAdd.name == '') {
			Notify("请输入土地名称！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}		
		
		if($scope.landInfoAdd.period == '') {
			Notify("请输入租地周期！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		
		if($scope.landInfoAdd.shipTimes == '') {
			Notify("请输入至少配送次数！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}		
		
		if($scope.landInfoAdd.brief == '') {
			Notify("请输入简介！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		
		if($scope.landInfoAdd.typeId == '') {
			Notify("请输入土地分类！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
							
		var addCoverImg = document.querySelector('input[name="addCoverImg"]').files;
		if(addCoverImg.length == 0){
			Notify("请上传封面！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		
		if(document.querySelector('input[name="addDescImg"]').files.length==0){
			Notify("请上传详细信息！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		
		if($scope.landInfoAdd.sort == '') {
			Notify("请输入排序！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		
		var fd = new FormData();
		fd.append("name", $scope.landInfoAdd.name);
		fd.append("period", $scope.landInfoAdd.period);
		fd.append("shipTimes", $scope.landInfoAdd.shipTimes);
		fd.append("sort", $scope.landInfoAdd.sort);
		fd.append("typeId", $scope.landInfoAdd.typeId);
		fd.append("brief", $scope.landInfoAdd.brief);

		fd.append("seedInfosJson",JSON.stringify($scope.seedInfos));
		
		fd.append("file",  document.querySelector('input[name="addCoverImg"]').files[0]);
		for(var i=0; i < document.querySelector('input[name="addDescImg"]').files.length; i++) {
		       fd.append("files", document.querySelector('input[name="addDescImg"]').files[i]);
		     }
		
		submitNow = true;
		$http({
		 	method  : 'POST',
	        url     : '/landInfo/addLandInfo.htm',
	        data : fd,
            transformRequest: angular.identity,
        	headers : {'Content-Type' : undefined}  //angularjs设置文件上传的content-type修改方式
		}).then(function(response){
			if(!response.data.success) {
				Notify("操作失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				submitNow = false;
				return;
			}

			Notify('添加成功', 'top-right', '5000', 'success', 'fa-check', true);
			$scope.common.showAdd = false;
			$scope.pageChanged();
			submitNow = false;					
			
			$scope.landInfoAdd = {
				name:'',
				period:'',
				sort:'',
				shipTimes:'',
				typeId:'',
				brief:'',
				description:''
			};
			$(".fileinput-remove-button").click();
		});
	}
	
	
	
	//提交编辑
	$scope.submitEdit = function() {
		if(submitNow) {
			return;
		}
		var fd = new FormData();
		fd.append("id", $scope.formData.landInfoId);
		fd.append("name", $scope.landInfoEdit.name);
		fd.append("period", $scope.landInfoEdit.period);
		fd.append("shipTimes", $scope.landInfoEdit.shipTimes);
		fd.append("sort", $scope.landInfoEdit.sort);
		fd.append("typeId", $scope.landInfoEdit.typeId);
		fd.append("brief", $scope.landInfoEdit.brief);
		
		fd.append("seedInfosJson",JSON.stringify($scope.seedInfos));
		
		fd.append("file",  document.querySelector('input[name="editCoverImg"]').files[0]);
		for(var i=0; i < document.querySelector('input[name="editDescImg"]').files.length; i++) {
		       fd.append("files", document.querySelector('input[name="editDescImg"]').files[i]);
		     }
		
		submitNow = true;
		$http({
		 	method  : 'POST',
	        url     : '/landInfo/editLandInfo.htm',
	        data : fd,
            transformRequest: angular.identity,
        	headers : {'Content-Type' : undefined}  //angularjs设置文件上传的content-type修改方式
		}).then(function(response){
			if(!response.data.success) {
				Notify("操作失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				submitNow = false;
				return;
			}

			Notify('添加成功', 'top-right', '5000', 'success', 'fa-check', true);
			$scope.common.showEdit = false;
			$scope.pageChanged();
			submitNow = false;					
			
			$scope.landInfoEdit = {
				name:'',
				period:'',
				sort:'',
				shipTimes:'',
				typeId:'',
				brief:'',
				description:''
			};
			$(".fileinput-remove-button").click();
		});
	}

	
	//添加土块
	$scope.addAcreageInfo= function() {
		$scope.acreageInfoList.push({id:'',code:'',name:'',area:'',cost:'',status:1,landId:$scope.formData.landInfoId});
	}
	
	//删除土块信息 
	$scope.deleteAcreageInfo = function(index) {
		if($scope.acreageInfoList[index].id!=''){
			$http({
			 	method  : 'GET',
		        url     : '/landInfo/removeAcreageInfo.htm',
		        params:{
		            'id':$scope.acreageInfoList[index].id
		        },
	        	headers : {'Content-Type' : 'application/x-www-form-urlencoded'}  //angularjs设置文件上传的content-type修改方式
			}).then(function(response){
				
			});
		}		
		$scope.acreageInfoList.splice(index,1);
	}
	
	//提交土块信息保存
	$scope.submitAcreage=function(){
		var fd = new FormData();
		fd.append("acreageInfoListJson", JSON.stringify($scope.acreageInfoList));
		submitNow = true;
		$http({
		 	method  : 'POST',
	        url     : '/landInfo/saveAcreage.htm',	        
	        data :  fd,      
            transformRequest: angular.identity,
            headers : {'Content-Type' : undefined}  //angularjs设置文件上传的content-type修改方式 
		}).then(function(response){
			if(!response.data.success) {
				Notify("操作失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				submitNow = false;
				return;
			}		
			Notify('操作成功', 'top-right', '5000', 'success', 'fa-check', true);
			$scope.common.showAcreage= false;		
			submitNow = false;			
			$scope.acreageInfoList = [];
			
		});		
	}
		
	//添加配送周期
	$scope.addShip= function() {
		$scope.shipList.push({id:'',frequency:'',name:'',sort:'',landId:$scope.formData.landInfoId});
	}
	
	//删除配送周期
	$scope.deleteShip= function(index) {
		if($scope.shipList[index].id!=''){
			$http({
			 	method  : 'GET',
		        url     : '/landInfo/removeShipStrategy.htm',
		        params:{
		            'id':$scope.shipList[index].id
		        },
	        	headers : {'Content-Type' : 'application/x-www-form-urlencoded'}  //angularjs设置文件上传的content-type修改方式
			}).then(function(response){
				
			});
		}		
		$scope.shipList.splice(index,1);
	}
	
	//提交配送周期信息保存
	$scope.submitShip=function(){
		var fd = new FormData();
		fd.append("shipStrategyJson", JSON.stringify($scope.shipList));
		submitNow = true;
		$http({
		 	method  : 'POST',
	        url     : '/landInfo/saveShipStrategy.htm',	        
	        data :  fd,      
            transformRequest: angular.identity,
            headers : {'Content-Type' : undefined}  //angularjs设置文件上传的content-type修改方式 
		}).then(function(response){
			if(!response.data.success) {
				Notify("操作失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				submitNow = false;
				return;
			}		
			Notify('操作成功', 'top-right', '5000', 'success', 'fa-check', true);
			$scope.common.showShip= false;		
			submitNow = false;			
			$scope.shipList = [];
			
		});		
	}
	
});
