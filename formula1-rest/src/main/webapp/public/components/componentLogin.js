import store from '../../store.js'


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

                    console.log(store.$jwt);
                })
                .catch(function (error) {
                    console.log("Error catch", error);
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
            <div class="text-center mt-4">
              <button @click="logout" class="btn btn-indigo">LOGOUT</button>
            </div>

            <p class="h4 text-center mb-4">Sign in</p>
            <label for="defaultFormLoginEx" class="grey-text">Your login</label>
            <input v-model="login" type="text" id="defaultFormLoginEx" class="form-control"/>
            <br/>
            <label for="defaultFormLoginPasswordEx" class="grey-text">Your password</label>
            <input v-model="password" type="password" id="defaultFormLoginPasswordEx" class="form-control"/>
            <div class="text-center mt-4">
              <button @click="submitLogin(login, password)" class="btn btn-indigo">Login</button>
            </div>
          <!-- Default form login -->
    `,
};