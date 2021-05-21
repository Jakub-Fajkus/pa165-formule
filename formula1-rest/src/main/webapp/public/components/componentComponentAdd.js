import store from '../../store.js'
import functions from '../../functions.js'

export default {
    name: 'Component add',

    props: {
        id: Number,
    },

    data() {
        return {
            name: null,
            type: null,
        }
    },

    setup() {
        return {store};
    },

    methods: {
        addComponent() {
            console.log("Add component")

            axios.put('http://localhost:8080/pa165/rest/components/', {
                "name": this.name,
                "type": this.type,
            }, {
                headers: {
                    "Content-type": "application/json",
                    "Authorization": `Bearer ${store.$jwt}`,
                }
            })
                .then(response => {
                    console.log("Success:", response);

                    this.name = response.data.data.name;

                    this.$emit('add-component');
                    functions.showSuccessNotification("Component successfully created")

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
              <h4 class="card-title">Add component</h4>
            </div>
            
            <div class="card-body">
              <form>
                <div class="row">
                  <div class="col-md-12">
                    <div class="form-group">
                      <label class="bmd-label-floating">Name</label>
                      <input type="text" class="form-control" v-model="name">
                    </div>
                  </div>
                </div>
                
            
            <div class="form-group">
               <label class="bmd-label-floating">Type</label>
                  <select class="form-control" v-model="type">
                    <option value="0">Engine</option>
                    <option value="1">Suspension</option>
                    <option value="2">Transmission</option>
                    <option value="3">Rims</option>
                    <option value="4">Rear spoiler</option>
                  </select>
                </div>
                
 
                 
                <button type="button" @click="addComponent()" class="btn btn-primary pull-right">Add</button>
                <div class="clearfix"></div>
              </form>
            </div>
          </div>
    `,
};