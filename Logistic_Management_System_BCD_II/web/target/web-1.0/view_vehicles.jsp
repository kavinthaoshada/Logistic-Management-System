<%--
  Created by IntelliJ IDEA.
  User: oshada kavintha
  Date: 5/15/2024
  Time: 9:50 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>View Vehicles</title>
    <link rel="stylesheet" href="css/style.css"/>
    <link rel="stylesheet" href="css/bootstrap.css"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css">
</head>
<body>
<div class="col-3 offset-1 mt-2 mb-2">
    <jsp:include page="offcanvas.jsp"/>
</div>

<div class="row">

    <div class="col-10 offset-1 mt-2 mb-2">
<%--        <input type="text" name="email" id="emp_email" value="${sessionScope.email}" hidden="hidden">--%>
    <div class="row">
        <div class="col-4 offset-7 mt-1 mb-1">
            <button class="btn btn-outline-primary" type="button"
                    onclick="openAddVehicleModal();">Add Vehicle
            </button>
        </div>
    </div>

        <input type="text" id="myInput" onkeyup="filterVehiclesTable();" placeholder="Search for vehicle type.."
               title="Type in a name">

        <table id="myTable">
            <thead>
            <tr class="header">
                <th style="width:25%;">Id</th>
                <th style="width:25%;">Capacity</th>
                <th style="width:25%;">Vehicle Type</th>
                <th style="width:25%;">Status</th>
            </tr>
            </thead>
            <tbody id="vehicleTableBody">

            </tbody>
        </table>
    </div>

</div>

<!-- modal -->
<div class="modal" tabindex="-1" id="addVehicleModal">
    <div class="modal-dialog">
        <div class="modal-content">

            <div class="modal-header">
                <h5 class="modal-title">Add Vehicle</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">

                <div class="col-12">
                    <select class="form-select" id="vehicleType">

                    </select>
                </div>

                <div class="col-12">
                    <label class="form-label">Capacity</label>
                    <input type="text" class="form-control" id="capacity"/>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" onclick="addVehicle();">Add Vehicle</button>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- modal -->


<script src="js/vehicle_script.js"></script>
<script src="js/bootstrap.bundle.js"></script>
<script src="js/bootstrap.js"></script>

<script type="text/javascript">
    window.onload = function () {
        getAllVehicles();
    };

    function filterVehiclesTable() {
        var input, filter, table, tr, td, i, txtValue;
        input = document.getElementById("myInput");
        filter = input.value.toUpperCase();
        table = document.getElementById("myTable");
        tr = table.getElementsByTagName("tr");
        for (i = 0; i < tr.length; i++) {
            td = tr[i].getElementsByTagName("td")[2];
            if (td) {
                txtValue = td.textContent || td.innerText;
                if (txtValue.toUpperCase().indexOf(filter) > -1) {
                    tr[i].style.display = "";
                } else {
                    tr[i].style.display = "none";
                }
            }
        }
    }
</script>
</body>
</html>
