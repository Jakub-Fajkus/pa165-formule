import componentCarsList from '../components/componentCarsList.js'

export default {
    name: 'Cars',
    icon: 'commute',
    components: {componentCarsList},

    setup() {        
        const title = 'Cars'
        return {title}
    },

    template: `
        <div>
            {{ title }}
            <componentCarsList></componentCarsList>
        </div>
    `,
};