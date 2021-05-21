import componentDriversStatistics from '../components/componentDriversStatistics.js'


export default {
    name: 'Driver statistics',
    icon: 'dashboard',
    components: {componentDriversStatistics},

    setup() {
        const title = 'Driver statistics'
        return {title}
    },

    template: `
        <div>
            {{ title }}
            
            <componentDriversStatistics></componentDriversStatistics>

        </div>
    `,
  };