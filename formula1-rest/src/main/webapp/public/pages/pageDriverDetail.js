import componentDriverDetail from '../components/componentDriverDetail.js'

export default {
    name: 'Driver detail',
    components: {componentDriverDetail},

    props: {
        pageParams: Object,
    },

    setup() {
        const title = 'Driver detail'

        return {title}
    },

    mounted() {
        console.log(this.pageParams);
    },

    template: `
        <div>
         <div class="content">
            <div class="container-fluid">
              {{ title }} {{id}} {{pageParams.id}}
              <div class="row">
                <div class="col-md-4">
                  <componentDriverDetail :id="pageParams.id"></componentDriverDetail>
                </div>
              </div>
            </div>
          </div>
        </div>
    `,
};