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
        <div id="sidebar">
            <nav>
                <button v-on:click="page = ''">Home</button>
                <template v-for="item, index in pages" key="item.name">
                    <button @click="tabClicked(index)">
                        {{ item.name }}
                    </button>
                    
                </template>               
            </nav><hr>
        </div>
        <div id="content">
            <component :is="page || 'homepage'"></component>
        </div>
    `,
  };