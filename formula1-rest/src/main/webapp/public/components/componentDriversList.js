import store from '../../store.js'
import functions from '../../functions.js'

export default {
    name: 'Driver list',

    data() {
        return {
            drivers: [],
        }
    },

    mounted() {
        axios.get('http://localhost:8080/pa165/rest/drivers', {}, {
            headers: {
                "Content-type": "application/json",
                "Authorization": `Bearer ${store.$jwt}`,
            }
        })
            .then(response => {
                console.log("Driver response", response);

                response.data.data.forEach(driver => {
                    console.log("Each driver", driver);

                    axios.get('http://localhost:8080/pa165/rest/cars/'+driver.car, {}, {
                        headers: {
                            "Content-type": "application/json",
                            "Authorization": `Bearer ${store.$jwt}`,
                        }
                    }).then(response => {
                        console.log("Car response", response);

                        this.drivers.forEach(driver => {
                            console.log("For each driver: ", driver);

                            if (driver.car == response.data.data.id) {
                                driver.car = {id: driver.car, name: response.data.data.name};
                                console.log("Editing driver: ", driver);
                            }
                        })
                    }).catch(function (error) {
                        console.log("Error catch", error);

                        functions.showErrorNotification(error);
                    });
                })

                this.drivers = response.data.data;

                console.log("DRIVERS:", this.drivers);
            })
            .catch(function (error) {
                console.log("Error catch", error);
                functions.showErrorNotification(error);
            });
    },

    setup() {

        return {store};
    },

    template: `
      <div>
        <div class="content">
            <div class="container-fluid">
              <div class="row">
                <div class="col-md-12">
                  <div class="card">
                    <div class="card-header card-header-primary">
                      <h4 class="card-title">Drivers</h4>
                    </div>
                    <div class="card-body">
                      <div class="table-responsive">
                        <table class="table">
                          <thead class="text-primary">
                            <th>
                              ID
                            </th>
                            <th>
                              Name
                            </th>
                            <th>
                              Surname  
                            </th>
                            <th>
                              Nationality
                            </th>
                            <th>
                              Car
                            </th>
                            <th>
                              
                            </th>
                          </thead>
                          <tbody>
                            <template v-for="driver, index in drivers" key="driver.surname">
                              <tr>
                                  <td>
                                    {{driver.id}}
                                  </td>
                                  <td>
                                    {{driver.name}}
                                  </td>
                                  <td>
                                    {{driver.surname}}
                                  </td>
                                  <td>
                                    {{driver.nationality}}
                                  </td>
                                  <td>
                                  <a href="">{{driver.car.id}}</a>
                                    {{driver.car.name}}
                                  </td>
                                  <td>
                                    <button @click="$emit('show-driver-detail', driver.id)" class="btn btn-primary pull-right">Detail</button>
                                  </td>
                                </tr>
                            </template>
                          </tbody>
                        </table>
                      </div>
                    </div>
                  </div>
                </div>
             </div>
          </div>     
        </div>
       </div> 
    `,
};

// <th>
//     Is Aggressive
// </th>
// <th>
//     Wet Driving
// </th>
// <th>
//     Reactions
// </th>
// <td>
//     {{driver.isAggressive}}
// </td>
// <td>
//     {{driver.wetDriving}}
// </td>
// <td>
//     {{driver.reactions}}
// </td>