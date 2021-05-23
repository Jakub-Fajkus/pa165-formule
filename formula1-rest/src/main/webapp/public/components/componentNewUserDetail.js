import store from '../../store.js'
import functions from '../../functions.js'

/**
 * @author Jiri Andrlik
 */
export default {
    name: 'New User',

    props: {
        id: Number,
    },

    data() {
        return {
            login: null,
            password: null,
            role: null,
            errors: {}
        }
    },

    setup() {
        return {store};
    },

    mounted() {
        console.log("New user detail.");
    },

    methods: {
        validateForm() {
            let success = true;

            this.errors = {};

            if (!this.login) {
                this.errors.login = "Login is required";
                functions.showWarningNotification(this.errors.login)
                success = false;
            }

            if (!this.password) {
                this.errors.password = "Temporary password is required";
                functions.showWarningNotification(this.errors.password)
                success = false;
            }

            return success;
        },

        newUser() {
            console.log("New user with properties: ",
                this.login, this.role)

            if (!this.validateForm()) {
                return;
            }

            axios.post('http://localhost:8080/pa165/rest/users/', {
                "login": this.login,
                "password": this.password,
                "role": this.role
            }, {
                headers: {
                    "Content-type": "application/json",
                    "Authorization": `Bearer ${store.$jwt}`,
                }
            })
                .then(response => {
                    console.log("Success:", response);

                    this.login = response.data.data.login;
                    this.password = response.data.data.password;
                    this.role = response.data.data.role;

                    this.$emit("show-users-list");
                    functions.showSuccessNotification("User successfully created.")
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
              <h4 class="card-title">User detail</h4>
            </div>
            <div class="card-body">
              <form>
                <div class="row">
                  <div class="col-md-12">
                    <div class="form-group">
                      <label class="bmd-label-floating">Login <span class="error">{{errors.login}}</span></label>
                      <input type="text" v-model="login" name="login" class="form-control">
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-md-12">
                    <div class="form-group">
                      <label class="bmd-label-floating">Password <span class="error">{{errors.password}}</span></label>
                      <input type="password" v-model="password" name="password" class="form-control">
                    </div>
                  </div>
                </div>
                <div class="row">
                  <div class="col-md-12">
                    <div class="form-group">
                      <label class="bmd-label-floating">Role <span class="error">{{errors.role}}</span></label>
                        <select v-model="role" name="role" class="form-control">
                            <option value="MANAGER">Manager</option>
                            <option value="ENGINEER">Engineer</option>
                        </select>
                    </div>
                  </div>
                </div>
                    <button type="button" @click="newUser()" class="btn btn-primary pull-right">New</button>
                <div class="clearfix"></div>
              </form>
            </div>
          </div>
    `,
};