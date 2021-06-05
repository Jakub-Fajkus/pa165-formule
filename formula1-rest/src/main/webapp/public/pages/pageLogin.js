import componentLogin from '../components/componentLogin.js'

/**
 * @author Jakub Fajkus
 */
export default {
    name: 'Login',
    icon: 'login',
    showInMenu: true,
    requiredRoles: ["ROLE_MANAGER", "ROLE_ENGINEER"],

    components: {componentLogin},

    methods: {
        onRedirectAfterLogin(userId) {
            this.$emit('redirect-after-login', userId);
        },
    },
    template: `
        <div>
            <componentLogin @redirect-after-login="onRedirectAfterLogin"></componentLogin>
        </div>
    `,
};