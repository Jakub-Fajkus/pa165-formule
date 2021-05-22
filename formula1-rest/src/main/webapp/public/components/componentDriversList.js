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
        this.manualReload();
    },

    setup() {

        return {store};
    },

    methods: {
        manualReload() {
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

                        if (!driver.car) {
                            return;
                        }

                        axios.get('http://localhost:8080/pa165/rest/cars/'+driver.car, {}, {
                            headers: {
                                "Content-type": "application/json",
                                "Authorization": `Bearer ${store.$jwt}`,
                            }
                        }).then(response => {
                            console.log("Car response", response);

                            this.drivers.forEach(driver => {
                                console.log("For each driver: ", driver);

                                console.log(response.data.data.id)
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

        removeDriver(driverId) {
            console.log("Delete driver with id: ", driverId)

            axios.delete('http://localhost:8080/pa165/rest/drivers/' + driverId + '', {
                headers: {
                    "Content-type": "application/json",
                    "Authorization": `Bearer ${store.$jwt}`,
                }
            })
                .then(response => {
                    console.log("Success:", response);

                    functions.showSuccessNotification("Driver successfully deleted")
                    this.manualReload()
                })
                .catch(error => {
                    console.log("Error catch", error);

                    functions.showErrorNotification(error)
                    this.manualReload()
                });
        },
    },

    template: `
      <div>
        <div class="content">
            <div class="container-fluid">
              <div class="row">
                <div class="col-md-12">
                  <div class="card">
                    <div class="card-header card-header-tabs card-header-primary">
                        <div class="nav-tabs-navigation">
                    <div class="nav-tabs-wrapper">
                      <span class="nav-tabs-title">Drivers</span>
                      <ul class="nav nav-tabs" data-tabs="tabs">
                        <li class="nav-item">
                          <a class="nav-link active" @click="$emit('new-driver-detail')" href="" data-toggle="tab">
                            <i class="material-icons">add</i> New
                            <div class="ripple-container"></div>
                          </a>
                        </li>
                      </ul>
                    </div>
                  </div>
                    </div>
                    <div class="card-body table-responsive">
                        <table class="table table-hover">
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
                              Is Aggressive
                            </th>
                            <th>
                              Wet Driving
                            </th>
                            <th>
                              Reactions
                            </th>
                            <th></th>
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
                                  <div v-if="driver.car">
                                  <a href="">{{driver.car.id}}</a>
                                    {{driver.car.name}}
                                  </div>
                                  </td>
                                  <td>
                                      <div class="form-check">
                                        <label class="form-check-label">
                                          <input class="form-check-input" type="checkbox" v-model="driver.aggressive" name="aggressive" disabled>
                                          <span class="form-check-sign">
                                            <span class="check"></span>
                                          </span>
                                        </label>
                                      </div>
                                  </td>
                                  <td>
                                    {{driver.wetDriving}}
                                  </td>
                                  <td>
                                    {{driver.reactions}}
                                  </td>
                                  <td class="td-actions text-right">
                                      <button type="button" @click="$emit('show-driver-detail', driver.id)" rel="tooltip" title="Edit Task" class="btn btn-primary btn-link btn-sm">
                                        <i class="material-icons">edit</i>
                                      </button>
                                      <button type="button" @click="removeDriver(driver.id)" rel="tooltip" title="Remove" class="btn btn-danger btn-link btn-sm" v-bind:disabled="driver.car">
                                        <i class="material-icons">close</i>
                                      </button>
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
    `,
};