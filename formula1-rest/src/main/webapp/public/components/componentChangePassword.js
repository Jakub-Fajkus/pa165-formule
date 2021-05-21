import store from '../../store.js'
import functions from '../../functions.js'


export default {
    name: 'Register',

    data() {
        return {
            login: '',
            password: '',
            password2: ''
        }
    },

    methods: {
            submitRegister(login, password) {
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