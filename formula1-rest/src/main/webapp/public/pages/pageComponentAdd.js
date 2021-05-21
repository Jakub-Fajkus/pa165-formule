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

    addComponent() {
        console.log("try to add component");
    },

    template: `
        <div>
         <div class="content">
            <div class="container-fluid">
              <div class="row">
                <div class="col-md-12">
                  <componentComponenAdd @show-component-detail="addComponent"></componentComponenAdd>
                </div>
              </div>
            </div>
          </div>
        </div>
    `,
};