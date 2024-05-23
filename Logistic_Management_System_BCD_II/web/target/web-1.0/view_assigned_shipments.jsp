<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>View Shipments</title>
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
        <input type="text" name="email" id="emp_email" value="${sessionScope.email}" hidden="hidden">

        <input type="text" id="myInput" onkeyup="filterShipmentTable();" placeholder="Search for names.."
               title="Type in a name">

        <table id="myTable">
            <thead>
            <tr class="header">
                <th style="width:12%;">Id</th>
                <th style="width:12%;">Customer Id</th>
                <th style="width:16%;">Customer Name</th>
                <th style="width:20%;">Customer Address</th>
                <th style="width:20%;">Created At</th>
                <th style="width:16%;">Status</th>
            </tr>
            </thead>
            <tbody id="filteredShipmentTableBody">

            </tbody>
        </table>
    </div>

</div>

<!-- modal -->
<div class="modal" tabindex="-1" id="viewShipmentDetailModal">
    <div class="modal-dialog">
        <div class="modal-content">

            <div class="modal-header">
                <h5 class="modal-title">View Detail</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">

                <div class="col-12">
                    <input type="text" name="shipment_id" id="shipmnt_id" hidden="hidden">
                </div>
                <div class="col-12">
                    <input type="text" name="shipment_stat" id="shipmnt_stat" hidden="hidden">
                </div>

                <div class="col-12">
                    <label class="form-label">Origin :</label>
                    <label class="form-label" id="origin">colombo</label>
                </div>

                <div class="col-12">
                    <label class="form-label">Destination :</label>
                    <label class="form-label" id="destination">Gampaha</label>
                </div>

                <div class="col-12">
                    <label class="form-label">Vehicle Type :</label>
                    <label class="form-label" id="v_type">SUV</label>
                </div>

                <div class="col-12">
                    <label class="form-label">Vehicle Id :</label>
                    <label class="form-label" id="v_id">01</label>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" onclick="openViewProductModalFromAssigned();">View Products</button>
                    <button type="button" class="btn btn-primary" onclick="updateAssShipmentStatus();">Update Status</button>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- modal -->

<!-- modal -->
<div class="modal" tabindex="-1" id="viewProductModal">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">View Product</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-12">

                        <table id="myTable2">
                            <thead>
                            <tr class="header">
                                <th style="width:20%;">Id</th>
                                <th style="width:20%;">Product Name</th>
                                <th style="width:20%;">Description</th>
                                <th style="width:20%;">Quantity</th>
                                <th style="width:20%;">Total Weight</th>
                            </tr>
                            </thead>
                            <tbody id="productTableBodyFromAssigned">

                            </tbody>
                        </table>

                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>
<!-- modal -->

<script src="js/assigned_shipment_script.js"></script>
<script src="js/bootstrap.bundle.js"></script>
<script src="js/bootstrap.js"></script>

<script type="text/javascript">
    window.onload = function () {
        getAllAssignedShipments('${sessionScope.email}');
    };

    function filterShipmentTable() {
        var input, filter, table, tr, td, i, txtValue;
        input = document.getElementById("myInput");
        filter = input.value.toUpperCase();
        table = document.getElementById("myTable");
        tr = table.getElementsByTagName("tr");
        for (i = 0; i < tr.length; i++) {
            td = tr[i].getElementsByTagName("td")[1];
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
