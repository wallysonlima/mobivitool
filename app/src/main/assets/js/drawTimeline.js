function drawTimelineChart(dataArray, width, height)
{
    /*var dataArray[] = Android.getDataSet();
    int width = Android.getWidth();
    int height = Android.getHeight();
    */

    var widthScale = d3.scale.linear()
                  .domain([0, 90])
                  .range([0, width]);

    var color = d3.scale.linear()
                  .domain([0, 90])
                  .range(["red", "blue"]);


    var axis = d3.svg.axis()
        .ticks(5)
        .scale(widthScale);

    var canvas = d3.select("p").append("svg")
                   .attr("width", 500)
                   .attr("height", 500)
                   .append("g")
                   .attr("transform", "translate(20, 20)");


    var bars = canvas.selectAll("rect")
                  .data(dataArray)
                  .enter()
                    .append("rect")
                    .attr("width", function(d) { return widthScale(d); })
                    .attr("height", 50)
                    .attr("fill", function(d) { return color(d); })
                    .attr("y", function(d, i) { return i * 60 } );

    canvas.append("g")
      .attr("transform", "translate(0, 400)")
      .call(axis);
}