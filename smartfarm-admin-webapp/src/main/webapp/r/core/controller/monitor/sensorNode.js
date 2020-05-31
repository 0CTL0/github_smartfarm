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
	$scope.sensorNodeAdd = {
		productCode:'',
		nodeName:'',
		sersorType:'',
		maxThreshold:'',
		minThreshold:'',
		gatewayId:''
	}
	//查询列表参数
	$scope.formData = {
		total:0,
		pageSize:10,
		pageNumber:1
	}
	//展示新增框
	$scope.showAddMsg = function(){
		$scope.querySensorInfoAndGateway();
		$scope.common.showAdd = true
	}
	//展示编辑框
	$scope.showEdit = function(id){
		$scope.querySensorInfoAndGateway();
		
		$scope.querySensorNodeById(id);
		$timeout(function(){
			$scope.common.showEdit = true;
		},500)
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
	        url     : '/monitor/queryMonitorsensorNodeList.htm',
	        data    : $.param($scope.formData),
	        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
	    }).then(function(response) {
	    	$scope.list = response.data.sensorNodeList;
	    	$scope.formData.total = response.data.total;
	    });
	}
	
	$scope.pageChanged();
	
	//根据id得到监控传感器
	$scope.querySensorNodeById = function(id){
		$http({
	        method  : 'GET',
	        url     : '/monitor/querySensorNodeById.htm',
	        params:{
	            'id':id
	        },
	        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
	    }).then(function(response) {
	    	$scope.sensorNodeInfo = response.data.sensorNodeInfo;
	    });
	}
	
	//查询传感器信息和网关
	$scope.querySensorInfoAndGateway = function(){
		$http({
	        method  : 'GET',
	        url     : '/monitor/querySensorInfoAndGateway.htm',
	        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
	    }).then(function(response) {
	    	$scope.sensorInfoList = response.data.allSensorInfoList;
	    	$scope.gatewayList = response.data.allGatewayList;
	    });
	}
	//新增
	$scope.submitAdd = function(){
		if($scope.sensorNodeAdd.productCode == '') {
			Notify("产品编码不能为空！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		if($scope.sensorNodeAdd.nodeName == '') {
			Notify("传感器节点名不能为空！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		if($scope.sensorNodeAdd.sersorType == '') {
			Notify("传感器类型不能为空！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		if($scope.sensorNodeAdd.minThreshold == '') {
			Notify("最小阀值不能为空！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		if($scope.sensorNodeAdd.maxThreshold == '') {
			Notify("最大阀值不能为空！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		if(parseInt($scope.sensorNodeAdd.minThreshold) >= parseInt($scope.sensorNodeAdd.maxThreshold)){
			Notify("最小阀值不能大于或等于最大阀值！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		if($scope.sensorNodeAdd.gatewayId == '') {
			Notify("网关节点不能为空！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		var fd = new FormData();
		fd.append("productCode", $scope.sensorNodeAdd.productCode);
		fd.append("nodeName", $scope.sensorNodeAdd.nodeName);
		fd.append("sersorType", $scope.sensorNodeAdd.sersorType);
		fd.append("minThreshold", $scope.sensorNodeAdd.minThreshold);
		fd.append("maxThreshold", $scope.sensorNodeAdd.maxThreshold);
		fd.append("gatewayId", $scope.sensorNodeAdd.gatewayId);
		$http({
		 	method  : 'POST',
		 	url     : '/monitor/addSensorNode.htm',	        
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
			
			$scope.sensorNodeAdd = {
				productCode:'',
				nodeName:'',
				sersorType:'',
				maxThreshold:'',
				minThreshold:'',
				gatewayId:''
			}
		});			
	}
	
	//编辑监控传感器
	$scope.submitEdit = function(){
		if($scope.sensorNodeInfo.productCode == '') {
			Notify("产品编码不能为空！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		if($scope.sensorNodeInfo.nodeName == '') {
			Notify("传感器节点名不能为空！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		if($scope.sensorNodeInfo.sersorType == '') {
			Notify("传感器类型不能为空！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		if($scope.sensorNodeInfo.minThreshold == '') {
			Notify("最小阀值不能为空！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		if($scope.sensorNodeInfo.maxThreshold == '') {
			Notify("最大阀值不能为空！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		if(parseInt($scope.sensorNodeInfo.minThreshold) >= parseInt($scope.sensorNodeInfo.maxThreshold)){
			Notify("最小阀值不能大于或等于最大阀值！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		if($scope.sensorNodeInfo.gatewayId == '') {
			Notify("网关节点不能为空！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		var fd = new FormData();
		fd.append("id", $scope.sensorNodeInfo.id);
		fd.append("productCode", $scope.sensorNodeInfo.productCode);
		fd.append("nodeName", $scope.sensorNodeInfo.nodeName);
		fd.append("sersorType", $scope.sensorNodeInfo.sersorType);
		fd.append("minThreshold", $scope.sensorNodeInfo.minThreshold);
		fd.append("maxThreshold", $scope.sensorNodeInfo.maxThreshold);
		fd.append("gatewayId", $scope.sensorNodeInfo.gatewayId);
		$http({
		 	method  : 'POST',
		 	url     : '/monitor/editSensorNodeById.htm',	        
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
	
	$scope.deleteById = function(id){
		if(confirm("你确定删除此记录吗?")){
			$http({
			 	method  : 'POST',
			 	url     : '/monitor/deleteSensorNodeById.htm',	        
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
