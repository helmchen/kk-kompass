<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setBundle basename="ch.helmchen.kompass.text.Translations" var="i18n"/>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="page.css">
        <link rel="stylesheet" type="text/css" href="map.css">
        <title><fmt:message key="Application.title" bundle="${i18n}"/></title>
    </head>

    <body>
        <header>
            <div id="header">
                <span class="appTitle"><fmt:message key="application.title" bundle="${i18n}"/></span>
            </div>
            <div id="mainNavig">
                <span class="locales">
                    <fmt:message key="locale.german" bundle="${i18n}"/>
                    <fmt:message key="locale.french" bundle="${i18n}"/>
                    <fmt:message key="locale.italian" bundle="${i18n}"/>
                </span>
                <!-- Additional Links to Documentation ... -->
            </div>
        </header>
        <div id="breadcrumps">
            <!-- todo -->
        </div>

        <div id="map">

        </div>
        <div id="statistics">

        </div>
        <div id="cockpit">

        </div>

        <footer>

        </footer>
        <!-- Javascript-Files -->
        <script src="js/libs/jquery/jquery.js"></script>
        <script src="js/libs/d3/d3.v3.js"></script>
        <script src="js/libs/d3/topojson.js"></script>
        <!--<script src="js/queue.v1.min.js"></script> -->
        <!-- <script src="js/topojson.v0.min.js"></script> -->
        <script src="js/map.js"></script>
        <script src="js/statistics.js"></script>

    </body>

</html>
