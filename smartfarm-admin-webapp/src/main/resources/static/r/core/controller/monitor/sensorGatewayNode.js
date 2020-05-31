var app = angular.module("myApp", ["ui.bootstrap"]);
app.controller("myCtrl", function($scope,$http,$timeout) {
	var submitNow = false;
	//图片路径前缀
	$scope.picUrl= picurl;
	//控制模态框
	$scope.common = {
		showAdd:false,
		showEdit:false
	}
	//添加监控传感器参数
	$scope.sensorGatewayAdd = {
		productCode:'',
		nodeName:'',
		baseId:''
	}
	//查询列表参数
	$scope.formData = {
		total:0,
		pageSize:10,
		pageNumber:1
	}
	//展示新增框
	$scope.showAddMsg = function(){
		
		$scope.queryBaseList();
		
		$scope.common.showAdd = true
	}
	//展示编辑框
	$scope.showEdit = function(infoId){
		$scope.common.showEdit = true;
		
		$timeout(function(){
			$scope.queryBaseList();
		},100)
		
		$scope.querySensorInfoById(infoId);
	}
	//关闭新增框
	$scope.closeAddMsg = function(){
		$scope.common.showAdd = false
	}
	//关闭编辑框
	$scope.closeshowEdit = function(){
		$scope.common.showEdit = false
	}
	//初始化列表
	$scope.pageChanged = function() {
		$http({
	        method  : 'POST',
	        url     : '/monitor/queryMonitorGatewayList.htm',
	        data    : $.param($scope.formData),
	        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
	    }).then(function(response) {
	    	$scope.list = response.data.sensorGatewayList;
	    	$scope.formData.total = response.data.total;
	    });
	}
	
	$scope.pageChanged();
	
	//根据id得到传感器网关
	$scope.querySensorInfoById = function(id){
		$http({
	        method  : 'GET',
	        url     : '/monitor/querySensorGatewayById.htm',
	        params:{
	            'id':id
	        },
	        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
	    }).then(function(response) {
	    	$scope.sensorGatewayInfo = response.data.sensorGatewayInfo;
	    });
	}
	
	//得到所有基地信息
	$scope.queryBaseList = function(){
		$http({
	        method  : 'GET',
	        url     : '/farmInfo/queryAll.htm',
	        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
	    }).then(function(response) {
	    	$scope.baseList = response.data.list;
	    });
	}

	//需要增加根据当前网关所属的基地id获取当前网关的基地做编辑所用
	
	//新增
	$scope.submitAdd = function(){
		if($scope.sensorGatewayAdd.productCode == '') {
			Notify("产品编码不能为空！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		if($scope.sensorGatewayAdd.nodeName == '') {
			Notify("网关节点名称不能为空！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		var fd = new FormData();
		fd.append("productCode", $scope.sensorGatewayAdd.productCode);
		fd.append("nodeName", $scope.sensorGatewayAdd.nodeName);
		fd.append("baseId", $scope.sensorGatewayAdd.baseId);
		fd.append("businessId",$scope.sensorGatewayAdd.baseId);
		$http({
		 	method  : 'POST',
		 	url     : '/monitor/addSensorGateway.htm',
	        data : fd,     
            transformRequest: angular.identity,
	        headers : {'Content-Type' : undefined}
		}).then(function(response){
			if(!response.data.success) {
				Notify("操作失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				submitNow = false;
				return;
			}
		
			Notify('添加成功', 'top-right', '5000', 'success', 'fa-check', true);
			$scope.common.showAdd = false;
			$scope.pageChanged();
			$scope.sensorGatewayAdd = {
				productCode:'',
				nodeName:''
			}
		});			
	}
	
	//编辑传感器网关
	$scope.submitEdit = function(){
		if($scope.sensorGatewayInfo.productCode == '') {
			Notify("产品编码不能为空！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		if($scope.sensorGatewayInfo.nodeName == '') {
			Notify("网关节点名称不能为空！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		var fd = new FormData();
		fd.append("id", $scope.sensorGatewayInfo.id);
		fd.append("productCode", $scope.sensorGatewayInfo.productCode);
		fd.append("nodeName", $scope.sensorGatewayInfo.nodeName);
		fd.append("baseId", $scope.sensorGatewayInfo.baseId);
		fd.append("businessId",$scope.sensorGatewayAdd.baseId);
		$http({
		 	method  : 'POST',
		 	url     : '/monitor/editSensorGatewayById.htm',	        
	        data : fd,     
            transformRequest: angular.identity,
	        headers : {'Content-Type' : undefined}
		}).then(function(response){
			if(!response.data.success) {
				Notify("操作失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				return;
			}
		
			Notify('操作成功', 'top-right', '5000', 'success', 'fa-check', true);
			$scope.common.showEdit = false;
			$scope.pageChanged();
		});
	}
	
	//删除传感器网关
	$scope.deleteById = function(id){
		if(confirm("你确定删除此记录吗?")){
			$http({
			 	method  : 'POST',
			 	url     : '/monitor/deleteSensorGatewayById.htm',	        
			 	params:{
		            'id':id
		        },     
	            transformRequest: angular.identity,
		        headers : {'Content-Type' : undefined}
			}).then(function(response){
				if(!response.data.success) {
					Notify("操作失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
					return;
				}
			
				Notify('操作成功', 'top-right', '5000', 'success', 'fa-check', true);
				$scope.pageChanged();
			});
		}else{
			//
		}
	}
});
