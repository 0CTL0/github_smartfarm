var app = angular.module("myApp", ["ui.bootstrap"]);
app.controller("myCtrl", function($scope,$http,$timeout) {
	var submitNow = false;
	$scope.common = {
		showAdd:false,
		showEdit:false
	};
	
	$scope.formData = {
		total:0,
		pageSize:10,
		pageNumber:1,
		name:''
	}	
	
	$scope.activityAdd = {
			name:'',
			address:'',
			joinNum:'',
			joinTime:'',
			mobile:'',
			businessId:'',
			needName:'',
			needMobile:'',
			needIdcard:'',
			detail:'',
			isShow:'',
			status:'',
			files:'',
			orderNum:'',
			activityTime:'',
			longitude:'',
			latitude:'',
			joinDeadline:'',
		activityDeadline:''
		};
	
	$scope.activityEdit = {
			name:'',
			address:'',
			joinNum:'',
			joinTime:'',
			mobile:'',
			businessId:'',
			needName:'',
			needMobile:'',
			needIdcard:'',
			detail:'',
			isShow:'',
			status:'',
			files:'',
			orderNum:'',
			activityTime:'',
			longitude:'',
			latitude:'',
			picUrl:'',
			joinDeadline:'',
		activityDeadline:''
		};	
	
	//存储新增的价格档次数组
	$scope.priceList = [];
	$scope.priceList.push({name:'',price:'',joinNum:'',remark:'',remain:$scope.activityAdd.joinNum});	
	//存储编辑的价格档次数组
	$scope.priceList2 = [];
	
	//新增价格档次
	$scope.addPrice= function() {
		$scope.priceList.push({id:'',price:'',joinNum:'',remark:''});
	}
	//移除新增的价格档次
	$scope.removePrice = function(index) {
		$scope.priceList.splice(index,1);
	}
	
	//编辑时删除活动的价格档次
	$scope.deletePrice = function(index) {
		if($scope.priceList2[index].id!=''){
			$http({
			 	method  : 'GET',
		        url     : '/activity/deleteActivityPriceById.htm',
		        params:{
		            'activityPriceId':$scope.priceList2[index].id
		        },
	        	headers : {'Content-Type' : 'application/x-www-form-urlencoded'}  //angularjs设置文件上传的content-type修改方式
			}).then(function(response){
				
			});
		}
		$scope.priceList2.splice(index,1);
	}
	
	//编辑时新增价格档次
	$scope.addPrice2= function() {
		$scope.priceList2.push({price:'',joinNum:'',remark:''});
	}
	
	
	//将传入的时间格式化
	$scope.formatDateTime = function(endDate) {
		return endDate.substring(0,4) + '-' + endDate.substring(4,6) + '-' + endDate.substring(6,8);
	}	
	//将时间控件生成的时间格式化为20180101124500
	$scope.formatDateTime2 = function(endDate) {
		return endDate.substring(0,4) + endDate.substring(5,7) + endDate.substring(8,10) +"000000";
	}
	
	//刷新页面		
	$scope.pageChanged = function() {
		$http({
	        method  : 'POST',	       
	        url     : '/activity/queryActivityList.htm',
	        data    : $.param($scope.formData),
	        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
	    }).then(function(response) {
	    	
	    	$scope.list = response.data.activityList;
	    	$scope.formData.total = response.data.total;	    	
 			
	    });
	}
	
	$scope.pageChanged();
	
		
	//改变活动状态
	$scope.changeStatus = function(activityId){
		$http({
		 	method  : 'GET',
	        url     : '/activity/changeStatus.htm',
	        params:{
	            'activityId':activityId
	        },
        	headers : {'Content-Type' : 'application/x-www-form-urlencoded'}  //angularjs设置文件上传的content-type修改方式
		}).then(function(response){
			$scope.pageChanged();
		});
	}
	
	//跳转到活动报名页面
	$scope.toActivityRegist=function(activityId,activityName){
		window.location.href = "/admin/farm/activityRegist.htm?activityId="+activityId+"&activityName="+activityName;
	}
	
	//初始化所有时间控件
	for(var i = 0;i<document.getElementsByName("myDates").length;i++){
		$(document.getElementsByName("myDates")).datetimepicker({
			  minView: "month",//设置只显示到月份
			  format : "yyyy-mm-dd",//日期格式
			  autoclose:true,//选中关闭
			  todayBtn: true//今日按钮
		});
	}
	
	
	//显示添加框
	$scope.showAddMsg = function() {
		$scope.common.showAdd = true;
		//时间控件初始化
		//$('#activityDate').datetimepicker();
		//$('#joinDate').datetimepicker();
		//$('#joinDeadlineDate').datetimepicker();
	}
	//关闭添加框
	$scope.closeAddMsg = function() {
		$scope.common.showAdd = false;
		$scope.priceList = [];
		$scope.priceList.push({price:'',joinNum:'',remark:''});	
	}
	
	//显示编辑框
	$scope.showEditMsg = function(activityId) {		
		$scope.common.showEdit = true;
		var fd = new FormData();
		fd.append("id", activityId);
		$http({
		 	method  : 'POST',
	        url     : '/activity/queryActivityInfoById.htm',
	        data : fd,
            transformRequest: angular.identity,
        	headers : {'Content-Type' : undefined}  //angularjs设置文件上传的content-type修改方式
		}).then(function(response){
			if(!response.data.success) {
				Notify("操作失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				return;
			}			
			   
			$scope.activityEdit = response.data.activityInfo;			
			$scope.priceList2=response.data.priceList;
			$scope.activityEdit.joinTime=$scope.formatDateTime($scope.activityEdit.joinTime);
			$scope.activityEdit.activityTime=$scope.formatDateTime($scope.activityEdit.activityTime);
			$scope.activityEdit.joinDeadline=$scope.formatDateTime($scope.activityEdit.joinDeadline);
			$scope.activityEdit.activityDeadline=$scope.formatDateTime($scope.activityEdit.activityDeadline);
			
		});
	}
	
	//关闭编辑框
	$scope.closeEditMsg = function() {
		$scope.common.showEdit = false;
		$scope.pageChanged();
		$scope.priceList2 = [];
	}
	
	//提交编辑
	$scope.submitEdit = function() {				
		
		var fd = new FormData();
		fd.append("id", $scope.activityEdit.id);	
		fd.append("name", $scope.activityEdit.name);		
		fd.append("joinNum", $scope.activityEdit.joinNum);		
		fd.append("activityTime", $scope.formatDateTime2($scope.activityEdit.activityTime));
		fd.append("activityDeadline", $scope.formatDateTime2($scope.activityEdit.activityDeadline));
		fd.append("joinTime",$scope.formatDateTime2($scope.activityEdit.joinTime) );
		fd.append("joinDeadline",$scope.formatDateTime2($scope.activityEdit.joinDeadline) );
		fd.append("address", $scope.activityEdit.address);
		// fd.append("longitude", $scope.activityEdit.longitude);
		// fd.append("latitude", $scope.activityEdit.latitude);
		fd.append("mobile", $scope.activityEdit.mobile);
 		fd.append("businessId", $scope.activityEdit.businessId);
		fd.append("needName", $scope.activityEdit.needName);
		fd.append("needMobile", $scope.activityEdit.needMobile);
		fd.append("needIdcard", $scope.activityEdit.needIdcard);
		fd.append("isShow", $scope.activityEdit.isShow);
		fd.append("orderNum", $scope.activityEdit.orderNum);		
		fd.append("file",  document.querySelector('input[name="editImg"]').files[0]);
		for(var i=0; i < document.querySelector('input[name="editDescImg"]').files.length; i++) {
		    fd.append("files", document.querySelector('input[name="editDescImg"]').files[i]);
		}
		
		//$.param({formJson:JSON.stringify(fd),gradeJson:JSON.stringify($scope.gradeList)}),
		fd.append("priceListJson", JSON.stringify($scope.priceList2));
				
		submitNow = true;
		$http({
		 	method  : 'POST',
	        url     : '/activity/editActivityInfo.htm',	       
	        data :  fd,      
            transformRequest: angular.identity,
            headers : {'Content-Type' : undefined}  //angularjs设置文件上传的content-type修改方式 
		}).then(function(response){
			if(!response.data.success) {
				Notify("操作失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				submitNow = false;
				return;
			}
		
			Notify('添加成功', 'top-right', '5000', 'success', 'fa-check', true);
			$scope.common.showEdit = false;
			$scope.pageChanged();
			$(".fileinput-remove-button").click();	
			submitNow = false;
		});			
	}
	
	
	//提交添加
	$scope.submitAdd = function() {		
		if(submitNow) {
			return;
		}

		if(!stringCheck("活动名称",$scope.activityAdd.name)){return;}
		if(!intCheck("活动人数",$scope.activityAdd.joinNum)){return;}
		if(!stringCheck("活动报名起始时间",$scope.activityAdd.joinTime)){return;}
		if(!stringCheck("活动报名截止时间",$scope.activityAdd.joinDeadline)){return;}
		if(!stringCheck("地址",$scope.activityAdd.address)){return;}
		if(!stringCheck("电话",$scope.activityAdd.mobile)){return;}
		if(!stringCheck("活动开始时间",$scope.activityAdd.activityTime)){return;}
		if(!stringCheck("活动结束时间",$scope.activityAdd.joinDeadline)){return;}
		if(!stringCheck("显示",$scope.activityAdd.isShow)){return;}
		if(!intCheck("排序",$scope.activityAdd.orderNum)){return;}
		if(!picCheck("活动封面图片","addImg")){return;}
		if(!picCheck("活动详情图片","addDescImg")){return;}

						
		if($scope.priceList[0].name == ''||$scope.priceList[0].price == '' || $scope.priceList[0].joinNum == ''||$scope.priceList[0].remark == '') {
			Notify("请至少输入一个价格档次！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		
		var fd = new FormData();
		fd.append("name", $scope.activityAdd.name);		
		fd.append("joinNum", $scope.activityAdd.joinNum);		
		fd.append("activityTime", $scope.formatDateTime2($scope.activityAdd.activityTime));
		fd.append("activityDeadline", $scope.formatDateTime2($scope.activityAdd.activityDeadline));
		fd.append("joinTime",$scope.formatDateTime2($scope.activityAdd.joinTime) );
		fd.append("joinDeadline",$scope.formatDateTime2($scope.activityAdd.joinDeadline) );
		fd.append("address", $scope.activityAdd.address);
		// fd.append("longitude", $scope.activityAdd.longitude);
		// fd.append("latitude", $scope.activityAdd.latitude);
		fd.append("mobile", $scope.activityAdd.mobile);
 		fd.append("businessId", $scope.activityAdd.businessId);
		fd.append("needName", $scope.activityAdd.needName);
		fd.append("needMobile", $scope.activityAdd.needMobile);
		fd.append("needIdcard", $scope.activityAdd.needIdcard);
		//fd.append("detail", $scope.activityAdd.detail);
		fd.append("isShow", $scope.activityAdd.isShow);
		fd.append("orderNum", $scope.activityAdd.orderNum);
		
		fd.append("file",  document.querySelector('input[name="addImg"]').files[0]);
		for(var i=0; i < document.querySelector('input[name="addDescImg"]').files.length; i++) {
		    fd.append("files", document.querySelector('input[name="addDescImg"]').files[i]);
		}
		
		//$.param({formJson:JSON.stringify(fd),gradeJson:JSON.stringify($scope.gradeList)}),
		fd.append("priceListJson", JSON.stringify($scope.priceList));
				
		submitNow = true;
		$http({
		 	method  : 'POST',
	        url     : '/activity/addActivity.htm',	        
	        data :  fd,      
            transformRequest: angular.identity,
            headers : {'Content-Type' : undefined}  //angularjs设置文件上传的content-type修改方式 
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
			$(".fileinput-remove-button").click();	
			$scope.priceList = [];
			$scope.priceList.push({price:'',joinNum:'',remark:'',remain:$scope.activityAdd.joinNum});	
			$scope.activityAdd = {
					name:'',
					businessId:'',
					joinNum:'',
					joinTime:'',
					address:'',
					// longitude:'',
					// latitude:'',
					mobile:'',
					needName:'',
					needMobile:'',
					needIdcard:'',
					detail:'',
					isShow:'',
					orderNum:'',
					activityTime:'',
					joinDeadline:'',
					files:''
			};
		});			
	}
});
