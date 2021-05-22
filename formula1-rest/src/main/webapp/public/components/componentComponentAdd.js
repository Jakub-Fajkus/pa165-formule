import store from '../../store.js'
import functions from '../../functions.js'

/**
 * @author Tomas Sedlacek
 */
export default {
    name: 'Component add',

    props: {
        id: Number,
    },

    data() {
        return {
            name: null,
            type: null,
            errors: {}
        }
    },

    setup() {
        return {store};
    },

    methods: {
        validateForm() {
            let success = true;
            this.errors = [];

            if (!this.name) {
                this.errors.name = "Name required!";
                functions.showWarningNotification(this.errors.name)
                success = false;
            }

            if (!this.type) {
                this.errors.type = "Type required!";
                functions.showWarningNotification(this.errors.type)
                success = false;
            }
            return success;
        },

        addComponent() {
            if (!this.validateForm()) {
                return;
            }

            axios.post('http://localhost:8080/pa165/rest/components/', {
                "name": this.name,
                "type": this.type,
            }, {
                headers: {
                    "Content-type": "application/json",
                    "Authorization": `Bearer ${store.$jwt}`,
                }
            })
                .then(response => {
                    this.$emit('go-back');
                    functions.showSuccessNotification("Component successfully created")
                })
                .catch(error => {
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
                      <label class="bmd-label-floating">Name <span class="error">{{errors.name}}</span></label>
                      <input type="text" class="form-control" v-model="name">
                    </div>
                  </div>
                </div>
                
                <div class="form-group">
                   <label class="bmd-label-floating">Type <span class="error">{{errors.type}}</span></label>
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