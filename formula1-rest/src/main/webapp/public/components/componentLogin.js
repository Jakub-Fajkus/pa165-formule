import store from '../../store.js'
import functions from '../../functions.js'


export default {
    name: 'Login',

    data() {
        return {
            login: '',
            password: ''
        }
    },

    methods: {
        submitLogin(login, password) {
            console.log(login, password)

            axios.post('http://localhost:8080/pa165/rest/auth/signin', {
                login: login,
                password: password
            })
                .then(function (response) {
                    console.log("Success:", response);

                    store.$jwt = response.data.jwt;
                    store.$username = response.data.username;
                    store.$role = response.data.role;

                    console.log(store);

                    functions.showSuccessNotification("Login successful")

                })
                .catch(function (error) {
                    console.log("Error catch", error);

                    functions.showErrorNotification("Bad credentials")
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
                  <h4 class="card-title">Login</h4>
                </div>
                <div class="card-body">
                  <form>
                    <div class="row">
                      <div class="col-md-12">
                        <div class="form-group">
                          <label class="bmd-label-floating">Username</label>
                          <input type="text" v-model="login" name="username" class="form-control">
                        </div>
                      </div>
                    </div>
                    <div class="row">
                      <div class="col-md-12">
                        <div class="form-group">
                          <label class="bmd-label-floating">Password</label>
                          <input type="text" v-model="password" class="form-control">
                        </div>
                      </div>
                    </div>

                    <button type="button" @click="submitLogin(login, password)" class="btn btn-primary pull-right">Login</button>
                    <div class="clearfix"></div>
                  </form>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
<!--      <div class="text-center mt-4">-->
<!--              <button @click="logout" class="btn btn-indigo">LOGOUT</button>-->
<!--            </div>-->

<!--            <p class="h4 text-center mb-4">Sign in</p>-->
<!--            <label for="defaultFormLoginEx" class="grey-text">Your login</label>-->
<!--            <input v-model="login" type="text" id="defaultFormLoginEx" class="form-control"/>-->
<!--            <br/>-->
<!--            <label for="defaultFormLoginPasswordEx" class="grey-text">Your password</label>-->
<!--            <input v-model="password" type="password" id="defaultFormLoginPasswordEx" class="form-control"/>-->
<!--            <div class="text-center mt-4">-->
<!--              <button @click="submitLogin(login, password)" class="btn btn-indigo">Login</button>-->
<!--            </div>-->
<!--          &lt;!&ndash; Default form login &ndash;&gt;-->
    `,
};


