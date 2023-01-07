<%@ page import="com.openmeet.shared.utils.MultiMapList" %>
<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% MultiMapList<String, String> data = (MultiMapList<String, String>) request.getAttribute("data"); %>
<% int rows = data.getRowsNumber(); %>

<div class="container h-100">
    <div class="h-100 row align-items-center justify-content-center">
        <div class="col-12 mb-5 mb-md-0 row">
            <h1 class="py-2 pb-lg-3 mb-3">Reports</h1>

            <div class="w-100 search-bar-container mb-4">
                <div class="search-bar-input-group input-group input-group-md">
                    <span class="input-group-text" id="search-addon"><i class="fa-solid fa-magnifying-glass"></i></span>
                    <input type="text" name="q" class="form-control" placeholder="Search..." aria-describedby="search-addon">
                </div>
            </div>

            <table class="w-100" id="dataTable">
                <thead>
                    <th>Full Name</th>
                    <th>Email</th>
                    <th>Reason</th>
                    <th>Date</th>
                    <th>Account Status</th>
                    <th>Actions</th>
                </thead>
                <tbody>
                    <% HashMap<String, String> rowData = new HashMap<>(); %>
                    <% for (int i = 0; i < rows; i++) { %>
                        <tr>
                            <td><%= data.get("meeterfullName", i).toArray()[0] %></td>
                            <td><%= data.get("email", i).toArray()[0] %></td>
                            <td><%= data.get("reason", i).toArray()[0] %></td>
                            <td><%= data.get("creationDate", i).toArray()[0] %></td>
                            <td>--</td>
                            <td>--</td>
                        </tr>
                    <% } %>
                </tbody>
            </table>

        </div>
    </div>
</div>