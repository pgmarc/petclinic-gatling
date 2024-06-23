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
        "total": "8",
        "ok": "-",
        "ko": "8"
    },
    "maxResponseTime": {
        "total": "60120",
        "ok": "-",
        "ko": "60120"
    },
    "meanResponseTime": {
        "total": "46882",
        "ok": "-",
        "ko": "46882"
    },
    "standardDeviation": {
        "total": "22974",
        "ok": "-",
        "ko": "22974"
    },
    "percentiles1": {
        "total": "60007",
        "ok": "-",
        "ko": "60007"
    },
    "percentiles2": {
        "total": "60012",
        "ok": "-",
        "ko": "60012"
    },
    "percentiles3": {
        "total": "60040",
        "ok": "-",
        "ko": "60040"
    },
    "percentiles4": {
        "total": "60100",
        "ok": "-",
        "ko": "60100"
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
        "total": "16.26",
        "ok": "-",
        "ko": "16.26"
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
        "total": "133",
        "ok": "-",
        "ko": "133"
    },
    "maxResponseTime": {
        "total": "60120",
        "ok": "-",
        "ko": "60120"
    },
    "meanResponseTime": {
        "total": "41544",
        "ok": "-",
        "ko": "41544"
    },
    "standardDeviation": {
        "total": "24347",
        "ok": "-",
        "ko": "24347"
    },
    "percentiles1": {
        "total": "60005",
        "ok": "-",
        "ko": "60005"
    },
    "percentiles2": {
        "total": "60015",
        "ok": "-",
        "ko": "60015"
    },
    "percentiles3": {
        "total": "60060",
        "ok": "-",
        "ko": "60060"
    },
    "percentiles4": {
        "total": "60114",
        "ok": "-",
        "ko": "60114"
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
        "total": "8.13",
        "ok": "-",
        "ko": "8.13"
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
        "total": "8",
        "ok": "-",
        "ko": "8"
    },
    "maxResponseTime": {
        "total": "60018",
        "ok": "-",
        "ko": "60018"
    },
    "meanResponseTime": {
        "total": "52221",
        "ok": "-",
        "ko": "52221"
    },
    "standardDeviation": {
        "total": "20145",
        "ok": "-",
        "ko": "20145"
    },
    "percentiles1": {
        "total": "60008",
        "ok": "-",
        "ko": "60008"
    },
    "percentiles2": {
        "total": "60011",
        "ok": "-",
        "ko": "60011"
    },
    "percentiles3": {
        "total": "60014",
        "ok": "-",
        "ko": "60014"
    },
    "percentiles4": {
        "total": "60015",
        "ok": "-",
        "ko": "60015"
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
        "total": "8.13",
        "ok": "-",
        "ko": "8.13"
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
