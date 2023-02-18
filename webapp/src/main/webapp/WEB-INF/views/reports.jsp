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
    <td><%= data.get("meeterfullName", i).toArray()[0] %>
    </td>
    <td><%= data.get("email", i).toArray()[0] %>
    </td>
    <td><%= data.get("reason", i).toArray()[0] %>
    </td>
    <td><%= data.get("creationDate", i).toArray()[0] %>
    </td>
    <td>
        <div class="btn-group dropend">
            <i class="fa-solid fa-ellipsis-vertical" style="cursor: pointer;" data-bs-toggle="dropdown"
               aria-expanded="false"></i>
            <ul class="dropdown-menu">
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

    const reportId = document.getElementById("report-to-archive");
    const meeterId = document.getElementById("meeter-to-ban");

    // document.querySelector("a[data-bs-target='#ban-modal']").addEventListener("click", function () {
    //     meeterId.value = this.getAttribute("data-meeter-id");
    // });
    //
    // document.querySelector("#ban-modal button[data-bs-dismiss]").addEventListener("click", () => meeterId.value = "");
    //
    // document.querySelectorAll("li[data-bs-target='#archive-modal']").forEach(li => {
    //     li.addEventListener("click", function () {
    //         reportId.value = this.getAttribute("data-report-id");
    //     });
    // });
    //
    // document.querySelector("#archive-modal button[data-bs-dismiss]").addEventListener("click", () => reportId.value = "");

    addFunctionality(reportId, "a[data-bs-target='#archive-modal']", "#archive-modal button[data-bs-dismiss]", "data-report-id");
    addFunctionality(meeterId, "a[data-bs-target='#ban-modal']", "#ban-modal button[data-bs-dismiss]", "data-meeter-id");
</script>
<% } %>
