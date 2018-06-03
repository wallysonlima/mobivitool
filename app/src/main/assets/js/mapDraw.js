// Define global variables
var width_4 = 400,
height_4 = 260,
centered;

function createNYCMap(){

            // Define the projection boundaries
            var projection = d3.geoMercator()
            .center([-48.63413553850004,-22.572100678728635])
            .scale(2434.989128794103)
            .translate([width_4/2,height_4/2]);

            // Define the path
            var path = d3.geoPath()
            .projection(projection);

            // Define the div for the tooltip
            var div = d3.select("#map_4")
            .append("div")
            .attr("class", "tooltip")
            .style("opacity", 0);

            // Define the svg for the map
            var svg = d3.select("#map_4")
            .append("svg")
            .attr("width", width_4)
            .attr("height", height_4)
            .style("margin-left", 100);

            // Define the g for each neighborhood
            var g = svg.append("g");

            var tooltip = d3.select("#map_4").append("div").attr("class","tooltip");

            d3.json("data/sp.topojson", function(error, geodata) {
                if (error) return console.log(error);

                //Create a path for each map feature in the data
                g.append("g")
                .attr("id", "neighborhood")
                .selectAll("path")
                .data(topojson.feature(geodata, geodata.objects.VWM_AGENCIA_CETESB_50_CETESB_20160201_POLPolygon).features)
                .enter()
                .append("path")
                .attr("d", path)
                .on("mouseover",showTooltip)
                .on("mousemove",moveTooltip)
                .on("mouseout",hideTooltip)
                .on("click", clicked);
            });

            function clicked(d){
                var x, y, k;
            // IF zoomed-in state is on
            if (d && centered !== d) {
                // Change the center of the projection
                var centroid = path.centroid(d);
                x = centroid[0];
                y = centroid[1];
                k = 4;
                centered = d;
                // Show tooltip
                div.transition()
                .duration(200)
                .style("opacity", .9);

                hideTooltip();

                // Write staff on tooltip
                div.html("Cidade:" + "<strong>" + d.properties.Agencia_CE.toLowerCase().toUpperCase().substring(21) + "</strong>" + "<br/>" +
                   "Prefixo:" + '<strong id="prefixo"></strong>' + "<br/>" +
                   "Bacia:" + '<strong id="bacia"></strong>' + "<br/>" +
                   "Latitude:" + '<strong id="latitude"></strong>' + "<br/>" +
                   "Longitude:" + '<strong id="longitude"></strong>' + "<br/>" +
                   "Media:" + '<strong id="media"></strong>'
                   )
                // Place the tooltip
                div.style("left", (d3.mouse(this)[0]) + "px")
                .style("top", (d3.mouse(this)[1]) + "px");

                // Load CSV for filling the missing info on tooltip
                d3.csv("file:///data/data/wallyson.lima.mobivitool/files/map.csv", function(data) {
                    var districtName = removeAccents(d.properties.Agencia_CE.toLowerCase().toUpperCase().substring(21));
                    var matchFound = false;
                    for(var i=0;i<data.length;i++) {
                        if (data[i]["municipio"]==districtName) {
                            $("#prefixo").html(data[i]['prefixo']);
                            $("#bacia").html(data[i]['bacia']);
                            $("#latitude").html(data[i]['latitude']);
                            $("#longitude").html(data[i]['longitude']);
                            $("#media").html(data[i]['media']);
                            matchFound = true;
                        }
                    }
                    if (!matchFound) {
                        $("#districtName").html("No data available");
                        $("#frequency").html("No data available");
                    }
                });
            // IF zoomed-out state is on
        } else {
            x = width_4 / 2;
            y = height_4 / 2;
            k = 1;
            centered = null;
            $(".tooltip").css('opacity', 0);
            showTooltip(d);
        }
            //
            g.selectAll("path")
            .classed("active", centered && function(d) {
                return d === centered;
            })

            g.transition()
            .duration(750)
            .attr("transform", "translate(" + width_4 / 2 + "," + height_4 / 2 + ")scale(" + k + ")translate(" + -x + "," + -y + ")")
            .style("stroke-width", 1.5 / k + "px");
        }

        //Position of the tooltip relative to the cursor
        var tooltipOffset = {x: 5, y: -25};

        //Create a tooltip, hidden at the start
        function showTooltip(d) {
          moveTooltip();

          tooltip.style("opacity", 100);
          tooltip.style("display","block")
              .text(d.properties.Agencia_CE.substring(21));
        }

        //Move the tooltip to track the mouse
        function moveTooltip() {
          tooltip.style("top",(d3.event.pageY+tooltipOffset.y)+"px")
              .style("left",(d3.event.pageX+tooltipOffset.x)+"px");
        }

        //Create a tooltip, hidden at the start
        function hideTooltip() {
          tooltip.style("display","none");
        }
    }

    function removeAccents (text) {
        var accents    = 'ÀÁÂÃÄÅàáâãäåÒÓÔÕÕÖØòóôõöøÈÉÊËèéêëðÇçÐÌÍÎÏìíîïÙÚÛÜùúûüÑñŠšŸÿýŽž',
            accentsOut = "AAAAAAaaaaaaOOOOOOOooooooEEEEeeeeeCcDIIIIiiiiUUUUuuuuNnSsYyyZz",
            textNoAccents = [];

        for (var i in text) {
            var idx = accents.indexOf(text[i]);
            if (idx != -1)
                textNoAccents[i] = accentsOut.substr(idx, 1);
            else
                textNoAccents[i] = text[i];
        }

        return textNoAccents.join('');
    }


        // Load NYC map function
        createNYCMap();
