import store from '../../store.js'
import functions from '../../functions.js'

export default {
    name: 'Component detail',

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

    mounted() {
        console.log("Component detail id: ", this.id);


        axios.get('http://localhost:8080/pa165/rest/components/' + this.id, {
            headers: {
                "Content-type": "application/json",
                "Authorization": `Bearer ${store.$jwt}`,
            }
        })
            .then(response => {
                console.log("All components: ", response);

                this.name = response.data.data.name;

            })
            .catch(error => {
                console.log("Component detail error: ", error);
                functions.showErrorNotification(error)
            });


    },

    methods: {
        validateForm() {
            let success = true;

            this.errors = [];

            if (!this.name) {
                this.errors.name = "Name is required!";
                functions.showWarningNotification(this.errors.name)
                success = false;
            }

            return success;
        },

        editComponent() {
            console.log("Edit component with properties: ", this.name)

            if (!this.validateForm()) {
                return;
            }

            axios.patch('http://localhost:8080/pa165/rest/components/' + this.id, {
                "name": this.name
            }, {
                headers: {
                    "Content-type": "application/json",
                    "Authorization": `Bearer ${store.$jwt}`,
                }
            })
                .then(response => {
                    console.log("Success:", response);

                    this.name = response.data.data.name;

                    this.$emit('go-back');
                    functions.showSuccessNotification("Component successfully edited.")

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
              <h4 class="card-title">Component detail</h4>
            </div>
            <div class="card-body">
              <form>
                <div class="row">
                  <div class="col-md-12">
                    <div class="form-group">
                      <label class="bmd-label-floating">Name <span class="error">{{errors.name}}</span></label>
                      <input type="text" v-model="name" name="name" class="form-control">
                    </div>
                  </div>
                </div>
                
    
                <button type="button" @click="editComponent()" class="btn btn-primary pull-right">Edit</button>
                <div class="clearfix"></div>
              </form>
            </div>
          </div>
    `,
};