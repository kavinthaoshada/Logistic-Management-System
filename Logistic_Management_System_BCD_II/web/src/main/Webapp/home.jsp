<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title> Home</title>
    <link rel="stylesheet" href="css/bootstrap.css"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css">
</head>
<%
    if (session.getAttribute("login") == null) {
        response.sendRedirect("index.jsp");
    }
%>
<body>
<div class="col-3 offset-1 mt-2 mb-2">
    <jsp:include page="offcanvas.jsp"/>
</div>

<div id="dynamic-content" class="col-12">
</div>
<script>
    function loadDynamicPage(pageURL) {
        fetch(pageURL)
            .then(response => response.text())
            .then(html => {
                document.getElementById('dynamic-content').innerHTML = html;
            })
            .catch(error => console.error('Error loading dynamic page:', error));
    }
</script>
<%--<h1>Hello, ${sessionScope.email}</h1>--%>
<%
    if (session.getAttribute("login").equals("admin")) {
%>
<%--<script>loadDynamicPage('admin/view_shipments.jsp');</script>--%>
<div class="row">
    <div class="col-4 offset-4">
        <h1>Admin Home</h1>
    </div>
</div>
<%
} else if (session.getAttribute("login").equals("employee")) {
%>
<div class="row">
    <div class="col-4 offset-4">
        <h1>Employee Home</h1>
    </div>
</div>
<%
    }
%>

<script src="js/main.js"></script>
<script src="js/bootstrap.bundle.js"></script>
<script src="js/bootstrap.js"></script>
</body>
</html>
