
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
                <td>
                    <div class="btn-group dropend">
                        <i class="fa-solid fa-ellipsis-vertical" style="cursor:pointer;" data-bs-toggle="dropdown"
                           aria-expanded="false"></i>
                        <ul class="dropdown-menu">
                            <li style="cursor: pointer;"><a class="dropdown-item" data-report-id="<%= data.get("id", i).toArray()[0] %>" data-bs-toggle="modal" data-bs-target="#archive-modal">Archive</a></li>
                            <li style="cursor: pointer;"><a class="dropdown-item" data-meeter-id="<%= data.get("meeterReported", i).toArray()[0] %>" data-bs-toggle="modal" data-bs-target="#ban-modal">Ban Meeter</a></li>
                        </ul>
                    </div>
                </td>
            </tr>
        <% } %>
    </tbody>


<%-- Archive Modal --%>
<div class="modal fade" id="archive-modal" tabindex="-1" aria-labelledby="archive-modal-label" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="archive-modal-label">Archive Report</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form action="<%= request.getContextPath() %>/archivedReports" method="POST">
                <div class="modal-body text-center">
                    Do you really want to archive this report?
                    <input type="hidden" name="reportId" id="report-to-archive">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-primary">Archive</button>
                </div>
            </form>
        </div>
    </div>
</div>

<%-- Ban Modal --%>
<div class="modal fade" id="ban-modal" tabindex="-1" aria-labelledby="ban-modal-label" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="ban-modal-label">Ban Meeter</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <form action="<%= request.getContextPath() %>/ban" method="POST">
                <div class="modal-body text-center">
                    Do you really want to archive this report?
                    <input type="hidden" name="reportId" id="meeter-to-ban">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-primary">Ban</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    function addModalListener(inputElement, showModalBtnSelector, dismissModalBtnSelector, attribute) {
        document.querySelector(showModalBtnSelector).addEventListener("click", function () {
            inputElement.value = this.getAttribute(attribute);
        });

        document.querySelector(dismissModalBtnSelector).addEventListener("click", function () {
            inputElement.value = "";
        });
    }

    const reportId = document.getElementById("report-to-archive");
    const meeterId = document.getElementById("meeter-to-ban");

    addModalListener(reportId, "a[data-bs-target='#archive-modal']", "#archive-modal button[data-bs-dismiss]", "data-report-id");
    addModalListener(meeterId, "a[data-bs-target='#ban-modal']", "#ban-modal button[data-bs-dismiss]", "data-meeter-id");

</script>