import componentUserDetail from '../components/componentUserDetail.js'

/**
 * @author Jiri Andrlik
 */
export default {
    name: 'User detail',
    requiredRoles: ["ROLE_MANAGER"],
    components: {componentUserDetail},

    props: {
        pageParams: Object,
    },

    setup() {
        const title = 'User detail'

        return {title}
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
                  <componentUserDetail :id="pageParams.id"></componentUserDetail>
                </div>
              </div>
            </div>
          </div>
        </div>
    `,
};