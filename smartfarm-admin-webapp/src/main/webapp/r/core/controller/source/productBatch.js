var app = angular.module("myApp", ["ui.bootstrap"]);
app.controller("myCtrl", function($scope,$http,$timeout) {
	var submitNow = false;
	$scope.common = {
			showAdd : false,
			showEdit : false,
			showAddSource : false,
			showPlanDetail : false,
			showEditDetail : false,
			showAddDetail : false
		};

		$scope.formData = {
			total : 0,
			pageSize : 10,
			pageNumber : 1
		}

		$scope.detailFormData = {
			total : 0,
			pageSize : 10,
			pageNumber : 1,
			masterId : ''
		}

		$scope.productBatchAdd = {
			productInfoId : '',
			batchCode : '',
			tunnelId : '',
			seedId : '',
			plantArea : '',
			startDate : ''
		}

		$scope.sourceAdd = {
			name : '',
			batchId : '',
			prefix : '',
			maxSearchTimes : '',
			remarks : ''
		}
		
		$scope.detailAdd = {
				name : '',
				brief : '',
				stage : '',
				taskDate : '',
				masterId : ''
		}

		$scope.nodeType = [ {
			"id" : 1,
			"name" : "电磁阀"
		} ]

		//刷新页面数据
		$scope.pageChanged = function() {
			$http(
					{
						method : "post",
						url : "/productBatch/queryProductBatchs.htm",
						data : $.param($scope.formData),
						headers : {
							"Content-Type" : "application/x-www-form-urlencoded"
						}
					})
					.then(
							function(response) {
								if (!response.data.success) {
									Notify("操作失败！",
											"top-right",
											"4000", "danger",
											"fa-bolt", true);
									return;
								}
								$scope.productBatchList = response.data.productBatchList;
								$scope.formData.total = response.data.total;
							});
		}
		$scope.pageChanged();

		//打开添加弹窗
		$scope.showAddWindow = function() {
			$http(
					{
						method : "post",
						url : "/productBatch/queryAddInfo.htm",
						params : {},
						headers : {
							"Content-Type" : "application/x-www-form-urlencoded"
						}
					})
					.then(
							function(response) {
								if (!response.data.success) {
									Notify("操作失败！",
											"top-right",
											"4000", "danger",
											"fa-bolt", true);
									return;
								}
								$scope.farmTunnelList = response.data.farmTunnelList;
								$scope.productList = response.data.productList;
								$scope.seedList = response.data.seedList;
							});
			$scope.common.showAdd = true;
		}

		//关闭添加弹窗
		$scope.closeAddWindow = function() {
			$scope.common.showAdd = false;
		}

		//提交添加
		$scope.submitAdd = function() {
			if (submitNow) {
				return;
			}
			var fd = new FormData();
			fd.append("productInfoId",
					$scope.productBatchAdd.productInfoId);
			fd.append("batchCode",
					$scope.productBatchAdd.batchCode);
			fd.append("tunnelId",
					$scope.productBatchAdd.tunnelId);
			fd.append("seedId",
					$scope.productBatchAdd.seedId);
			fd.append("plantArea",
					$scope.productBatchAdd.plantArea);
			fd.append("startDate",
					$scope.productBatchAdd.startDate);

			//console.log(fd);
			submitNow = true;
			//console.log(1);
			$http({
				method : 'post',
				url : '/productBatch/addBatchAndMaster.htm',
				data : fd,
				transformRequest : angular.identity,
				headers : {
					'Content-Type' : undefined
				}
			//angularjs设置文件上传的content-type修改方式
			}).then(
					function(response) {
						if (!response.data.success) {
							Notify("操作失败！", 'top-right',
									'4000', 'danger',
									'fa-bolt', true);
							submitNow = false;
							return;
						}

						Notify('添加成功', 'top-right', '5000',
								'success', 'fa-check', true);
						$scope.common.showAdd = false;
						$scope.pageChanged();
						submitNow = false;

						$scope.productBatchAdd = {
							productInfoId : '',
							batchCode : '',
							tunnelId : '',
							seedId : '',
							plantArea : '',
							startDate : ''
						};
					});
		}

		//初始化所有时间控件
		for (var i = 0; i < document
				.getElementsByName("myDates").length; i++) {
			$(document.getElementsByName("myDates"))
					.datetimepicker({
						minView : "month",//设置只显示到月份
						format : "yyyy-mm-dd",//日期格式
						autoclose : true,//选中关闭
						todayBtn : true
					//今日按钮
					});
		}

		//删除
		$scope.deleteBatchAndMaster = function(id, masterId) {
			var flag = confirm("删除批次：" + id);
			if (flag) {
				$http(
						{
							method : "post",
							url : "/productBatch/deleteBatchAndMaster.htm",
							params : {
								"id" : id,
								"masterId" : masterId
							},
							headers : {
								"Content-Type" : "application/x-www-form-urlencoded"
							}
						}).then(
						function(response) {
							if (!response.data.success) {
								Notify("删除失败！", "top-right",
										"4000", "danger",
										"fa-bolt", true);
								return;
							}
							$scope.pageChanged();//刷新数据
						});
			}
		}

		//打开添加溯源窗口
		$scope.showAddSourceWindow = function(id) {
			$scope.sourceAdd.batchId = id;
			$scope.common.showAddSource = true;
		}

		//关闭添加溯源窗口
		$scope.closeAddSourceWindow = function() {
			$scope.common.showAddSource = false;
		}


		$scope.submitAddSource = function() {
			if (submitNow) {
				return;
			}
			var fd = new FormData();
			fd.append("name", $scope.sourceAdd.name);
			fd.append("batchId", $scope.sourceAdd.batchId);
			fd.append("prefix", $scope.sourceAdd.prefix);
			fd.append("maxSearchTimes",
					$scope.sourceAdd.maxSearchTimes);
			fd.append("remarks", $scope.sourceAdd.remarks);

			//console.log(12);
			//console.log(fd);
			submitNow = true;
			//console.log(2);
			$http({
				method : 'post',
				url : '/productBatch/addSource.htm',
				data : fd,
				transformRequest : angular.identity,
				headers : {
					'Content-Type' : undefined
				}
			//angularjs设置文件上传的content-type修改方式
			}).then(
					function(response) {
						if (!response.data.success) {
							Notify("添加失败！", 'top-right',
									'4000', 'danger',
									'fa-bolt', true);
							submitNow = false;
							return;
						}

						Notify('添加成功', 'top-right', '5000',
								'success', 'fa-check', true);
						$scope.common.showAddSource = false;
						$scope.pageChanged();
						submitNow = false;

						$scope.sourceAdd = {
							name : '',
							batchId : '',
							prefix : '',
							maxSearchTimes : '',
							remarks : ''
						};
					});
		}

		//打开计划明细窗口
		$scope.showPlanDetailWindow = function(id) {
			$scope.detailFormData.masterId = id;
			//console.log(typeof id);
			$http(
					{
						method : 'post',
						url : '/planDetail/getPageListByMasterId.htm',
						data : $.param($scope.detailFormData),
						transformRequest : angular.identity,
						headers : {
							'Content-type' : 'application/x-www-form-urlencoded'
						}
					//angularjs设置文件上传的content-type修改方式
					})
					.then(
							function(response) {
								if (!response.data.success) {
									Notify("操作失败！",
											'top-right',
											'4000', 'danger',
											'fa-bolt', true);
									return;
								}
								$scope.planDetailList = response.data.planDetailList;
								$scope.detailFormData.total = response.data.total;
								$scope.common.showPlanDetail = true;
							});
		}

		//关闭计划明细窗口
		$scope.closePlanDetailWindow = function() {
			$scope.common.showPlanDetail = false;
		}

		//打开修改计划明细窗口
		$scope.showEditDetailWindow = function(id) {
			$http(
					{
						method : 'post',
						url : '/planDetail/getDetailById.htm',
						params : {
							"id" : id
						},
						transformRequest : angular.identity,
						headers : {
							'Content-type' : 'application/x-www-form-urlencoded'
						}
					//angularjs设置文件上传的content-type修改方式
					})
					.then(
							function(response) {
								if (!response.data.success) {
									Notify("操作失败！",
											'top-right',
											'4000', 'danger',
											'fa-bolt', true);
									return;
								}
								$scope.planDetailEdit = response.data.planDetail;
							});
			
			$scope.common.showEditDetail = true;
		}

		//关闭修改计划明细窗口
		$scope.closeEditDetailWindow = function() {
			$scope.common.showEditDetail = false;
		}

		//提交修改计划
		$scope.submitEditDetail = function(){
			if (submitNow) {
				return;
			}
			var fd = new FormData();
			fd.append("id",$scope.planDetailEdit.id);
			fd.append("name",$scope.planDetailEdit.name);
			fd.append("brief",$scope.planDetailEdit.brief);
			fd.append("stage",$scope.planDetailEdit.stage);
			fd.append("taskDate",$scope.planDetailEdit.taskDate);

			//console.log(fd);
			submitNow = true;
			$http({
				method : 'post',
				url : '/planDetail/modifyDetailById.htm',
				data : fd,
				transformRequest : angular.identity,
				headers : {
					'Content-Type' : undefined
				}
			//angularjs设置文件上传的content-type修改方式
			}).then(function(response) {
					if (!response.data.success) {
						Notify("操作失败！", 'top-right','4000', 'danger','fa-bolt', true);
						submitNow = false;
						return;
					}

					Notify('添加成功', 'top-right', '5000','success', 'fa-check', true);
					$scope.showPlanDetailWindow($scope.detailFormData.masterId);
					$scope.common.showEditDetail = false;
				setTimeout(function () {

					document.getElementById('closeEditDetailWin').click();

				}, 100)

					submitNow = false;
				});
		}
		
		//打开添加计划窗口
		$scope.showAddDetailWindow = function(id){
			$scope.detailAdd.masterId = id;
			$scope.common.showAddDetail = true;
		}
		
		
		//关闭添加计划窗口
		$scope.closeAddDetailWindow = function(){
			$scope.common.showAddDetail = false;
		}
		
		//提交添加计划
		$scope.submitAddDetail = function(){
			if (submitNow) {
				return;
			}
			var fd = new FormData();
			fd.append("plantId",$scope.detailAdd.masterId);
			fd.append("name",$scope.detailAdd.name);
			fd.append("brief",$scope.detailAdd.brief);
			fd.append("stage",$scope.detailAdd.stage);
			fd.append("taskDate",$scope.detailAdd.taskDate);

			//console.log(fd);
			submitNow = true;
			$http({
				method : 'post',
				url : '/planDetail/addPlanDetail.htm',
				data : fd,
				transformRequest : angular.identity,
				headers : {
					'Content-Type' : undefined
				}
			//angularjs设置文件上传的content-type修改方式
			}).then(function(response) {
					if (!response.data.success) {
						Notify("添加失败！", 'top-right','4000', 'danger','fa-bolt', true);
						submitNow = false;
						return;
					}

					Notify('添加成功', 'top-right', '10000','success', 'fa-check', true);
					$scope.showPlanDetailWindow($scope.detailFormData.masterId);
					$scope.common.showAddDetail = false;
				setTimeout(function () {

					$scope.$applyAsync(document.getElementById('closeAddDetailWin').click());

				}, 100)
					submitNow = false;
				});
			// console.log(1);
			$scope.detailAdd = {
					name : '',
					brief : '',
					stage : '',
					taskDate : '',
					masterId : ''
			}
		}
		
		//删除计划
		$scope.deletePlanDerail = function(id,name,taskDate){
			var flag = confirm("删除任务：" + name + "\n任务日期：" + taskDate);
			if (flag) {
				$http(
						{
							method : "post",
							url : "/planDetail/deleteDetailById.htm",
							params : {
								"id" : id
							},
							headers : {
								"Content-Type" : "application/x-www-form-urlencoded"
							}
						}).then(
						function(response) {
							if (!response.data.success) {
								Notify("删除失败！", "top-right",
										"4000", "danger",
										"fa-bolt", true);
								return;
							}
							$scope.showPlanDetailWindow($scope.detailFormData.masterId);//刷新计划数据
						});
			}
		}
		
		$scope.optionChange = function(id) {
			//console.log(id);
		}
});
