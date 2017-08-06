<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

</body>
</html>
<script>
option = {
	    backgroundColor: "#404A59",
	    color: ['#ffd285', '#ff733f', '#ec4863'],

	    title: {
	        text: ' 项目利润总额',
	        left: '1%',
	        top: '6%',
	        textStyle: {
	            color: '#fff'
	        }
	    },
	    tooltip: {
	        trigger: 'axis'
	    },
	    legend: {
	        x: 300,
	        top: '7%',
	        textStyle: {
	            color: '#ffd285',
	        },
	    },

	    toolbox: {
	        "show": false,
	        feature: {
	            saveAsImage: {}
	        }
	    },
	    xAxis: {
	        type: 'category',
	        "axisLine": {
	            lineStyle: {
	                color: '#FF4500'
	            }
	        },
	        "axisTick": {
	            "show": false
	        },
	        axisLabel: {
	            textStyle: {
	                color: '#fff'
	            }
	        },
	        boundaryGap: false,
	        data: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12']
	    },
	    yAxis: {
	        "axisLine": {
	            lineStyle: {
	                color: '#fff'
	            }
	        },
	        splitLine: {
	            show: true,
	            lineStyle: {
	                color: '#fff'
	            }
	        },
	        "axisTick": {
	            "show": false
	        },
	        axisLabel: {
	            textStyle: {
	                color: '#fff'
	            }
	        },
	        type: 'value'
	    },
	    series: [{
	        name: '本年实际数',
	        smooth: true,
	        type: 'line',
	        symbolSize: 8,
	        symbol: 'circle',
	        data: [230, 256, 351, 322, 240, 0, 0, 0, 0, 0, 0, 0]
	    }, {
	        name: '上年同期数',
	        smooth: true,
	        type: 'line',
	        symbolSize: 8,
	        symbol: 'circle',
	        data: [214, 236, 333, 304, 222, 0, 0, 0, 0, 0, 0, 0]
	    }, {
	        name: '本年预算',
	        smooth: true,
	        type: 'line',
	        symbolSize: 8,
	        symbol: 'circle',
	        data: [93, 99, 94, 122, 92, 82, 113, 122, 85, 107, 151, 96]
	    }]

	}
</script>