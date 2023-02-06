<%@ page import="com.openmeet.shared.utils.MultiMapList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% MultiMapList<String, String> data = (MultiMapList<String, String>) request.getAttribute("data"); %>
<% int rows = data.getRowsNumber(); %>

<thead>
<th>Full Name</th>
<th>Email</th>
<th>Description</th>
<th>Start</th>
<th>End</th>
<th>Actions</th>
</thead>
<tbody>
<% for (int i = 0; i < rows; i++) { %>
<tr>
    <td><%= data.get("meeterfullName", i).toArray()[0] %>
    </td>
    <td><%= data.get("email", i).toArray()[0] %>
    </td>
    <td class="text-truncate" style="max-width: 20ch"><%= data.get("description", i).toArray()[0] %>
    </td>
    <td><%= data.get("startTime", i).toArray()[0] %>
    </td>
    <td><%= data.get("endTime", i).toArray()[0] == null ? "Permanent" : data.get("endTime", i).toArray()[0] %>
    </td>
    <td>
        <div class="btn-group dropend">
            <i class="fa-solid fa-ellipsis-vertical" style="cursor:pointer;" data-bs-toggle="dropdown"
               aria-expanded="false"></i>
            <ul class="dropdown-menu">
                <li style="cursor: pointer"><a class="dropdown-item"
                                               href="<%= request.getContextPath() %>/ban?banIdToUnban=<%= data.get("id", i).toArray()[0]%>">Unban</a>
                </li>
                <li style="cursor: pointer"><a class="dropdown-item"
                                               data-ban-description="<%= data.get("description", i).toArray()[0] %>"
                                               data-ban-end-time="<%= data.get("endTime", i).toArray()[0] %>"
                                               data-meeter-id="<%= data.get("meeterId", i).toArray()[0] %>"
                                               data-ban-id="<%= data.get("id", i).toArray()[0] %>"
                                               data-bs-toggle="modal" data-bs-target="#ban-modal">Modify</a></li>
            </ul>
        </div>
    </td>
</tr>
<% } %>
</tbody>

<%@include file="../templates/modals/banModal.jsp" %>

<% if (rows > 0) { %>
<script>
    document.querySelector("a[data-bs-target='#ban-modal']").addEventListener('click', function () {
        const description = document.getElementById("description");
        const meeterToBan = document.getElementById("meeter-to-ban");
        const endTime = document.getElementById("endTime");
        const banId = document.getElementById("ban-id");

        description.value = this.getAttribute("data-ban-description");
        meeterToBan.value = this.getAttribute("data-meeter-id");

        if (this.getAttribute("data-ban-end-time") != null) {
            endTime.value = this.getAttribute("data-ban-end-time");
        }

        banId.value = this.getAttribute("data-ban-id");
        if (banId.hasAttribute("disabled")) {
            banId.removeAttribute("disabled");
        }

    });
</script>
<% } %>