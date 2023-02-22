<%@ page import="com.openmeet.webapp.dataLayer.moderator.Moderator" %>
<%@ page import="java.io.File" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%-- TODO: Change SVG Icons --%>

<% Moderator user = (Moderator) request.getSession().getAttribute("user"); %>
<% String profilePic = user.getProfilePic(); %>
<% String basePath = request.getContextPath() + File.separator + "assets" + File.separator; %>

<% if (profilePic == null || profilePic.length() == 0) { %>
<% profilePic = basePath + "imgs" + File.separator
        + "special" + File.separator + "userplaceholder.png"; %>
<% } else { %>
<% profilePic = basePath + "uploads" + File.separator + "moderators" + File.separator + user.getId() + File.separator
        + user.getProfilePic(); %>
<% } %>

<div class="sidebar-container">
    <aside class="sidebar open" data-sidebar>
        <div class="top-sidebar mb-3">
            <img src="<%= profilePic %>" alt="user profile pic" class="channel-logo show-on-error-imgs">
            <div class="hidden-sidebar your-channel"><%= user.getModeratorName() + " " + user.getModeratorSurname()%>
            </div>
            <div class="hidden-sidebar channel-name"><%= user.getEmail() %>
            </div>
        </div>
        <div class="middle-sidebar">
            <ul class="sidebar-list">
                <li class="sidebar-list-item ">
                    <a href="<%= request.getContextPath() %>/" class="sidebar-link">
                        <svg class="sidebar-icon" viewBox="0 0 24 24" preserveAspectRatio="xMidYMid meet"
                             focusable="false">
                            <g>
                                <path d="M3 13h8V3H3v10zm0 8h8v-6H3v6zm10 0h8V11h-8v10zm0-18v6h8V3h-8z"></path>
                            </g>
                        </svg>
                        <div class="hidden-sidebar">Dashboard</div>
                    </a>
                </li
                <li class="sidebar-list-item ">
                    <a href="<%= request.getContextPath() %>/reports" class="sidebar-link">
                        <svg class="sidebar-icon" viewBox="0 0 24 24" preserveAspectRatio="xMidYMid meet"
                             focusable="false">
                            <g>
                                <path d="M3 13h8V3H3v10zm0 8h8v-6H3v6zm10 0h8V11h-8v10zm0-18v6h8V3h-8z"></path>
                            </g>
                        </svg>
                        <div class="hidden-sidebar">Reports</div>
                    </a>
                </li>
                <li class="sidebar-list-item ">
                    <a href="<%= request.getContextPath() %>/archivedReports" class="sidebar-link">
                        <svg class="sidebar-icon" viewBox="0 0 24 24" preserveAspectRatio="xMidYMid meet"
                             focusable="false">
                            <g>
                                <path d="M3 13h8V3H3v10zm0 8h8v-6H3v6zm10 0h8V11h-8v10zm0-18v6h8V3h-8z"></path>
                            </g>
                        </svg>
                        <div class="hidden-sidebar">Archived Reports</div>
                    </a>
                </li>
                <li class="sidebar-list-item ">
                    <a href="<%= request.getContextPath() %>/ban" class="sidebar-link">
                        <svg class="sidebar-icon" viewBox="0 0 24 24" preserveAspectRatio="xMidYMid meet"
                             focusable="false">
                            <g>
                                <path d="M3 13h8V3H3v10zm0 8h8v-6H3v6zm10 0h8V11h-8v10zm0-18v6h8V3h-8z"></path>
                            </g>
                        </svg>
                        <div class="hidden-sidebar">Bans</div>
                    </a>
                </li>
            </ul>
        </div>
        <div class="bottom-sidebar">
            <ul class="sidebar-list">
                <li class="sidebar-list-item ">
                    <a href="<%= request.getContextPath() %>/settings" class="sidebar-link">
                        <svg class="sidebar-icon" viewBox="0 0 24 24" preserveAspectRatio="xMidYMid meet"
                             focusable="false">
                            <g>
                                <path d="M3 13h8V3H3v10zm0 8h8v-6H3v6zm10 0h8V11h-8v10zm0-18v6h8V3h-8z"></path>
                            </g>
                        </svg>
                        <div class="hidden-sidebar">Settings</div>
                    </a>
                </li>
                <li class="sidebar-list-item ">
                    <a href="<%= request.getContextPath() %>/logout" class="sidebar-link">
                        <svg class="sidebar-icon" viewBox="0 0 24 24" preserveAspectRatio="xMidYMid meet"
                             focusable="false">
                            <g>
                                <path d="M3 13h8V3H3v10zm0 8h8v-6H3v6zm10 0h8V11h-8v10zm0-18v6h8V3h-8z"></path>
                            </g>
                        </svg>
                        <div class="hidden-sidebar">Logout</div>
                    </a>
                </li>
            </ul>
        </div>
    </aside>
</div>