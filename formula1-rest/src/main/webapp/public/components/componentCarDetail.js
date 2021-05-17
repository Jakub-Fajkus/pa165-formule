import store from '../../store.js'
import functions from '../../functions.js'

export default { //todo: hide somehow from the menu
    name: 'Car detail',

    props: {
        id: Number,
    },

    data() {
        return {
            drivers: [],
            name: null,
            driver: null,
        }
    },

    setup() {
        return {store};
    },

    mounted() {
        console.log("Car detail id: ", this.id);

        axios.get('http://localhost:8080/pa165/rest/drivers', {}, {
            headers: {
                "Content-type": "application/json",
                "Authorization": `Bearer ${store.$jwt}`,
            }
        })
            .then(response => {
                console.log("All drivers: ", response);

                this.drivers = response.data.data;

                functions.showSuccessNotification("Driver successfully edited")
            })
            .catch(error => {
                console.log("Car detail error: ", error);
                functions.showErrorNotification(error)
            });

        axios.get('http://localhost:8080/pa165/rest/cars/' + this.id, {}, {
            headers: {
                "Content-type": "application/json",
                "Authorization": `Bearer ${store.$jwt}`,
            }
        })
            .then(response => {
                console.log("Car detail: ", response);

                this.name = response.data.data.name;
                this.driver = response.data.data.driver;
            })
            .catch(error => {
                console.log("Car detail error: ", error);
                functions.showErrorNotification(error)
            });
    },

    methods: {
        editCar() {
            console.log("Edit car with properties: ", this.name, this.driver)

            axios.patch('http://localhost:8080/pa165/rest/cars/' + this.id + '', {
                "name": this.name,
                "driver": this.driver
            }, {
                headers: {
                    "Content-type": "application/json",
                    "Authorization": `Bearer ${store.$jwt}`,
                }
            })
                .then(response => {
                    console.log("Success:", response);

                    this.name = response.data.data.name;
                    this.driver = response.data.data.driver;

                })
                .catch(error => {
                    console.log("Error catch", error);
                    functions.showErrorNotification(error)
                });
        },
    },


    template: `
        <h1>Car detail with id {{id}}</h1>
        <div class="content">
        <div class="container-fluid">
          <div class="row">
            <div class="col-md-6">
              <div class="card">
                <div class="card-header card-header-primary">
                  <h4 class="card-title">Car detail</h4>
                </div>
                <div class="card-body">
                  <form>
                    <div class="row">
                      <div class="col-md-12">
                        <div class="form-group">
                          <label class="bmd-label-floating">Name</label>
                          <input type="text" v-model="name" name="name" class="form-control">
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
            </div>
          </div>
        </div>
      </div>
    `,
};