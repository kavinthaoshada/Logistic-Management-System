<%--
  Created by IntelliJ IDEA.
  User: oshada kavintha
  Date: 5/11/2024
  Time: 12:20 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>--%>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="css/style.css"/>
    <link rel="stylesheet" href="css/bootstrap.css"/>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css">
</head>
<body>
<div class="col-3 offset-1 mt-2 mb-2">
    <jsp:include page="offcanvas.jsp"/>
</div>

<%--<button onclick="m();">btn2</button>--%>
<div class="row">
    <div class="col-4 offset-4 mt-2 mb-2">
        <h1>View Employees</h1>
    </div>

    <div class="col-10 offset-1 mt-2 mb-2">
        <div class="row">
            <div class="col-4 offset-7 mt-1 mb-1">
                <button class="btn btn-outline-primary" type="button"
                   onclick="openAddEmployeeModal();">Add Employees
                </button>
            </div>
        </div>

        <input type="text" id="myInput" onkeyup="filterEmployeeTable();" placeholder="Search for names.."
               title="Type in a name">

        <table id="myTable">
            <thead>
            <tr class="header">
                <th style="width:10%;">Id</th>
                <th style="width:17%;">Email</th>
                <th style="width:14%;">Username</th>
                <th style="width:13%;">Password</th>
                <th style="width:15%;">Registered Date</th>
                <th style="width:14%;">User Type</th>
                <th style="width:17%;">Role</th>
            </tr>
            </thead>
            <tbody id="employeeTableBody">

            </tbody>
        </table>


    </div>
</div>

<!-- modal -->
<div class="modal" tabindex="-1" id="updateChooser">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Select To Update</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-6">
                        <label class="form-label">Employee Id : </label>
                        <label class="form-label" id="emp_id">e_id</label>
                    </div>
                    <div class="col-6">
                        <label class="form-label">Employee Username : </label>
                        <label class="form-label" id="emp_uname">u_name</label>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="OpenAssignShipmentModal();">Assign Shipments</button>
                <button type="button" class="btn btn-primary" onclick="openUpdateRoleModal();">Update Role</button>
            </div>
        </div>
    </div>
</div>
<!-- modal -->

<!-- modal -->
<div class="modal" tabindex="-1" id="updateRole">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Select To Update</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-12">
                        <label class="form-label">Employee Id : </label>
                        <label class="form-label" id="emp_id2">e_id</label>
                    </div>
                    <div class="col-12">
                        <label class="form-label">Role : </label>
                        <input type="text" class="form-control" id="role"/>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" onclick="updateRole();">Update</button>
            </div>
        </div>
    </div>
</div>
<!-- modal -->


<!-- modal -->
<div class="modal" tabindex="-1" id="addEmployeeModal">
    <div class="modal-dialog">
        <div class="modal-content">

            <div class="modal-header">
                <h5 class="modal-title">Select To Update</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="col-12">
                    <p class="title02">Add Employee</p>
                    <span class="text-danger" id="msg"></span>
                </div>

                <div class="col-12">
                    <label class="form-label">Email</label>
                    <input type="text" class="form-control" id="email"/>
                </div>

                <div class="row">
                    <div class="col-6">
                        <label class="form-label">Username</label>
                        <input type="text" class="form-control" id="username"/>
                    </div>
                    <div class="col-6">
                        <label class="form-label">User-Type</label>
                        <select class="form-select" id="usertype">
<%--                            <option value="<!-- in this there must be user type id -->">--%>
                                <!-- in this there must be user type name -->
<%--                            </option>--%>
                        </select>
                    </div>
                </div>

                <div class="col-12">
                    <label class="form-label">Password</label>
                    <input type="password" class="form-control" id="password"/>
                </div>

                <div class="col-12">
                    <label class="form-label">User-Role</label>
                    <input type="text" class="form-control" id="user_role"/>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" onclick="addEmployee();">Add Employee</button>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- modal -->

<!-- modal -->
<div class="modal" tabindex="-1" id="assignShipmentModal">
    <div class="modal-dialog modal-xl">
        <div class="modal-content">

            <div class="modal-header">
                <h5 class="modal-title">Assign Shipment</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">

                <div class="col-12">
                    <input type="text" class="form-control" id="employee_id" hidden="hidden"/>
                </div>

                <div class="col-12">
                    <table class="table" id="shipmentTable">
                        <thead>

                        <tr class="border border-1 border-white">
                            <th class="text-center">Shipment Id</th>
                            <th class="text-center">Created At</th>
                            <th class="text-center">Customer Id</th>
                            <th class="text-center">Status</th>
                            <th class="text-center">....</th>
                        </tr>

                        </thead>
                        <tbody class="text-black fs-6" id="shipmentTableBodyToAssign">
                        </tbody>
                    </table>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" onclick="assignShipments();">Assign Shipments</button>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- modal -->


<script src="js/main.js"></script>
<script src="js/script.js"></script>
<script src="js/bootstrap.bundle.js"></script>
<script src="js/bootstrap.js"></script>

<script type="text/javascript">
    let updateChooserModal;
    window.onload = function () {
        getAllEmployees();

    };

    function filterEmployeeTable() {
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
