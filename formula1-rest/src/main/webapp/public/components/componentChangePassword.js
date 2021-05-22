import store from '../../store.js'
import functions from '../../functions.js'


export default {
    name: 'ChangePassword',

    data() {
        return {
            oldpassword: '',
            newpassword: '',
            newpasswordagain: ''
        }
    },

    methods: {
            submitPasswordChange() {
                console.log("Changing password for user: ", store.$username)

                axios.post('http://localhost:8080/pa165/rest/auth/signin', {
                    login: store.$username,
                    password: this.oldpassword
                }, {
                    headers: {
                        "Content-type": "application/json",
                        "Authorization": `Bearer ${store.$jwt}`,
                    }
                })
                    .then(function (response) {

                    })
                    .catch(function (error) {
                        console.log("Error catch", error);

                        functions.showErrorNotification("Old password not valid.")
                    });


                axios.post("http://localhost:8080/pa165/rest/users/password", {
                    login: store.$username,
                    password: this.newpassword
                })
                    .then(function (response){
                        console.log("Successfully changed!", response);

                        functions.showSuccessNotification("Password changed")
                    })
                    .catch(function (error) {
                        console.log("Error catch", error);

                        functions.showErrorNotification("Something went wrong..")
                    });

            },
            logout() {
                store.$jwt = null;
            }
        },

        setup() {
                return {store};
        },


        template: `
                <div class="content">
                <div class="container-fluid">
                  <div class="row">
                    <div class="col-md-6">
                      <div class="card">
                        <div class="card-header card-header-primary">
                          <h4 class="card-title">Change Password</h4>
                        </div>
                        <div class="card-body">
                          <form>
                            <div class="row">
                              <div class="col-md-12">
                                <div class="form-group">
                                  <label class="bmd-label-floating">Old Password</label>
                                  <input type="password" v-model="oldpassword" class="form-control">
                                </div>
                              </div>
                            </div>
                            <div class="row">
                              <div class="col-md-12">
                                <div class="form-group">
                                  <label class="bmd-label-floating">New Password</label>
                                  <input type="password" v-model="newpassword" class="form-control">
                                </div>
                              </div>
                            </div>
                            <div class="row">
                              <div class="col-md-12">
                                <div class="form-group">
                                  <label class="bmd-label-floating">New Password again</label>
                                  <input type="password" v-model="newpasswordagain" class="form-control">
                                </div>
                              </div>
                            </div>

                            <button type="button" @click="submitPasswordChange()" class="btn btn-primary pull-right">Submit</button>
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