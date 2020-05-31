var app = angular.module("myApp", ["ui.bootstrap"]);
app.controller("myCtrl", function($scope,$http,$timeout) {
	var submitNow = false;
	$scope.orderStatus = [
	             		{id:1,name:'待支付'},
	             		{id:2,name:'支付成功'},
	             		{id:3,name:'支付超时'},
	             		{id:4,name:'支付失败'}
	             	];
	$scope.ticketStatus = [
	             		{id:1,name:'未使用'},
	             		{id:2,name:'已使用'}
	             	];
	
	$scope.formData = {
			total:0,
			pageSize:10,
			pageNumber:1,
			name:'',
			activityName:''
	}
	
	$scope.activityRegistSearch={
			activityId:'',
			orderNo:'',
			status:'',
			ticketStatus:'',
			name:'',
			mobile:''
	}
	
	//根据URL的参数名获取参数值  
	$scope.getParams=function(name) {
	    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");  
	    var r = window.location.search.substr(1).match(reg);  
	    if (r != null) return unescape(r[2]);  
	    return null;  
	}
	
	//页面刷新
	$scope.pageChanged = function() {		
    	$scope.activityRegistSearch.activityId=$scope.getParams('activityId');
    	//$scope.formData.activityName=$scope.getParams('activityName');
		
		$http({
	        method  : 'POST',
	        url     : '/activity/queryActivityRegistList.htm',
	        data    : $.param({activityId:$scope.activityRegistSearch.activityId,pageSize:$scope.formData.pageSize,pageNumber:$scope.formData.pageNumber,activityRegistrationJson:JSON.stringify($scope.activityRegistSearch)}),
	        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
	    }).then(function(response) {
	    	$scope.list = response.data.activityRegistList;
	    	$scope.formData.total = response.data.total;
	    	$scope.activityName=response.data.activityName;
	    });
	}
	
	$scope.pageChanged();
});
