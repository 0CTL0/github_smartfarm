var app = angular.module("myApp", ["ui.bootstrap"]);
app.controller("myCtrl", function($scope,$http,$timeout) {
	$scope.formData = {
		total:0,
		pageSize:10,
		pageNumber:1,
		name:''
	}
	
	
	$scope.ticketSearch = function() {
		$http({
	        method  : 'POST',
	        url     : '/activity/searchActivityRegist.htm',
	        data    : $.param({ticketNo:$scope.formData.ticketNo}),
	        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
	    }).then(function(response) {
	    	if(response.data.activityRegist==null){
				Notify("没有该票号的记录！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				return;
	    	}
	    	$scope.activityRegist = response.data.activityRegist;
	    });
	}

	$scope.formatDateTime = function(str) {
		return str.substring(0,8);
	}
	$scope.formatDateTime2 = function(endDate) {
		return endDate.substring(0,4) + '-' + endDate.substring(4,6) + '-' + endDate.substring(6,8);
	}

	$scope.updateStatus = function(activityRegist) {
		var activityTime=$scope.formatDateTime(activityRegist.activityTime);
		var activityDeadline=$scope.formatDateTime(activityRegist.activityDeadline);
		var startDate=new Date(activityTime.replace(/^(\d{4})(\d{2})(\d{2})$/,"$1/$2/$3"));
		var endDate=new Date(activityDeadline.replace(/^(\d{4})(\d{2})(\d{2})$/,"$1/$2/$3"));
		var nowDate=new Date();
		if(nowDate<startDate){
			Notify("还没到活动开始时间！活动开始时间为"+$scope.formatDateTime2(activityRegist.activityTime)+"!", 'top-right', '7000', 'danger', 'fa-bolt', true);
			return;
		}
		if(nowDate>endDate){
			Notify("活动已过期！活动结束时间为"+$scope.formatDateTime2(activityRegist.activityDeadline)+"!", 'top-right', '7000', 'danger', 'fa-bolt', true);
			return;
		}
		$http({
	        method  : 'POST',
	        url     : '/activity/updateTicke.htm',
	        data    : $.param({id:activityRegist.id,status:2}),
	        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
	    }).then(function(response) {
			if(!response.data.success) {
				Notify("操作失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				submitNow = false;
				return;
			}
			Notify('操作成功', 'top-right', '5000', 'success', 'fa-check', true);
	    	$scope.ticketSearch();
	    });
	}
});
