import componentComponenAdd from '../components/componentComponentAdd.js'


export default {
    name: 'Component Add',
    requiredRoles: ["ROLE_MANAGER", "ROLE_ENGINEER"],
    components: {componentComponenAdd},

    props: {
        pageParams: Object,
    },

    methods: {
        onGoBack() {
            this.$emit('go-to-component-list');
        },
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