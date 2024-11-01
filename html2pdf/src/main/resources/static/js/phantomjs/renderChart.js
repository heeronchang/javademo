// renderChart.js
var page = require('webpage').create();
var system = require('system');
var fs = require('fs');

if (system.args.length !== 3) {
    console.log('Usage: phantomjs renderChart.js <echartsOptionJSON> <outputPath>');
    phantom.exit(1);
}

var optionsJSON = system.args[1];
var outputPath = system.args[2];

page.viewportSize = { width: 600, height: 400 };

page.onConsoleMessage = function(msg) {
    console.log(msg);
};

page.open("about:blank", function(status) {
    if (status !== "success") {
        console.log("Unable to load page");
        phantom.exit();
    }

    page.evaluate(function(optionsJSON) {
        var div = document.createElement("div");
        div.id = "chart";
        div.style.width = "600px";
        div.style.height = "400px";
        document.body.appendChild(div);

        var script = document.createElement("script");
        script.src = "https://cdn.jsdelivr.net/npm/echarts/dist/echarts.min.js";
        script.onload = function() {
            var chart = echarts.init(document.getElementById('chart'));
            var options = JSON.parse(optionsJSON);
            chart.setOption(options);
        };
        document.body.appendChild(script);
    }, optionsJSON);

    window.setTimeout(function() {
        page.render(outputPath);
        phantom.exit();
    }, 8000);  // 等待8秒，确保图表渲染完成
});
