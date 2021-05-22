import componentCarsList from '../components/componentCarsList.js'

/**
 * @author Jakub Fajkus
 */
export default {
    name: 'Cars',
    icon: 'commute',
    showInMenu: true,
    requiredRoles: ["ROLE_MANAGER"],

    components: {componentCarsList},

    methods: {
        onShowCarDetail(carId) {
            console.log("onShowCarDetail " + carId);

            this.$emit('show-car-detail', carId);
        }
    },

    template: `
        <div>
            <componentCarsList @show-car-detail="onShowCarDetail"></componentCarsList>
        </div>
    `,
};