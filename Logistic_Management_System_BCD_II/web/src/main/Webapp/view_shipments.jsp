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
        <div class="row">
            <div class="col-4 offset-7 mt-1 mb-1">
                <button class="btn btn-outline-primary" type="button"
                        onclick="openAddProductModal();">Add Product
                </button>
            </div>
        </div>

        <input type="text" id="myInput" onkeyup="filterShipmentTable();" placeholder="Search for names.."
               title="Type in a name">

        <table id="myTable">
            <thead>
            <tr class="header">
                <th style="width:20%;">Id</th>
                <th style="width:20%;">Customer Id</th>
                <th style="width:20%;">Customer Name</th>
                <th style="width:20%;">Created At</th>
                <th style="width:20%;">Status</th>
            </tr>
            </thead>
            <tbody id="shipmentTableBody">

            </tbody>
        </table>
    </div>

</div>

<!-- modal -->
<div class="modal" tabindex="-1" id="optionModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Select To Update</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-6">
                        <label class="form-label">Shipment Id : </label>
                        <label class="form-label" id="shipment_id">shipment_id</label>
                    </div>
                    <div class="col-6">
                        <label class="form-label">Customer Name : </label>
                        <label class="form-label" id="cus_name">cus_name</label>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="openUpdateShipmentStatusModal();">Update Status</button>
                <button type="button" class="btn btn-primary" onclick="openViewProductModal();">View Products</button>
            </div>
        </div>
    </div>
</div>
<!-- modal -->

<!-- modal -->
<div class="modal" tabindex="-1" id="viewProductModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Select To Update</h5>
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
                            <tbody id="productTableBody">

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

<!-- modal -->
<div class="modal" tabindex="-1" id="updateShipmentStatusModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Update Shipment Status</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-12">
                        <label class="form-label">Shipment Id : </label>
                        <label class="form-label" id="shipment_id2">s_id</label>
                    </div>
                    <div class="col-12">
                        <select class="form-select" id="shipmentStatus">
                            <%--                            <option value="<!-- in this there must be user type id -->">--%>
                            <!-- in this there must be user type name -->
                            <%--                            </option>--%>
                        </select>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" onclick="updateShipmentStatus();">Update Status</button>
            </div>
        </div>
    </div>
</div>
<!-- modal -->

<script src="js/product_script.js"></script>
<script src="js/bootstrap.bundle.js"></script>
<script src="js/bootstrap.js"></script>

<script type="text/javascript">
    window.onload = function () {
        getAllShipments();
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
