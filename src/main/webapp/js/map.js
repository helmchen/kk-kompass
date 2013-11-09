/* 
 * Copyright 2013 helmut.
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in
 * compliance with the License. Obtain your copy at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations.
 */

/*** D3/SVG setup ***/
var selectedCanton;
var width = $('#map').width();
var height = width * 0.66;
/*
 if (height > $(window).height() * 0.66) {
 // Map überschreitet den Viewport. Wir verkleinern sie entsprechend.
 height = $(window).height() * 0.66;
 width = height * 1.5;
 }
 */
var map;

var svg = d3.select("#map").append("svg")
        .attr("width", width)
        .attr("height", height);

var canvas = svg.append("svg:g");

var scale = 15 * width;
var projection = d3.geo.albers()
        .rotate([0, 0])
        .center([8.43, 46.8])
        .scale(scale);

var path = d3.geo.path()
        .projection(projection);


d3.json("js/data/switzerland.topojson", function(switzerland) {
    map = switzerland;

    // Zeichnen Ländergrenzen
    canvas.append("path")
            .datum(topojson.mesh(map, map.objects["swiss-cantons"], function(a, b) {
                return a.properties.no === b.properties.no;
            }))
            .attr("d", path)
            .attr("class", "outerBoundary");

    // Kantone bereitstellen
    canvas.selectAll("path")
            .data(topojson.feature(map, map.objects["swiss-cantons"]).features)
            .enter().append("path")
            .attr("d", path)
            .on("click", click)
            .attr("class", "canton");

    // Kantonscodes einblenden
    canvas.selectAll(".cantonLabel")
            .data(topojson.feature(map, map.objects["swiss-cantons"]).features)
            .enter().append("text")
            .attr("class", function(d) {
                return "cantonLabel " + d.id;
            })
            .attr("transform", function(d) {
                return "translate(" + path.centroid(d) + ")";
            })
            .attr("dy", ".35em")
            .text(function(d) {
                return d.properties.abbr;
            });
});


function click(observed) {
    if (observed.properties.cantonNo) {
        if (observed.properties.cantonNo === selectedCanton) {
            return reset();
        }
        selectedCanton = observed.properties.cantonNo;
    }

    if (observed.properties.no) {
        if (observed.properties.no === selectedCanton) {
            return reset();
        }
        selectedCanton = observed.properties.no;
    }
    canvas.selectAll(".active").classed("active", false);
    d3.select(this).classed("active", true);

    var b = path.bounds(observed);

    console.log("Object Geo id: " + observed.properties.no);
    canvas.transition().duration(750).attr("transform",
            "translate(" + projection.translate() + ")"
            + "scale(" + .95 / Math.max((b[1][0] - b[0][0]) / width, (b[1][1] - b[0][1]) / height) + ")"
            + "translate(" + -(b[1][0] + b[0][0]) / 2 + "," + -(b[1][1] + b[0][1]) / 2 + ")");
    resetMunicipals();
    addMunicipals();

}

function addMunicipals() {
    d3.json("js/data/switzerland.topojson", function(map) {
        var cities = map.objects["swiss-municipalities"];
        for (var i = cities.geometries.length - 1; i >= 0; i--) {
            if (cities.geometries[i].properties.cantonNo !== selectedCanton) {
                cities.geometries.splice(i, 1);
            }
        }
        // Gemeinden bereitstellen
        canvas.selectAll("path")
                .data(topojson.feature(map, cities).features)
                .enter()
                .append("path")
                .attr("d", path)
                // .attr("title", )
                .on("click", click)
                .attr("class", "area municipal");
    });
}

function reset() {
    resetMunicipals();
    canvas.selectAll(".active").classed("active", false);
    canvas.transition().duration(750).attr("transform", "");
    //active = null;
    selectedCanton = null;
}

function resetMunicipals() {
    canvas.selectAll(".municipal").remove();
}

/**
 * Füllt die Karte entsprechend der Daten aus.
 * @returns {undefined}
 */
function rePaint() {

}


