var app = angular.module("myApp", ["ui.bootstrap"]);
var submitNow = false;
app.controller("myCtrl", function($scope,$http) {
	$scope.common = {
		showAdd:false
	};
	$scope.taskStatus ={
		"草稿":1,
		"上架":2,
		"下架":3,
		"结束":4
	}
	$scope.statusList = [
		{id:1,name:'成功'},
		{id:2,name:'待处理'},
		{id:3,name:'拒绝'}
	];

	$scope.formData = {
		total:0,
		pageSize:10,
		pageNumber:1,
		memberName:'',
		status:''
	}


	$scope.formatDateTime = function(endDate) {
		return endDate.substring(0,4) + '-' + endDate.substring(4,6) + '-' + endDate.substring(6,8) + " " + endDate.substring(8,10) + ":" + endDate.substring(10,12);
	}

	$scope.formatDateTime2 = function(startDate) {
		return endDate.substring(0,4) + endDate.substring(5,7) +endDate.substring(8,10) + endDate.substring(8,10) +  endDate.substring(11,13)+  endDate.substring(14,16);
	}

	$scope.pageChanged = function() {
		$http({
			method  : 'POST',
			url     : '/memberCash/queryMemberCashRecordPageList.htm',
			data    : $.param($scope.formData),
			headers : {'Content-Type': 'application/x-www-form-urlencoded'},
		}).then(function(response) {
			if(!response.data.success) {
				Notify("操作失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				return;
			}
			$scope.memberCashRecordList = response.data.memberCashRecordList;
			$scope.formData.total = response.data.total;
		});
	}

	$scope.pageChanged();


	//改变会员金额状态
	$scope.changeStatus = function(status,id){
		$http({
			method  : 'GET',
			url     : '/memberCash/updateMemberCashRecord.htm',
			params:{
				'status':status,
				'id':id
			},
			headers : {'Content-Type' : 'application/x-www-form-urlencoded'}  //angularjs设置文件上传的content-type修改方式
		}).then(function(response){
			if(!response.data.success) {
				Notify("操作失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				return;
			}
			Notify('操作成功', 'top-right', '5000', 'success', 'fa-check', true);
			$scope.pageChanged();
		});
	}

	$scope.exportExcle=function(){
		var blob = new Blob([document.getElementById('exportable').innerHTML], {
			type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"
		});
		saveAs(blob, "会员提现处理表.xls");
	}

});