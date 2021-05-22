import store from '../../store.js'
import functions from '../../functions.js'

export default {
    name: 'Component list',

    data() {
        return {
            components: [],
        }
    },

    mounted() {
        axios.get('http://localhost:8080/pa165/rest/components', {
            headers: {
                "Content-type": "application/json",
                "Authorization": `Bearer ${store.$jwt}`,
            }
        })
            .then(response => {
                response.data.data.forEach(component => {
                    axios.get('http://localhost:8080/pa165/rest/components/'+ component.id, {
                        headers: {
                            "Content-type": "application/json",
                            "Authorization": `Bearer ${store.$jwt}`,
                        }
                    }).catch(function (error) {
                        functions.showErrorNotification(error);
                    });
                })
                this.components = response.data.data;

                })
            .catch(function (error) {
                functions.showErrorNotification(error);
            });
    },

    setup() {
        return {store};
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
                        <h4 class="nav-tabs-title">Components</h4>
                          <ul class="nav nav-tabs" data-tabs="tabs">
                            <li class="nav-item">
                              <a class="nav-link active" @click="$emit('add-component')" href="" data-toggle="tab">
                                <i class="material-icons">add</i> New
                                <div class="ripple-container"></div>
                              </a>
                            </li>
                          </ul>
                        </div>
                      </div>
                    </div>
                    <div class="card-body">
                      <div class="table-responsive">
                        <table class="table">
                          <thead class="text-primary">
                            <th>
                              Name
                            </th>
                            <th>
                              Component type
                           </th>
                          </thead>
                          <tbody>
                            <template v-for="component, index in components" key="component.name">
                              <tr>
                                <td>
                                {{component.name}}
                                </td>
                                <td>
                                {{component.type}}
                                </td>
                                <td>
                                <button @click="$emit('show-component-detail', component.id)" class="btn btn-primary pull-right">Detail</button>
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
      </div>
    `,
};