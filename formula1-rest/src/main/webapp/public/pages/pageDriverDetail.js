import componentDriverDetail from '../components/componentDriverDetail.js'

export default {
    name: 'Driver detail',
    requiredRoles: ["ROLE_MANAGER"],
    components: {componentDriverDetail},

    props: {
        pageParams: Object,
    },

    mounted() {
        console.log(this.pageParams);
    },

    template: `
        <div>
         <div class="content">
            <div class="container-fluid">
              <div class="row">
                <div class="col-md-12">
                  <componentDriverDetail :id="pageParams.id"></componentDriverDetail>
                </div>
              </div>
            </div>
          </div>
        </div>
    `,
};