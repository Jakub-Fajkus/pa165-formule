import componentLogin from '../components/componentLogin.js'

export default {
    name: 'Login',
    icon: 'login',
    showInMenu: true,

    components: {componentLogin},

    template: `
        <div>
            <componentLogin></componentLogin>
        </div>
    `,
};