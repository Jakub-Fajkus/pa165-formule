import componentLogin from '../components/componentLogin.js'

export default {
    name: 'Login',
    components: {componentLogin},

    setup() {        
        const title = 'Login'
        return {title}
    },

    template: `
        <div>
            {{ title }}
            <componentLogin></componentLogin>
        </div>
    `,
};