var app = angular.module("myApp", ["ui.bootstrap"]);
app.controller("myCtrl", function($scope,$http,$timeout) {
	var editNow = false;
	var submitNow = false;
	$scope.picurl1 =  picurl;
	$scope.common = {
		showAdd:false,
		showEdit:false
	};
	$scope.formData = {
		total:0,
		pageSize:10,
		pageNumber:1,
		name:''
	};
	
	$scope.categoryAdd = {
		name:'',
		parentId:'',
		sortOrder:'',
	};
	
	$scope.pageChanged = function() {
		$http({
	        method  : 'POST',
	        url     : '/productCategory/queryProductCategoryList.htm',
	        data    : $.param($scope.formData),
	        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
	    }).then(function(response) {
	    	$scope.list = response.data.productCategoryList;
	    	$scope.formData.total = response.data.total;
	    });
	}
	
	$scope.getCategories = function() {
		$http({
	        method  : 'GET',
	        url     : '/productCategory/queryAllProductCategoryList.htm',
	        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
	    }).then(function(response) {
	    	$scope.categories = response.data.AllCategories;
	    });
	}
	
	$scope.getCategoryInfoById = function(categoryId){
		$http({
	        method  : 'GET',
	        url     : '/productCategory/queryProductCategoryById.htm',
	        params:{
	            'categoryId':categoryId
	        },
	        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
	    }).then(function(response) {
	    	$scope.categoryInfo = response.data.category;
	    	$scope.categoryStatus = response.data.category.status;
	    	
	    	$scope.parentCategory = response.data.parentCategory;
	    });
	}
	
	$scope.changestatus = function(categoryId){
		$http({
		 	method  : 'GET',
	        url     : '/productCategory/updateStatus.htm',
	        params:{
	            'categoryId':categoryId
	        },
        	headers : {'Content-Type' : 'application/x-www-form-urlencoded'}  //angularjs设置文件上传的content-type修改方式
		}).then(function(response){
			$scope.pageChanged();
		});
	}
	
	$scope.pageChanged();
	
	$scope.showAddMsg = function() {
		$scope.common.showAdd = true;
		$scope.getCategories()
	}
	
	$scope.closeAddMsg = function() {
		$scope.common.showAdd = false;
	}
	
	$scope.showEditMsg = function(categoryId) {
		/* alert(categoryId) */
		
		$scope.common.showEdit = true;
		$scope.getCategoryInfoById(categoryId)
		$scope.getCategories();
	}
	
	
	
	$scope.closeEditMsg = function() {
		$scope.common.showEdit = false;
	}
	
	$scope.submitAdd = function() {
		if(submitNow) {
			return;
		}

		if(!stringCheck("类别名称",$scope.categoryAdd.name)){return;}
		if(!intCheck("类别等级",$scope.categoryAdd.level)){return;}
		if(!intCheck("排序",$scope.categoryAdd.sortOrder)){return;}
		if(!picCheck("类别图片","addImg")){return;}

		console.log($scope.categoryAdd);
		console.log($.param($scope.categoryAdd));
		var fd = new FormData();
		fd.append("name", $scope.categoryAdd.name);
		fd.append("level", $scope.categoryAdd.level);
		fd.append("sortOrder", $scope.categoryAdd.sortOrder);
		fd.append("parentId", $scope.categoryAdd.parentId);	
		
		fd.append("file",  document.querySelector('input[name="addImg"]').files[0]);
		
		submitNow = true;
		$http({
		 	method  : 'POST',
	        url     : '/productCategory/addProductCategory.htm',
	        data : fd,
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
			$scope.categoryAdd = {
					name:'',
					parentId:'',
					sortOrder:'',
					level:''
			};
			
		});
	}
	
	
	$scope.submitEdit = function() {
		if(editNow) {
			return;
		}

		/* console.log($scope.categoryEdit);
		console.log($.param($scope.categoryEdit)); */
		var fd = new FormData();
		fd.append("id",$scope.categoryInfo.id)
		fd.append("name", $scope.categoryInfo.name);
		fd.append("sortOrder", $scope.categoryInfo.sortOrder);
		var parentId=$scope.categoryInfo.parentId;
		if(parentId==null){parentId=''}
		fd.append("parentId", parentId);
		 fd.append("level",$scope.categoryInfo.level)
		fd.append("file",  document.querySelector('input[name="editImg"]').files[0]);
		editNow = true;
		$http({
		 	method  : 'POST',
	        url     : '/productCategory/editProductCategory.htm',
	        data : fd,
            transformRequest: angular.identity,
        	headers : {'Content-Type' : undefined}  //angularjs设置文件上传的content-type修改方式
		}).then(function(response){
			if(!response.data.success) {
				Notify("操作失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				/* submitNow = false; */
				return;
			}

			Notify('更新成功', 'top-right', '5000', 'success', 'fa-check', true);
			$scope.common.showEdit = false;
			$scope.pageChanged();
			editNow = false;
			

		});
	}
});
