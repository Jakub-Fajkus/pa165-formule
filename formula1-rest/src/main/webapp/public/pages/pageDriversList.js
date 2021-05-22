import componentDriversList from '../components/componentDriversList.js'

/**
 * @author Karolina Hecova
 */
export default {
    name: 'Drivers',
    icon: 'accessible_forward',
    showInMenu: true,
    requiredRoles: ["ROLE_MANAGER"],

    components: {componentDriversList},

    methods: {
        onShowDriverDetail(driverId) {
            console.log("onShowDriverDetail " + driverId);

            this.$emit('show-driver-detail', driverId);
        },

        onNewDriverDetail() {
            console.log("onNewDriverDetail");

            this.$emit('new-driver-detail');
        },
    },

    template: `
        <div>
            <componentDriversList @show-driver-detail="onShowDriverDetail" @new-driver-detail="onNewDriverDetail" @show-drivers-list="onShowDriversList"></componentDriversList>
        </div>
    `,
};