var app = angular.module("myApp", ["ui.bootstrap"]);
app.controller("myCtrl", function($scope,$http,$timeout) {
	var submitNow = false;
	//图片路径前缀
	$scope.picUrl=picurl;
	//控制模态框
	$scope.common = {
		showAdd:false,
		showEdit:false
	}
	//查询列表参数
	$scope.formData = {
		total:0,
		pageSize:10,
		pageNumber:1
	}
	//展示编辑框
	$scope.showEdit = function(id){
		$scope.querySensorInfoAndGateway();
		
		$scope.querySensorNodeById(id);
		$timeout(function(){
			$scope.common.showEdit = true;
		},500)
	}
	//关闭编辑框
	$scope.closeshowEdit = function(){
		$scope.common.showEdit = false
	}
	//初始化列表
	$scope.pageChanged = function() {
		$http({
	        method  : 'POST',
	        url     : '/farm/queryFarmerSensorNodeList.htm',
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
	        url     : '/farm/querySensorNodeById.htm',
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
	//编辑监控传感器
	$scope.submitEdit = function(){
		if($scope.sensorNodeInfo.minThreshold == '') {
			Notify("最小阀值不能为空！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		if($scope.sensorNodeInfo.maxThreshold == '') {
			Notify("最大阀值不能为空！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		var fd = new FormData();
		fd.append("id", $scope.sensorNodeInfo.id);
		fd.append("minThreshold", $scope.sensorNodeInfo.minThreshold);
		fd.append("maxThreshold", $scope.sensorNodeInfo.maxThreshold);
		$http({
		 	method  : 'POST',
		 	url     : '/farm/editSensorNodeById.htm',	        
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
});
