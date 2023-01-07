
<%@ page import="java.util.HashMap" %>
<%@ page import="com.openmeet.shared.utils.MultiMapList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% MultiMapList<String, String> data = (MultiMapList<String, String>) request.getAttribute("data"); %>
<% int rows = data.getRowsNumber(); %>

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
                <td>
                    <div class="btn-group dropend">
                        <i class="fa-solid fa-ellipsis-vertical" style="cursor:pointer;" data-bs-toggle="dropdown"
                           aria-expanded="false"></i>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="#">Archive</a></li>
                            <li><a class="dropdown-item" href="#">Ban Meeter</a></li>
                        </ul>
                    </div>
                </td>
            </tr>
        <% } %>
    </tbody>