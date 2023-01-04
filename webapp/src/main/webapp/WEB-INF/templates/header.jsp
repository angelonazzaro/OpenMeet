<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String title = (String) request.getAttribute("title"); %>

<html lang="it">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title><%= title %> | OpenMeet</title>
    <%@ include file="scripts/top_scripts.jsp" %>
</head>
<body>
<%@ include file="navigation/navigation.jsp" %>

