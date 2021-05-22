import store from '../../store.js'
import functions from '../../functions.js'

/**
 * @author Jakub Fajkus
 */
export default {
    name: 'Car list',

    data() {
        return {
            cars: [],
        }
    },

    mounted() {
        axios.get('http://localhost:8080/pa165/rest/cars', {
            headers: {
                "Content-type": "application/json",
                "Authorization": `Bearer ${store.$jwt}`,
            }
        })
            .then(response => {
                response.data.data.forEach(car => {
                    axios.get('http://localhost:8080/pa165/rest/drivers/'+car.driver, {
                        headers: {
                            "Content-type": "application/json",
                            "Authorization": `Bearer ${store.$jwt}`,
                        }
                    }).then(response => {
                        this.cars.forEach(car => {
                            if (car.driver == response.data.data.id) {
                                car.driver = {id: car.driver, name: response.data.data.surname};
                                console.log("Editing car: ", car);
                            }
                        })
                    }).catch(function (error) {
                        functions.showErrorNotification(error);
                    });
                })

                this.cars = response.data.data;
            })
            .catch(function (error) {
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
                      <h4 class="card-title ">Cars</h4>
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
                              Driver  
                            </th>
                            <th>
                              
                            </th>
                          </thead>
                          <tbody>
                            <template v-for="car, index in cars" key="car.name">
                              <tr>
                                  <td>
                                    {{car.id}}
                                  </td>
                                  <td>
                                    {{car.name}}
                                  </td>
                                  <td>
                                    {{car.driver.name}}
                                  </td>
                                  <td>
                                    <button @click="$emit('show-car-detail', car.id)" class="btn btn-primary pull-right">Detail</button>
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