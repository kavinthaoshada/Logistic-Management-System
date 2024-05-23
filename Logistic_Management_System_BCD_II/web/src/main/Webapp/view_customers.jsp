<%--
  Created by IntelliJ IDEA.
  User: oshada kavintha
  Date: 5/12/2024
  Time: 7:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin | Customer View</title>
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
                        onclick="openAddCustomerModal();">Add Customers
                </button>
            </div>
        </div>

        <input type="text" id="myInput" onkeyup="filterCustomerTable();" placeholder="Search for names.."
               title="Type in a name">

        <table id="myTable">
            <thead>
            <tr class="header">
                <th style="width:10%;">Id</th>
                <th style="width:17%;">Name</th>
                <th style="width:14%;">Address</th>
                <th style="width:13%;">Contact Number</th>
                <th style="width:15%;">Email</th>
                <th style="width:14%;">Status</th>
            </tr>
            </thead>
            <tbody id="customerTableBody">

            </tbody>
        </table>


    </div>
</div>

</div>

<!-- modal -->
<div class="modal" tabindex="-1" id="updateStatusModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Select To Update</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-6">
                        <label class="form-label">Customer Id : </label>
                        <label class="form-label" id="customer_id">c_id</label>
                    </div>
                    <div class="col-6">
                        <label class="form-label">Customer Id : </label>
                        <label class="form-label" id="customer_name">c_name</label>
                    </div>
                    <div class="col-12">
                        <select class="form-select" id="customerStatus">
                            <%--                            <option value="<!-- in this there must be user type id -->">--%>
                            <!-- in this there must be user type name -->
                            <%--                            </option>--%>
                        </select>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" onclick="updateStatus();">Update Status</button>
                <button type="button" class="btn btn-primary" onclick="openAddShipmentDetailModal();">Add Shipment
                </button>
            </div>
        </div>
    </div>
</div>
<!-- modal -->

<!-- modal -->
<div class="modal" tabindex="-1" id="addCustomerModal">
    <div class="modal-dialog">
        <div class="modal-content">

            <div class="modal-header">
                <h5 class="modal-title">Add Customer</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="col-12">
                    <p class="title02">Add Customer</p>
                    <span class="text-danger" id="msg"></span>
                </div>

                <div class="col-12">
                    <label class="form-label">Name</label>
                    <input type="text" class="form-control" id="name"/>
                </div>

                <div class="col-12">
                    <label class="form-label">Address</label>
                    <input type="text" class="form-control" id="address"/>
                </div>

                <div class="col-12">
                    <label class="form-label">Contact Number</label>
                    <input type="text" class="form-control" id="c_number"/>
                </div>

                <div class="col-12">
                    <label class="form-label">Email</label>
                    <input type="text" class="form-control" id="email"/>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" onclick="addCustomer();">Add Customer</button>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- modal -->

<!-- modal -->
<div class="modal" tabindex="-1" id="addShipmentDetailModal">
    <div class="modal-dialog modal-xl">
        <div class="modal-content">

            <div class="modal-header">
                <h5 class="modal-title">Add Shipment</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">

                <div class="col-12">
                    <input type="text" class="form-control" id="cus_id" hidden="hidden"/>
                </div>

                <div class="col-12">
                    <label class="form-label">Name</label>
                    <input type="text" class="form-control" id="product_name"/>
                </div>

                <div class="col-12">
                    <label class="form-label">Product Description</label>
                    <input type="text" class="form-control" id="product_description"/>
                </div>

                <div class="row">
                    <div class="col-6">
                        <label class="form-label">Quantity</label>
                        <input type="number" class="form-control" id="product_quantity"/>
                    </div>
                    <div class="col-6">
                        <label class="form-label">Total Weight</label>
                        <input type="text" class="form-control" id="tot_weight"/>
                    </div>
                </div>

                <div class="col-12 mt-1 mb-1">
                    <button type="button" class="btn btn-outline-primary rounded" onclick="addProductToTable();">
                        Add Product to Table</button>
                </div>

                <div class="col-12">
                    <table class="table" id="productTable">
                        <thead>

                        <tr class="border border-1 border-white">
                            <th class="text-center">Name</th>
                            <th class="text-center">Description</th>
                            <th class="text-center">Quantity</th>
                            <th class="text-center">Total Weight</th>
                        </tr>

                        </thead>
                        <tbody class="text-black fs-3 fw-bold">
                        </tbody>
                    </table>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" onclick="addShipment();">Add Customer</button>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- modal -->

<script src="js/customer_script.js"></script>
<script src="js/product_table_script.js"></script>
<script src="js/bootstrap.bundle.js"></script>
<script src="js/bootstrap.js"></script>

<script type="text/javascript">
    window.onload = function () {
        getAllCustomer()
    };

    function filterCustomerTable() {
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
