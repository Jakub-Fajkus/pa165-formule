import componentDriversList from '../components/componentDriversList.js'

export default {
    name: 'Drivers',
    icon: 'accessible',
    component: {componentDriversList},

    setup() {
        const title = 'Drivers'
        return {title}
    },

    methods: {
        onShowDriverDetail(driverId) {
            console.log("onShowDriverDetail " + driverId);

            this.$emit('show-driver-detail', driverId);
        }
    },

    template: `
        <div>
            {{ title }}
            <componentDriversList @show-driver-detail="onShowDriverDetail"></componentDriversList>
        </div>
    `,
};