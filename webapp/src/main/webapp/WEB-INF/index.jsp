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

<% String view = "views/" + (String) request.getAttribute("view") + ".jsp"; %>
<% String heading = (String) request.getAttribute("heading"); %>
<% String[] scripts = (String[]) request.getAttribute("scripts"); %>

<%-- Login view does not need the navigation bar --%>
<%@ include file="templates/header.jsp" %>


<% if (!view.contains("login.jsp")) { %>
    <div class="d-flex">
        <%@ include file="templates/navigation/navigation.jsp" %>
        <main style="padding: 4rem 0 2rem 0; background-color: #F5F6F8">
            <% if (!view.contains("settings") && !view.contains("dashboard")) { %>
                <div class="container h-100">
                    <div class="h-100 row align-items-center justify-content-center">
                        <div class="col-12 mb-5 mb-md-0 row">
                            <h1 class="py-2 pb-lg-3 mb-3"><%= heading %></h1>

                            <div class="w-100 search-bar-container mb-4">
                                <div class="search-bar-input-group input-group input-group-md">
                                    <span class="input-group-text" id="search-addon"><i class="fa-solid fa-magnifying-glass"></i></span>
                                    <input type="text" name="q" class="form-control" placeholder="Search..." aria-describedby="search-addon">
                                </div>
                            </div>

                            <table class="w-100" id="dataTable">
            <% } %>
<% } %>

    <jsp:include page="<%= view %>" />

<% if (!view.contains("login.jsp")) { %>
            <% if (!view.contains("settings") && !view.contains("dashboard")) { %>
                            </table>
                        </div>
                    </div>
                </div>
            <% } %>
        </main>
    </div>
<% } %>

<%@ include file="templates/footer.jsp" %>

<% if (!view.contains("settings") && !view.contains("dashboard") && !view.contains("login")) { %>
    <script>
        $(document).ready(() => {
            $("#dataTable").DataTable({
                searching: false
            });
        })
    </script>
<% } %>

<% if (scripts != null && scripts.length > 0) { %>
    <% for (String script : scripts) { %>
        <script src="<%= request.getContextPath() %>/assets/js/<%= script %>.js"></script>
    <% } %>
<% } %>

<% if (view.contains("dashboard")) { %>
    <% String reportsData = (String) request.getAttribute("reportsData"); %>
    <% String meetersData = (String) request.getAttribute("meetersData"); %>

    <script>
        const reportsData = JSON.parse('<%= reportsData %>');
        const reportsPieData = {
            labels: [
                'Archived Reports',
                'Unarchived Reports'
            ],
            datasets: [{
                label: 'Reports Ratio',
                data: [reportsData.archivedReportsPcg, reportsData.unarchivedReportsPcg],
                backgroundColor: [
                    'rgb(255, 99, 132)',
                    'rgb(255, 205, 86)'
                ]
            }]
        }
        const reportsPieChart = new Chart(document.getElementById("reports-ratio").getContext("2d"), {
            type: 'pie',
            data: reportsPieData,
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        position: 'top',
                    },
                }
            }
        });
        const meetersData = JSON.parse('<%= meetersData %>');
        const meetersPieData = {
            labels: [
                'Banned Meeters',
                'Active Meeters'
            ],
            datasets: [{
                label: 'Meeters Ratio',
                data: [meetersData.bannedMeeters, reportsData.activeMeeters],
                backgroundColor: [
                    'rgb(255, 99, 132)',
                    'rgb(255, 205, 86)'
                ]
            }]
        };
        const meetersPieChart = new Chart(document.getElementById("meeters-ratio").getContext("2d"), {
            type: 'pie',
            data: meetersPieData,
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        position: 'top',
                    },
                }
            }
        });
    </script>
<% } %>
