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
        "total": "1",
        "ok": "-",
        "ko": "1"
    },
    "maxResponseTime": {
        "total": "60102",
        "ok": "-",
        "ko": "60102"
    },
    "meanResponseTime": {
        "total": "16881",
        "ok": "-",
        "ko": "16881"
    },
    "standardDeviation": {
        "total": "26866",
        "ok": "-",
        "ko": "26866"
    },
    "percentiles1": {
        "total": "12",
        "ok": "-",
        "ko": "12"
    },
    "percentiles2": {
        "total": "60002",
        "ok": "-",
        "ko": "60002"
    },
    "percentiles3": {
        "total": "60014",
        "ok": "-",
        "ko": "60014"
    },
    "percentiles4": {
        "total": "60025",
        "ok": "-",
        "ko": "60025"
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
        "total": "148.84",
        "ok": "-",
        "ko": "148.84"
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
        "total": "1",
        "ok": "-",
        "ko": "1"
    },
    "maxResponseTime": {
        "total": "60102",
        "ok": "-",
        "ko": "60102"
    },
    "meanResponseTime": {
        "total": "31174",
        "ok": "-",
        "ko": "31174"
    },
    "standardDeviation": {
        "total": "29788",
        "ok": "-",
        "ko": "29788"
    },
    "percentiles1": {
        "total": "59969",
        "ok": "-",
        "ko": "59966"
    },
    "percentiles2": {
        "total": "60009",
        "ok": "-",
        "ko": "60009"
    },
    "percentiles3": {
        "total": "60016",
        "ok": "-",
        "ko": "60016"
    },
    "percentiles4": {
        "total": "60033",
        "ok": "-",
        "ko": "60033"
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
        "total": "74.42",
        "ok": "-",
        "ko": "74.42"
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
        "total": "1",
        "ok": "-",
        "ko": "1"
    },
    "maxResponseTime": {
        "total": "60017",
        "ok": "-",
        "ko": "60017"
    },
    "meanResponseTime": {
        "total": "2589",
        "ok": "-",
        "ko": "2589"
    },
    "standardDeviation": {
        "total": "12153",
        "ok": "-",
        "ko": "12153"
    },
    "percentiles1": {
        "total": "8",
        "ok": "-",
        "ko": "8"
    },
    "percentiles2": {
        "total": "16",
        "ok": "-",
        "ko": "16"
    },
    "percentiles3": {
        "total": "138",
        "ok": "-",
        "ko": "138"
    },
    "percentiles4": {
        "total": "60012",
        "ok": "-",
        "ko": "60012"
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
        "total": "74.42",
        "ok": "-",
        "ko": "74.42"
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
