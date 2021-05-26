import componentDriversStatistics from '../components/componentDriversStatistics.js'

/**
 * @author Jakub Fajkus
 */
export default {
    name: 'Driver statistics',
    icon: 'dashboard',
    requiredRoles: ["ROLE_MANAGER"],
    showInMenu: true,
    components: {componentDriversStatistics},

    template: `
        <div>
            <componentDriversStatistics></componentDriversStatistics>
        </div>
    `,
  };