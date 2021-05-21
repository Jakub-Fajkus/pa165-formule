import store from '../../store.js'
import functions from '../../functions.js'

export default {
    name: 'Driver detail',

    props: {
        id: Number,
    },

    data() {
        return {
            cars: [],
            car: null,
            name: null,
            surname: null,
            nationality: null,
            aggressive: null,
            wetDriving: null,
            reactions: null,
        }
    },

    setup() {
        return {store};
    },

    mounted() {
        console.log("Driver detail id: ", this.id);

        axios.get('http://localhost:8080/pa165/rest/cars', {}, {
            headers: {
                "Content-type": "application/json",
                "Authorization": `Bearer ${store.$jwt}`,
            }
        })
            .then(response => {
                console.log("All cars: ", response);

                this.cars = response.data.data;
            })
            .catch(error => {
                console.log("Driver detail error: ", error);
                functions.showErrorNotification(error)
            });

        axios.get('http://localhost:8080/pa165/rest/drivers/' + this.id, {}, {
            headers: {
                "Content-type": "application/json",
                "Authorization": `Bearer ${store.$jwt}`,
            }
        })
            .then(response => {
                console.log("Driver detail: ", response);

                this.car = response.data.data.car;
                this.name = response.data.data.name;
                this.surname = response.data.data.surname;
                this.nationality = response.data.data.nationality;
                this.aggressive = response.data.data.aggressive;
                this.wetDriving = response.data.data.wetDriving;
                this.reactions = response.data.data.reactions;
            })
            .catch(error => {
                console.log("Driver detail error: ", error);
                functions.showErrorNotification(error)
            });
    },

    methods: {
        validateForm() {
            let success = true;

            this.errors = {};

            if (!this.name) {
                this.errors.name = "Name is required";
                success = false;
            }

            if (!this.surname) {
                this.errors.surname = "Surname is required";
                success = false;
            }

            if (!this.nationality) {
                this.errors.nationality = "nationality is required";
                success = false;
            }

            return success;
        },

        editDriver() {
            console.log("Edit car with properties: ",
                        this.car, this.name, this.surname, this.nationality,
                        this.aggressive, this.wetDriving, this.reactions)

            if (!this.validateForm()) {
                return;
            }

            axios.patch('http://localhost:8080/pa165/rest/drivers/' + this.id + '', {
                "car": this.car,
                "name": this.name,
                "surname": this.surname,
                "nationality": this.nationality,
                "aggressive": this.aggressive,
                "wetDriving": this.wetDriving,
                "reactions": this.reactions
            }, {
                headers: {
                    "Content-type": "application/json",
                    "Authorization": `Bearer ${store.$jwt}`,
                }
            })
                .then(response => {
                    console.log("Success:", response);

                    this.car = response.data.data.car;
                    this.name = response.data.data.name;
                    this.surname = response.data.data.surname;
                    this.nationality = response.data.data.nationality;
                    this.aggressive = response.data.data.aggressive;
                    this.wetDriving = response.data.data.wetDriving;
                    this.reactions = response.data.data.reactions;

                    functions.showSuccessNotification("Driver successfully edited")
                })
                .catch(error => {
                    console.log("Error catch", error);
                    functions.showErrorNotification(error)
                });
        },
    },


    template: `
        <div class="card">
            <div class="card-header card-header-primary">
              <h4 class="card-title">Driver detail</h4>
            </div>
            <div class="card-body">
              <form>
                <div class="row">
                  <div class="col-md-12">
                    <div class="form-group">
                      <label class="bmd-label-floating">Car</label>
                      <select v-model="car" class="form-control">
                          <option></option>
                          <option v-for="carOption in cars" :value="carOption.id">
                              {{carOption.name }}
                          </option>
                      </select>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-md-12">
                    <div class="form-group">
                      <label class="bmd-label-floating">Name <span class="error">{{errors.name}}</span></label>
                      <input type="text" v-model="name" name="name" class="form-control">
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-md-12">
                    <div class="form-group">
                      <label class="bmd-label-floating">Surname <span class="error">{{errors.surname}}</span></label>
                      <input type="text" v-model="surname" name="surname" class="form-control">
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-md-12">
                    <div class="form-group">
                      <label class="bmd-label-floating">Nationality <span class="error">{{errors.nationality}}</span></label>
                      <input type="text" v-model="nationality" name="nationality" class="form-control">
                    </div>
                  </div>
                </div>
                <div class="row">
                    <div class="col-md-8">
                        <td>
                          <div class="form-check">
                            <label class="form-check-inline">Is Aggresive? &nbsp;&nbsp;
                              <input class="form-check-input" type="checkbox" v-model="aggressive" name="aggressive">
                              <span class="form-check-sign">
                                <span class="check"></span>
                              </span>
                            </label>
                          </div>
                        </td>
                    </div>
                </div>
                <div class="row">
                  <div class="col-md-12">
                    <div class="form-group">
                      <!-- popis soupatek -->
                      <div class="form-control-range">
                        <label class="bmd-label-floating">Wet Driving</span></label>
                        <input type="range" min="0" max="10" step="1" v-model="wetDriving" name="wetDriving" class="form-control">
                        <label>{{wetDriving}}</label>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-md-12">
                    <div class="form-group">
                      <label class="bmd-label-floating">Reactions</span></label>
                      <input type="range" min="0" max="10" v-model="reactions" name="reactions" class="form-control">
                      <label>{{reactions}}</label>
                    </div>
                  </div>
                </div>
                    <button type="button" @click="editDriver()" class="btn btn-primary pull-right">Edit</button>
                <div class="clearfix"></div>
              </form>
            </div>
          </div>
    `,
};