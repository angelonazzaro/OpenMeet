<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
                    <div class="form-floating mb-3">
                        <textarea name="description" id="description"
                                  placeholder="Describe why you are banning this Meeter." required
                                  class="form-control"></textarea>
                        <label for="description">Describe why you are banning this Meeter.</label>
                    </div>
                    <div class="input-group input-group-md has-validation col">
                        <span class="input-group-text" id="endTime-addon"><i
                                class="fa-solid fa-calendar-days"></i></span>
                        <input type="datetime-local" name="endTime" id="endTime" class="form-control"
                               aria-describedby="endTimeHelpBlock"/>
                    </div>
                    <div id="endTimeHelpBlock" class="form-text text-start">
                        Leave it blank to make it a permanent ban.
                    </div>
                    <input type="hidden" name="meeterId" id="meeter-to-ban">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-primary">Ban Meeter</button>
                </div>
            </form>
        </div>
    </div>
</div>
