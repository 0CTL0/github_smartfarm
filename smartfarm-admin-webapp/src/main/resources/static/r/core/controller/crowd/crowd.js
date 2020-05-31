var app = angular.module("myApp", ["ui.bootstrap"]);
var submitNow = false;
app.controller("myCtrl", function($scope,$http,$timeout) {

    $scope.common = {
        showAdd:false,
        showEdit:false,
        showOrderList:false,
        showOrderProgress:false,
        showProgress:false
    };

    //图片路径前缀
    $scope.picUrl = picurl;

    for(var i = 0;i<document.getElementsByName("myDates").length;i++){
        $(document.getElementsByName("myDates")).datetimepicker({
            minView: "month",//设置只显示到月份
            format : "yyyy-mm-dd",//日期格式
            autoclose:true,//选中关闭
            todayBtn: true//今日按钮
        });
    }

    //添加项目进展参数
    $scope.orderProInfo = {
        id:'',
        content:''
    }
    //查询列表参数
    $scope.formData = {
        total:0,
        pageSize:10,
        pageNumber:1
    }

    //众筹列表参数
    $scope.crowdOrderData = {
        total:0,
        pageSize:10,
        pageNumber:1,
        id:''
    }

    //添加众筹参数
    $scope.crowdFundingAdd = {
        name:'',
        sponsor:'',
        targerAmount:'',
        orderNum:'',
        startTime:'',
        endTime:'',
        provideTime:'',
        brief:'',
        period:'',
        miniQuantity:'',
        bonusRate:'',
        profitType:'',
        isShow:''
    }

    //存储新增的档次设置数组
    $scope.gearList = [];
    $scope.gearList.push({price:'',description:''});
    //新增档次设置
    $scope.addGear= function() {
        $scope.gearList.push({id:'',price:'',description:''});
    }
    //移除新增的档位设置
    $scope.removeGear = function(index) {
        $scope.gearList.splice(index,1);
    }

    $scope.showAddMsg = function() {
        $scope.common.showAdd = true;
        //时间控件初始化
        $('#profitDate').datetimepicker();
        $('#joinDate').datetimepicker();
        $('#joinDeadlineDate').datetimepicker();
    }
    //打开众筹列表
    $scope.showOrderList = function(id){
        $scope.common.showOrderList = true;

        $scope.crowdOrderData.id = id;

        $scope.queryCrowOrderList();
        /* console.log($scope.crowdOrderList) */
    }
    //关闭众筹列表
    $scope.closeOrderList = function(){
        $scope.common.showOrderList = false;
    }
    $scope.closeAddProgress = function(){
        $scope.showProgress = false;
    }
    $scope.showAddProgress = function(){
        $scope.showProgress = true;
        /* console.log($scope.showProgress) */
    }
    //关闭订单进展
    $scope.closeOrderProgress = function(){
        $scope.common.showOrderProgress = false;
    }
    //打开编辑框
    $scope.showEdit = function(crowdFunding){
        $scope.common.showEdit = true;
        $scope.queryCrowdFundingById(crowdFunding.id);

        $('#editProfitDate').datetimepicker();
        $('#editJoinDate').datetimepicker();
        $('#editJoinDeadlineDate').datetimepicker();
        $timeout(function(){
            $scope.crowdFundingInfo.startTime=$scope.formatDateTime($scope.crowdFundingInfo.startTime);
            $scope.crowdFundingInfo.endTime=$scope.formatDateTime($scope.crowdFundingInfo.endTime);
            $scope.crowdFundingInfo.provideTime=$scope.formatDateTime($scope.crowdFundingInfo.provideTime);
        },500);

    }
    //打开项目进展
    $scope.showOrderProgress = function(crowdId){
        $scope.common.showOrderProgress = true;

        $scope.queryAllOrderProgressById(crowdId);
        //设置项目id为当前项目id
        $scope.orderProInfo.id = crowdId;
    }

    //立即分红
    $scope.updateBonusById = function(orderId){
        var fd = new FormData();
        fd.append("id",orderId)
        $http({
            method  : 'POST',
            url     : '/crowdFunding/updateBonusById.htm',
            data : fd,
            transformRequest: angular.identity,
            headers : {'Content-Type' : undefined}  //angularjs设置文件上传的content-type修改方式
        }).then(function(response){
            if(!response.data.success) {
                Notify("分红失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
                return;
            }

            Notify('分红成功', 'top-right', '5000', 'success', 'fa-check', true);
            $scope.queryCrowOrderList();
        });
    }

    //一键分红
    $scope.updateAllBonus = function(){
        if(window.confirm('你确定一键分红吗？')){
        }else{
            return false;
        }
        $http({
            method  : 'POST',
            url     : '/crowdFunding/updateAllBonus.htm',
            transformRequest: angular.identity,
            headers : {'Content-Type' : undefined}  //angularjs设置文件上传的content-type修改方式
        }).then(function(response){
            if(!response.data.success) {
                Notify("一键分红失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
                return;
            }
            Notify('一键分红成功', 'top-right', '5000', 'success', 'fa-check', true);
            $scope.queryCrowOrderList();
        });
    }

    $scope.closeEdit = function(crowdFunding){
        $scope.common.showEdit = false;
    }

    $scope.closeAddMsg = function() {
        $scope.common.showAdd = false;
        $scope.gearList = [];
        $scope.gearList.push({price:'',describe:''});
    }
    //初始化列表
    $scope.pageChanged = function() {
        $http({
            method  : 'POST',
            url     : '/crowdFunding/quryCrowdFundingList.htm',
            data    : $.param($scope.formData),
            headers : {'Content-Type': 'application/x-www-form-urlencoded'},
        }).then(function(response) {
            $scope.list = response.data.crowdFundingList;
            $scope.formData.total = response.data.total;
        });
    }

    $scope.pageChanged();

    //查询全部众筹列表
    $scope.queryCrowOrderList = function() {
        $http({
            method  : 'POST',
            url     : '/crowdFunding/queryCrowOrderList.htm',
            data    : $.param($scope.crowdOrderData),
            headers : {'Content-Type': 'application/x-www-form-urlencoded'},
        }).then(function(response) {
            $scope.crowdOrderList = response.data.crowdOrderList;
            $scope.crowdOrderData.total = response.data.total;
        });
    }

    //格式化时间
    $scope.formatDateTime = function(endDate) {
        return endDate.substring(0,4) + '-' + endDate.substring(4,6) + '-' + endDate.substring(6,8);
    }

    //将时间控件生成的时间格式化为20180101124500
    $scope.formatDateTime2 = function(endDate) {
        return endDate.substring(0,4) + endDate.substring(5,7) + endDate.substring(8,10) +  endDate.substring(11,13)+  endDate.substring(14,16)+"000000";
    }

    $scope.formatDate = function(endDate) {
        return endDate.substring(0,4) + '/' + endDate.substring(4,6) + '/' + endDate.substring(6,8);
    }

    //根据id得到众筹信息
    $scope.queryCrowdFundingById = function(id){
        $http({
            method  : 'GET',
            url     : '/crowdFunding/queryCrowdFundingById.htm',
            params:{
                'id':id
            },
            headers : {'Content-Type': 'application/x-www-form-urlencoded'},
        }).then(function(response) {
            $scope.crowdFundingInfo = response.data.crowdFundingInfo;
            /* console.log($scope.crowdFundingInfo.startTime) */
        });
    }

    //根据项目id得到项目进展
    $scope.queryAllOrderProgressById = function(id){
        $http({
            method  : 'GET',
            url     : '/crowdFunding/queryAllOrderProgressById.htm',
            params:{
                'id':id
            },
            headers : {'Content-Type': 'application/x-www-form-urlencoded'},
        }).then(function(response) {
            $scope.progressInfoList = response.data.progressInfoList;
        });
    }

    //编辑时移除档位设置
    $scope.deleteGear = function(index){
        $http({
            method  : 'GET',
            url     : '/crowdFunding/deleteGroupById.htm',
            params:{
                'id':$scope.crowdFundingInfo.gradeList[index].id
            },
            headers : {'Content-Type' : 'application/x-www-form-urlencoded'}  //angularjs设置文件上传的content-type修改方式
        }).then(function(response){
            if(!response.data.success) {
                Notify("操作失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
                return;
            }
            $scope.crowdFundingInfo.gradeList.splice(index,1);
        });
    }

    //编辑时新增价格档位
    $scope.addEditGear= function() {
        $scope.crowdFundingInfo.gradeList.push({price:'',description:''});
    }

    //提交添加项目
    $scope.submitAdd = function() {
        if($scope.crowdFundingAdd.name == '') {
            Notify("请输入项目名称！", 'top-right', '4000', 'danger', 'fa-bolt', true);
            return;
        }
        if($scope.crowdFundingAdd.sponsor == '') {
            Notify("请输入项目负责人！", 'top-right', '4000', 'danger', 'fa-bolt', true);
            return;
        }

        if(document.querySelector('input[name="addCover"]').files[0] == null){
            Notify("请输入封面图片！", 'top-right', '4000', 'danger', 'fa-bolt', true);
            return;
        }
        if(document.querySelector('input[name="addDesc"]').files[0] == null){
            Notify("请输入项目详细！", 'top-right', '4000', 'danger', 'fa-bolt', true);
            return;
        }
        if($scope.crowdFundingAdd.provideTime === ''){
            Notify("请输入收益时间！", 'top-right', '4000', 'danger', 'fa-bolt', true);
            return;
        }
        if($scope.crowdFundingAdd.startTime == ''){
            Notify("请输入开始时间！", 'top-right', '4000', 'danger', 'fa-bolt', true);
            return;
        }
        if($scope.crowdFundingAdd.endTime == ''){
            Notify("请输入截止时间！", 'top-right', '4000', 'danger', 'fa-bolt', true);
            return;
        }
        if($scope.crowdFundingAdd.brief == ''){
            Notify("请输入项目简介！", 'top-right', '4000', 'danger', 'fa-bolt', true);
            return;
        }
        if($scope.crowdFundingAdd.period == ''){
            Notify("请输入项目周期！", 'top-right', '4000', 'danger', 'fa-bolt', true);
            return;
        }
        if($scope.crowdFundingAdd.miniQuantity == ''){
            Notify("请输入众筹最少量！", 'top-right', '4000', 'danger', 'fa-bolt', true);
            return;
        }
        if($scope.crowdFundingAdd.targerAmount == ''){
            Notify("请输入目标金额！", 'top-right', '4000', 'danger', 'fa-bolt', true);
            return;
        }
        if($scope.crowdFundingAdd.miniQuantity > $scope.crowdFundingAdd.targerAmount){
            Notify("众筹最少量不能大于目标金额！", 'top-right', '4000', 'danger', 'fa-bolt', true);
            return;
        }
        if($scope.crowdFundingAdd.bonusRate == ''){
            Notify("请输入预计分红率！", 'top-right', '4000', 'danger', 'fa-bolt', true);
            return;
        }
        if($scope.crowdFundingAdd.profitType == ''){
            Notify("请选择收益类型！", 'top-right', '4000', 'danger', 'fa-bolt', true);
            return;
        }
        if($scope.crowdFundingAdd.startTime >= $scope.crowdFundingAdd.endTime){
            Notify("开始时间不能大于截止时间！", 'top-right', '4000', 'danger', 'fa-bolt', true);
            return;
        }
        if($scope.crowdFundingAdd.provideTime < $scope.crowdFundingAdd.endTime){
            Notify("截止时间不能大于收益时间！", 'top-right', '4000', 'danger', 'fa-bolt', true);
            return;
        }
        if($('#moneySet').val() == ''){
            Notify("请输入档位金额！", 'top-right', '4000', 'danger', 'fa-bolt', true);
            return;
        }
        if($('#easyDesc').val() == ''){
            Notify("请输入档位简单描述！", 'top-right', '4000', 'danger', 'fa-bolt', true);
            return;
        }
        if($scope.crowdFundingAdd.targerAmount == '') {
            Notify("请输入目标金额！", 'top-right', '4000', 'danger', 'fa-bolt', true);
            return;
        }
        if($scope.crowdFundingAdd.orderNum == '') {
            Notify("请输入项目排序！", 'top-right', '4000', 'danger', 'fa-bolt', true);
            return;
        }
        var fd = new FormData();
        fd.append("name", $scope.crowdFundingAdd.name);
        fd.append("sponsor", $scope.crowdFundingAdd.sponsor);
        fd.append("targerAmount", $scope.crowdFundingAdd.targerAmount);
        fd.append("orderNum", $scope.crowdFundingAdd.orderNum);
        fd.append("brief", $scope.crowdFundingAdd.brief);
        fd.append("miniQuantity", $scope.crowdFundingAdd.miniQuantity);
        fd.append("bonusRate", $scope.crowdFundingAdd.bonusRate);
        fd.append("profitInfo", $scope.crowdFundingAdd.profitInfo);
        fd.append("isShow", $scope.crowdFundingAdd.isShow);
        fd.append("profitType", $scope.crowdFundingAdd.profitType);
        fd.append("period", $scope.crowdFundingAdd.period);
        fd.append("startTime", $scope.formatDateTime2($scope.crowdFundingAdd.startTime));
        fd.append("endTime", $scope.formatDateTime2($scope.crowdFundingAdd.endTime));
        fd.append("provideTime", $scope.formatDateTime2($scope.crowdFundingAdd.provideTime));

        fd.append("covers",  document.querySelector('input[name="addCover"]').files[0]);

        for(var i=0; i < document.querySelector('input[name="addDesc"]').files.length; i++) {
            fd.append("files", document.querySelector('input[name="addDesc"]').files[i]);
        }
        /* console.log(document.querySelector('input[name="addDesc"]').files.length)  */
        fd.append("gearListJson", JSON.stringify($scope.gearList));
        /* console.log(JSON.stringify($scope.gearList)) */

        submitNow = true;
        $http({
            method  : 'POST',
            url     : '/crowdFunding/addCrowdFunding.htm',
            data : fd,
            transformRequest: angular.identity,
            headers : {'Content-Type' : undefined}
        }).then(function(response){
            if(!response.data.success) {
                Notify("操作失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
                submitNow = false;
                return;
            }

            Notify('添加成功', 'top-right', '5000', 'success', 'fa-check', true);
            submitNow = false;
            $scope.common.showAdd = false;
            $scope.pageChanged();

            $scope.gearList = [];
            $scope.gearList.push({price:'',description:''});
            $scope.crowdFundingAdd = {
                name:'',
                sponsor:'',
                targerAmount:'',
                orderNum:'',
                startTime:'',
                endTime:'',
                provideTime:''
            };
            $(".fileinput-remove-button").click();
        });
    }
    //提交修改
    $scope.submitEdit = function(crowdFunding){
        if($scope.crowdFundingInfo.name == '') {
            Notify("请输入项目名称！", 'top-right', '4000', 'danger', 'fa-bolt', true);
            return;
        }
        if($scope.crowdFundingInfo.sponsor == '') {
            Notify("请输入项目负责人！", 'top-right', '4000', 'danger', 'fa-bolt', true);
            return;
        }
        if($scope.crowdFundingInfo.startTime >= $scope.crowdFundingInfo.endTime){
            Notify("开始时间不能大于截止时间！", 'top-right', '4000', 'danger', 'fa-bolt', true);
            return;
        }
        if($scope.crowdFundingInfo.provideTime < $scope.crowdFundingInfo.endTime){
            Notify("截止时间不能大于收益时间！", 'top-right', '4000', 'danger', 'fa-bolt', true);
            return;
        }
        if($scope.crowdFundingInfo.brief == ''){
            Notify("请输入项目简介！", 'top-right', '4000', 'danger', 'fa-bolt', true);
            return;
        }
        if($scope.crowdFundingInfo.period == ''){
            Notify("请输入项目周期！", 'top-right', '4000', 'danger', 'fa-bolt', true);
            return;
        }
        if($scope.crowdFundingInfo.miniQuantity == ''){
            Notify("请输入众筹最少量！", 'top-right', '4000', 'danger', 'fa-bolt', true);
            return;
        }
        if($scope.crowdFundingInfo.targerAmount == ''){
            Notify("请输入目标金额！", 'top-right', '4000', 'danger', 'fa-bolt', true);
            return;
        }
        if($scope.crowdFundingInfo.miniQuantity > $scope.crowdFundingInfo.targerAmount){
            Notify("众筹最少量不能大于目标金额！", 'top-right', '4000', 'danger', 'fa-bolt', true);
            return;
        }
        if($scope.crowdFundingInfo.bonusRate == ''){
            Notify("请输入预计分红率！", 'top-right', '4000', 'danger', 'fa-bolt', true);
            return;
        }
        if($scope.crowdFundingInfo.profitType == ''){
            Notify("请选择收益类型！", 'top-right', '4000', 'danger', 'fa-bolt', true);
            return;
        }
        if($('#moneyEditSet').val() == ''){
            Notify("请输入档位金额！", 'top-right', '4000', 'danger', 'fa-bolt', true);
            return;
        }
        if($('#easyEditDesc').val() == ''){
            Notify("请输入档位简单描述！", 'top-right', '4000', 'danger', 'fa-bolt', true);
            return;
        }
        if($scope.crowdFundingInfo.targerAmount == '') {
            Notify("请输入目标金额！", 'top-right', '4000', 'danger', 'fa-bolt', true);
            return;
        }
        if($scope.crowdFundingInfo.orderNum == '') {
            Notify("请输入项目排序！", 'top-right', '4000', 'danger', 'fa-bolt', true);
            return;
        }
        var fd = new FormData();
        fd.append("id",$scope.crowdFundingInfo.id);
        fd.append("name", $scope.crowdFundingInfo.name);
        fd.append("sponsor", $scope.crowdFundingInfo.sponsor);
        fd.append("targerAmount", $scope.crowdFundingInfo.targerAmount);
        fd.append("orderNum", $scope.crowdFundingInfo.orderNum);
        fd.append("brief", $scope.crowdFundingInfo.brief);
        fd.append("miniQuantity", $scope.crowdFundingInfo.miniQuantity);
        fd.append("bonusRate", $scope.crowdFundingInfo.bonusRate);
        fd.append("profitInfo", $scope.crowdFundingInfo.profitInfo);
        fd.append("isShow", $scope.crowdFundingInfo.isShow);
        fd.append("profitType", $scope.crowdFundingInfo.profitType);
        fd.append("period", $scope.crowdFundingInfo.period);
        fd.append("startTime",$scope.formatDateTime2($scope.crowdFundingInfo.startTime));
        fd.append("endTime",$scope.formatDateTime2($scope.crowdFundingInfo.endTime));
        fd.append("provideTime",$scope.formatDateTime2($scope.crowdFundingInfo.provideTime));

        fd.append("editCovers",  document.querySelector('input[name="editCover"]').files[0]);

        for(var i=0; i < document.querySelector('input[name="editDesc"]').files.length; i++) {
            fd.append("editFiles", document.querySelector('input[name="editDesc"]').files[i]);
        }
        /* console.log(document.querySelector('input[name="addDesc"]').files.length)  */
        fd.append("editGearListJson", JSON.stringify($scope.crowdFundingInfo.gradeList));
        /* console.log(JSON.stringify($scope.gearList)) */
        $http({
            method  : 'POST',
            url     : '/crowdFunding/editCrowdFundingById.htm',
            data : fd,
            transformRequest: angular.identity,
            headers : {'Content-Type' : undefined}
        }).then(function(response){
            if(!response.data.success) {
                Notify("操作失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
                submitNow = false;
                return;
            }

            Notify('操作成功', 'top-right', '5000', 'success', 'fa-check', true);
            $scope.common.showEdit = false;
            $scope.pageChanged();
            $(".fileinput-remove-button").click();
        });
    }
    //添加进度
    $scope.addProgress = function(id){
        console.log($scope.orderProInfo.id)
        if($scope.orderProInfo.content == '') {
            Notify("请输入进度内容！", 'top-right', '4000', 'danger', 'fa-bolt', true);
            return;
        }
        if(document.querySelector('input[name="addPro"]').files[0] == null){
            Notify("请输入进度说明！", 'top-right', '4000', 'danger', 'fa-bolt', true);
            return;
        }
        var fd = new FormData();
        for(var i=0; i < document.querySelector('input[name="addPro"]').files.length; i++) {
            fd.append("files", document.querySelector('input[name="addPro"]').files[i]);
        }
        fd.append("projectId",id);
        fd.append("content", $scope.orderProInfo.content);
        $http({
            method  : 'POST',
            url     : '/crowdFunding/addProgressInfo.htm',
            data : fd,
            transformRequest: angular.identity,
            headers : {'Content-Type' : undefined}
        }).then(function(response){
            if(!response.data.success) {
                Notify("操作失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
                submitNow = false;
                return;
            }

            Notify('添加成功', 'top-right', '5000', 'success', 'fa-check', true);
            $('#myAddModel').modal('hide');
            $(".fileinput-remove-button").click();
            $scope.queryAllOrderProgressById(id);
            $scope.orderProInfo = {
                content:''
            }
            $scope.orderProInfo.id = id;
        });
    }
    //删除项目进展
    $scope.deleteProgressInfoById = function(id,pid){
        /* console.log(pid) */
        if(window.confirm('你确定删除这个项目进展吗？')){

        }else{
            return false;
        }
        $http({
            method  : 'POST',
            url     : '/crowdFunding/deleteProgressInfoById.htm',
            params:{
                'id':id
            },
            headers : {'Content-Type': 'application/x-www-form-urlencoded'},
        }).then(function(response) {
            if(!response.data.success) {
                Notify("删除失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
                return;
            }
            Notify('删除成功', 'top-right', '5000', 'success', 'fa-check', true);
            $scope.showOrderProgress(pid);
        });
    }
});
