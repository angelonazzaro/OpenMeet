<%@ page import="java.util.HashMap" %>
<%@ page import="com.openmeet.shared.utils.MultiMapList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% MultiMapList<String, String> data = (MultiMapList<String, String>) request.getAttribute("data"); %>
<% int rows = data.getRowsNumber(); %>

<thead>
<tr>
    <th class="text-center">Full Name</th>
    <th class="text-center">Email</th>
    <th class="text-center">Reason</th>
    <th class="text-center">Date</th>
    <th class="text-center">Actions</th>
</tr>
</thead>
<tbody>
<% for (int i = 0; i < rows; i++) { %>
<tr class="text-center">
    <td id="meeterFullName-<%= data.get("id", i).toArray()[0] %>"><%= data.get("meeterfullName", i).toArray()[0] %>
    </td>
    <td id="meeterEmail-<%= data.get("id", i).toArray()[0] %>"><%= data.get("email", i).toArray()[0] %>
    </td>
    <td id="reportReason-<%= data.get("id", i).toArray()[0] %>"><%= data.get("reason", i).toArray()[0] %>
    </td>
    <td id="reportDate-<%= data.get("id", i).toArray()[0] %>"><%= data.get("creationDate", i).toArray()[0] %>
    </td>
    <td>
        <div class="btn-group dropend">
            <i class="fa-solid fa-ellipsis-vertical" style="cursor: pointer;" data-bs-toggle="dropdown"
               aria-expanded="false"></i>
            <ul class="dropdown-menu">
                <li style="cursor: pointer"><a class="dropdown-item"
                                                data-report-id="<%= data.get("id", i).toArray()[0] %>"
                                                data-bs-toggle="modal" data-bs-target="#archive-modal">Archive</a></li>
                                               data-report-id="<%= data.get("id", i).toArray()[0] %>"
                                               data-bs-toggle="modal" data-bs-target="#view-report-modal">View</a></li>
                <li style="cursor: pointer"><a class="dropdown-item"
                                               data-report-id="<%= data.get("id", i).toArray()[0] %>"
                                               data-bs-toggle="modal" data-bs-target="#archive-modal">Archive</a></li>
                <li style="cursor: pointer"><a class="dropdown-item"
                                               data-meeter-id="<%= data.get("meeterReported", i).toArray()[0] %>"
                                               data-bs-toggle="modal" data-bs-target="#ban-modal">Ban Meeter</a></li>
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

<%-- View Report modal --%>
<div class="modal fade" id="view-report-modal" tabindex="-1" aria-labelledby="view-report-modal-label"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="view-report-modal-label"></h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body text-center">
                    <div class="row mb-4">
                        <label for="meeterReportedFullName">Meeter Full Name</label>
                        <input type="text" id="meeterReportedFullName"
                               class="form-control" disabled aria-label="meeterReportedFullName"/>
                    </div>
                    <div class="row mb-4">
                        <label for="meeterEmail">Meeter Email</label>
                        <input type="email" id="meeterEmail"
                               class="form-control" disabled aria-label="meeterEmail"/>
                    </div>
                    <div class="row mb-4">
                        <label for="reportReason">Reason</label>
                        <input type="text" id="reportReason"
                               class="form-control" disabled aria-label="reportReason"/>
                    </div>
                    <div class="row mb-4">
                        <label for="reportDate">Date</label>
                        <input type="text" id="reportDate"
                               class="form-control" disabled aria-label="reportDate"/>
                    </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<%@include file="../templates/modals/banModal.jsp" %>

<% if (rows > 0) { %>
<script>


   function addFunctionality(inputElement, modalBtnToggleSelector, modalBtnDismissSelector, attribute) {

       document.querySelectorAll(modalBtnToggleSelector).forEach(el => {
           el.addEventListener("click", function () {
               inputElement.value = this.getAttribute(attribute);
               console.log(inputElement, modalBtnToggleSelector, modalBtnDismissSelector, attribute);
         });
       });

       document.querySelectorAll(modalBtnDismissSelector).forEach(el => {
           el.addEventListener("click", () => inputElement.value = "");
       });
   }

    document.querySelectorAll("a[data-bs-target='#view-report-modal']").forEach(el => {
        el.addEventListener("click", function () {
            const reportId = this.getAttribute("data-report-id");

            document.getElementById("view-report-modal-label").innerText = "Report #" + reportId;
            document.getElementById("meeterReportedFullName").value = document.getElementById("meeterFullName-" + reportId).innerText;
            document.getElementById("meeterEmail").value = document.getElementById("meeterEmail-" + reportId).innerText;
            document.getElementById("reportReason").value = document.getElementById("reportReason-" + reportId).innerText;
            document.getElementById("reportDate").value = document.getElementById("reportDate-" + reportId).innerText;

        });
    });

    function addFunctionality(inputElement, modalBtnToggleSelector, modalBtnDismissSelector, attribute) {

        document.querySelectorAll(modalBtnToggleSelector).forEach(el => {
            el.addEventListener("click", function () {
                inputElement.value = this.getAttribute(attribute);
                console.log(inputElement, modalBtnToggleSelector, modalBtnDismissSelector, attribute);
            });
        });

        document.querySelectorAll(modalBtnDismissSelector).forEach(el => {
            el.addEventListener("click", () => inputElement.value = "");
        });
    }


    const reportId = document.getElementById("report-to-archive");
    const meeterId = document.getElementById("meeter-to-ban");

    addFunctionality(reportId, "a[data-bs-target='#archive-modal']", "#archive-modal button[data-bs-dismiss]", "data-report-id");
    addFunctionality(meeterId, "a[data-bs-target='#ban-modal']", "#ban-modal button[data-bs-dismiss]", "data-meeter-id");
</script>
<% } %>
