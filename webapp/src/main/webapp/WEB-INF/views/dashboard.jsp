<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<div class="container h-100">
    <div class="h-100 row align-items-center justify-content-center">
        <div class="col-12 mb-5 mb-md-0 row">
            <h1 class="py-2 pb-lg-3 mb-3">Dashboard</h1>

            <div class="row flex-wrap col-lg-12 justify-content-between align-items-center">
                <div class="card col-lg-5 col-sm-12">
                    <div class="card-body">
                        <div class="card-title">Meeter Distribution</div>
                        <div>
                            <canvas id="meeters-ratio"></canvas>
                        </div>
                    </div>
                </div>

                <div class="card col-lg-5 col-sm-12">
                    <div class="card-body">
                        <div class="card-title">Reports Ratio</div>
                        <div>
                            <canvas id="reports-ratio"></canvas>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
