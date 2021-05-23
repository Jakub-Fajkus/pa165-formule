import homepage from './public/pages/home.js'
import * as pages from './public/pages/index.js'
import store from './store.js'
import functions from './functions.js'

/**
 * @author Jakub Fajkus, Tomas Sedlacek, Karolina Hecova, Jiri Andrlik
 */
export default {
    name: 'App',
    components: pages,

    data() {
        return {
            page: null,
            pageParams: {},
            role: store.$role,
        }
    },

    computed: {
        currentRole() {
            // `this` points to the vm instance
            return store.$role;
        }
    },

    methods: {
        tabClicked(index) {
            console.log("tabClicked", index)
            console.log("JWT:", store.$jwt, store.$username, store.$role);
            if (store.$jwt == null) {
                this.page = "pageLogin";
            } else {
                console.log("NULL?:", store.$jwt);
                if (store.$role != "ROLE_MANAGER") {
                    let whitelist = ["pageLogin", "pageComponentAdd", "pageComponentDetail", "pageComponentList", "pageUsersList"];

                    if (whitelist.indexOf(index) !== -1) {/* in whitelist*/
                        this.page = index;
                    } else { /*restricted*/
                        this.page = "pageLogin";
                        functions.showErrorNotification("Unauthorized access. Please login into a manager account.")
                    }

                } else {
                    this.page = index;
                }
            }
        },

        onShowCarDetail(carId) {
            console.log("parent onShowCarDetail " + carId);

            this.page = "pageCarDetail";
            this.pageParams = {id: carId};
        },

        onShowComponentDetail(componentId) {
            console.log("parent onShowComponentDetail " + componentId);

            this.page = "pageComponentDetail";
            this.pageParams = {id: componentId};
        },


        onAddComponent() {
            console.log("parent addComponent ");

            this.page = "pageComponentAdd";
        },

        onGoToList() {
            this.page = "pageComponentList";
        },

        onShowDriverDetail(driverId) {
            console.log("parent onShowDriverDetail " + driverId);

            this.page = "pageDriverDetail";
            this.pageParams = {id: driverId};
        },

        onNewDriverDetail() {
            console.log("parent onShowDriverDetail ");

            this.page = "pageNewDriverDetail";
        },

        onShowDriversList() {
            console.log("parent onShowDriversList ");

            this.page = "pageDriversList";
        },

        handleLogout() {
            console.log("logging out...")
            store.$jwt = null;
            store.$role = null;
            store.$username = null;
            this.page = "pageLogin";
        },

        onShowUserDetail(userId) {
            console.log("parent onShowUserDetail " + userId);

            this.page = "pageUserDetail";
            this.pageParams = {id: userId};
        },

        onNewUserDetail() {
            console.log("parent onShowUserDetail ");

            this.page = "pageNewUserDetail";
        },

        onShowUsersList() {
            console.log("parent onShowUsersList ");

            this.page = "pageUsersList";
        },

        onSubmitPasswordChange(userLogin) {
            console.log("parent onSubmitPasswordChange ");

            this.page = "submitPasswordChange";
            this.pageParams = {login: userLogin};
        }
    },


    setup() {
        const {watchEffect, onMounted, ref} = Vue;
        const page = ref(null);

        //store management: save $variables to localstorage
        onMounted(() => {
            window.addEventListener('beforeunload', () => {
                Object.keys(store).forEach(function (key){
                    if (key.charAt(0) == "$") {localStorage.setItem(key, store[key]); } else {localStorage.removeItem("$" + key);}
                });
            });
            Object.keys(store).forEach(function (key){
                if (key.charAt(0) == "$") {
                    if (localStorage.getItem(key)) {
                        if (localStorage.getItem(key) === 'null') {
                            store[key] = null;
                        } else {
                            store[key] = localStorage.getItem(key);

                        }
                    }
                }}
            )
        })

        //url management
        watchEffect(() => {
            let urlpage = window.location.pathname.split("/").pop();

            if (page.value == null) {page.value = urlpage}
            // if (page.value != urlpage) {const url = page.value ? page.value : './'; window.history.pushState({url: url}, '', url);                                }
            // window.onpopstate = function() {page.value = window.location.pathname.split("/").pop()};
        })
        return {page, pages, store}
    },

    mounted() {
        this.tabClicked('homepage');
    },

    template: `

        <div class="sidebar" data-color="green" data-background-color="black">
      <!--
      Tip 1: You can change the color of the sidebar using: data-color="purple | azure | green | orange | danger"

      Tip 2: you can also add an image using data-image tag
  -->

      <div class="sidebar-wrapper">
        <ul class="nav">
          
          <template v-for="item, index in pages" key="item.name">
              <li class="nav-item " v-if="index == 'pageLogin' || item.showInMenu && item.requiredRoles && item.requiredRoles.indexOf(currentRole) !== -1">
                <a @click="tabClicked(index)" class="nav-link" href="#0">
                  <i class="material-icons">{{ item.icon }}</i>
                  <p>{{ item.name }}</p>
                </a>
              </li>
          </template>
          
          <!-- your sidebar here -->
        </ul>
      </div>
    </div>
    <div class="main-panel">
      <!-- Navbar -->
      <nav class="navbar navbar-expand-lg navbar-transparent navbar-absolute fixed-top ">
        <div class="container-fluid">
          <button class="navbar-toggler" type="button" data-toggle="collapse" aria-controls="navigation-index" aria-expanded="false" aria-label="Toggle navigation">
            <span class="sr-only">Toggle navigation</span>
            <span class="navbar-toggler-icon icon-bar"></span>
            <span class="navbar-toggler-icon icon-bar"></span>
            <span class="navbar-toggler-icon icon-bar"></span>
          </button>
          <div class="collapse navbar-collapse justify-content-end">
            <ul class="navbar-nav">
              <li class="nav-item">
                <a class="nav-link" href="javascript:;" @click="handleLogout()">
                  <i class="material-icons">logout</i>
                  <p class="d-lg-none d-md-block">
                    Log out
                  </p>
              </a>
              </li>
              <!-- your navbar here -->
            </ul>
          </div>
        </div>
      </nav>
      <!-- End Navbar -->
      <div class="content">
        <div class="container-fluid">
          <!-- your content here -->
          <component :is="page" @show-car-detail="onShowCarDetail" 
                                @add-component="onAddComponent" 
                                @show-component-detail="onShowComponentDetail" 
                                @go-to-component-list="onGoToList" 
                                @show-driver-detail="onShowDriverDetail" 
                                @new-driver-detail="onNewDriverDetail" 
                                @show-drivers-list="onShowDriversList"
                                @show-user-detail="onShowUserDetail"
                                @new-user-detail="onNewUserDetail"
                                @show-users-list="onShowUsersList"
                                @submit-user-password="onSubmitPasswordChange" :pageParams="pageParams"></component>
        </div>
      </div>
      <footer class="footer">
        <div class="container-fluid">
          <nav class="float-left">
            <ul>
              <li>
                <a href="https://www.creative-tim.com">
                  Creative Tim
                </a>
              </li>
            </ul>
          </nav>
          <div class="copyright float-right">
            <script>
              document.write(new Date().getFullYear())
            </script>made with <i class="material-icons">favorite</i> by
            <a href="https://www.creative-tim.com" target="_blank">Creative Tim</a> for a better web.
          </div>
          <!-- your footer here -->
        </div>
      </footer>
    </div>
    `,
  };
