import store from '../../store.js'
import functions from '../../functions.js'

export default { //todo: hide somehow from the menu
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
            isAggressive: null,
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

                this.car = response.data.car;
                this.name = response.data.data.name;
                this.surname = response.data.data.surname;
                this.nationality = response.data.nationality;
                this.isAggressive = response.data.isAggressive;
                this.wetDriving = response.data.wetDriving;
                this.reactions = response.data.reactions;
            })
            .catch(error => {
                console.log("Driver detail error: ", error);
                functions.showErrorNotification(error)
            });
    },

    methods: {
        editCar() {
            console.log("Edit car with properties: ",
                        this.car, this.name, this.surname, this.nationality,
                        this.isAggressive, this.wetDriving, this.reactions)

            axios.patch('http://localhost:8080/pa165/rest/drivers/' + this.id + '', {
                "car": this.car,
                "name": this.name,
                "surname": this.surname,
                "nationality": this.nationality,
                "isAggressive": this.isAggressive,
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
                    this.name = response.data.data.data.name;
                    this.surname = response.data.data.data.surname;
                    this.nationality = response.data.data.nationality;
                    this.isAggressive = response.data.data.isAggressive;
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
                      <label class="bmd-label-floating">Name</label>
                      <input type="text" v-model="name" name="name" class="form-control">
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-md-12">
                    <div class="form-group">
                      <label class="bmd-label-floating">Surname</label>
                      <input type="text" v-model="surname" name="surname" class="form-control">
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-md-12">
                    <div class="form-group">
                      <label class="bmd-label-floating">Nationality</label>
                      <input type="text" v-model="nationality" name="nationality" class="form-control">
                    </div>
                  </div>
                </div>
                <!--udelat jako zatrzitko-->
                <div class="row">
                  <div class="col-md-12">
                    <div class="form-group">
                      <label class="bmd-label-floating">Is Aggressive</label>
                      <input type="checkbox" v-model="isAggressive" name="isAggressive" class="form-control">
                    </div>
                  </div>
                </div>
                <!--pridat hranice pro input hodnoty-->
                <div class="row">
                  <div class="col-md-12">
                    <div class="form-group">
                      <label class="bmd-label-floating">Wet Driving</label>
                      <input type="range" min="0" max="10" v-model="wetDriving" name="wetDriving" class="form-control">
                    </div>
                  </div>
                </div>
                <!--pridat hranice pro input hodnoty-->
                <div class="row">
                  <div class="col-md-12">
                    <div class="form-group">
                      <label class="bmd-label-floating">Reactions</label>
                      <input type="range" min="0" max="10" v-model="reactions" name="reactions" class="form-control">
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