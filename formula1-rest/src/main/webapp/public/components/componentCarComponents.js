import store from '../../store.js'
import functions from '../../functions.js'

/**
 * @author Jakub Fajkus
 */
export default {
    name: 'Car components',

    props: {
        id: Number,
    },

    data() {
        return {
            engine: null,
            suspension: null,
            transmission: null,
            rims: null,
            rearspoiler: null,

            allEngines: [''],
            allSuspensions: [''],
            allTransmissions: [''],
            allRims: [''],
            allRearspoilers: [''],
        }
    },

    setup() {
        return {store};
    },

    mounted() {
        console.log("Car components for id: ", this.id);

        axios.get('http://localhost:8080/pa165/rest/cars/' + this.id + "/components", {
            headers: {
                "Content-type": "application/json",
                "Authorization": `Bearer ${store.$jwt}`,
            }
        })
            .then(response => {
                console.log("Car components: ", response);
                response.data.data.forEach(component => {
                    console.log(component);

                    if (component.type == "ENGINE") {
                        this.engine = component.id;
                    } else if (component.type == "SUSPENSION") {
                        this.suspension = component.id;
                    } else if (component.type == "TRANSMISSION") {
                        this.transmission = component.id;
                    } else if (component.type == "RIMS") {
                        this.rims = component.id;
                    } else if (component.type == "REARSPOILER") {
                        this.rearspoiler = component.id;
                    } else {
                        functions.showErrorNotification("unknown component type " + component.type);
                    }

                    console.log(this.engine, this.suspension, this.transmission, this.rims, this.rearspoiler);
                });
            })
            .catch(error => {
                console.log("Car components error: ", error);
                functions.showErrorNotification(error)
            });

        axios.get('http://localhost:8080/pa165/rest/components', {
            headers: {
                "Content-type": "application/json",
                "Authorization": `Bearer ${store.$jwt}`,
            }
        })
            .then(response => {
                console.log("All components: ", response);
                response.data.data.forEach(component => {
                    console.log(component);

                    if (component.car != null && component.car != this.id) {
                        return;
                    }

                    if (component.type == "ENGINE") {
                        this.allEngines.push(component);
                    } else if (component.type == "SUSPENSION") {
                        this.allSuspensions.push(component);
                    } else if (component.type == "TRANSMISSION") {
                        this.allTransmissions.push(component);
                    } else if (component.type == "RIMS") {
                        this.allRims.push(component);
                    } else if (component.type == "REARSPOILER") {
                        this.allRearspoilers.push(component);
                    } else {
                        functions.showErrorNotification("unknown component type " + component.type);
                    }

                    console.log(this.allEngines, this.allSuspensions, this.allTransmissions, this.allRims, this.allRearspoilers);
                });
            })
            .catch(error => {
                console.log("All components error: ", error);
                functions.showErrorNotification(error)
            });
    },

    methods: {
        editCar() {
            console.log("Edit car components")
            let newComponents = [];

            if (this.engine) {
                newComponents.push(this.engine)
            }

            if (this.transmission) {
                newComponents.push(this.transmission)
            }

            if (this.rims) {
                newComponents.push(this.rims)
            }

            if (this.suspension) {
                newComponents.push(this.suspension)
            }
            if (this.rearspoiler) {
                newComponents.push(this.rearspoiler)
            }

            console.log(newComponents);
            axios.put('http://localhost:8080/pa165/rest/cars/' + this.id + '/components', newComponents, {
                headers: {
                    "Content-type": "application/json",
                    "Authorization": `Bearer ${store.$jwt}`,
                }
            })
                .then(response => {
                    console.log("Success:", response);

                    functions.showSuccessNotification("Car assembled successfully")
                    this.$forceUpdate();
                })
                .catch(error => {
                    console.log("Error catch", error);
                    functions.showErrorNotification(error)
                    this.$forceUpdate();
                });
        },
    },


    template: `
        <div class="content">
        <div class="container-fluid">
          <div class="row">
            <div class="col-md-12">
              <div class="card">
                <div class="card-header card-header-primary">
                  <h4 class="card-title">Car components</h4>
                </div>
                <div class="card-body">
                  <form>
                    <div class="row">
                      <div class="col-md-12">
                        <div class="form-group">
                          <label class="bmd-label-floating">Engine</label>
                          <select v-model="engine" class="form-control">
                              <option v-for="engineOption in allEngines" :value="engineOption.id">
                                  {{ engineOption.name }}
                              </option>
                          </select>
                        </div>
                      </div>
                    </div>
                    <div class="row">
                      <div class="col-md-12">
                        <div class="form-group">
                          <label class="bmd-label-floating">Suspension</label>
                          <select v-model="suspension" class="form-control">
                              <option v-for="suspensionOption in allSuspensions" :value="suspensionOption.id">
                                  {{ suspensionOption.name }}
                              </option>
                          </select>
                        </div>
                      </div>
                    </div>
                    <div class="row">
                      <div class="col-md-12">
                        <div class="form-group">
                          <label class="bmd-label-floating">Rims</label>
                          <select v-model="rims" class="form-control">
                              <option v-for="rimsOption in allRims" :value="rimsOption.id">
                                  {{ rimsOption.name }}
                              </option>
                          </select>
                        </div>
                      </div>
                    </div>
                    <div class="row">
                      <div class="col-md-12">
                        <div class="form-group">
                          <label class="bmd-label-floating">Transmissions</label>
                          <select v-model="transmission" class="form-control">
                              <option v-for="transmissionOption in allTransmissions" :value="transmissionOption.id">
                                  {{ transmissionOption.name }}
                              </option>
                          </select>
                        </div>
                      </div>
                    </div>
                    <div class="row">
                      <div class="col-md-12">
                        <div class="form-group">
                          <label class="bmd-label-floating">Rearspoilers</label>
                          <select v-model="rearspoiler" class="form-control">
                              <option v-for="rearspoilerOption in allRearspoilers" :value="rearspoilerOption.id">
                                  {{ rearspoilerOption.name }}
                              </option>
                          </select>
                        </div>
                      </div>
                    </div>
                    <button type="button" @click="editCar()" class="btn btn-primary pull-right">Assemble car</button>
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