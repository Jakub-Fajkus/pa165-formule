import componentComponenAdd from '../components/componentComponentAdd.js'
import functions from "../../functions.js";


export default {
    name: 'Component Add',
    components: {componentComponenAdd},

    props: {
        pageParams: Object,
    },

    methods: {
        onGoBack() {
            this.$emit('go-to-component-list');
        },
    },

    setup() {
        const title = 'Add new component'

        return {title}
    },

    template: `
        <div>
         <div class="content">
            <div class="container-fluid">
              <div class="row">
                <div class="col-md-12">
                  <componentComponenAdd @go-back="onGoBack"></componentComponenAdd>
                </div>
              </div>
            </div>
          </div>
        </div>
    `,
};