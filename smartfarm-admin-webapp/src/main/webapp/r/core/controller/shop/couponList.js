var app = angular.module("myApp", ["ui.bootstrap"]);
app.controller("myCtrl", function($scope,$http,$timeout) {
	//控制模态框
	$scope.common = {
		showAdd:false,
		showBatchNum:false,
		showAddBatchRule:false,
		showAllRule:false
	};
	
	for(var i = 0;i<document.getElementsByName("myDates").length;i++){
		$(document.getElementsByName("myDates")).datetimepicker({
			  minView: "month",//设置只显示到月份
			  format : "yyyy-mm-dd",//日期格式
			  autoclose:true,//选中关闭
			  todayBtn: true//今日按钮
		});
	}
	//定义一个数组来存储选中的id
	$scope.checkBoxStatus = [];
	//添加选中
	$scope.boxStatus = function(event,id){
		if(event.target.checked){
			if($scope.checkBoxStatus.indexOf(id) == -1){
				$scope.checkBoxStatus.push(id)
			}
		}else{
			var index = $scope.checkBoxStatus.indexOf(id);
			if(index !== -1){
				$scope.checkBoxStatus.splice(index,1);
			}
		}
	}
	//返回是否选中
	$scope.expression = function(id){
		return $scope.checkBoxStatus.indexOf(id) !== -1;
	}
	
	//新增批次参数
	$scope.batchAdd = {
		discretion:'',
		strategyType:'',
		deadline:'',
		dayCount:'',
		displayName:'',
		reductPrice:'',
		deadlineStr:'',
		amount:'',
		type:'',
		discount:'',
		useTime:1
	}
	//存储新增的档次设置数组
	$scope.productSkuList = [];
	$scope.skuVal = []
	$scope.choiceList=[]
	
	//优惠券数量
	$scope.batchNum = {
		num:'',
		id:''
	}
	//优惠券规则类型
	$scope.batchRuleAdd = {
		type:'',
		couponBatchId:''
	}
	
	//分页数据
	$scope.formData = {
		pageNumber:1,
		pageSize:10,
		total:0,
		type:''
	};
	//初始化页面
	$scope.pageChanged = function() {
		$http({
	        method  : 'POST',
	        url     : '/coupon/queryCouponBatchList.htm',
	        data    : $.param($scope.formData),
	        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
	    }).then(function(response) {
	    	$scope.couponBatchList = response.data.couponBatchList;
	    	$scope.formData.total = response.data.total;
	    });
	}

	$scope.pageChanged();
	
	//查询所有分类信息
	$scope.queryAllProductCategory = function() {
		$http({
	        method  : 'GET',
	        url     : '/coupon/queryAllProductCategory.htm',
	        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
	    }).then(function(response) {
	    	$scope.productCategoryList = response.data.productCategoryList;
	    });
	}
	//设置产品规格
	$scope.showCategoryProSku = function(categoryId){
		if($scope.categoryId === categoryId){
  		  return;
  	  	}
  	    $scope.categoryId = categoryId;
		$http({
	        method  : 'GET',
	        url     : '/coupon/querySkuDetailsById.htm',
	        params:{
	            'id':categoryId
	        },
	        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
	    }).then(function(response) {
	    	
	    	
	    	$scope.productSkuList=[]
	    	for(var i = 0;i<response.data.productSkuList.length;i++){
	    		$scope.productSkuList.push({"id":response.data.productSkuList[i].id,"details":response.data.productSkuList[i].details})
	    	}
	    	$("input[name='checkRule']:checked").each(function(){
	    		if($scope.choiceList.indexOf($(this).val()) == -1){
	    			$scope.choiceList.push($(this).val());
	    		}
			})
	    });
	}
	
	$scope.showAllRule = function(batchId){
		$scope.common.showAllRule = true;
		$scope.showBatchRules(batchId)
	}
	//根据id查询优惠券规则
	$scope.showBatchRules = function(batchId){
		
		$http({
	        method  : 'GET',
	        url     : '/coupon/queryProductSkuById.htm',
	        params:{
	            'id':batchId
	        },
	        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
	    }).then(function(response) {
	    	$scope.skusList = response.data.skusList;
	    	/* console.log($scope.skusList.length) */
	    });
	}
	
	//打开添加框
	$scope.showAddMsg = function() {
		$scope.categoryId ='';
		$scope.common.showAdd = true;//时间控件初始化
		$('#dateLine').datetimepicker();
	}
	//打开新增优惠券规则
	$scope.showAddBatchRule = function(batchId){
		$scope.batchRuleAdd.couponBatchId = batchId;
		
		
		$scope.showBatchRules(batchId);
		
		/* $timeout(function(){
			if($scope.skusList[0] !== ''){
				Notify("该优惠券已有规则!", 'top-right', '4000', 'danger', 'fa-bolt', true);
				return;
			}
		},500); */
		$timeout(function(){
			if($scope.skusList.length !== 0){
				Notify("该优惠券已有规则!", 'top-right', '4000', 'danger', 'fa-bolt', true);
				return;
			}
			$scope.common.showAddBatchRule = true;
		},500)
		
		$timeout(function(){
			$scope.queryAllProductCategory();
		},500)
	}
	//打开生成优惠券框
	$scope.showBatchNum = function(id){
		$scope.common.showBatchNum = true;
		
		$scope.batchNum.id = id;
	}
	//打开编辑框
	$scope.showEdit = function(id){
		$scope.common.showEdit = true;
		
		$scope.queryCouponBatchById(id);
		
		$timeout(function(){
			$scope.batchInfo.deadlineStr=$scope.formatDateTime($scope.batchInfo.deadline);
		},500);
	}
	//关闭添加框
	$scope.closeAddMsg = function() {
		$scope.categoryId ='';
		$scope.common.showAdd = false;
	}
	$scope.closeEditMsg = function(){
		$scope.common.showEdit = false;
	}
	//关闭生成优惠券框
	$scope.closeBatchNum = function() {
		$scope.common.showBatchNum = false;
	}
	//关闭添加规则框
	$scope.closeAddBatchRule = function(){
		$scope.common.showAddBatchRule = false;
		
		$scope.productSkuList = [];
		$scope.checkBoxStatus = [];
		$scope.choiceList=[];
		$scope.batchRuleAdd = {
			type:'',
			couponBatchId:''
		}
	}
	$scope.closeAllRule = function(){
		$scope.common.showAllRule = false; 
	}
	
	//将时间控件生成的时间格式化为20180101124500
	$scope.formatDateTime2 = function(endDate) {
		return endDate.substring(0,4) + endDate.substring(5,7) + endDate.substring(8,10) +  endDate.substring(11,13)+  endDate.substring(14,16)+"000000";
	}
	$scope.formatDateTime = function(endDate) {
		return endDate.substring(0,4) + '/' + endDate.substring(4,6) + '/' + endDate.substring(6,8);
	}
	//根据id查询优惠券批次
	$scope.queryCouponBatchById = function(id){
		$http({
		 	method  : 'GET',
		 	url     : '/coupon/queryCouponBatchById.htm',	        
		 	params:{
	            'id':id
	        },     
            transformRequest: angular.identity,
	        headers : {'Content-Type' : undefined}
		}).then(function(response){
			if(!response.data.success) {
				Notify("修改失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				return;
			}
			$scope.batchInfo = response.data.batchInfo;
		});	
	}
	//添加
	$scope.submitAddBatch = function(id){
		if($scope.batchNum.num == '') {
			Notify("优惠券数量不能为空！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		if(parseInt($scope.batchNum.num)>1000){
			Notify("一次性生成优惠券数量不能超过1000！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		/* var fd = new FormData();
		fd.append("id", id);
		fd.append("num", parseInt($scope.batchNum.num));
		console.log(parseInt($scope.batchNum.num)) */
		
		$http({
		 	method  : 'POST',
		 	url     : '/coupon/addCouponInfoList.htm',	        
		 	params:{
	            'id':id,
	            'num':parseInt($scope.batchNum.num)
	        },     
            transformRequest: angular.identity,
	        headers : {'Content-Type' : undefined}
		}).then(function(response){
			if(!response.data.success) {
				Notify("新增失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				return;
			}
		
			Notify('新增成功', 'top-right', '5000', 'success', 'fa-check', true);
			$scope.common.showBatchNum = false;
			$scope.pageChanged();
			
			$scope.batchNum = {
				num:''
			}
		});			
	}
	
	
	//提交添加项目
	$scope.submitAdd = function() {	
		if($scope.batchAdd.discretion == '') {
			Notify("优惠券描述不能为空！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		if($scope.batchAdd.displayName == '') {
			Notify("优惠券名称不能为空！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		if($scope.batchAdd.strategyType == '') {
			Notify("请选择优惠券凭证策略！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		
		if($scope.batchAdd.strategyType == '1') {
			if($scope.batchAdd.dayCount == '') {
				Notify("有效天数不能为空！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				return;
			}
		}
		if($scope.batchAdd.strategyType == '2') {
			if($scope.batchAdd.deadlineStr == '') {
				Notify("最后期限不能为空！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				return;
			}else{
				$scope.batchAdd.deadline = $scope.formatDateTime2($scope.batchAdd.deadlineStr);
			}
		}
		if($scope.batchAdd.type == '') {
			Notify("请选择优惠券类型！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		
		if($scope.batchAdd.type == '1') {
			if($scope.batchAdd.reductPrice == '') {
				Notify("门槛价不能为空！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				return;
			}
			if($scope.batchAdd.amount == '') {
				Notify("优惠金额不能为空！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				return;
			}
		}
		if($scope.batchAdd.type == '2') {
			if($scope.batchAdd.discount == '') {
				Notify("打折不能为空！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				return;
			}
		}
		
		$http({
		 	method  : 'POST',
		 	url     : '/coupon/addCouponBatch.htm',	        
	        data : $.param($scope.batchAdd),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
		}).then(function(response){
			if(!response.data.success) {
				Notify("新增失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				return;
			}
		
			Notify('新增成功', 'top-right', '5000', 'success', 'fa-check', true);
			$scope.common.showAdd = false;
			$scope.pageChanged();
			
			$scope.batchAdd = {
					discretion:'',
					strategyType:'',
					deadline:'',
					deadlineStr:'',
					dayCount:'',
					displayName:'',
					reductPrice:'',
					amount:'',
					type:'',
					discount:''
			}
		});			
	}
	//新增规则
	$scope.submitAddBatchRule = function(){
		if($scope.batchRuleAdd.type == '') {
			Notify("请选择优惠券规则！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		
		if($("input[name='checkAll']:checked").length !== 0){
			$scope.submitAddAllBatchRules();
			return;
		}
		if($("input[name='checkRule']:checked").length ===0 && $("input[name='checkAll']:checked").length === 0){
			Notify("请选择规则项！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		$("input[name='checkRule']:checked").each(function(){
    		$scope.choiceList.push($(this).val());
		})
		
		var checkiList = new Array();
		for(var i = 0;i<$scope.choiceList.length;i++){
			if(checkiList.indexOf($scope.choiceList[i]) == -1){
				checkiList.push($scope.choiceList[i])
			}
		}
		
		$http({
		 	method  : 'POST',
		 	url     : '/coupon/addBatchRule.htm',	        
	        data : $.param({
	        	ruleType: $scope.batchRuleAdd.type,
	        	couponBatchId:$scope.batchRuleAdd.couponBatchId,
	        	productSkuListJson:checkiList.toString()
	        }),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
		}).then(function(response){
			if(!response.data.success) {
				Notify("操作失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				return;
			}
		
			Notify('添加成功', 'top-right', '5000', 'success', 'fa-check', true);
			$scope.common.showAddBatchRule = false;
			$scope.pageChanged();
			
			$scope.productSkuList = [];
			$scope.checkBoxStatus = [];
			$scope.choiceList=[];
			$scope.batchRuleAdd = {
				type:'',
				couponBatchId:''
			}
			$scope.categoryId =''
		});
	}
	
	$scope.submitAddAllBatchRules = function(){
		$http({
		 	method  : 'POST',
		 	url     : '/coupon/addAllBatchRules.htm',	        
	        data : $.param({
	        	ruleType: $scope.batchRuleAdd.type,
	        	couponBatchId:$scope.batchRuleAdd.couponBatchId
	        }),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
		}).then(function(response){
			if(!response.data.success) {
				Notify("操作失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				return;
			}
		
			Notify('添加成功', 'top-right', '5000', 'success', 'fa-check', true);
			$scope.common.showAddBatchRule = false;
			$scope.pageChanged();
			
			$scope.productSkuList = [];
			$scope.checkBoxStatus = [];
			$scope.choiceList=[];
			$scope.batchRuleAdd = {
				type:'',
				couponBatchId:''
			}
		});
	}
	//修改
	$scope.submitEdit = function(){

		if($scope.batchInfo.discretion == '') {
			Notify("优惠券描述不能为空！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		if($scope.batchInfo.displayName == '') {
			Notify("优惠券名称不能为空！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		if($scope.batchInfo.strategyType == '') {
			Notify("请选择优惠券凭证策略！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		
		if($scope.batchInfo.strategyType == '1') {
			if($scope.batchInfo.dayCount == '') {
				Notify("有效天数不能为空！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				return;
			}
		}
		if($scope.batchInfo.strategyType == '2') {
			if($scope.batchInfo.deadlineStr == '') {
				Notify("最后期限不能为空！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				return;
			}else{
				$scope.batchInfo.deadline= $scope.formatDateTime2($scope.batchInfo.deadlineStr);
			}
		}
		if($scope.batchInfo.type == '') {
			Notify("请选择优惠券类型！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		
		if($scope.batchInfo.type == '1') {
			if($scope.batchInfo.reductPrice == '') {
				Notify("门槛价不能为空！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				return;
			}
			if($scope.batchInfo.amount == '') {
				Notify("优惠金额不能为空！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				return;
			}
		}
		if($scope.batchInfo.type == '2') {
			if($scope.batchInfo.discount == '') {
				Notify("打折不能为空！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				return;
			}
		}
		$http({
		 	method  : 'POST',
		 	url     : '/coupon/updateCouponBatch.htm',	        
	        data : $.param($scope.batchInfo),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
		}).then(function(response){
			if(!response.data.success) {
				Notify("修改失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				return;
			}
		
			Notify('修改成功', 'top-right', '5000', 'success', 'fa-check', true);
			$scope.common.showEdit = false;
			$scope.pageChanged();
			
		});
	}
});
