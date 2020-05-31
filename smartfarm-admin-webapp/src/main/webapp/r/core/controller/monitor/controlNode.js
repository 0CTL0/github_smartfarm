var app = angular.module("myApp", ["ui.bootstrap"]);
app.controller("myCtrl", function($scope,$http,$timeout) {
	var submitNow = false;
	$scope.common = {
			showNodeInfo : false,
			showCtrlRecord : false,
			showAdd : false,
			showEdit : false
		};
	
	$scope.formData = {
			total : 0,
			pageSize : 10,
			pageNumber : 1,
			id : ''
	}
	
	$scope.controlNodeAdd = {
			name:'',
			controlType:'',
			gatewayId:'',
			sensorCode:'',
			sensorType:'',
			sersorType:'',
			number:''
	}

	$scope.nodeType = [
		{"id":1,"name":"电磁阀"}                   
	]

	
	$scope.pageChanged = function() {
		$http({	
				method : 'POST',
				url : '/controlNode/getControlNodes.htm',
				data : {},
				headers : {'Content-Type' : 'application/x-www-form-urlencoded'},
		}).then(function(response) {
			if (!response.data.success) {
				Notify("操作失败！",'top-right','4000', 'danger','fa-bolt', true);
				return;
			}
			$scope.controlNodesList = response.data.controlNodesList;
		});
	}
	$scope.pageChanged();
	
	
	
	//打开节点窗口
	$scope.showNodeWindow = function(id){
		$scope.formData.id = id;
		$http({
				method : 'POST',
				url : '/controlNode/getSingleControlNode.htm',
				params : {
					'id' : id
				},
				headers : {'Content-Type' : 'application/x-www-form-urlencoded'}//angularjs设置文件上传的content-type修改方式		
			}).then(function(response) {
				if (!response.data.success) {
					Notify("操作失败！",'top-right','4000', 'danger','fa-bolt', true);
					return;
				}
			$scope.controlNodeEdit = response.data.controlNode;
			//设备类型为1（电磁阀），不显示暂停按钮
			if($scope.controlNodeEdit.type==1){
				$scope.isShow = false;
			}else{
				$scope.isShow = true;
			}
			$scope.isClickable($scope.controlNodeEdit.operateStatus);
		});
		
		$scope.common.showNodeInfo = true;
	}
	
	$scope.closeNodeWindow = function() {
		$scope.common.showNodeInfo = false;
	}
	
	//根据状态值返回状态
	$scope.numToWord = function(num){
		if(num==1){
			return "开启";
		}
		if(num==2){
			return "关闭";
		}
		if(num==3){
			return "暂停";
		}
	}
	
	//更改节点的状态
	$scope.changeNodeStatus = function(num){
		if (submitNow) {
			return;
		}
		var fd = new FormData();
		fd.append("id", $scope.formData.id);
		fd.append("name", $scope.controlNodeEdit.name);
		fd.append("code", $scope.controlNodeEdit.code);
		fd.append("nodeStatus", $scope.controlNodeEdit.nodeStatus);
		fd.append("operateStatus", num);
		fd.append("type", $scope.controlNodeEdit.type);
		
		//控制记录，参数
		fd.append("nodeId", $scope.formData.id);
		fd.append("operateType", 1);
		fd.append("switchType", num);

		var id = $scope.formData.id;
		submitNow = true;
		$http({
			method : 'POST',
			url : '/controlNode/updateControlNode.htm',
			data : fd,
			transformRequest : angular.identity,
			headers : {'Content-Type' : undefined}//angularjs设置文件上传的content-type修改方式
		}).then(function(response) {
			if (!response.data.success) {
				Notify("操作失败！", 'top-right','4000', 'danger','fa-bolt', true);
				submitNow = false;
				return;
			}

			$scope.controlNodeEdit.operateStatus = num;//点击状态按钮后，更改弹窗中的状态为新的值
			Notify('操作成功', 'top-right', '5000','success', 'fa-check', true);
			/* if(num!=2){
				$scope.addCtrlRecord(id,num);
			}else{
				$scope.updateCtrlRecord(id);
			} */
			
			$scope.isClickable(num);
			$scope.pageChanged();
			
			submitNow = false;
		});
	}
	
	//根据节点当前状态，设置按钮是否可用
	$scope.isClickable = function(state){
		if(state==1){//开启状态
			$("#open").attr("disabled",true);
			$("#pause").attr("disabled",false);
			$("#close").attr("disabled",false);
		}
		if(state==2){//关闭状态
			$("#open").attr("disabled",false);
			$("#pause").attr("disabled",true);
			$("#close").attr("disabled",true);
		}
		if(state==3){//暂停状态
			$("#open").attr("disabled",false);
			$("#pause").attr("disabled",true);
			$("#close").attr("disabled",false);
		}
	}
	
	//打开控制记录弹窗
	$scope.showCtrlRecordWindow = function(nodeId){
		
		$scope.formData.nodeId = nodeId;
		$http({
				method : 'POST',
				url : '/controlRecord/getControlRecordsByNodeId.htm',
				params : {
					'nodeId' : nodeId,
					'pageSize' :$scope.formData.pageSize,
					'pageNumber' :$scope.formData.pageNumber
				},
				/* data : $.param($scope.formData), */
				headers : {'Content-Type' : 'application/x-www-form-urlencoded'}//angularjs设置文件上传的content-type修改方式		
			}).then(function(response) {
				if (!response.data.success) {
					Notify("操作失败！",'top-right','4000', 'danger','fa-bolt', true);
					return;
				}
				$scope.controlRecords = response.data.controlRecords;
				$timeout(function(){
					
					$scope.formData.total = response.data.total;
				},500)
			
			
		});
		
		/* $scope.common.showCtrlRecord = true; */
		console.log($scope.common.showCtrlRecord);
	}
	
	//关闭控制记录弹窗
	$scope.closeCtrlRecordWindow = function(){
		$scope.common.showCtrlRecord = false;
	}
	
	//添加控制记录，启动
	$scope.addCtrlRecord = function(nodeId,switchType){
		var fd = new FormData();
		fd.append("nodeId", nodeId);
		fd.append("operateType", 1);
		fd.append("switchType", switchType);

		$http({
			method : 'POST',
			url : '/controlRecord/addControlRecord.htm',
			data : fd,
			transformRequest : angular.identity,
			headers : {'Content-Type' : undefined}//angularjs设置文件上传的content-type修改方式
		}).then(function(response) {
			if (!response.data.addSuccess) {
				Notify("操作失败！", 'top-right','4000', 'danger','fa-bolt', true);
				return;
			}
			Notify('操作记录已添加！', 'top-right', '5000','success', 'fa-check', true);
		});
	}
	
	//更新控制记录，关闭
	$scope.updateCtrlRecord = function(nodeId){
		var fd = new FormData();
		fd.append("nodeId", nodeId);
		fd.append("operateType", 1);
		fd.append("switchType", 2);

		$http({
			method : 'POST',
			url : '/controlRecord/updateCtrlRecord.htm',
			data : fd,
			transformRequest : angular.identity,
			headers : {'Content-Type' : undefined}//angularjs设置文件上传的content-type修改方式
		}).then(function(response) {
			if (!response.data.updateSuccess) {
				Notify("操作失败！", 'top-right','4000', 'danger','fa-bolt', true);
				return;
			}
			Notify('操作记录已更新！', 'top-right', '5000','success', 'fa-check', true);
		});
	}

	//查询传感器信息和网关
	$scope.querySensorInfoAndGateway = function(){
		$http({
			method  : 'GET',
			url     : '/controlNode/querySensorInfoAndGateway.htm',
			headers : {'Content-Type': 'application/x-www-form-urlencoded'},
		}).then(function(response) {
			$scope.sensorInfoList = response.data.allSensorInfoList;
			$scope.gatewayList = response.data.allGatewayList;
		});
	}

	//打开新增弹窗
	$scope.showAddWindow = function(){
		$scope.querySensorInfoAndGateway();
		$scope.common.showAdd = true;
	}
	
	//提交新增
	$scope.submitAdd = function(){
		var productCode = $scope.controlNodeAdd.sensorCode + "-" + $scope.controlNodeAdd.number;
		console.log(productCode);
		var fd = new FormData();
		fd.append("name", $scope.controlNodeAdd.name);
		fd.append("type", $scope.controlNodeAdd.controlType);
		fd.append("productCode", productCode);
		fd.append("gatewayId", $scope.controlNodeAdd.gatewayId);
		fd.append("sensorType", $scope.controlNodeAdd.sensorType);
		fd.append("sersorType", $scope.controlNodeAdd.sersorType);

		$http({
			method : 'POST',
			url : '/controlNode/addControlNode.htm',
			data : fd,
			transformRequest : angular.identity,
			headers : {'Content-Type' : undefined}//angularjs设置文件上传的content-type修改方式
		}).then(function(response) {
			if (!response.data.success) {
				Notify("操作失败！", 'top-right','4000', 'danger','fa-bolt', true);
				return;
			}
			Notify('添加成功！', 'top-right', '5000','success', 'fa-check', true);
			
			$scope.common.showAdd = false;
			$scope.pageChanged();
			/* submitNow = false; */

			$scope.controlNodeAdd = {
				name:'',
				controlType:'',
				gatewayId:'',
				sensorCode:'',
				sersorType:'',
				number:''
			}
		});
	}
	
	//关闭新增弹窗
	$scope.closeAddWindow = function(){
		$scope.common.showAdd = false;
	}
	
	//打开修改弹窗
	$scope.showEditWindow = function(id){
		$scope.querySensorInfoAndGateway();
		$http({
			method : 'POST',
			url : '/controlNode/getSingleControlNode.htm',
			params : { "id" : id },
			transformRequest : angular.identity,
			headers : {'Content-Type' : undefined}//angularjs设置文件上传的content-type修改方式
		}).then(function(response) {
			if (!response.data.success) {
				Notify("操作失败！", 'top-right','4000', 'danger','fa-bolt', true);
				return;
			}
			$scope.controlNodeEdit = response.data.controlNode;
			var codes = $scope.controlNodeEdit.productCode.split("-");
			$scope.sensorCodeEdit = codes[0];
			$scope.numberEdit = codes[1];
			$scope.common.showEdit = true;
		});
		
	}
	
	//提交修改
	$scope.submitEdit = function(){
		var productCode = $scope.sensorCodeEdit + "-" + $scope.numberEdit;
		console.log(productCode);
		var fd = new FormData();
		fd.append("id",$scope.controlNodeEdit.id);
		fd.append("name", $scope.controlNodeEdit.name);
		// fd.append("type", $scope.controlNodeEdit.type);
		fd.append("productCode", productCode);
		fd.append("gatewayId", $scope.controlNodeEdit.gatewayId);
		fd.append("sersorType", $scope.controlNodeAdd.sersorType);
		$http({
			method : 'POST',
			url : '/controlNode/updateControlNodeInfo.htm',
			data : fd,
			transformRequest : angular.identity,
			headers : {'Content-Type' : undefined}//angularjs设置文件上传的content-type修改方式
		}).then(function(response) {
			if (!response.data.success) {
				Notify("操作失败！", 'top-right','4000', 'danger','fa-bolt', true);
				return;
			}
			Notify('添加成功！', 'top-right', '5000','success', 'fa-check', true);
			
			$scope.common.showEdit = false;
			$scope.pageChanged();
			
		});
	}
	
	//关闭修改弹窗
	$scope.closeEditWindow = function(){
		$scope.common.showEdit = false;
	}
	
	//删除设备
	$scope.deleteNode = function(node){
		var choice = confirm("删除设备:"+node.name+"!");
		if(choice){
			$http({
				method : 'POST',
				url : '/controlNode/deleteControlNode.htm',
				params : { "id" : node.id },
				transformRequest : angular.identity,
				headers : {'Content-Type' : undefined}//angularjs设置文件上传的content-type修改方式
			}).then(function(response) {
				if (!response.data.success) {
					Notify("删除失败！", 'top-right','4000', 'danger','fa-bolt', true);
					return;
				}
				Notify('删除成功！', 'top-right', '5000','success', 'fa-check', true);
				$scope.pageChanged();
			});
		}
	}
});
