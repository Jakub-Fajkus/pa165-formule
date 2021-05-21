import componentRegister from '../components/componentRegister.js'

export default {
    name: 'Register',
    icon: 'login',
    components: {componentRegister},

    setup() {
        const title = 'Register'
        return {title}
    },

    template: `
        <div>
            <componentRegister></componentRegister>
        </div>
    `,
}