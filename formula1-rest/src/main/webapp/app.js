import homepage from './public/pages/home.js'
import * as pages from './public/pages/index.js'
import store from './store.js'

export default {
    name: 'App',
    components: Object.assign({homepage}, pages),

    data() {
        return {
            page: "home",
        }
    },

    methods: {
        tabClicked(index) {
            //todo: roles!!
            console.log("tabClicked")
            console.log("JWT:", store.$jwt);
            if (store.$jwt == null) {
                this.page = "pageLogin";
            } else {
                this.page = index;
            }
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
                    if (localStorage.getItem(key)) store[key] = localStorage.getItem(key);
                }}
            )
        })

        //url management
        watchEffect(() => {
            let urlpage = window.location.pathname.split("/").pop();

            if (store.$jwt == null) {
            }

            if (page.value == null) {page.value = urlpage}
            // if (page.value != urlpage) {const url = page.value ? page.value : './'; window.history.pushState({url: url}, '', url);                                }
            // window.onpopstate = function() {page.value = window.location.pathname.split("/").pop()};
        })
        return {page, pages, console}
    },

    template: `

        <div class="sidebar" data-color="green" data-background-color="black">
      <!--
      Tip 1: You can change the color of the sidebar using: data-color="purple | azure | green | orange | danger"

      Tip 2: you can also add an image using data-image tag
  -->

      <div class="sidebar-wrapper">
        <ul class="nav">
          <li class="nav-item">
            <a v-on:click="page = ''" class="nav-link" href="#0">
              <i class="material-icons">dashboard</i>
              <p>Dashboard</p>
            </a>
          </li>
          
          <template v-for="item, index in pages" key="item.name">
              <li class="nav-item ">
                <a @click="tabClicked(index)" class="nav-link" href="#0">
                  <i class="material-icons">{{ item.icon }}</i>
                  <p v-on:click="page = ''">{{ item.name }}</p>
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
                <a class="nav-link" href="javascript:;">
                  <i class="material-icons">notifications</i> Notifications
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
          <component :is="page || 'homepage'"></component>
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
