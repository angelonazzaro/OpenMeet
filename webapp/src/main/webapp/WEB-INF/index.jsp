<%--
  This file is going to be used to display all the
  views of the application.
  It will act as the basic template and will change its content
  dynamically.

  The "view" attribute represent the view to display to the user
  The "scripts" attribute represent the scripts that needs to loaded alogn with the view
  specified by the "page" attribute.

  Why aren't the scripts included into the view?
  Because they will be loaded before the footer that contains the dependecies
  for the frameworks, plugins etc.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String view = "views/" + (String) request.getAttribute("view"); %>
<% String[] scripts = (String[]) request.getAttribute("scripts"); %>

<%@ include file="templates/header.jsp" %>

    <jsp:include page="<%= view %>" />

<%@ include file="templates/footer.jsp" %>

<% if (scripts != null && scripts.length > 0) { %>
    <% for (String script : scripts) { %>
        <script src="<%= request.getContextPath() %>/assets/js/<%= script %>"></script>
    <% } %>
<% } %>

