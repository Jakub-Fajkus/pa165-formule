import store from '../../store.js'
import functions from '../../functions.js'

export default {
    name: 'User list',

    data() {
        return {
            users: [],
        }
    },

    mounted() {
        this.manualReload();
    },

    setup() {
        return {store};
    },

    methods: {
        manualReload() {
            axios.get('http://localhost:8080/pa165/rest/users', {}, {
                headers: {
                    "Content-type": "application/json",
                    "Authorization": `Bearer ${store.$jwt}`,
                }
            })
                .then(response => {
                    console.log("User response", response);

                    this.users = response.data.data;
                    console.log("USERS:", this.users);

                })
                .catch(function (error) {
                    console.log("Error catch", error);
                    functions.showErrorNotification(error);
                });

        },

        removeUser(userId) {
            console.log("Delete user with id: ", userId)

            axios.delete('http://localhost:8080/pa165/rest/users/' + userId + '', {
                headers: {
                    "Content-type": "application/json",
                    "Authorization": `Bearer ${store.$jwt}`,
                }
            })
            .then(response => {
                console.log("Success:", response);
                functions.showSuccessNotification("User successfully deleted")
                this.manualReload()
            })
            .catch(error => {
                console.log("Error catch ", error);

                functions.showErrorNotification(error)
                this.manualReload()
            });
        },
    },

    template: `
      <div>
        <div class="content">
            <div class="container-fluid">
              <div class="row">
                <div class="col-md-12">
                  <div class="card">
                    <div class="card-header card-header-tabs card-header-primary">
                        <div class="nav-tabs-navigation">
                    <div class="nav-tabs-wrapper">
                      <span class="nav-tabs-title">Users</span>
                      <ul class="nav nav-tabs" data-tabs="tabs">
                        <li class="nav-item">
                          <a class="nav-link active" @click="$emit('new-user-detail')" href="" data-toggle="tab">
                            <i class="material-icons">add</i> New
                            <div class="ripple-container"></div>
                          </a>
                        </li>
                      </ul>
                    </div>
                  </div>
                    </div>
                    <div class="card-body table-responsive">
                        <table class="table table-hover">
                          <thead class="text-primary">
                            <th>
                              ID
                            </th>
                            <th>
                              Login
                            </th>
                            <th>
                              Role
                            </th>
                            <th></th>
                          </thead>
                          <tbody>
                            <template v-for="user, index in users" key="user.surname">
                              <tr>
                                  <td>
                                    {{user.id}}
                                  </td>
                                  <td>
                                    {{user.login}}
                                  </td>
                                  <td>
                                    {{user.role}}
                                  </td>
                                  <td class="td-actions text-right">
                                      <button type="button" @click="$emit('show-user-detail', user.id)" rel="tooltip" title="Edit Task" class="btn btn-primary btn-link btn-sm">
                                        <i class="material-icons">edit</i>
                                      </button>
                                      <button type="button" @click="removeUser(user.id)" rel="tooltip" title="Remove" class="btn btn-danger btn-link btn-sm" v-bind:disabled="user.car">
                                        <i class="material-icons">close</i>
                                      </button>
                                  </td>
                                </tr>
                            </template>
                          </tbody>
                        </table>
                    </div>
                  </div>
                </div>
             </div>
          </div>
        </div>
       </div>
    `,
};
