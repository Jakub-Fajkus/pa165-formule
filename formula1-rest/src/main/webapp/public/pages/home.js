import componentDriversStatistics from '../components/componentDriversStatistics.js'


export default {
    name: 'Driver statistics',
    icon: 'dashboard',
    showInMenu: true,
    components: {componentDriversStatistics},

    template: `
        <div>
            <componentDriversStatistics></componentDriversStatistics>

        </div>
    `,
  };