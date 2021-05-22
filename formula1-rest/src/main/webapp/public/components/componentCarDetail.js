import store from '../../store.js'
import functions from '../../functions.js'

/**
 * @author Jakub Fajkus
 */
export default {
    name: 'Car detail',

    props: {
        id: Number,
    },

    data() {
        return {
            drivers: [],
            name: null,
            driver: null,
            errors: {}
        }
    },

    setup() {
        return {store};
    },

    mounted() {
        axios.get('http://localhost:8080/pa165/rest/drivers', {
            headers: {
                "Content-type": "application/json",
                "Authorization": `Bearer ${store.$jwt}`,
            }
        })
            .then(response => {
                this.drivers = response.data.data;
            })
            .catch(error => {
                functions.showErrorNotification(error)
            });

        axios.get('http://localhost:8080/pa165/rest/cars/' + this.id, {
            headers: {
                "Content-type": "application/json",
                "Authorization": `Bearer ${store.$jwt}`,
            }
        })
            .then(response => {
                this.name = response.data.data.name;
                this.driver = response.data.data.driver;
            })
            .catch(error => {
                functions.showErrorNotification(error)
            });
    },

    methods: {
        validateForm() {
            let success = true;
            this.errors = {};

            if (!this.name) {
                this.errors.name = "Name is required";
                functions.showWarningNotification("Name is required")

                success = false;
            }
            return success;
        },

        editCar() {
            if (!this.validateForm()) {
                return;
            }

            axios.patch('http://localhost:8080/pa165/rest/cars/' + this.id, {
                "name": this.name,
                "driver": this.driver
            }, {
                headers: {
                    "Content-type": "application/json",
                    "Authorization": `Bearer ${store.$jwt}`,
                }
            })
                .then(response => {
                    this.name = response.data.data.name;
                    this.driver = response.data.data.driver;

                    functions.showSuccessNotification("Car successfully edited")
                })
                .catch(error => {
                    functions.showErrorNotification(error)
                });
        },
    },

    template: `
        <div class="card">
            <div class="card-header card-header-primary">
              <h4 class="card-title">Car detail</h4>
            </div>
            <div class="card-body">
              <form>
                <div class="row">
                  <div class="col-md-12">
                    <div class="form-group">
                      <label class="bmd-label-floating">Name <span class="error">{{errors.name}}</span></label>
                      <input type="text" v-model="name" name="name" class="form-control" required>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-md-12">
                    <div class="form-group">
                      <label class="bmd-label-floating">Driver</label>
                      <select v-model="driver" class="form-control">
                          <option v-for="driverOption in drivers" :value="driverOption.id">
                              {{ driverOption.name }} {{driverOption.surname}}
                          </option>
                      </select>
                    </div>
                  </div>
                </div>
    
                <button type="button" @click="editCar()" class="btn btn-primary pull-right">Edit</button>
                <div class="clearfix"></div>
              </form>
            </div>
          </div>
    `,
};