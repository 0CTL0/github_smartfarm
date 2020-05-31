var app = angular.module("myApp", ["ui.bootstrap"]);
app.controller("myCtrl", function($scope,$http,$timeout) {
	var submitNow = false;
	$scope.common = {
		showAdd:false,
		showEitor:false
	};
	
	$scope.formData = {
		total:0,
		pageSize:10,
		pageNumber:1,
		name:''
	}	
	
	$scope.types=[
	              {name:"按重计费",id:1},
	              {name:"按件计费",id:2}
	              ];

	
	//刷新页面	
	$scope.pageChanged = function() {
		$http({
	        method  : 'POST',	       
	        url     : '/logisticsTemplate/getPageList.htm',
	        data    : $.param($scope.formData),
	        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
	    }).then(function(response) {
	    	$scope.list = response.data.logisticsTemplateManagerList;
	    	$scope.formData.total = response.data.total;
	    });
	}
		
	$scope.pageChanged();
	
	
	$scope.showAddMsg = function() {
		$scope.common.showAdd = true;
	}
	
	$scope.closeAddMsg = function() {
		$scope.common.showAdd = false;
	}

	//添加框提交
	$scope.submitAdd = function() {
		if(submitNow) {
			return;
		}
		if(!stringCheck("模板类型",$scope.logisticsTemplateAdd.type)){return;}
		if(!stringCheck("模板名称",$scope.logisticsTemplateAdd.name)){return;}
		submitNow = true;
		$http({
			method  : 'POST',
			url     : '/logisticsTemplate/addLogisticsTemplate.htm',
			params : {'type':$scope.logisticsTemplateAdd.type,
				'name':$scope.logisticsTemplateAdd.name},
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
			submitNow = false;

			$scope.logisticsTemplateAdd = {
				type:'',
				name:''
			};
		});
	}
	
	//显示编辑框
	$scope.showEitorMsg = function(id) {
		$http({
		 	method  : 'POST',
	        url     : '/logisticsTemplate/queryLogisticsTemplate.htm',
	        params : {'id':id},
            transformRequest: angular.identity,
        	headers : {'Content-Type' : 'application/x-www-form-urlencoded'}
		}).then(function(response){
			if(!response.data.success) {
				Notify("操作失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				return;
			}			
			$scope.logisticsTemplateEitor = response.data.logisticsTemplate;
			$scope.common.showEitor = true;
		});
	}
	
	//关闭编辑框
	$scope.closeEitorMsg = function() {
		$scope.common.showEitor = false;
		$scope.pageChanged();
	}
				
	//提交编辑	
	$scope.submitEitor = function() {
		$http({
		 	method  : 'POST',
	        url     : '/logisticsTemplate/editLogisticsTemplate.htm',
	        params : {
		 		'id':$scope.logisticsTemplateEitor.id,
				'type':$scope.logisticsTemplateEitor.type,
				'name':$scope.logisticsTemplateEitor.name
					},
	        transformRequest: angular.identity,
	    	headers : {'Content-Type' : 'application/x-www-form-urlencoded'}
		}).then(function(response){
			if(!response.data.success) {
				Notify("操作失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				submitNow = false;
				return;
			}
			Notify('编辑成功', 'top-right', '5000', 'success', 'fa-check', true);
			$scope.common.showEitor = false;
			$scope.pageChanged();
			submitNow = false;
		});
	}


	$scope.toTemplateDetail=function(templateId,type){
		window.location.href = "/admin/shop/templateDetail.htm?templateId="+templateId+"&type="+type;
	}

});
