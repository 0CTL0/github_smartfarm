var app = angular.module("myApp", ["ui.bootstrap"]);
app.controller("myCtrl", function($scope,$http,$timeout,$window) {
	var flag = true;
	//图片路径前缀
	$scope.picUrl=picurl;
	
	$scope.formData = {
		id:''
	}
	$scope.common = {
		showNodeInfo:false,
		showVideo:false,
		showCtVideo:false,
		ctHeight:'300px',
		showData:false,
		open:false
	}
	//传感器 type 1气象站  2土壤 3水 4控制器
	
	$scope.cgList = [
	    {flag:'860344047999306',nodes:'1',hasCamera:true,cameraId:1,index:0,top:'250px',right:'280px',type:2},
		{flag:'860344047999306',nodes:'2',hasCamera:false,cameraId:0,index:1,top:'300px',right:'370px',type:2},
		{flag:'860344047999306',nodes:'3',hasCamera:true,cameraId:8,index:2,top:'350px',right:'440px',type:2},

		{flag:'865650042738845',nodes:'1,2,3,4',hasCamera:false,cameraId:0,index:3,top:'310px',right:'240px',type:1},
		{flag:'865650042738845',nodes:'5',hasCamera:true,cameraId:7,index:4,top:'420px',right:'290px',type:2},
		{flag:'865650042738845',nodes:'6',hasCamera:true,cameraId:2,index:5,top:'470px',right:'355px',type:2},

		{flag:'865650044627384',nodes:'2',hasCamera:false,cameraId:0,index:6,top:'540px',right:'350px',type:2},
		{flag:'865650044627384',nodes:'1',hasCamera:true,cameraId:5,index:7,top:'560px',right:'410px',type:2},
		{flag:'865650044627384',nodes:'3',hasCamera:true,cameraId:4,index:8,top:'610px',right:'450px',type:2},

		{flag:'865650042797437',nodes:'1',hasCamera:true,cameraId:3,index:9,top:'660px',right:'480px',type:2},
		{flag:'865650042797437',nodes:'2',hasCamera:false,cameraId:0,index:10,top:'690px',right:'460px',type:2},
		/* oK */
		{flag:'865650042797437',nodes:'3',hasCamera:true,cameraId:6,index:11,top:'715px',right:'440px',type:2},
	                 
	    /*{flag:'865650042738845',nodes:'1,2,3',hasCamera:false,cameraId:0,index:0,top:'500px',right:'505px',type:1},
	    {flag:'860344047999306',nodes:'1,2,3',hasCamera:true,cameraId:1,index:1,top:'230px',right:'240px',type:2},
	    {flag:'865650042797437',nodes:'1,2,3',hasCamera:true,cameraId:2,index:2,top:'400px',right:'210px',type:2},
	    {flag:'865650044627384',nodes:'1,2,3',hasCamera:true,cameraId:3,index:3,top:'320px',right:'530px',type:2},
	    {flag:'865650042738845',nodes:'4,5,6',hasCamera:true,cameraId:4,index:4,top:'458px',right:'760px',type:2},
	    {flag:'865650042738845',nodes:'2,3,4,5,6',hasCamera:true,cameraId:3,index:5,top:'680px',right:'310px',type:3},*/
	    {flag:'4', nodes:'1', hasCamera:true, cameraId:3, index:12, top:'650px', right:'535px', type:4},
	];
	
	
	//初始化列表
	$scope.pageChanged = function() {
		$http({
	        method  : 'GET',
	        url     : '/realData/queryMeteorologicalList.htm',
	        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
	    }).then(function(response) {
	    	console.log(response.data);
	    	$scope.avgSoilList = response.data.avgSoilList;
	    	$scope.meteorologicalList = response.data.meteorologicalList;
	    	$scope.liquidList = response.data.liquidList;
	    	//console.log(response.data.meteorologicalList)
	    });
	}
	
	$scope.pageChanged();
	
	$scope.clickWindow = function() {
		if($scope.common.open) {
			$scope.closeWindow();
		}else {
			$scope.openWindow();
		}
		$scope.common.open = !$scope.common.open;
	}
	$scope.openWindow = function() {
		var viewFullScreen = document.getElementById("titleOpen");
        if (viewFullScreen) {
            viewFullScreen.addEventListener("click", function () {
                var docElm = document.documentElement;
                if (docElm.requestFullscreen) {
                    docElm.requestFullscreen();
                }
                else if (docElm.msRequestFullscreen) {
                    docElm = document.body; //overwrite the element (for IE)
                    docElm.msRequestFullscreen();
                }
                else if (docElm.mozRequestFullScreen) {
                    docElm.mozRequestFullScreen();
                }
                else if (docElm.webkitRequestFullScreen) {
                    docElm.webkitRequestFullScreen();
                }
            }, false);
        }
	}
	
	$scope.closeWindow = function() {
		var cancelFullScreen = document.getElementById("titleOpen");
		if (cancelFullScreen) {
            cancelFullScreen.addEventListener("click", function () {
                if (document.exitFullscreen) {
                    document.exitFullscreen();
                }
                else if (document.msExitFullscreen) {
                    document.msExitFullscreen();
                }
                else if (document.mozCancelFullScreen) {
                    document.mozCancelFullScreen();
                }
                else if (document.webkitCancelFullScreen) {
                    document.webkitCancelFullScreen();
                }
            }, false);
        }
	}
	
	$scope.runPrefixMethod = function(element, method) {
		var usablePrefixMethod;
        ["webkit", "moz", "ms", "o", ""].forEach(function(prefix) {
            if (usablePrefixMethod) return;
            if (prefix === "") {
                // 无前缀，方法首字母小写
                method = method.slice(0,1).toLowerCase() + method.slice(1);
                
            }
            var typePrefixMethod = typeof element[prefix + method];
            if (typePrefixMethod + "" !== "undefined") {
                if (typePrefixMethod === "function") {
                    usablePrefixMethod = element[prefix + method]();
                } else {
                    usablePrefixMethod = element[prefix + method];
                }
            }
        });
        return usablePrefixMethod;
	}
	
	//初始化专家列表
	$scope.pageProficientList = function() {
		$http({
	        method  : 'GET',
	        url     : '/proficient/queryProficientList.htm',
	        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
	    }).then(function(response) {
    		$scope.proficientList = response.data.proficientList;
    		$timeout(function() {
    			$("#pic_list_1").cxScroll();
    		},500);
    		
	    });
	}
	
	$scope.pageProficientList();
	
	//根据id展示土壤数据
	$scope.showSoilDataByCode = function(codeFlag,cameraId,index){
		$scope.common.showData = false;
		$scope.common.showVideo = false;
		$scope.common.showNodeInfo = false;
		$scope.querySersorNodeByCode(codeFlag,cameraId,index);
		
	}
	//根据code查询传感数据信息
	$scope.querySersorNodeByCode = function(codeFlag,cameraId,index){
		if($scope.lastSoidIndex == index) {
			$scope.lastSoidIndex = "";
			return;
		}
		$http({
	        method  : 'POST',
	        url     : '/realData/querySersorNodeByCode.htm',
	        params:{
	            'productCode':codeFlag,
	            'cameraId':cameraId,
	            showCamera:$scope.cgList[index].hasCamera,
	            nodes:$scope.cgList[index].nodes
	        },
	        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
	    }).then(function(response) {
	    	$scope.sersorSoilDataList = response.data.sersorSoilDataList;
	    	$scope.liveAddress = response.data.liveAddress;
	    	$scope.common.showData = true;
			if($scope.cgList[index].hasCamera){
				$scope.common.showVideo = true;
				$timeout(function(){
		    		 $("#myPlayer").attr("src", response.data.liveAddress);
	 				var player = new EZUIKit.EZUIPlayer("myPlayer");
		    	},500)
			}
			$scope.lastSoidIndex = index;
	    });
	}
	
	//打开节点窗口
	$scope.showNodeWindow = function(index){
		$scope.formData.id = $scope.cgList[index].flag;
		$scope.common.showData = false;
		$scope.common.showVideo = false;
		$scope.common.showNodeInfo = false;
		$http({
			method : 'POST',
			url : '/controlNode/getSingleControlNode.htm',
			params : {
				'id' : $scope.cgList[index].flag
			},
			headers : {'Content-Type' : 'application/x-www-form-urlencoded'}//angularjs设置文件上传的content-type修改方式		
		}).then(function(response) {
			if (!response.data.success) {
				Notify("操作失败！",'top-right','4000', 'danger','fa-bolt', true);
				return;
			}
			$scope.controlNodeEdit = response.data.controlNode;
		});
		$scope.common.showNodeInfo = true;
		if($scope.cgList[index].hasCamera) {
			console.log(1);
			$http({
				method : 'POST',
				url : '/realData/queryVideoAddress.htm',
				params : {
					'cameraId' : $scope.cgList[index].cameraId
				},
				headers : {'Content-Type' : 'application/x-www-form-urlencoded'}//angularjs设置文件上传的content-type修改方式		
			}).then(function(response) {
				$scope.common.showVideo = true;
				$timeout(function(){
		    		 $("#myPlayer").attr("src", response.data.liveAddress);
	 				var player = new EZUIKit.EZUIPlayer("myPlayer");
		    	},500)
			});
		}
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
				return;
			}
			Notify('操作成功', 'top-right', '5000','success', 'fa-check', true);
			$scope.controlNodeEdit.operateStatus = num;
		});
	}
});