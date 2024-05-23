<%--
  Created by IntelliJ IDEA.
  User: oshada kavintha
  Date: 5/15/2024
  Time: 4:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>View Routes</title>
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
                        onclick="openAddRouteModal();">Add Route
                </button>
            </div>
        </div>

        <input type="text" id="myInput" onkeyup="filterRouteTable();" placeholder="Search for destinations.."
               title="Type in a name">

        <table id="myTable">
            <thead>
            <tr class="header">
                <th style="width:14%;">Id</th>
                <th style="width:18%;">Origin</th>
                <th style="width:18%;">Destination</th>
                <th style="width:14%;">Distance (Km)</th>
                <th style="width:18%;">Start Date</th>
                <th style="width:18%;">Estimated Time</th>
            </tr>
            </thead>
            <tbody id="routeTableBody">

            </tbody>
        </table>
    </div>

</div>

<!-- modal -->
<div class="modal" tabindex="-1" id="addRoutesModal">
    <div class="modal-dialog">
        <div class="modal-content">

            <div class="modal-header">
                <h5 class="modal-title">Add Route</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">

                <div class="col-12">
                    <label class="form-label">Origin</label>
                    <input type="text" class="form-control" id="origin"/>
                </div>

                <div class="col-12">
                    <label class="form-label">Destination</label>
                    <input type="text" class="form-control" id="destination"/>
                </div>

                <div class="col-12">
                    <label class="form-label">Distance</label>
                    <input type="text" class="form-control" id="distance"/>
                </div>

                <div class="col-12">
                    <label class="form-label">Estimated Time</label>
                    <input type="text" class="form-control" id="estimated_time" placeholder="00:00"/>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" onclick="addRoute();">Add Route</button>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- modal -->

<!-- modal -->
<div class="modal" tabindex="-1" id="addShipmentsToRouteModal">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Add Shipments to Route</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-12">
                        <input type="text" id="route_id" hidden="hidden">
                    </div>

                    <div class="col-12">
                        <table class="table" id="addShipmentToRouteTable">
                            <thead>

                            <tr class="border border-1 border-white">
                                <th class="text-center">Shipment Id</th>
                                <th class="text-center">Created At</th>
                                <th class="text-center">Customer Id</th>
                                <th class="text-center">Status</th>
                                <th class="text-center">....</th>
                            </tr>

                            </thead>
                            <tbody class="text-black fs-6" id="addShipmentToRouteTableBody">
                            </tbody>
                        </table>
                    </div>

                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="addShipmentsToRoute();">Add Shipments</button>
                <button type="button" class="btn btn-primary" onclick="openAddVehicleToRouteModal();">Add Vehicles</button>
            </div>
        </div>
    </div>
</div>
<!-- modal -->

<!-- modal -->
<div class="modal" tabindex="-1" id="addVehiclesToRouteModal">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Add Shipments to Route</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-12">
                        <input type="text" id="r_id" hidden="hidden">
                    </div>

                    <div class="col-12">
                        <table class="table" id="addVehiclesToRouteTable">
                            <thead>

                            <tr class="border border-1 border-white">
                                <th class="text-center">Vehicle Id</th>
                                <th class="text-center">Vehicle Type</th>
                                <th class="text-center">Capacity</th>
                                <th class="text-center">Status</th>
                                <th class="text-center">....</th>
                            </tr>

                            </thead>
                            <tbody class="text-black fs-6" id="addVehiclesToRouteTableBody">
                            </tbody>
                        </table>
                    </div>

                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" onclick="addVehiclesToRoute();">Add Vehicles</button>
            </div>
        </div>
    </div>
</div>
<!-- modal -->

<script src="js/route_script.js"></script>
<script src="js/bootstrap.bundle.js"></script>
<script src="js/bootstrap.js"></script>

<script type="text/javascript">
    window.onload = function () {
        getAllRoutes();
    };

    function filterRouteTable() {
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
