import componentNewUserDetail from '../components/componentNewUserDetail.js'

/**
 * @author Jiri Andrlik
 */
export default {
    name: 'New User',
    requiredRoles: ["ROLE_MANAGER"],
    components: {componentNewUserDetail},

    props: {
        pageParams: Object,
    },

    setup() {
        const title = 'New user detail'

        return {title}
    },

    methods: {
        onShowUsersList() {
            this.$emit("show-users-list");
        },
    },

    template: `
        <div>
         <div class="content">
            <div class="container-fluid">
              <div class="row">
                <div class="col-md-12">
                  <componentNewUserDetail @new-user-detail="onNewUserDetail" @show-users-list="onShowUsersList"></componentNewUserDetail>
                </div>
              </div>
            </div>
          </div>
        </div>
    `,
};