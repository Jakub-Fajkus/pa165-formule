import componentLogin from '../components/componentLogin.js'

export default {
    name: 'Login',
    icon: 'login',
    showInMenu: true,

    components: {componentLogin},

    setup() {        
        const title = 'Login'
        return {title}
    },

    template: `
        <div>
            <componentLogin></componentLogin>
        </div>
    `,
};