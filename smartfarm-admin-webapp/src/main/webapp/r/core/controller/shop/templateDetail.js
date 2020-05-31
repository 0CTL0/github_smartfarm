var app = angular.module("myApp", ["ui.bootstrap"]);
app.controller("myCtrl", function($scope,$http,$timeout) {
	var submitNow = false;
	$scope.common = {
		showAdd:false,
		showEitor:false,
		all:''
	};
	
	$scope.formData = {
		total:0,
		pageSize:10,
		pageNumber:1,
		templateId:'',
		type:''
	}	

	//配送区域列表
	$scope.areaList=[{provinces:'辽宁省',flag:false},{provinces:'吉林省',flag:false},{provinces:'黑龙江省',flag:false},
		{provinces:'上海市',flag:false},{provinces:'江苏省',flag:false},{provinces:'浙江省',flag:false},{provinces:'安徽省',flag:false},{provinces:'江西省',flag:false},
		{provinces:'河南省',flag:false},{provinces:'湖北省',flag:false},{provinces:'湖南省',flag:false},
		{provinces:'福建省',flag:false},{provinces:'广东省',flag:false},{provinces:'广西壮族自治区',flag:false},{provinces:'海南省',flag:false},
		{provinces:'北京市',flag:false},{provinces:'天津市',flag:false},{provinces:'河北省',flag:false},{provinces:'山西省',flag:false},{provinces:'内蒙古自治区',flag:false},{provinces:'山东省',flag:false},
		{provinces:'陕西省',flag:false},{provinces:'甘肃省',flag:false},{provinces:'青海省',flag:false},{provinces:'宁夏回族自治区',flag:false},{provinces:'新疆维吾尔族自治区',flag:false},
		{provinces:'重庆市',flag:false},{provinces:'四川省',flag:false},{provinces:'贵州省',flag:false},{provinces:'云南省',flag:false},{provinces:'西藏自治区',flag:false},
		{provinces:'陕西省',flag:false},{provinces:'台湾省',flag:false},{provinces:'香港特别行政区',flag:false},{provinces:'澳门特别行政区',flag:false}
	]

	//清除所有勾选项
	$scope.clearArea=function () {
		for (let i = 0,len=	$scope.areaList.length; i < len; i++) {
			$scope.areaList[i].flag=false;
		}
	}

	//全选或取消全选
	$scope.allChose=function(){
		for (let i = 0,len=	$scope.areaList.length; i < len; i++) {
			$scope.areaList[i].flag=$scope.common.all;
		}
	}

	
	//刷新页面	
	$scope.pageChanged = function() {
		$scope.formData.templateId=getParams("templateId");
		$scope.formData.type=getParams("type");
		//console.log("templateId:"+$scope.formData.templateId+",type:"+$scope.formData.type);
		$http({
	        method  : 'POST',	       
	        url     : '/logisticsTemplate/queryTemplateDetailList.htm',
	        data    : $.param($scope.formData),
	        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
	    }).then(function(response) {
	    	$scope.list = response.data.templateDetailList;
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
		if($scope.formData.type==1){if(!doubleCheck("首重",$scope.templateDetailAdd.firstHeavy)){return;}}
		if($scope.formData.type==2){if(!intCheck("首件",$scope.templateDetailAdd.firstPiece)){return;}}
		if($scope.formData.type==1){if(!doubleCheck("首重运费",$scope.templateDetailAdd.firstFreight)){return;}}
		if($scope.formData.type==2){if(!doubleCheck("首件运费",$scope.templateDetailAdd.firstFreight)){return;}}
		if($scope.formData.type==1){if(!doubleCheck("续重",$scope.templateDetailAdd.continueHeavy)){return;}}
		if($scope.formData.type==2){if(!intCheck("续件",$scope.templateDetailAdd.continuePiece)){return;}}
		if($scope.formData.type==1){if(!doubleCheck("续重运费",$scope.templateDetailAdd.continueFreight)){return;}}
		if($scope.formData.type==2){if(!doubleCheck("续件运费",$scope.templateDetailAdd.continueFreight)){return;}}
		let areaStr='';
		for (let i = 0,len=	$scope.areaList.length; i < len; i++) {
			if($scope.areaList[i].flag){
				areaStr=areaStr+$scope.areaList[i].provinces+',';
			}
		}
		if(!stringCheck("配送区域",areaStr)){return;}
		areaStr=areaStr.substr(0,areaStr.length-1);
		submitNow = true;
		$http({
			method  : 'POST',
			url     : '/logisticsTemplate/addTemplateDetail.htm',
			params : {
				'logisticsTemplateId':$scope.formData.templateId,
				'firstHeavy':$scope.templateDetailAdd.type,
				'firstPiece':$scope.templateDetailAdd.firstPiece,
				'firstHeavy':$scope.templateDetailAdd.firstHeavy,
				'firstFreight':$scope.templateDetailAdd.firstFreight,
				'continuePiece':$scope.templateDetailAdd.continuePiece,
				'continueHeavy':$scope.templateDetailAdd.continueHeavy,
				'continueFreight':$scope.templateDetailAdd.continueFreight,
				'shipArea':areaStr
			},
			transformRequest: angular.identity,
			headers : {'Content-Type' : 'application/x-www-form-urlencoded'}
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
				firstPiece:'',
				firstHeavy:'',
				firstFreight:'',
				continueHeavy:'',
				continuePiece:'',
				continueFreight:''
			};
			$scope.clearArea();
		});
	}


	//显示编辑框
	$scope.showEitorMsg = function(id) {
		$http({
		 	method  : 'POST',
	        url     : '/logisticsTemplate/queryProductTemplateDetail.htm',
	        params : {'id':id},
            transformRequest: angular.identity,
        	headers : {'Content-Type' : 'application/x-www-form-urlencoded'}
		}).then(function(response){
			if(!response.data.success) {
				Notify("操作失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				return;
			}			
			$scope.templateDetailEitor = response.data.templateDetail;
			let areaStr=$scope.templateDetailEitor.shipArea;
			let areaArr=areaStr.split(',');
			for(let i=0,len=areaArr.length;i<len;i++){
				for(let j=0,len2=$scope.areaList.length;j<len2;j++){
					if(areaArr[i]==$scope.areaList[j]){
						$scope.areaList[j].flag=true;
					}

				}
			}
			$scope.common.showEitor = true;
		});
	}


	//关闭编辑框
	$scope.closeEitorMsg = function() {
		$scope.common.showEitor = false;
		$scope.pageChanged();
		$scope.clearArea();
	}
	
				
	//提交编辑	
	$scope.submitEitor = function() {
		let areaStr='';
		for (let i = 0,len=	$scope.areaList.length; i < len; i++) {
			if($scope.areaList[i].flag){
				areaStr=areaStr+$scope.areaList[i].provinces+',';
			}
		}
		if(areaStr!=''){
			areaStr=areaStr.substr(0,areaStr.length-1);
		}
		$http({
		 	method  : 'POST',
			url     : '/logisticsTemplate/editTemplateDetail.htm',
			params : {
		 		'id':$scope.templateDetailEitor.id,
				'logisticsTemplateId':$scope.formData.templateId,
				'firstHeavy':$scope.templateDetailEitor.firstHeavy,
				'firstPiece':$scope.templateDetailEitor.firstPiece,
				'firstHeavy':$scope.templateDetailEitor.firstHeavy,
				'firstFreight':$scope.templateDetailEitor.firstFreight,
				'continuePiece':$scope.templateDetailEitor.continuePiece,
				'continueHeavy':$scope.templateDetailEitor.continueHeavy,
				'continueFreight':$scope.templateDetailEitor.continueFreight,
				'shipArea':areaStr
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
			$scope.clearArea();
			submitNow = false;
		});
	}


});
