import componentNewDriverDetail from '../components/componentNewDriverDetail.js'

export default {
    name: 'New driver detail',
    components: {componentNewDriverDetail},

    props: {
        pageParams: Object,
    },

    setup() {
        const title = 'New driver detail'

        return {title}
    },

    methods: {
        onShowDriversList() {
            this.$emit("show-drivers-list");
        },
    },

    template: `
        <div>
         <div class="content">
            <div class="container-fluid">
            {{ title }}
              <div class="row">
                <div class="col-md-12">
                  <componentNewDriverDetail @new-driver-detail="onNewDriverDetail" @show-drivers-list="onShowDriversList"></componentNewDriverDetail>
                </div>
              </div>
            </div>
          </div>
        </div>
    `,
};