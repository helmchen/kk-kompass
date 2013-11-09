/* 
 * Copyright 2013 helmut.
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in
 * compliance with the License. Obtain your copy at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations.
 */
var statisticsWidth = $('#statistics').width();
var statisticsHeight = 300;

var statisticsSvg = d3.select("#statistics").append("svg")
        .attr("width", statisticsWidth)
        .attr("height", statisticsHeight);

var statisticsCanvas = statisticsSvg.append("svg:g");

//var scale = 15 * width;
//var projection = d3.geo.albers()
//        .rotate([0, 0])
//        .center([8.43, 46.8])
//        .scale(scale);


