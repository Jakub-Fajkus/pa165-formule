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

    template: `
        <div>
            <componentLogin></componentLogin>
        </div>
    `,
};