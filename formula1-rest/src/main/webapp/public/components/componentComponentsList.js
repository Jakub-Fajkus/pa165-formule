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
        axios.get('http://localhost:8080/pa165/rest/components', {}, {
            headers: {
                "Content-type": "application/json",
                "Authorization": `Bearer ${store.$jwt}`,
            }
        })
            .then(response => {
                console.log("Component response is here!", response);

                response.data.data.forEach(component => {
                    console.log("Each component", component);

                    axios.get('http://localhost:8080/pa165/rest/components/'+ component.id, {}, {
                        headers: {
                            "Content-type": "application/json",
                            "Authorization": `Bearer ${store.$jwt}`,
                        }
                    }).then(response => {
                        console.log("Component response", response);

                        this.components.forEach(component => {
                            console.log("For each component: ", component);

                            // if (car.driver == response.data.data.id) {
                            //     car.driver = {id: car.driver, name: response.data.data.surname};
                            //     console.log("Editing car: ", car);
                            // }
                        })
                    }).catch(function (error) {
                        console.log("Error catch", error);

                        functions.showErrorNotification(error);
                    });
                })

                this.components = response.data.data;

                console.log("components:", this.components);



            })
            .catch(function (error) {
                console.log("Error catch, something went wrong Johny", error);
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
                    <div class="card-header card-header-primary">
                      <h4 class="card-title ">
                        <div class="nav-tabs-wrapper">
                      <span class="nav-tabs-title">Components</span>
                      <ul class="nav nav-tabs" data-tabs="tabs">
                        <li class="nav-item">
                          <a class="nav-link active" @click="$emit('add-component')" href="" data-toggle="tab">
                            <i class="material-icons">add</i> New
                            <div class="ripple-container"></div>
                          </a>
                        </li>
                      </ul>
                    </div>
                    </h4>
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
                            <th>
                              
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