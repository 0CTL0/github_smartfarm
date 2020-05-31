var app = angular.module("myApp", ["ui.bootstrap"]);
app.controller("myCtrl", function($scope,$http,$timeout) {
	var submitNow = false;
	
	//查询列表参数
	$scope.formData = {
		total:0,
		pageSize:10,
		pageNumber:1
	}
	//初始化列表
	$scope.pageChanged = function() {
		$http({
	        method  : 'POST',
	        url     : '/waring/queryWarningPage.htm',
	        data    : $.param($scope.formData),
	        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
	    }).then(function(response) {
	    	$scope.list = response.data.rows;
	    	$scope.formData.total = response.data.total;
	    });
	}
	
	$scope.pageChanged();
	
});
