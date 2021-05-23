import componentChangePassword from '../components/componentChangePassword.js'

export default {
    name: 'ChangePassword',
    icon: 'lock',
    showInMenu: true,
    components: {componentChangePassword},
    requiredRoles: ["ROLE_MANAGER", "ROLE_ENGINEER"],


    props: {
        pageParams: Object,
    },

    setup() {
        const title = 'Change Password'
        return {title}
    },

    mounted() {
        console.log(this.pageParams);
    },

    template: `
        <div>
          <div class="content">
            <div class="container-fluid">
              {{ title }} {{login}} {{pageParams.login}}
              <div>
                <componentChangePassword :login = "pageParams.login"></componentChangePassword>
              </div>
            </div>
          </div>
        </div>
    `,
}