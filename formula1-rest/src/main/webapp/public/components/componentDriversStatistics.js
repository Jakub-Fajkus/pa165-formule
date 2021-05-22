import store from '../../store.js'
import functions from "../../functions.js";

/**
 * @author Jakub Fajkus
 */
export default {
    name: 'Drivers statistics',


    mounted() {
        axios.get('http://localhost:8080/pa165/rest/drivers/score', {
            headers: {
                "Content-type": "application/json",
                "Authorization": `Bearer ${store.$jwt}`,
            }
        })
            .then(response => {
                response.data.data.map(driver => {
                    return driver.firstName + " " + driver.lastName;
                });

                let dataDailySalesChart = {
                    labels: response.data.data.map(driver => {
                        return driver.firstName + " " + driver.lastName;
                    }),
                    series: [
                        response.data.data.map(driver => {
                            return driver.score;
                        })
                    ]
                };

                let optionsDailySalesChart = {
                    lineSmooth: Chartist.Interpolation.cardinal({
                        tension: 0
                    }),
                    low: 0,
                    high: 100,
                    chartPadding: {
                        top: 0,
                        right: 0,
                        bottom: 0,
                        left: 0
                    },
                }

                new Chartist.Bar('#websiteViewsChart', dataDailySalesChart, optionsDailySalesChart);
            })
            .catch(error => {
                functions.showErrorNotification(error)
            });



        return {store};
    },


    template: `
        <div class="content">
        <div class="container-fluid">
          <div class="row">
            <div class="col-md-8">
              <div class="card card-chart">
                <div class="card-header card-header-primary">
                  <div class="ct-chart" id="websiteViewsChart">
                    <!--tu je graf! :trollface:-->
                  </div>
                </div>
                <div class="card-body">
                  <h4 class="card-title">Drivers score</h4>
                  <p class="card-category">Drivers scored based on their properties. Higher is better.</p>
                </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    `,
};


