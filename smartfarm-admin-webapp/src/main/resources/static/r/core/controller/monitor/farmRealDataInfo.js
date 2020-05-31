var app = angular.module("myApp", ["ui.bootstrap"]);
app.controller("myCtrl", function($scope,$http,$timeout) {
	var submitNow = false;
	//图片路径前缀
	$scope.picUrl=picurl;
	
	$scope.formData = {
		range : '1'
	}
	
	//初始化列表
	$scope.pageChanged = function() {
		$http({
	        method  : 'Post',
	        url     : '/farm/queryFarmerRealDataList.htm',
	        params : {
				'range' : $scope.formData.range
			},
	        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
	    }).then(function(response) {
	    	$scope.realDataList = response.data.realDataList;
	    	console.log($scope.realDataList);
	    	$timeout($scope.loadData(),500);
	    });
	}


	$scope.pageChanged();

	$scope.realDataChanged = function(){
		$scope.pageChanged();
	}

//	$(window).bind("load", function () {
//    $timeout(function(){
//
//		//console.log($scope.realDataList)
//    	jQuery(document).ready(function() {
//    		$scope.loadData();
//            });
//
//    },1000)
//    // Set up the control widget
//   
//
//    //-------------------------Initiates Easy Pie Chart instances in page--------------------//
//   /*  InitiateEasyPieChart.init(); */
//
//    //-------------------------Initiates Sparkline Chart instances in page------------------//
//    /* InitiateSparklineCharts.init(); */
//});
	
	$scope.loadData = function(){
		var myChart = echarts.init(document.getElementById('myChart'));
        var series=[];
        var dates = [];
        var realValues=[];
        //得到传感值
        $scope.getValues = function(index){
            for(var j = 0;j<$scope.realDataList[index].realDataList.length && j < $scope.realDataList[0].realDataList.length;j++){
            	if($scope.realDataList[0].realDataList[j].transferDate === $scope.realDataList[index].realDataList[j].transferDate){
                	realValues.push({
                		value:$scope.realDataList[index].realDataList[j].realValue
                	})
            	}
            	/* console.log($scope.realDataList[index].realDataList[j].realValue) */
            }
           /* console.log(realValues) */
            return realValues;
        }
        //得到日期并切割
        if($scope.formData.range === '1'){
            for(var j = 0;j<$scope.realDataList[0].realDataList.length;j++){
            	dates.push({
        			value:$scope.realDataList[0].realDataList[j].transferDate + ":00"
            	})
            }
        }else{
        	for(var j = 0;j<$scope.realDataList[0].realDataList.length;j++){
            	dates.push({
        			value:$scope.realDataList[0].realDataList[j].transferDate
            	})
            }
        }
        
        //遍历封装series值
        for(var i = 0;i<$scope.realDataList.length;i++){
            series.push({
                name: $scope.realDataList[i].name,
                type: 'line',
                stack: '总量'+i,
                // data:[120, 132, 101, 134, 90, 230, 210]
                data: $scope.getValues(i),
                names:$scope.realDataList[i].name
            });
            /* console.log($scope.realDataList[i].name) */
            var realValues = []
        }
        	option = {
        		    title: {
        		        text: '折线图堆叠'
        		    },
        		    tooltip: {
        		        trigger: 'axis'
        		    },
        		    legend: {
        		    	show:true,
        		        data:series.names
        		    },
        		    grid: {
        		        left: '3%',
        		        right: '4%',
        		        bottom: '3%',
        		        containLabel: true
        		    },
        		    toolbox: {
        		        feature: {
        		            saveAsImage: {}
        		        }
        		    },
        		    xAxis: {
        		        type: 'category',
        		        boundaryGap: false,
        		        data: dates
        		    },
        		    yAxis: {
        		        type: 'value'
        		    },
       		    	series:series
        		};
		    myChart.setOption(option)
	}
});
