import componentComponenAdd from '../components/componentComponentAdd.js'


export default {
    name: 'Component Add',
    components: {componentComponenAdd},

    props: {
        pageParams: Object,
    },

    setup() {
        const title = 'Add new component'

        return {title}
    },

    mounted() {
        console.log("try to add component");
    },

    template: `
        <div>
         <div class="content">
            <div class="container-fluid">
              <div class="row">
                <div class="col-md-12">
                  <componentComponenAdd></componentComponenAdd>
                </div>
              </div>
            </div>
          </div>
        </div>
    `,
};