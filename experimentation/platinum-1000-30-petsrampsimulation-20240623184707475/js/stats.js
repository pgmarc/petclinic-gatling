var stats = {
    type: "GROUP",
name: "All Requests",
path: "",
pathFormatted: "group_missing-name--1146707516",
stats: {
    "name": "All Requests",
    "numberOfRequests": {
        "total": "2000",
        "ok": "0",
        "ko": "2000"
    },
    "minResponseTime": {
        "total": "30004",
        "ok": "-",
        "ko": "30004"
    },
    "maxResponseTime": {
        "total": "60016",
        "ok": "-",
        "ko": "60016"
    },
    "meanResponseTime": {
        "total": "56622",
        "ok": "-",
        "ko": "56622"
    },
    "standardDeviation": {
        "total": "8778",
        "ok": "-",
        "ko": "8778"
    },
    "percentiles1": {
        "total": "60007",
        "ok": "-",
        "ko": "60007"
    },
    "percentiles2": {
        "total": "60011",
        "ok": "-",
        "ko": "60011"
    },
    "percentiles3": {
        "total": "60015",
        "ok": "-",
        "ko": "60015"
    },
    "percentiles4": {
        "total": "60016",
        "ok": "-",
        "ko": "60016"
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
    "count": 2000,
    "percentage": 100.0
},
    "meanNumberOfRequestsPerSecond": {
        "total": "13.16",
        "ok": "-",
        "ko": "13.16"
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
        "total": "1000",
        "ok": "0",
        "ko": "1000"
    },
    "minResponseTime": {
        "total": "30004",
        "ok": "-",
        "ko": "30004"
    },
    "maxResponseTime": {
        "total": "60016",
        "ok": "-",
        "ko": "60016"
    },
    "meanResponseTime": {
        "total": "53235",
        "ok": "-",
        "ko": "53235"
    },
    "standardDeviation": {
        "total": "11453",
        "ok": "-",
        "ko": "11453"
    },
    "percentiles1": {
        "total": "60004",
        "ok": "-",
        "ko": "60004"
    },
    "percentiles2": {
        "total": "60009",
        "ok": "-",
        "ko": "60009"
    },
    "percentiles3": {
        "total": "60015",
        "ok": "-",
        "ko": "60015"
    },
    "percentiles4": {
        "total": "60016",
        "ok": "-",
        "ko": "60016"
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
    "count": 1000,
    "percentage": 100.0
},
    "meanNumberOfRequestsPerSecond": {
        "total": "6.58",
        "ok": "-",
        "ko": "6.58"
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
        "total": "1000",
        "ok": "0",
        "ko": "1000"
    },
    "minResponseTime": {
        "total": "60000",
        "ok": "-",
        "ko": "60000"
    },
    "maxResponseTime": {
        "total": "60016",
        "ok": "-",
        "ko": "60016"
    },
    "meanResponseTime": {
        "total": "60009",
        "ok": "-",
        "ko": "60009"
    },
    "standardDeviation": {
        "total": "5",
        "ok": "-",
        "ko": "5"
    },
    "percentiles1": {
        "total": "60009",
        "ok": "-",
        "ko": "60009"
    },
    "percentiles2": {
        "total": "60013",
        "ok": "-",
        "ko": "60013"
    },
    "percentiles3": {
        "total": "60015",
        "ok": "-",
        "ko": "60015"
    },
    "percentiles4": {
        "total": "60016",
        "ok": "-",
        "ko": "60016"
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
    "count": 1000,
    "percentage": 100.0
},
    "meanNumberOfRequestsPerSecond": {
        "total": "6.58",
        "ok": "-",
        "ko": "6.58"
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
