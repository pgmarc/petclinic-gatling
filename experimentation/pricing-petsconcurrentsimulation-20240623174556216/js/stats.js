var stats = {
    type: "GROUP",
name: "All Requests",
path: "",
pathFormatted: "group_missing-name--1146707516",
stats: {
    "name": "All Requests",
    "numberOfRequests": {
        "total": "32000",
        "ok": "0",
        "ko": "32000"
    },
    "minResponseTime": {
        "total": "5",
        "ok": "-",
        "ko": "5"
    },
    "maxResponseTime": {
        "total": "68115",
        "ok": "-",
        "ko": "68115"
    },
    "meanResponseTime": {
        "total": "26031",
        "ok": "-",
        "ko": "26031"
    },
    "standardDeviation": {
        "total": "21709",
        "ok": "-",
        "ko": "21709"
    },
    "percentiles1": {
        "total": "15457",
        "ok": "-",
        "ko": "15469"
    },
    "percentiles2": {
        "total": "60008",
        "ok": "-",
        "ko": "60008"
    },
    "percentiles3": {
        "total": "62327",
        "ok": "-",
        "ko": "62327"
    },
    "percentiles4": {
        "total": "64640",
        "ok": "-",
        "ko": "64640"
    },
    "group1": {
    "name": "t < 800 ms",
    "htmlName": "t < 800 ms",
    "count": 0,
    "percentage": 0.0
},
    "group2": {
    "name": "800 ms <= t < 1200 ms",
    "htmlName": "t >= 800 ms <br> t < 1200 ms",
    "count": 0,
    "percentage": 0.0
},
    "group3": {
    "name": "t >= 1200 ms",
    "htmlName": "t >= 1200 ms",
    "count": 0,
    "percentage": 0.0
},
    "group4": {
    "name": "failed",
    "htmlName": "failed",
    "count": 32000,
    "percentage": 100.0
},
    "meanNumberOfRequestsPerSecond": {
        "total": "258.06",
        "ok": "-",
        "ko": "258.06"
    }
},
contents: {
"req_registration--1185989415": {
        type: "REQUEST",
        name: "Registration",
path: "Registration",
pathFormatted: "req_registration--1185989415",
stats: {
    "name": "Registration",
    "numberOfRequests": {
        "total": "16000",
        "ok": "0",
        "ko": "16000"
    },
    "minResponseTime": {
        "total": "953",
        "ok": "-",
        "ko": "953"
    },
    "maxResponseTime": {
        "total": "64479",
        "ok": "-",
        "ko": "64479"
    },
    "meanResponseTime": {
        "total": "33132",
        "ok": "-",
        "ko": "33132"
    },
    "standardDeviation": {
        "total": "23331",
        "ok": "-",
        "ko": "23331"
    },
    "percentiles1": {
        "total": "18455",
        "ok": "-",
        "ko": "18387"
    },
    "percentiles2": {
        "total": "61075",
        "ok": "-",
        "ko": "61075"
    },
    "percentiles3": {
        "total": "62321",
        "ok": "-",
        "ko": "62321"
    },
    "percentiles4": {
        "total": "63365",
        "ok": "-",
        "ko": "63365"
    },
    "group1": {
    "name": "t < 800 ms",
    "htmlName": "t < 800 ms",
    "count": 0,
    "percentage": 0.0
},
    "group2": {
    "name": "800 ms <= t < 1200 ms",
    "htmlName": "t >= 800 ms <br> t < 1200 ms",
    "count": 0,
    "percentage": 0.0
},
    "group3": {
    "name": "t >= 1200 ms",
    "htmlName": "t >= 1200 ms",
    "count": 0,
    "percentage": 0.0
},
    "group4": {
    "name": "failed",
    "htmlName": "failed",
    "count": 16000,
    "percentage": 100.0
},
    "meanNumberOfRequestsPerSecond": {
        "total": "129.03",
        "ok": "-",
        "ko": "129.03"
    }
}
    },"req_login-73596745": {
        type: "REQUEST",
        name: "Login",
path: "Login",
pathFormatted: "req_login-73596745",
stats: {
    "name": "Login",
    "numberOfRequests": {
        "total": "16000",
        "ok": "0",
        "ko": "16000"
    },
    "minResponseTime": {
        "total": "5",
        "ok": "-",
        "ko": "5"
    },
    "maxResponseTime": {
        "total": "68115",
        "ok": "-",
        "ko": "68115"
    },
    "meanResponseTime": {
        "total": "18930",
        "ok": "-",
        "ko": "18930"
    },
    "standardDeviation": {
        "total": "17246",
        "ok": "-",
        "ko": "17246"
    },
    "percentiles1": {
        "total": "13885",
        "ok": "-",
        "ko": "13885"
    },
    "percentiles2": {
        "total": "18551",
        "ok": "-",
        "ko": "18551"
    },
    "percentiles3": {
        "total": "62439",
        "ok": "-",
        "ko": "62439"
    },
    "percentiles4": {
        "total": "66090",
        "ok": "-",
        "ko": "66090"
    },
    "group1": {
    "name": "t < 800 ms",
    "htmlName": "t < 800 ms",
    "count": 0,
    "percentage": 0.0
},
    "group2": {
    "name": "800 ms <= t < 1200 ms",
    "htmlName": "t >= 800 ms <br> t < 1200 ms",
    "count": 0,
    "percentage": 0.0
},
    "group3": {
    "name": "t >= 1200 ms",
    "htmlName": "t >= 1200 ms",
    "count": 0,
    "percentage": 0.0
},
    "group4": {
    "name": "failed",
    "htmlName": "failed",
    "count": 16000,
    "percentage": 100.0
},
    "meanNumberOfRequestsPerSecond": {
        "total": "129.03",
        "ok": "-",
        "ko": "129.03"
    }
}
    }
}

}

function fillStats(stat){
    $("#numberOfRequests").append(stat.numberOfRequests.total);
    $("#numberOfRequestsOK").append(stat.numberOfRequests.ok);
    $("#numberOfRequestsKO").append(stat.numberOfRequests.ko);

    $("#minResponseTime").append(stat.minResponseTime.total);
    $("#minResponseTimeOK").append(stat.minResponseTime.ok);
    $("#minResponseTimeKO").append(stat.minResponseTime.ko);

    $("#maxResponseTime").append(stat.maxResponseTime.total);
    $("#maxResponseTimeOK").append(stat.maxResponseTime.ok);
    $("#maxResponseTimeKO").append(stat.maxResponseTime.ko);

    $("#meanResponseTime").append(stat.meanResponseTime.total);
    $("#meanResponseTimeOK").append(stat.meanResponseTime.ok);
    $("#meanResponseTimeKO").append(stat.meanResponseTime.ko);

    $("#standardDeviation").append(stat.standardDeviation.total);
    $("#standardDeviationOK").append(stat.standardDeviation.ok);
    $("#standardDeviationKO").append(stat.standardDeviation.ko);

    $("#percentiles1").append(stat.percentiles1.total);
    $("#percentiles1OK").append(stat.percentiles1.ok);
    $("#percentiles1KO").append(stat.percentiles1.ko);

    $("#percentiles2").append(stat.percentiles2.total);
    $("#percentiles2OK").append(stat.percentiles2.ok);
    $("#percentiles2KO").append(stat.percentiles2.ko);

    $("#percentiles3").append(stat.percentiles3.total);
    $("#percentiles3OK").append(stat.percentiles3.ok);
    $("#percentiles3KO").append(stat.percentiles3.ko);

    $("#percentiles4").append(stat.percentiles4.total);
    $("#percentiles4OK").append(stat.percentiles4.ok);
    $("#percentiles4KO").append(stat.percentiles4.ko);

    $("#meanNumberOfRequestsPerSecond").append(stat.meanNumberOfRequestsPerSecond.total);
    $("#meanNumberOfRequestsPerSecondOK").append(stat.meanNumberOfRequestsPerSecond.ok);
    $("#meanNumberOfRequestsPerSecondKO").append(stat.meanNumberOfRequestsPerSecond.ko);
}
