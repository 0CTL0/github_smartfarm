var app = angular.module("myApp", ["ui.bootstrap"]);
app.controller("myCtrl", function($scope,$http,$timeout) {
	var submitNow = false;
	//分页数据
	$scope.formData = {
		pageNumber:1,
		pageSize:10,
		total:0,
		couponName:'',
		status:'',
		nickName:''
	};
	
	$scope.common = {
			showAdd:false,
			memberId:''
	}
	
	$scope.pageChanged = function() {
		$http({
	        method  : 'POST',
	        url     : '/coupon/queryCouponInfoList.htm',
	        data    : $.param($scope.formData),
	        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
	    }).then(function(response) {
	    	$scope.couponInfoList = response.data.couponInfoList;
	    	$scope.formData.total = response.data.total;
	    });
	}

	$scope.pageChanged();
	
	$scope.formatDate = function(endDate) {
		return endDate.substring(0,4) + '-' + endDate.substring(4,6) + '-' + endDate.substring(6,8);
	}
	
	$scope.openSet = function(id) {
		$scope.common.showAdd = true;
		$scope.common.id = id;
	}
	
	$scope.closeAddMsg = function() {
		$scope.common.showAdd = false;
	}
	
	$http({
        method  : 'POST',
        url     : '/coupon/queryAllMember.htm',
        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
    }).then(function(res) {
    	$scope.memberList = res.data.memberList;
    });
	
	$scope.submitAdd = function() {
		if(submitNow) {
			return;
		}
		if($scope.common.memberId == "") {
			Notify("请选择用户！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		submitNow = true;
		$http({
	        method  : 'POST',
	        url     : '/coupon/updateCouponForUser.htm',
	        data    : $.param({memberId:$scope.common.memberId, couponId:$scope.common.id}),
	        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
	    }).then(function(res) {
	    	submitNow = false;
	    	if(!res.data.success) {
	    		Notify("下发失败", 'top-right', '4000', 'danger', 'fa-bolt', true);
	    		return;
	    	}
	    	Notify('下发成功', 'top-right', '5000', 'success', 'fa-check', true);
	    	$scope.pageChanged();
	    	$scope.closeAddMsg();
	    	$scope.common.memberId = "";
	    });
	}
});
