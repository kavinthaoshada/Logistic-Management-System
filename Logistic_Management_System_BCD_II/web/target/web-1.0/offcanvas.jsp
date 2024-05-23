<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

</head>
<body>

<button class="btn btn-primary" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasScrolling"
        aria-controls="offcanvasScrolling"><i class="bi bi-list fs-4 me-1"></i>Open Menu
</button>

<div class="offcanvas offcanvas-start" data-bs-scroll="true" data-bs-backdrop="false" tabindex="-1"
     id="offcanvasScrolling" aria-labelledby="offcanvasScrollingLabel">
    <div class="offcanvas-header">
        <h5 class="offcanvas-title" id="offcanvasScrollingLabel">My Menu</h5>
        <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
    </div>
    <div class="offcanvas-body">
        <div class="col-12">
            <div class="row">
                <%
                    if (session.getAttribute("login").equals("admin")) {
                %>
                <div class="col-10 mt-1 mb-1">
                    <a class="btn btn-outline-secondary" style="width: 275px;" type="button"
                       href="view_shipments.jsp">View Shipments
                    </a>
                </div>
                <div class="col-10 mt-1 mb-1">
                    <a class="btn btn-outline-secondary" style="width: 275px;" type="button"
                       href="view_employees.jsp">View Employees
                    </a>
                </div>
                <div class="col-10 mt-1 mb-1">
                    <a class="btn btn-outline-secondary" style="width: 275px;" type="button"
                       href="view_customers.jsp">View Customers
                    </a>
                </div>
                <div class="col-10 mt-1 mb-1">
                    <a class="btn btn-outline-secondary" style="width: 275px;" type="button"
                       href="view_vehicles.jsp">View Vehicles
                    </a>
                </div>
                <div class="col-10 mt-1 mb-1">
                    <a class="btn btn-outline-secondary" style="width: 275px;" type="button"
                       href="view_routes.jsp">View Routes
                    </a>
                </div>
                <%
                } else if (session.getAttribute("login").equals("employee")) {
                %>
                <div class="col-10 mt-1 mb-1">
                    <a class="btn btn-outline-secondary" style="width: 275px;" type="button"
                       href="view_assigned_shipments.jsp">View Shipments
                    </a>
                </div>
                <%
                    }
                %>
            </div>
        </div>
    </div>
</div>

</body>
</html>
